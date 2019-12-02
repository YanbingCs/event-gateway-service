package com.proxzone.cloud.event.api.common;

import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/8/8
 */
public class DefaultApplicationArgsBuilder implements ApplicationArgsBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApplicationArgsBuilder.class);
    private String[] args;

    @Inject
    DefaultApplicationArgsBuilder(String[] args) {
        this.args = args;
    }

    @Override
    public ApplicationArgs build() throws Exception {
        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse(getOptions(), args);
        ApplicationArgs applicationArgs = new ApplicationArgs();
        checkMiddleware(line, applicationArgs);
        checkHttpArgs(line, applicationArgs);
        checkOthers(line, applicationArgs);
        //TODO 需要完成其它参数的检查以及配置
        return applicationArgs;
    }

    private void checkMiddleware(CommandLine line, ApplicationArgs applicationArgs) throws Exception {
        if (!line.hasOption("dbaddr")) {
            throw new Exception("parameter [--mysql-addr] is not set");
        }
        applicationArgs.setMysqlAddress(line.getOptionValue("mysql-addr"));
        if (!line.hasOption("dbport")) {
            LOGGER.info("parameter [--db-port] is not set,set to default [{}]", applicationArgs.getMysqlPort());
        }
        else {
            applicationArgs.setMysqlPort(Integer.valueOf(line.getOptionValue("dbport")));
            if (applicationArgs.getMysqlPort() <= 0) {
                throw new Exception("bad [--http-port] value [" + applicationArgs.getMysqlPort() + "]");
            }
        }


        if (!line.hasOption("etcdaddr")) {
            throw new Exception("parameter [--etcd-addr] is not set");
        }
        applicationArgs.setEtcdUrls(line.getOptionValue("etcd-addr"));
        if (!line.hasOption("natsaddr")) {
            throw new Exception("parameter [--nats-addr] is not set");
        }
        applicationArgs.setNatsUrls(line.getOptionValue("nats-addr"));
    }

    private void checkOthers(CommandLine line, ApplicationArgs applicationArgs) throws Exception {

        if (!line.hasOption("bindaddr"))
            throw new Exception("parameter [--bind-address] is not set");
        applicationArgs.setBindAddress(line.getOptionValue("bind-address"));
    }

    private void checkHttpArgs(CommandLine line, ApplicationArgs applicationArgs) throws Exception {
        if (line.hasOption("https")) {
            applicationArgs.setEnableHTTPS(true);
            LOGGER.info("is enable https");
        } else {
            applicationArgs.setEnableHTTPS(false);
            LOGGER.info("not enable https");
        }
        if (!line.hasOption("hport")) {
            LOGGER.info("parameter [--http-port] is not set,set to default [{}]", applicationArgs.getHttpPort());
        } else {
            applicationArgs.setHttpPort(Integer.valueOf(line.getOptionValue("hport")));
            if (applicationArgs.getHttpPort() <= 0) {
                throw new Exception("bad [--http-port] value [" + applicationArgs.getHttpPort() + "]");
            }
        }
    }

    private Options getOptions() {
        Options options = new Options();
        options.addOption("https", "enable-https", false, "是否激活HTTPS");
        options.addOption("hport", "http-port", true, "HTTP端口");

        options.addOption("dbaddr", "mysql-addr", true, "MySQL地址");
        options.addOption("dbport", "db-port", true, "MySQL端口");

        options.addOption("etcdaddr", "etcd-addr", true, "ETCD地址");
        options.addOption("natsaddr", "nats-addr", true, "NATS地址");
        options.addOption("mode", "app-mode", true, "启动模式");

        options.addOption("bindaddr", "bind-address", true, "服务器绑定的API地址");
        return options;
    }
}
