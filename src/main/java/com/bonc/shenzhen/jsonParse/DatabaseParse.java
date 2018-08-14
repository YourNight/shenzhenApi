package com.bonc.shenzhen.jsonParse;

import com.bonc.shenzhen.util.UnZipFromPath;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaolong on 2018/8/9.
 */
public class DatabaseParse {


    private static List<JSONObject> getDatabaseList(){
        List<JSONObject> databaseJsonList = UnZipFromPath.unzip("C:\\Users\\BONC\\Desktop\\018950\\DatasourceExport_20180710094013.zip");
        List<JSONObject> databaseParams = getDatabaseParams(databaseJsonList);
        System.out.println(databaseParams);
        return databaseParams;
    }

    public static List<JSONObject> getDatabaseParams(List<JSONObject> databaseJsonList){
        List<JSONObject> list = new ArrayList<>();

        for (JSONObject database : databaseJsonList) {

            Map<String, Object> infoMap = new HashMap<>();
            Map<String, Object> databaseParamMap = new HashMap<>();
//            String id = database.get("id").toString();
            String name = database.get("name").toString();
            String dbTypeName = database.get("type").toString().toUpperCase();

            databaseParamMap.put("code",name);
            databaseParamMap.put("name",name);
            databaseParamMap.put("metaDirTree","深圳测试用例/表");
            databaseParamMap.put("dbTypeName",getDbType(dbTypeName));
            databaseParamMap.put("dbCharset","UTF-8");
            databaseParamMap.put("ip","172.36.11.9");
            databaseParamMap.put("port","1000");
            databaseParamMap.put("serviceName","default");
            databaseParamMap.put("maxConnections","10");
            databaseParamMap.put("username","admin");
            databaseParamMap.put("pwd","admin");

            infoMap.put("token","1");
            infoMap.put("objectInfo",databaseParamMap);

            list.add(JSONObject.fromObject(infoMap));

        }
        return list;

    }

    private static String getDbType(String type){
        String dbType = "";
        switch (type){
            case "MYSQL" : dbType = "MYSQL";break;
            case "ORACLE" : dbType = "ORACLE";break;
            case "HIVE" : dbType = "HIVE2";break;
            case "MPPDB" : dbType = "MYSQL";break;
            default: dbType = "MYSQL";break;
        }
        return dbType;
    }

    public static Map<String,String> getIdNameMap(List<JSONObject> databaseJsonList){
        Map<String, String> idNameMap = new HashMap<>();
        for (JSONObject databaseJson : databaseJsonList) {
            String id = databaseJson.get("id").toString();
            String name = databaseJson.get("name").toString();
            idNameMap.put(id,name);
        }
        return idNameMap;
    }

/*    {
            "id" : "4df0a335-8dfb-4ab8-afc7-f62a72c4b042",
            "name" : "qingdao_oracle_goal",
            "description" : "t",
            "createDate" : 1530185767633,
            "creatorId" : -1,
            "readonlyUserType" : "UseDefault",
            "readwriteonlyUserType" : "UseDefault",
            "manageUserType" : "UseDefault",
            "type" : "Oracle",
            "charset" : "UTF-8",
            "database" : "",
            "hdfsPath" : "",
            "hbaseNamespace" : "",
            "modifyDate" : -1,
            "modifyUserId" : -1,
            "status" : 1,
            "lastTestConnectionDate" : 1530186881550
    }*/



    public static void main(String[] args) {
        getDatabaseList();
    }
}
