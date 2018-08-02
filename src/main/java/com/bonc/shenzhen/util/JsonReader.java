package com.bonc.shenzhen.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * Created by liuhaolong on 2018/8/1.
 */
public class JsonReader {
    public static void parse(JSONObject jsonStr){
//        jsonStr.keySet().stream().forEach(key ->{
//            System.out.println(key+"   ----->   "+jsonStr.get(key).toString());
//        });
//        System.out.println(JSONObject.fromObject(jsonStr.get("444")));

        //获取工作流节点
        JSONObject workflowNode = JSONObject.fromObject(jsonStr.get("workflowNode"));
        //获取节点关系
        JSONArray edges = JSONArray.fromObject(workflowNode.get("edges"));
        //  将节点关系存到map中
        Map<String, String> relationMap = new HashMap<>();

        Set<String> sourceIds = null;// 存放所有sourceid
        Set<String> targetIds = new HashSet<>();// 存放所有targetid
        for (Object edgeStr : edges) {
            //节点关系
            JSONObject edge = JSONObject.fromObject(edgeStr);
            String sourceId = edge.get("source").toString();
            String targetId = edge.get("target").toString();
            relationMap.put(sourceId,targetId);
        }

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



        System.out.println("源表id："+sourceTablesId);
        System.out.println("目标表id："+targetTablesId);
//        System.out.println(map);
        // TODO   获取所有源表    获取结果表

//        getSourceIdsAndResultId(map);
        JSONArray nodesList = JSONArray.fromObject(workflowNode.get("nodes"));//所有节点存放的List
        Map<String,JSONObject> nodesMap = new HashMap<>();//将节点List转为节点Map,便于使用get(id),key为id
        for (Object nodeStr : nodesList) {
            JSONObject node = JSONObject.fromObject(nodeStr);
            String id = node.get("id").toString();
            JSONObject metadata = JSONObject.fromObject(node.get("metadata"));
            nodesMap.put(id,metadata);
        }
        System.out.println("所有节点的map：");
        nodesMap.forEach((a,b)->{
            System.out.println(a+":"+b);
        });

        Map<String, String> enumMap = getEnumMap(targetTablesId.toArray()[0].toString(), nodesMap);//对目标源表的列名编号  列名：integer


//        for (Object o : sourceTablesId) {
//            String id = o.toString();
//            List<Map> list = new ArrayList<>();
//            list = getMetaRelDetails(id, relationMap, nodesMap, enumMap, list);
//        }


    }


    public static List<Map> getMetaRelDetails(String id,Map<String,String> relationMap,Map<String,JSONObject> nodeMap ,Map<String,String> enumMap,List<Map> columnList){
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

        JSONArray columns = JSONArray.fromObject(metadata.get("columns"));//获取所有column
        for (Object columnObj : columns) {//遍历
            JSONObject column = JSONObject.fromObject(columnObj);
            if (columnList.size()>0){//如果columnList已经有数据，即column已存入
                for (Map map : columnList) {
                    String desColumn = map.get("desColumn").toString();//前一个节点的outputname
                    if (desColumn.equals(column.getString("inputName"))){//如果前一个节点的outputname和此次的inputname相同
                        map.put("desColumn",column.getString("outputName"));//重新将desColumn设置为此次的outputname
                        Object config = column.get("expressionConfig");//获取表达式对象
                        Map calcRule = (Map) map.get("calcRule");
                        if (config!=null){//表达式对象不为空
                            JSONObject expressionConfig = JSONObject.fromObject(config);
                            String expression = expressionConfig.get("expr").toString();//获取表达式
                            expression = expression.equals(column.getString("inputName"))?"":expression;//如果表达式和inputname一样则置为空
                            calcRule.put("value",calcRule.get("value").toString()+expression);
                        }
                        if (expr.contains(column.getString("inputName"))){
                            calcRule.put("value",calcRule.get("value").toString()+expr);
                        }
                    }
                }
            }else {//columnList为空
                Map<String, Object> tempMap = new HashMap<>();
                String inputName = column.get("inputName").toString();
                tempMap.put("srcColumn",inputName);//源列名
                tempMap.put("desColumn",column.get("outputName").toString());//目标列名
                tempMap.put("ruleType",0);
                Map<String, Object> rule = new HashMap<>();
                rule.put("id",enumMap.get(inputName)!=null?enumMap.get(inputName):"");
                Object config = column.get("expressionConfig");
                if (config!=null){
                    JSONObject expressionConfig = JSONObject.fromObject(config);
                    String expression = expressionConfig.get("expr").toString();
                    expression = expression.equals(inputName)?"":expression;
                    rule.put("value",rule.get("value").toString()+expression);
                }
                if (expr.contains(inputName)){
                    rule.put("value",rule.get("value").toString()+expr);
                }
                rule.put("desc","");
                rule.put("breifSql","");
                tempMap.put("calcRule",rule);
                columnList.add(tempMap);
            }
        }
        if (relationMap.get(id)!=null){//如果有下一个节点则循环
            getMetaRelDetails(relationMap.get(id),relationMap,nodeMap,enumMap,columnList);
        }else {
            return columnList;
        }
        return null;
    }

    public static Map<String,String> getEnumMap(String id,Map<String,JSONObject> nodeMap){
        Map<String, String> enumMap = new HashMap<>();
        JSONObject metadata = nodeMap.get(id);
        JSONArray columns = JSONArray.fromObject(metadata.get("columns"));
        int i= 1;
        for (Object columnObj : columns) {
            JSONObject column = JSONObject.fromObject(columnObj);
            String columnName = column.get("inputName").toString();
            enumMap.put(columnName,i+"");
            i++;
        }
        return enumMap;
    }

    public static Set getDifferent(Set<String> baseSet,Set<String> delSet){
        baseSet.removeAll(delSet);
        return baseSet;
    }

    public static JSONObject getJson(String path){
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
        parse(getJson("C:/Users/BONC/Desktop/018950/30c5fca3-dc07-4206-ae1e-8c5b9e72d230.json"));
    }
}
