import com.google.inject.Injector;
import com.proxzone.cloud.event.api.common.ApplicationArgs;
import com.proxzone.cloud.event.api.common.ApplicationInjector;
import com.proxzone.cloud.event.api.common.EnvironmentModule;
import com.proxzone.cloud.event.api.common.Json;
import com.proxzone.cloud.event.api.http.HTTPAPIServerModule;
import com.proxzone.cloud.event.core.db.DatabaseModule;
import com.proxzone.cloud.event.core.db.MetaDataBase;
import com.proxzone.cloud.event.core.db.entity.JobEntity;
import com.proxzone.cloud.event.core.db.entry.RuntimeNodeStatusEntry;
import com.proxzone.cloud.event.core.service.DynamicJobService;
import com.proxzone.cloud.event.core.service.ServicesModule;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Set;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-5-5 上午11:38
 */
public class TimingTasksMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimingTasksMain.class);
    public static void main(String[] args) throws Exception {
        args = new String[]{
                "--http-port","9016",
                "--mysql-addr","127.0.0.1",
                "--etcd-addr", "http://192.168.139.245:2379",
                "--nats-addr", "nats://192.168.139.245:4222",
                "--bind-address", "192.168.139.208",
        };
        ApplicationInjector applicationInjector = ApplicationInjector.initInjector(
                new EnvironmentModule(args),
                new HTTPAPIServerModule(),
                new DatabaseModule(),
                new ServicesModule()
        );

        Injector injector = applicationInjector.getGuiceInjector();
        putNodeStatus(injector);
        startTask(injector);
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
    private static void startTask(Injector injector) throws Exception {
      DynamicJobService jobService = injector.getInstance(DynamicJobService.class);
      StdSchedulerFactory schedulerFactory=new StdSchedulerFactory("quartz.properties");
            synchronized (LOGGER) {                                                         //只允许一个线程进入操作
                Scheduler scheduler = schedulerFactory.getScheduler();
               Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
               scheduler.pauseJobs(GroupMatcher.anyGroup());                               //暂停所有JOB
                for (JobKey jobKey : set) {                                                 //删除从数据库中注册的所有JOB
                    scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                    scheduler.deleteJob(jobKey);
                }
        for (JobEntity job : jobService.getAll()) {                               //从数据库中注册的所有JOB
            LOGGER.info("Job register name : {} , group : {} , cron : {}", job.getName(), job.getGrop(), job.getCron());
            JobDataMap map = jobService.getJobDataMap(job);
            JobKey jobKey = jobService.getJobKey(job);
            JobDetail jobDetail = jobService.getJobDetail(jobKey, job.getDescription(), map);
            if (job.getStatus().equals("OPEN")) {
                scheduler.scheduleJob(jobDetail, jobService.getTrigger(job));
                scheduler.start();
            }
            else
                LOGGER.info("Job jump name : {} , Because {} status is {}", job.getName(), job.getName(), job.getStatus());
        }

    }
    }

}

