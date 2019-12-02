package com.proxzone.cloud.event.core.service.impl;

import com.coreos.jetcd.data.KeyValue;
import com.google.inject.Inject;
import com.proxzone.cloud.event.api.common.Json;
import com.proxzone.cloud.event.core.db.MetaDataBase;
import com.proxzone.cloud.event.core.db.RelationalDatabase;
import com.proxzone.cloud.event.core.db.entry.NodeStatusEntity;
import com.proxzone.cloud.event.core.service.ManagerService;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-11-20 下午4:36
 */
public class DefaultManagerService implements ManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultManagerService.class);
    private static final String KEY_NODE_STATUS = "/node/status/";
    private MetaDataBase metaDataBase;
    private Json json;

    @Inject
    public DefaultManagerService(MetaDataBase metaDataBase, Json json) {
        this.metaDataBase = metaDataBase;
        this.json = json;

    }
    @Override
    public List<NodeStatusEntity> getNodeStatus(String nodeType) throws Exception {
        List<NodeStatusEntity> nodeStatusEntities = new ArrayList<>();
        List<KeyValue> kvs = metaDataBase.getValuesByKey(KEY_NODE_STATUS, false);
        for (KeyValue kv : kvs) {
            String key = kv.getKey().toStringUtf8();
            String value = kv.getValue().toStringUtf8();
            if (Strings.isNullOrEmpty(value)) {
                LOGGER.warn("status value is null");
                continue;
            }
            NodeStatusEntity nodeStatusEntity = json.fromJson(NodeStatusEntity.class, value);
            if (nodeStatusEntity == null) {
                LOGGER.warn("entity not format,json [{}]", value);
                continue;
            }
            if (!Strings.isNullOrEmpty(nodeType))
                if (!nodeStatusEntity.getNodeType().equals(nodeType))
                    continue;
            String[] keyInfo = key.split("/");
            nodeStatusEntity.setNodeId(keyInfo[keyInfo.length - 1]);
            nodeStatusEntities.add(nodeStatusEntity);
        }
        return nodeStatusEntities;
    }


}
