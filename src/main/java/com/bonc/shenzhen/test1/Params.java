package com.bonc.shenzhen.test1;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class Params {

    ParseDataCollection dataCollectionParse = new ParseDataCollection();
    ParseDataSource parseDataSource = new ParseDataSource();

    JSONArray jsonArray11 = null;
    JSONArray jsonArray22 = null;

    JSONArray jsonArray1 = null;
    JSONArray jsonArray2 = null;

    public JSONArray StringTest(String type) throws IOException {
        jsonArray1 = dataCollectionParse.parseJson();
        jsonArray2 = parseDataSource.parseJson();
        List<Object> list1 = new ArrayList<>();
        List<Object> list2 = new ArrayList<>();
        Map map1 = new HashMap();
        Map map2 = new HashMap();
        for (int i = 0; i < jsonArray1.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i);
            String sourceDataSourceId = jsonObject1.get("sourceDataSourceId").toString();//数据源ID
            String sourceTable = jsonObject1.get("sourceTable").toString();//源表名
            String targetTable = jsonObject1.get("targetTable").toString();//目标表名
            String sourceTableSchema = jsonObject1.get("sourceTableSchema").toString();//schema
            for (int j = 0; j < jsonArray2.size(); j++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray2.get(j);
                Map<String, String> map = (Map<String, String>) jsonObject2;
                Set<String> set = map.keySet();
                String resouceCode = map.get(set.toArray()[0].toString());
                String id = set.toArray()[0].toString();
                if (sourceDataSourceId.equals(id)) {
                    map1.put("sourceTable", sourceTable);
                    map1.put("sourceTableSchema", sourceTableSchema);
                    map1.put("resouceCode", resouceCode);
                    list1.add(map1);
                    /*list1.add(sourceTable);
                    list1.add(sourceTableSchema);
                    list1.add(resouceCode);*/

                    map2.put("targetTable", targetTable);
                    map2.put("sourceTableSchema", sourceTableSchema);
                    map2.put("resouceCode", resouceCode);
                    list2.add(map2);
                    /*list2.add(targetTable);
                    list2.add(sourceTableSchema);
                    list2.add(resouceCode);*/
                }
            }
        }
        if (type.equals("源表")) {
            jsonArray11 = JSONArray.fromObject(list1);
            System.out.println("源表输出了==" + jsonArray11.toString());
            return jsonArray11;
        } else {
            jsonArray22 = JSONArray.fromObject(list2);
            System.out.println("目标表输出了==" + jsonArray22.toString());
            return jsonArray22;
        }
    }

    public void Boold() throws IOException{
        jsonArray1 = dataCollectionParse.parseJson();
        List<Object> list3 = new ArrayList<>();
        Map map = new HashMap();
        for (int i = 0; i < jsonArray1.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i);
            String sourceTable = jsonObject1.get("sourceTable").toString();//源表名
            String targetTable = jsonObject1.get("targetTable").toString();//目标表名
//            map.put("desEntityId",desEntityId);
        }


    }

    public static void main(String[] args) throws IOException {
        new Params().StringTest("源表");
    }

}
