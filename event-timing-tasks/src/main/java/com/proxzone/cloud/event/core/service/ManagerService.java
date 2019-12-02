package com.proxzone.cloud.event.core.service;

import com.proxzone.cloud.event.core.db.entry.NodeStatusEntity;

import java.util.List;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 18-11-20 下午4:26
 */
public interface ManagerService {
    /**
     * 获取节点状态
     *
     * @param nodeType   节点类型,如果为空，获取全部类型节点
     * @return entities 节点信息
     * @throws Exception 节点信息获取异常
     */
    List<NodeStatusEntity> getNodeStatus(String nodeType) throws Exception;



}
