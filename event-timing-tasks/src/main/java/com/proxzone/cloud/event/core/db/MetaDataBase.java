package com.proxzone.cloud.event.core.db;

import com.coreos.jetcd.data.KeyValue;

import java.util.List;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-7-3 下午4:24
 */
public interface MetaDataBase {
    /**
     * 保活leaseID
     *
     * @param leaseID 申请的leaseID
     */
    void keepaliveLeaseId(long leaseID);

    /**
     * 检查key是否存在
     *
     * @param key      检查的key
     * @param isPrefix 是否通配
     * @return bool 是否存在
     * @throws Exception key检查异常
     */
    boolean existKey(String key, boolean isPrefix) throws Exception;

    /**
     * 删除key
     *
     * @param key 要删除的key
     * @return bool 是否删除成功
     * @throws Exception key删除异常
     */
    boolean removeKey(String key) throws Exception;

    /**
     * 使用leaseID保存键值对
     *
     * @param leaseId 申请的leaseID
     * @param key     保存的key
     * @param value   保存的值
     * @throws Exception 键值对保存异常
     */
    void putKeyValueByLease(long leaseId, String key, String value) throws Exception;

    /**
     * 使用默认leaseID保存键值对
     *
     * @param key   保存的key
     * @param value 保存的值
     * @throws Exception 键值对保存异常
     */
    void putKeyValueByDefaultLease(String key, String value) throws Exception;

    /**
     * 申请并且维持leaseID的生命周期
     *
     * @param ttlSecond leaseID生命周期（秒）
     * @return leaseID 申请成功的leaseID
     * @throws Exception leaseID申请异常
     */
    long grantAndKeepAliveLease(long ttlSecond) throws Exception;

    /**
     * 申请leaseID
     *
     * @param ttlSecond leaseID生命周期（秒）
     * @return 申请成功的leaseID
     * @throws Exception leaseID申请异常
     */
    long granLease(long ttlSecond) throws Exception;

    /**
     * 获取指定key下的子key个数（包含自己）
     *
     * @param key    获取的根key
     * @param prefix 是否获取子key
     * @return integer key的个数
     */
    long getKeyCount(String key, boolean prefix);

    /**
     * @param key
     * @return
     */
    String getFirstValueByKey(String key);

    /**
     * @param key
     * @param keysOnly
     * @return
     */
    List<KeyValue> getValuesByKey(String key, boolean keysOnly);

    /**
     * @param key
     * @param isPrefix
     */
    void deleteByKey(String key, boolean isPrefix);

    /**
     * 获取节点的leasID
     *
     * @return leaseID
     */
    long getNodeLeaseId();
}
