package com.bonc.shenzhen.restfulApi;

import com.bonc.shenzhen.util.*;

import com.bonc.shenzhen.util.Params;
import com.bonc.shenzhen.util.Httppost;
import com.bonc.shenzhen.util.DataFlowParse;
import com.bonc.shenzhen.util.ParseCollection;
import com.bonc.shenzhen.util.UnZipFromPath;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liuhaolong on 2018/8/1.
 */
@RestController
public class RestfulApi {

    private static Logger logger = Logger.getLogger(RestfulApi.class);

    @Value("${config.dirUrl}")
    String dirUrl;

    @Value("${config.dataFlowUrl}")
    String dataFlowUrl;

    @Value("${config.dataCollectionUrl}")
    String collectionUrl;

    @Value("${config.datasourceUrl}")
    String datasourceUrl;

    @Value("${config.saveMetaRelationsUrl}")
    String saveMetaRelationsUrl;

    @Value("${config.entityTableCodeUrl}")
    String entityTableCodeUrl;

    public static JSONArray dataSource;
    public static JSONArray dataCollection;
    public static JSONObject tableCodes;

    @RequestMapping("/setCode")
    public void setCode(){
        Params params = new Params();
        String result = "";
        dataCollection = ParseCollection.getInter(collectionUrl);
        dataSource = ParseTable.getData(datasourceUrl);
        JSONArray anInterface = params.getInterface(dataSource,dataCollection);
        try {
            result = Httppost.doPost(entityTableCodeUrl, anInterface.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableCodes = JSONObject.fromObject(result);

        logger.info("dataCollection----->"+dataCollection);
        logger.info("dataSource----->"+dataSource);
        logger.info("tableCodes----->"+tableCodes);
    }


    @RequestMapping(value = "/hello/{thing}", method = {RequestMethod.GET, RequestMethod.POST})
    public String hello(@PathVariable String thing) {
//        System.out.println(thing);
//        HashMap<String, Object> hashMap = new HashMap<>();
//        File dir = new File(dirUrl);
//        File[] files = dir.listFiles();
//        for (File file : files) {
//            if (file.isFile()){
//                System.out.println(file.getPath().contains("DataCollectionExport"));
//            }
//        }
        return "you say :"+thing;
    }

    @RequestMapping(value = "/saveRelationFromDataFlow")
    public String saveRelationFromDataFlow() {
        String result = null;
        try {
            if (dataSource == null || dataCollection == null || tableCodes == null)  this.setCode();
            List<JSONObject> jsonList = UnZipFromPath.unzip(dataFlowUrl);
            for (JSONObject json : jsonList) {
                try {
                    List<JSONObject> postParams = DataFlowParse.getPostParams(json, dataSource, dataCollection,tableCodes);
                    for (JSONObject postParam : postParams) {
                        String s = Httppost.doPost(saveMetaRelationsUrl, postParam.toString());
                        logger.info(s);
                    }
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage());
                    e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"returnStatus\": 500,\"returnStatusStr\": \"失败:"+e.toString()+"\" }";
        }
        return result;
    }



    @RequestMapping("/saveRelationFromDataCollection")
    public String saveRelationFromDataCollection(){
        String result = "{\"returnStatus\": 1, \"returnStatusStr\": \"成功\" }";
        try {
            if (dataSource == null || dataCollection == null || tableCodes == null)  this.setCode();
            JSONArray boold = ParamsBoold.getBoold(collectionUrl,tableCodes);
            for (Object o : boold) {
                logger.info(o.toString());
                try {
                    String s = Httppost.doPost(saveMetaRelationsUrl, o.toString());
                    logger.info(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"returnStatus\": 0, \"returnStatusStr\": \"失敗\" }";
        }
        return result;
    }



}
