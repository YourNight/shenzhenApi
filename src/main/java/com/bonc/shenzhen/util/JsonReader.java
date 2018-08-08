package com.bonc.shenzhen.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.*;

/**
 * Created by liuhaolong on 2018/8/1.
 */
public class JsonReader {


    @Value("${config.entityTableCodeUrl}")
    static String entityTableCodeUrl;


    public static List<JSONObject> getPostParams(JSONObject jsonStr,JSONArray dataSource,JSONArray dataCollection){
        List<JSONObject> paramsList = new ArrayList<>();
        //获取工作流节点
        JSONObject workflowNode = JSONObject.fromObject(jsonStr.get("workflowNode"));
        //获取所有节点
        Map<String, JSONObject> nodesMap = getNodeMap(workflowNode);
        //所有节点的关系Map
        Map<String, String> relationMap = getRelationMap(workflowNode);
        //获取源表和目标表的id
        Map<String, Set> tableIds = getTableIds(relationMap);
        Set sourceTablesId = tableIds.get("sourceTablesId");
        Set targetTablesId = tableIds.get("targetTablesId");
        String targetId = targetTablesId.toArray()[0].toString();
        //对目标源表的列名编号  列名：integer
        Map<String, String> enumMap = getEnumMap(targetId, nodesMap);


        System.out.println("源表id："+sourceTablesId);
        System.out.println("目标表id："+targetTablesId);
//        System.out.println(map);
//        getSourceIdsAndResultId(map);
//        System.out.println("所有节点的map：");
        nodesMap.forEach((a,b)->{
            System.out.println(a+":"+b);
        });
//        System.out.println(nodesMap.get("83"));
//        System.out.println(JSONObject.fromObject(enumMap));




        JSONObject targetJson = nodesMap.get(targetId);
        String targetTableName = targetJson.get("modelName").toString();
        String targetDatasourceId = targetJson.get("dataSourceId").toString();
        String targetSchema = getSchema(dataCollection, targetTableName, targetDatasourceId);
        String targetDatasourceCode = getResourceCode(dataSource, targetDatasourceId);
        // Todo  源表和目标表
//        JSONObject sourceTableCodes = getSourceTableCode(nodesMap, sourceTablesId,dataSource,dataCollection);
//        JSONObject targetTableCode = getTargetTableCode(targetJson,dataSource,dataCollection);

        for (Object o : sourceTablesId) {
            Map<String, Object> params = new HashMap<>();
            String id = o.toString();
            JSONObject tableMetaData = nodesMap.get(id);
            String dataSourceId = tableMetaData.getString("dataSourceId");//华为数据库资源id   用来查询数据库名和schema

            // TODO 加入目标表id

            params.put("desEntityId",targetTableCode.get(targetDatasourceCode+"-"+targetSchema+"-"+targetTableName).toString());
            params.put("desEntityCode",targetTableName);
            // todo 加入源表id
            String tableName = tableMetaData.get("modelName").toString();//当前表名
            String schema = getSchema(dataCollection, tableName, dataSourceId);//源表当前schema
            String resourceCode = getResourceCode(dataSource, tableName);

            params.put("srcEntityId",sourceTableCodes.get(resourceCode+"-"+schema+"-"+tableName).toString());
            params.put("srcEntityCode",tableName);
            params.put("systemId","");
            params.put("processId","");
            params.put("relationType",0);
            params.put("tenantId","tenantId");
            List<Map> list = new ArrayList<>();
            list = getMetaRelDetails(id, relationMap, nodesMap, enumMap, list);
            params.put("metaRelDetails",list);
            paramsList.add(JSONObject.fromObject(params));
            System.out.println(JSONObject.fromObject(params));
        }
        return  paramsList;

    }



    private static String getSchema(JSONArray dataCollection, String tableName, String dataSourceId) {
        String schema = "";
        for (Object o : dataCollection) {
            JSONObject data = JSONObject.fromObject(o);
            if (dataSourceId.equals(data.get("sourceDataSourceId").toString())&&(tableName.equals(data.get("targetTable").toString())||tableName.equals(data.get("sourceTable").toString()))){
                schema = data.get("sourceTableSchema").toString();
                return schema;
            }
        }
        return schema;
    }


    private static String getResourceCode(JSONArray dataSource, String dataSourceId) {
        String dataSourceName = "";
        for (Object ds : dataSource) {
            JSONObject database = JSONObject.fromObject(ds);
            if (database.containsKey(dataSourceId)){
                dataSourceName = database.get(dataSourceId).toString();
                return dataSourceName;
            }
        }
        return dataSourceName;
    }

