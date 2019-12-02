package com.proxzone.cloud.event.api.common;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-5-5 上午11:56
 */
public class ApplicationArgs {
    private Boolean quartz = true; //程序模式，api模式为跟视综交互的API，file模式为视频文件的下载
    private String bindAddress;
    private boolean isEnableHTTPS = true; //是否激活HTTPS
    private int httpPort = 8070; //HTTP端口，默认8080
    private int httpMaxThread = 1000;
    private String mysqlAddress;
    private int mysqlPort = 3306;
    private String mysqlUser = "root";
    private String mysqlPwd = "123456";
    private String dbName = "quartz_test";


    private String etcdUrls;

    private String natsUrls;

    public Boolean getQuartz() {
        return quartz;
    }

    public void setQuartz(Boolean quartz) {
        this.quartz = quartz;
    }

    public String getBindAddress() {
        return bindAddress;
    }

    public void setBindAddress(String bindAddress) {
        this.bindAddress = bindAddress;
    }

    public boolean isEnableHTTPS() {
        return isEnableHTTPS;
    }

    public void setEnableHTTPS(boolean enableHTTPS) {
        isEnableHTTPS = enableHTTPS;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public int getHttpMaxThread() {
        return httpMaxThread;
    }

    public void setHttpMaxThread(int httpMaxThread) {
        this.httpMaxThread = httpMaxThread;
    }

    public String getEtcdUrls() {
        return etcdUrls;
    }

    public void setEtcdUrls(String etcdUrls) {
        this.etcdUrls = etcdUrls;
    }

    public String getNatsUrls() {
        return natsUrls;
    }

    public void setNatsUrls(String natsUrls) {
        this.natsUrls = natsUrls;
    }

    public String getMysqlAddress() {
        return mysqlAddress;
    }

    public void setMysqlAddress(String mysqlAddress) {
        this.mysqlAddress = mysqlAddress;
    }

    public int getMysqlPort() {
        return mysqlPort;
    }

    public void setMysqlPort(int mysqlPort) {
        this.mysqlPort = mysqlPort;
    }

    public String getMysqlUser() {
        return mysqlUser;
    }

    public void setMysqlUser(String mysqlUser) {
        this.mysqlUser = mysqlUser;
    }

    public String getMysqlPwd() {
        return mysqlPwd;
    }

    public void setMysqlPwd(String mysqlPwd) {
        this.mysqlPwd = mysqlPwd;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
