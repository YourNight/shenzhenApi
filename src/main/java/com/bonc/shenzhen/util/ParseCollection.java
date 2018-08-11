package com.bonc.shenzhen.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseCollection {

    public static JSONArray getInter(String collectionUrl) {
        List<JSONObject> list = UnZipFromPath.unzip(collectionUrl);
        List<Object> return_list = new ArrayList();
        for (JSONObject jsonObject : list) {
            Map map2 = new HashMap<>();
            map2.put("sourceTable", jsonObject.getString("sourceTable"));
            map2.put("targetTable", jsonObject.getString("targetTable"));
            map2.put("sourceDataSourceId", jsonObject.getString("sourceDataSourceId"));
            map2.put("sourceTableSchema", jsonObject.getString("sourceTableSchema"));
            return_list.add(map2);
        }
        return JSONArray.fromObject(return_list);
    }
}