    /**
     * 获取目标表在数据治理平台的id
     * @param targetJson
     * @return
     */
    private static JSONObject getTargetTableCode( JSONObject targetJson,JSONArray dataSource,JSONArray dataCollection) {
        List<Map> paramsList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        String targetTableName = targetJson.get("modelName").toString();
        String dataSourceId = targetJson.get("dataSourceId").toString();
        String dataSourceName = getResourceCode(dataSource, dataSourceId);
        String schema = getSchema(dataCollection, targetTableName, dataSourceId);
        params.put("tableCode",targetTableName);
        params.put("schema",schema);
        params.put("resourceCode",dataSourceName);
        paramsList.add(params);
        JSONObject targetTableCode = null;
        try {
            String targetTableCodeStr = Httppost.doPost(entityTableCodeUrl, JSONArray.fromObject(paramsList).toString());
            targetTableCode = JSONObject.fromObject(targetTableCodeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetTableCode;
    }

    /**
     * 获取源表在治理平台的id集合
     * @param nodesMap
     * @param sourceTablesId
     * @return
     */
    private static JSONObject getSourceTableCode(Map<String, JSONObject> nodesMap, Set sourceTablesId,JSONArray dataSource,JSONArray dataCollection) {
        List<Map> paramsList = new ArrayList<>();
        for (Object o : sourceTablesId) {
            Map<String, Object> params = new HashMap<>();
            String id = o.toString();
            JSONObject tableJson = nodesMap.get(id);

            String sourceTableName = tableJson.get("modelName").toString();
            String dataSourceId = tableJson.get("dataSourceId").toString();
            String dataSourceName = getResourceCode(dataSource, dataSourceId);
            String schema = getSchema(dataCollection, sourceTableName, dataSourceId);

            params.put("tableCode",sourceTableName);
            params.put("schema",schema);
            params.put("resourceCode",dataSourceName);
            paramsList.add(params);
        }
        JSONObject sourceTableCodes = null;
        try {
            String sourceTableCodesStr = Httppost.doPost(entityTableCodeUrl, JSONArray.fromObject(paramsList).toString());
            sourceTableCodes = JSONObject.fromObject(sourceTableCodesStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sourceTableCodes;
    }



    /**
     * 获取metaRelDetails
     * @param id sourceId
     * @param relationMap 存放节点关系的map
     * @param nodeMap 存放所有节点的map
     * @param enumMap 目标表的column：id  的map
     * @param columnList 存放所有列处理的List
     * @return List<Map> 返回metaRelDetails
     */
    private static List<Map> getMetaRelDetails(String id,Map<String,String> relationMap,Map<String,JSONObject> nodeMap ,Map<String,String> enumMap,List<Map> columnList){
        if (columnList==null){
            columnList = new ArrayList<Map>();
        }
        JSONObject metadata = nodeMap.get(id);
        Object exp = metadata.get("expression");//metadata下可能有expression
        String expr = "";
        if (exp!=null&&!"".equals(exp.toString())){
            JSONObject expression =JSONObject.fromObject(exp);
            expr = expression.get("expr").toString();
        }

        if (metadata.containsKey("columns")) {
            JSONArray columns = JSONArray.fromObject(metadata.get("columns"));//获取所有column
            if (columnList.size() > 0) {//如果columnList已经有数据，即column已存入
                for (Object columnObj : columns) {//遍历
                    JSONObject column = JSONObject.fromObject(columnObj);
                    for (Map map : columnList) {
                        String desColumn = map.get("desColumn").toString();//前一个节点的outputname
                        if (desColumn.equals(column.get("inputName")!=null?column.get("inputName").toString().toUpperCase():"")) {//如果前一个节点的outputname和此次的inputname相同
//                            map.put("desColumn", column.getString("outputName"));//重新将desColumn设置为此次的outputname
                            Object config = column.get("expressionConfig");//获取表达式对象
                            Map calcRule = (Map) map.get("calcRule");
                            if (config != null) {//表达式对象不为空
                                JSONObject expressionConfig = JSONObject.fromObject(config);
                                String expression = expressionConfig.get("expr").toString();//获取表达式
                                expression = expression.equals(column.getString("inputName")) ? "" : expression;//如果表达式和inputname一样则置为空
                                calcRule.put("value", calcRule.get("value").toString() +" \n "+ expression);
                            }
                            if (expr.contains(column.getString("inputName"))) {
                                calcRule.put("value", calcRule.get("value").toString() + expr);
                            }
                        }
                    }
                }
            } else {//columnList为空
                for (Object columnObj : columns) {//遍历
                    JSONObject column = JSONObject.fromObject(columnObj);
                    Map<String, Object> tempMap = new HashMap<>();
                    String inputName = column.get("inputName").toString().toUpperCase();
                    tempMap.put("srcColumn", inputName);//源列名
                    tempMap.put("desColumn", column.get("outputName").toString().toUpperCase());//目标列名
                    tempMap.put("ruleType", 0);
                    Map<String, Object> rule = new HashMap<>();
                    rule.put("id", enumMap.get(inputName) != null ? enumMap.get(inputName) : "");//判断是否在目标table中存在该字段
                    Object config = column.get("expressionConfig");
                    if (config != null) {
                        JSONObject expressionConfig = JSONObject.fromObject(config);
                        String expression = expressionConfig.get("expr").toString();
                        expression = expression.equals(inputName) ? "" : expression;
                        rule.put("value", expression);
                    } else if (expr.contains(inputName)) {
                        rule.put("value", expr);
                    } else {
                        rule.put("value", "");
                    }
                    rule.put("desc", "");
                    rule.put("breifSql", "");
                    tempMap.put("calcRule", rule);

                    if(enumMap.get(inputName) != null){//如果存在该字段，则添加
                        columnList.add(tempMap);
                    }
                }
            }
        }

        if (relationMap.get(id)!=null){//如果有下一个节点则循环
            getMetaRelDetails(relationMap.get(id),relationMap,nodeMap,enumMap,columnList);
        }else {
            return columnList;
        }
        return columnList;
    }

    /**
     * 获取所有节点的关系
     * @param workflowNode 工作流节点
     * @return
     */
    private static Map<String, String> getRelationMap(JSONObject workflowNode){
        //获取节点关系
        JSONArray edges = JSONArray.fromObject(workflowNode.get("edges"));
        //  将节点关系存到map中
        Map<String, String> relationMap = new HashMap<>();
        for (Object edgeStr : edges) {
            //节点关系
            JSONObject edge = JSONObject.fromObject(edgeStr);
            String sourceId = edge.get("source").toString();
            String targetId = edge.get("target").toString();
            relationMap.put(sourceId,targetId);
        }
        return relationMap;
    }

    /**
     * 获取所有节点
     * @param workflowNode 工作流节点
     * @return
     */
    private static Map<String,JSONObject> getNodeMap(JSONObject workflowNode){
        JSONArray nodesList = JSONArray.fromObject(workflowNode.get("nodes"));//所有节点存放的List
        Map<String,JSONObject> nodesMap = new HashMap<>();//将节点List转为节点Map,便于使用get(id),key为id
        for (Object nodeStr : nodesList) {
            JSONObject node = JSONObject.fromObject(nodeStr);
            String id = node.get("id").toString();
            JSONObject metadata = JSONObject.fromObject(node.get("metadata"));
            nodesMap.put(id,metadata);
        }
        return nodesMap;
    }

    /**
     * 获取目标表的所有column：id
     * @param id 目标表的id
     * @param nodeMap 所有节点map
     * @return
     */
    private static Map<String,String> getEnumMap(String id,Map<String,JSONObject> nodeMap){
        Map<String, String> enumMap = new HashMap<>();
        JSONObject metadata = nodeMap.get(id);
        JSONArray columns = JSONArray.fromObject(metadata.get("columns"));
        int i= 1;
        for (Object columnObj : columns) {
            JSONObject column = JSONObject.fromObject(columnObj);
            String columnName = column.get("outputName").toString().toUpperCase();
            enumMap.put(columnName,i+"");
            i++;
        }
        return enumMap;
    }

    /**
     * 获取源表和目标标的id
     * @param relationMap
     * @return
     */
    private static Map<String, Set> getTableIds(Map<String, String> relationMap){
        Map<String, Set> tablesMap = new HashMap<>();
        Set<String> sourceIds = null;// 存放所有sourceid
        Set<String> targetIds = new HashSet<>();// 存放所有targetid
        System.out.println("链表展示节点："+relationMap);
        sourceIds = relationMap.keySet();//current节点id
        System.out.println("所有可以作为起始节点的id："+sourceIds);
        targetIds.addAll(relationMap.values());//next节点id
        System.out.println("所有可以作为后续节点的id："+targetIds);

        HashSet set1 = new HashSet<>();
        set1.addAll(sourceIds);
        HashSet set2 = new HashSet<>();
        set2.addAll(targetIds);
        Set sourceTablesId = getDifferent(set1, set2);//源表id
        Set targetTablesId = getDifferent(targetIds, sourceIds);//目标表id
        tablesMap.put("sourceTablesId",sourceTablesId);
        tablesMap.put("targetTablesId",targetTablesId);
        return tablesMap;
    }


    /**
     * baseSet中有而delSet中没有的数据
     * @param baseSet 源Set
     * @param delSet 需要删除的数据
     * @return
     */
    private static Set getDifferent(Set<String> baseSet,Set<String> delSet){
        baseSet.removeAll(delSet);
        return baseSet;
    }

    private static JSONObject getJson(String path){
        JSONObject jsonFrom = null;
        try {
            String str = "";
            FileInputStream fileInputStream = new FileInputStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String s;
            while ((s = bufferedReader.readLine())!=null) {
                str = str + s ;
            }
            jsonFrom = JSONObject.fromObject(str);
            bufferedReader.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonFrom;
    }

    public static void main(String[] args) {
//        getPostParams(getJson("C:/Users/BONC/Desktop/018950/7c132565-8eb3-41da-b2e6-6d3e0690ba7c.json"));
//        System.out.println(getJson("C:/Users/BONC/Desktop/018950/log.txt"));
        System.out.println(ClassLoader.getSystemResource(""));
    }
}
