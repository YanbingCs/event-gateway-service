package com.proxzone.cloud.event.core.service.impl;
import com.google.inject.Inject;
import com.proxzone.cloud.event.api.common.Json;
import com.proxzone.cloud.event.util.JsonUtil;
import com.proxzone.cloud.event.core.db.RelationalDatabase;
import com.proxzone.cloud.event.core.db.entry.EventProxyReplyEntity;
import com.proxzone.cloud.event.core.db.entry.EventProxyRequestEntity;
import com.proxzone.cloud.event.core.service.EventServiceProxy;
import com.proxzone.cloud.event.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultEventServiceProxy implements EventServiceProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEventServiceProxy.class);
    private Json json;
    private RelationalDatabase relationalDatabase;

    @Inject
    public DefaultEventServiceProxy(Json json,RelationalDatabase relationalDatabase) {
        this.json = json;
        this.relationalDatabase=relationalDatabase;

    }


    @Override
    public EventProxyReplyEntity handleRequest(String gatewayBody) throws Exception {
        EventProxyRequestEntity sessionRequest= JsonUtil.fromJson(EventProxyRequestEntity.class,gatewayBody);
        String serviceUrl=relationalDatabase.getServiceInfo(sessionRequest.getServiceId()).getServiceUrl();
        String vmParam = relationalDatabase.getServiceInfo(sessionRequest.getServiceId()).getVmParam();
        String parameter=relationalDatabase.getServiceInfo(sessionRequest.getServiceId()).getParameter();
        EventProxyReplyEntity replyEntity=new EventProxyReplyEntity();
        long startTime = System.currentTimeMillis();
        if (!StringUtils.isEmpty(serviceUrl)) {
            File service = new File(serviceUrl);
            if (service.exists()) {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.directory(service.getParentFile());
                List<String> commands = new ArrayList<>();
                commands.add("java");
                if (!StringUtils.isEmpty(vmParam)) commands.add(vmParam);
                commands.add("-jar");
                commands.add(serviceUrl);
                if (!StringUtils.isEmpty(parameter)) commands.add(parameter);
                processBuilder.command(commands);
                LOGGER.info("Running Job details as follows >>>>>>>>>>>>>>>>>>>>: ");
                LOGGER.info("Running Job commands : {}  ", StringUtil.getListString(commands));
                try {
                Process process = processBuilder.start();
                logProcess(process.getInputStream(), process.getErrorStream());
                replyEntity.setResult("true");
                } catch (IOException e) {
                  replyEntity.setResult("false");
                  replyEntity.setContent("");
                }
            } else replyEntity.setContent("Job Jar not found >>  " + serviceUrl);
                LOGGER.info("Job Jar not found >>  " + serviceUrl);
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info(">>>>>>>>>>>>> Running Job has been completed , cost time : {}ms\n ", (endTime - startTime));
        return replyEntity;
    }
 //记录Job执行内容
    private void logProcess(InputStream inputStream, InputStream errorStream) throws IOException {
        String inputLine;
        String errorLine;
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        while (Objects.nonNull(inputLine = inputReader.readLine())) LOGGER.info(inputLine);
        while (Objects.nonNull(errorLine = errorReader.readLine())) LOGGER.error(errorLine);
    }
}
