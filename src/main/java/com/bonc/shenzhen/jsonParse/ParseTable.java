package com.bonc.shenzhen.jsonParse;

import com.bonc.shenzhen.util.UnZipFromPath;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.*;

public class ParseTable {

    public static  JSONArray getData(String datasourceUrl) {
        List<Object> list2 = new ArrayList<>();
        List<JSONObject> list = UnZipFromPath.unzip(datasourceUrl);
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = list.get(i);
            String id = jsonObject.get("id").toString();
            String name = jsonObject.get("name").toString();
            Map map2 = new HashMap();
            map2.put(id,name);
            list2.add(map2);
        }
        return JSONArray.fromObject(list2);
    }
}

