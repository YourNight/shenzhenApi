package com.bonc.shenzhen.util;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaolong on 2018/8/9.
 */
public class DatabaseParse {


    @Value("${file.databaseUrl}")
    static String databaseUrl;

    private static List<JSONObject> getDatabaseList(){
        List<JSONObject> databaseJsonList = UnZipFromPath.unzip("C:\\Users\\BONC\\Desktop\\018950\\DatasourceExport_20180710094013.zip");
        return databaseJsonList;
    }

    public static void getDatabaseIdName(List<JSONObject> databaseJsonList){
        Map<Object, Object> databaseIdNameMap = new HashMap<>();
        for (JSONObject database : databaseJsonList) {
            String id = database.get("id").toString();
            String name = database.get("name").toString();
        }

    }


    public static void main(String[] args) {
        getDatabaseList();
    }
}
