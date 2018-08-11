package com.bonc.shenzhen.util;

import com.bonc.shenzhen.restfulApi.RestfulApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseCollection {

    @Value("${datacollection.url}")
    String collectionUrl;

    UnZipFromPath unZipFromPath = new UnZipFromPath();

    public static void main(String[] args) {
        JSONArray jsonArray1 = new ParseCollection().getInter();
        for (int i = 0; i < jsonArray1.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i);
            String sourceTable = jsonObject1.get("sourceTable").toString();//源表名
            String resourceCode = jsonObject1.getString("sourceDataSourceId");
            String sourceTableSchema = jsonObject1.get("sourceTableSchema").toString();//schema
            System.out.println( "----------" + sourceTable + "---" + sourceTableSchema+"-----"+resourceCode);
        }
    }

    public JSONArray getInter() {
        JSONArray object = null;
        List<JSONObject> list = new ArrayList<>();
        list = unZipFromPath.unzip(collectionUrl);
        List<Object> return_list = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            map = list.get(i);
            JSONArray jsonArray = JSONArray.fromObject(map);
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                String SourceTable = jsonObject.getString("sourceTable");
                String TargetTable = jsonObject.getString("targetTable");
                String resourceCode = jsonObject.getString("sourceDataSourceId");
                String schema = jsonObject.getString("sourceTableSchema");
                Map map2 = new HashMap<>();
                map2.put("sourceTable", SourceTable);
                map2.put("targetTable", TargetTable);
                map2.put("sourceDataSourceId", resourceCode);
                map2.put("sourceTableSchema", schema);
                return_list.add(map2);
            }
        }
        object = JSONArray.fromObject(return_list);
        System.out.println(object.toString());
        return object;
    }

    public JSONArray getBoold() {

        JSONArray object = null;
        List<JSONObject> list = new ArrayList<>();
        List<Object> param_list = new ArrayList();
        List<Object> param_list2 = new ArrayList();
        list = unZipFromPath.read();
        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            map = list.get(i);
            JSONArray jsonArray = JSONArray.fromObject(map);
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                String SourceTable = jsonObject.getString("sourceTable");
                String TargetTable = jsonObject.getString("targetTable");
                String resourceCode = jsonObject.getString("sourceDataSourceId");
                String schema = jsonObject.getString("sourceTableSchema");
                JSONArray jsonArray2 = jsonObject.getJSONArray("fieldMappings");
                List<Object> list3 = new ArrayList();
                for (int k = 0; k < jsonArray2.size(); k++) {
                    Map map2 = new HashMap();
                    Map map3 = new HashMap();
                    Object obj = jsonArray2.get(i);
                    JSONObject fieldMapping = JSONObject.fromObject(obj);
                    String src = fieldMapping.get("sourceField").toString();
                    String dest = fieldMapping.get("targetField").toString();
                    map3.put("id", "");
                    map3.put("value", "");
                    map3.put("lobValue", "");
                    map3.put("desc", "");
                    map3.put("breifSql", "");
                    map2.put("srcColumn", src);
                    map2.put("desColumn", dest);
                    map2.put("ruleType", "0");
                    map2.put("calcRule", map3);
                    list3.add(map2);
                }
                Map map4 = new HashMap();
                JSONObject jsonObject1 = RestfulApi.tableCodes;
                String desId = jsonObject1.get(resourceCode + "-" + schema + "-" + TargetTable).toString();
                String srcId = jsonObject1.get(resourceCode + "-" + schema + "-" + SourceTable).toString();
                map4.put("desEntityId", desId);
                map4.put("desEntityCode", TargetTable);
                map4.put("srcEntityId", srcId);
                map4.put("srcEntityCode", SourceTable);
                map4.put("metaRelDetails", list3);
                param_list.add(map4);
                Map map5 = new HashMap();
                map5.put("systemId", "");
                map5.put("processId", "");
                map5.put("relationType", "0");
                map5.put("tenantId", "");
                map5.put("metaRelations",param_list);
                param_list.add(map5);
                object = JSONArray.fromObject(param_list2);
                System.out.println(object.toString());
            }
        }
        return object;
    }
}
