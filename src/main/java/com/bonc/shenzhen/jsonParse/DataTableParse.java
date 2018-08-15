package com.bonc.shenzhen.jsonParse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaolong on 2018/8/13.
 */
public class DataTableParse {
    public static List<JSONObject> getTableParams(List<JSONObject> tableJsonList,Map<String,String> idNameMap){
        List<JSONObject> tableParams = new ArrayList<>();
        for (JSONObject tableJson : tableJsonList) {
            Map<String, Object> po = new HashMap<>();
            Map<String, Object> tableParam = new HashMap<>();
            String name = tableJson.get("name").toString();
            String dataSourceId = tableJson.get("dataSourceId").toString();
            String dataSourceName =  idNameMap.get(dataSourceId)!=null?idNameMap.get(dataSourceId):"";
            tableParam.put("code",name);
            tableParam.put("name",name);
            tableParam.put("metaDirTree","深圳测试用例/table");
            tableParam.put("subResouceCode",dataSourceName);
            tableParam.put("subUser","default");
            tableParam.put("tableTypeId","96305");
            tableParam.put("tableTypeName","事实表");
            tableParam.put("isAggregate","是");
            tableParam.put("isDistinguishTable","是");

            JSONArray columns = JSONArray.fromObject(JSONObject.fromObject(tableJson.get("columns")).get("columns"));
            List<Map> columnList = new ArrayList<>();
            int i=1;
            for (Object columnJson : columns) {
                Map<String, Object> columnTemp = new HashMap<>();
                JSONObject column = JSONObject.fromObject(columnJson);
                String columnName = column.get("name").toString();
                columnTemp.put("columnCode",columnName);
                columnTemp.put("columnName",columnName);
                columnTemp.put("columnLength",200);
                columnTemp.put("columnType","字符串");
                columnTemp.put("isKey","否");
                columnTemp.put("isNullable","是");
                columnTemp.put("dataCharacter","描述");
                columnTemp.put("zoneType","无");
                columnTemp.put("ord",i);
                columnTemp.put("defaultValue","");
                columnTemp.put("valueType","本期值");
                columnTemp.put("alias","无");
                columnTemp.put("columnforeignName","无");
                i++;
                columnList.add(columnTemp);
            }
            tableParam.put("tableStructFields",columnList);
            po.put("token","1");
            po.put("objectInfo",tableParam);
            tableParams.add(JSONObject.fromObject(po));
        }
        return tableParams;
    }


    public static Map<String, String> getTableDatabaseIdMap(List<JSONObject> tableJsonList){
        Map<String, String> tableDatabaseIdMap = new HashMap<>();
        for (JSONObject tableJson : tableJsonList) {
            String tableName = tableJson.get("name").toString();
            String dataSourceId = tableJson.get("dataSourceId").toString();
            tableDatabaseIdMap.put(tableName,dataSourceId);
        }
        return tableDatabaseIdMap;
    }

}
