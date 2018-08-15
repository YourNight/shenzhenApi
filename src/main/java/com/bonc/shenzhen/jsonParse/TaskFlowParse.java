package com.bonc.shenzhen.jsonParse;

import com.bonc.shenzhen.restfulApi.RestfulApi;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * Created by liuhaolong on 2018/8/10.
 */
public class TaskFlowParse {

    private static Map<String,String> taskTypeId;
    static {
        taskTypeId = new HashMap<>();
        taskTypeId.put("DataFlowTaskConfig","workflowId");
        taskTypeId.put("RemoteScriptTaskConfig","remoteServerId");
        taskTypeId.put("DataCollectTaskConfig","dataCollectionTaskId");
        taskTypeId.put("FtpTaskConfig","ftpId");
    }

    public static List<JSONObject> getTaskflowParams(JSONObject jsonStr,List<JSONObject> dataFlowList){
        List<JSONObject> paramsList = new ArrayList<>();
//        //获取工作流ID
//        String workflowId = jsonStr.get("workflowId").toString();
        //获取工作流节点
        JSONObject workflowNode = JSONObject.fromObject(jsonStr.get("workflowNode"));
        //获取所有节点
        Map<String, JSONObject> nodesMap = DataFlowParse.getNodeMap(workflowNode);
//        nodesMap.forEach((a,b)->{
//            System.out.println(a+":"+b.get("metaType").toString());
//        });
        //所有节点的关系Map
        Map<String, String> relationMap = DataFlowParse.getRelationMap(workflowNode);


        //获取源表和目标表的id
        Map<String, Set> tableIds = DataFlowParse.getSourceIds(relationMap);
        Set sourceIds = tableIds.get("sourceIds");

        if (sourceIds.size()==0&&nodesMap.size()==1){
            sourceIds.addAll(nodesMap.keySet());
        }

        List<JSONObject> taskParams = new ArrayList<>();
        for (Object o : sourceIds) {
            String sourceId = o.toString();
            List<JSONObject> relationParams = getNextNode(sourceId, relationMap, dataFlowList, taskParams,nodesMap);
            paramsList.addAll(relationParams);
        }
        return paramsList;
    }

    private static List<JSONObject> getNextNode(String id , Map<String, String> relationMap , List<JSONObject> dataflowJson,List<JSONObject> taskParams,Map<String, JSONObject> nodesMap){
        for (JSONObject dataflow : dataflowJson) {
            String workflowId = dataflow.get("workflowId").toString();//数据流id

            JSONObject nodeJson = nodesMap.get(id);//当前工作流
            String metaType = nodeJson.get("metaType").toString();//工作流类型
            String typeName = "";//该类型对应的id名字
            for (String key : taskTypeId.keySet()) {
                if (metaType.equals(key)){
                    typeName = taskTypeId.get(key);
                }
            }
            String taskId=nodeJson.get(typeName)!=null?nodeJson.get(typeName).toString():"";
            if (taskId.equals(workflowId)){
                List<JSONObject> postParams = DataFlowParse.getPostParams(dataflow, RestfulApi.databaseIdNameMap, RestfulApi.tableCodes);
                taskParams.addAll(postParams);
            }
        }
        if (relationMap.get(id)!=null){
            getNextNode(relationMap.get(id),relationMap,dataflowJson,taskParams,nodesMap);
        }
        return taskParams;
    }
}
