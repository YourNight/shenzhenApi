package com.bonc.shenzhen.test1;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseDataCollection {

    GetAllFiles gal = new GetAllFiles();
    JSONArray json = null;
    static List<Object> list = new ArrayList<>();
    static List<Object> list1 = new ArrayList<>();
    List<Object> list2 = new ArrayList();
    List<Object> list3 = new ArrayList();

    public static void main(String[] args) throws IOException {
        JSONArray jsonArray1 = new ParseDataCollection().parseJson();
        for (int i = 0; i < jsonArray1.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i);
            String s = jsonObject1.get("sourceDataSourceId").toString();
            String sourceTable = jsonObject1.get("sourceTable").toString();//源表名
            String sourceTableSchema = jsonObject1.get("sourceTableSchema").toString();//schema
            System.out.println(s + "----------" + sourceTable + "---" + sourceTableSchema);
        }
        /*for(int j=0;j<list2.size();j++){
            System.out.println(list2.get(j));
        }*/
    }

    /**
     * @Description: 遍历解析文件
     * @Param: []
     * @return: net.sf.json.JSONArray
     */

//    @RequestMapping("/parseJson")
    public JSONArray parseJson() throws IOException {
        List<String> list2 = gal.getJsonFiles1();
        for (String x : list2) {
            if (x.endsWith("DirMapping.json")) {
                continue;
            }
            json = getJson(x);
        }
        return json;
    }

    /**
     * @Description: 解析json
     * @Param: [path]
     * @return: net.sf.json.JSONArray
     */

    public JSONArray getJson(String path) throws IOException {
        JSONArray object = null;
        String str = "";
        FileInputStream fileInputStream = new FileInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            str = str + s;
        }
        JSONObject jsonObject = JSONObject.fromObject(str);
        String SourceTable = jsonObject.getString("sourceTable");
        String TargetTable = jsonObject.getString("targetTable");
        String sourceDataSourceId = jsonObject.getString("sourceDataSourceId");
        String sourceTableSchema = jsonObject.getString("sourceTableSchema");

        Map map = new HashMap<>();
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        Map map4 = new HashMap();
        map.put("sourceTable", SourceTable);
        map.put("targetTable", TargetTable);
        map.put("sourceDataSourceId", sourceDataSourceId);
        map.put("sourceTableSchema", sourceTableSchema);
        JSONArray jsonArray = jsonObject.getJSONArray("fieldMappings");
        for (int i = 0; i < jsonArray.size(); i++) {
            Object obj = jsonArray.get(i);
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
            map4.put("desEntityId", "");
            map4.put("desEntityCode", "");
            map4.put("srcEntityId", "");
            map4.put("srcEntityCode", "");
            map4.put("systemId", "");
            map4.put("processId", "");
            map4.put("relationType", "0");
            map4.put("tenantId", "");
            map4.put("metaRelDetails", list2);
        }
        list.add(map);
//        list.add(map4);
        object = JSONArray.fromObject(list);
        System.out.println(object.toString());
        return object;
    }
}
