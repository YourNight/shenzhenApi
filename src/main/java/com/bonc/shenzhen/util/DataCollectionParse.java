package com.bonc.shenzhen.util;

import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by liuhaolong on 2018/8/9.
 */
public class DataCollectionParse {
    public static void getDatabaseInfo(){
        List<JSONObject> collections = UnZipFromPath.unzip("C:\\Users\\BONC\\Desktop\\018950\\DataCollectionExport_20180710102020.zip");
        for (JSONObject collection : collections) {

        }

    }
}
