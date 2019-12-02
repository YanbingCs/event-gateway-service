package com.proxzone.cloud.event.core.db;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.kv.PutResponse;
import com.coreos.jetcd.lease.LeaseGrantResponse;
import com.coreos.jetcd.options.DeleteOption;
import com.coreos.jetcd.options.GetOption;
import com.coreos.jetcd.options.PutOption;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proxzone.cloud.event.api.common.ApplicationArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-7-3 下午4:25
 */
@Singleton
public class DefaultEtcdMetaDataBase implements MetaDataBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEtcdMetaDataBase.class);
    private static final int DEFAULT_LEASEID_TTL = 3;
    private ApplicationArgs applicationArgs;
    private Client client;
    private long leaseId;

    @Inject
    public DefaultEtcdMetaDataBase(ApplicationArgs applicationArgs) throws Exception {
        this.applicationArgs = applicationArgs;
        init();
    }

    private void init() throws Exception {
        String[] urls = applicationArgs.getEtcdUrls().split(",");
        client = Client.builder().endpoints(urls).build();
        leaseId = grantAndKeepAliveLease(DEFAULT_LEASEID_TTL);
    }

    @Override
    public void keepaliveLeaseId(long leaseId) {
        client.getLeaseClient().keepAliveOnce(leaseId);
    }

    @Override
    public boolean existKey(String key, boolean isPrefix) throws Exception {
        ByteSequence keyByteSequence = ByteSequence.fromString(key);
        GetOption.Builder builder = GetOption.newBuilder().withKeysOnly(true);
        if (isPrefix)
            builder = builder.withPrefix(keyByteSequence);
        GetOption option = builder.build();
        GetResponse response = client.getKVClient().get(keyByteSequence, option).get();
        List<KeyValue> keyValues = response.getKvs();
        return keyValues != null && keyValues.size() != 0;
    }

    @Override
    public boolean removeKey(String key) throws Exception {
        ByteSequence keyByteSequence = ByteSequence.fromString(key);
        DeleteOption deleteOption = DeleteOption.newBuilder().build();
        client.getKVClient().delete(keyByteSequence, deleteOption).get();
        return true;
    }

    @Override
    public void putKeyValueByLease(long leaseId, String key, String value) throws Exception {
        ByteSequence testPutKey = ByteSequence.fromString(key);
        ByteSequence testPutValue = ByteSequence.fromString(value);
        PutOption option = PutOption.newBuilder().withLeaseId(leaseId).build();
        CompletableFuture<PutResponse> putFuture = client.getKVClient().put(testPutKey, testPutValue, option);
        putFuture.get();
        LOGGER.debug("put key [{}] value [{}]", key, value);
    }

    @Override
    public void putKeyValueByDefaultLease(String key, String value) throws Exception {
        putKeyValueByLease(leaseId, key, value);
    }

    @Override
    public long grantAndKeepAliveLease(long ttlSecond) throws Exception {
        long leaseId = granLease(ttlSecond);
        client.getLeaseClient().keepAlive(leaseId);
        LOGGER.info("keep-alive middleware lease id [{}]", leaseId);
        return leaseId;
    }

    @Override
    public long granLease(long ttlSecond) throws Exception {
        CompletableFuture<LeaseGrantResponse> grantFuture = client.getLeaseClient().grant(ttlSecond);
        long leaseId;
        leaseId = grantFuture.get().getID();
        LOGGER.info("grant middleware lease id [{}]", leaseId);
        return leaseId;
    }

    @Override
    public long getKeyCount(String key, boolean prefix) {
        ByteSequence keyByteSequence = ByteSequence.fromString(key);
        GetOption.Builder optionBuilder = GetOption.newBuilder()
                .withKeysOnly(true);
        if (prefix)
            optionBuilder = optionBuilder.withPrefix(keyByteSequence);
        GetOption options = optionBuilder.build();
        try {
            GetResponse response = client.getKVClient().get(keyByteSequence, options).get();
            return response.getKvs().size();
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public String getFirstValueByKey(String key) {
        ByteSequence keyByteSequence = ByteSequence.fromString(key);
        GetOption option = GetOption.DEFAULT;
        try {
            GetResponse response = client.getKVClient().get(keyByteSequence, option).get();
            List<KeyValue> keyValues = response.getKvs();
            if (keyValues == null || keyValues.size() == 0)
                return null;
            int idxFirst = 0;
            return keyValues.get(idxFirst).getValue().toStringUtf8();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<KeyValue> getValuesByKey(String key, boolean keysOnly) {
        ByteSequence keyByteSequence = ByteSequence.fromString(key);
        GetOption option = GetOption.newBuilder()
                .withKeysOnly(keysOnly)
                .withPrefix(keyByteSequence)
                .build();
        try {
            GetResponse response = client.getKVClient().get(keyByteSequence, option).get();
            return response.getKvs();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteByKey(String key, boolean isPrefix) {
        ByteSequence keyByteSequence = ByteSequence.fromString(key);
        DeleteOption.Builder optionBuilder = DeleteOption.newBuilder();
        if (isPrefix)
            optionBuilder.withPrefix(keyByteSequence);
        try {
            client.getKVClient().delete(
                    keyByteSequence, optionBuilder.build()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getNodeLeaseId() {
        return leaseId;
    }
}
