package com.bonc.shenzhen.util;

import com.bonc.shenzhen.restfulApi.RestfulApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsBoold {

    public  JSONArray getBoold(String collectionUrl) {

        JSONArray object = null;
        List<JSONObject> list = new ArrayList<>();
        List<Object> param_list = new ArrayList();
        List<Object> param_list2 = new ArrayList();
        list = UnZipFromPath.unzip(collectionUrl);
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
