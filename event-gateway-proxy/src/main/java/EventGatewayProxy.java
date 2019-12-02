import com.google.inject.Injector;
import com.proxzone.cloud.event.api.common.ApplicationArgs;
import com.proxzone.cloud.event.api.common.ApplicationInjector;
import com.proxzone.cloud.event.api.common.EnvironmentModule;
import com.proxzone.cloud.event.api.common.Json;
import com.proxzone.cloud.event.core.db.DatabaseModule;
import com.proxzone.cloud.event.core.db.MessagesBus;
import com.proxzone.cloud.event.core.db.MetaDataBase;
import com.proxzone.cloud.event.core.db.entry.EventProxyReplyEntity;
import com.proxzone.cloud.event.core.db.entry.RuntimeNodeStatusEntry;
import com.proxzone.cloud.event.core.service.EventServiceProxy;
import com.proxzone.cloud.event.core.service.ServicesModule;
import io.nats.client.Message;
import io.nats.client.MessageHandler;

import java.io.IOException;
import java.util.Date;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-5-5 上午11:38
 */
public class EventGatewayProxy {
    public static void main(String[] args) throws Exception {
        args = new String[]{
                "--mysql-addr","127.0.0.1",
                "--etcd-addr", "http://192.168.139.245:2379",
                "--nats-addr", "nats://192.168.139.245:4222",
                "--bind-address", "192.168.139.208",
        };
        ApplicationInjector applicationInjector = ApplicationInjector.initInjector(
                new EnvironmentModule(args),
                new DatabaseModule(),
                new ServicesModule()
        );

        Injector injector = applicationInjector.getGuiceInjector();
        putNodeStatus(injector);
        putNats(injector);
    }


    private static void putNodeStatus(Injector injector) throws Exception {
        MetaDataBase metaDataBase = injector.getInstance(MetaDataBase.class);
        Json json = injector.getInstance(Json.class);
        ApplicationArgs applicationArgs = injector.getInstance(ApplicationArgs.class);
        long leaseId = metaDataBase.getNodeLeaseId();
        String key = "/node/status/" + leaseId;
        RuntimeNodeStatusEntry nodeStatusEntry = new RuntimeNodeStatusEntry();
        nodeStatusEntry.setAddress(applicationArgs.getBindAddress());
        nodeStatusEntry.setUpTime(new Date());
        nodeStatusEntry.setHttpPort(applicationArgs.getHttpPort());
        metaDataBase.putKeyValueByDefaultLease(key, json.toJson(nodeStatusEntry));
    }
    private static void putNats(Injector injector) throws Exception {
        EventServiceProxy proxyService=injector.getInstance(EventServiceProxy.class);
        MetaDataBase metaDataBase = injector.getInstance(MetaDataBase.class);
        MessagesBus messageBus=injector.getInstance(MessagesBus.class);
        long leaseId = metaDataBase.getNodeLeaseId();
        System.out.println("subject---------------------------"+leaseId);
        messageBus.subscibe("" + leaseId, new MessageHandler() {
            @Override
            public void onMessage(Message msg) {
                try {
                    EventProxyReplyEntity response=proxyService.handleRequest(new String(msg.getData()));
                    messageBus.publish(msg.getReplyTo(), response);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
