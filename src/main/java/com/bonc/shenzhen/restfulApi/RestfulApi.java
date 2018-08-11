package com.bonc.shenzhen.restfulApi;

import com.bonc.shenzhen.jsonParse.*;
import com.bonc.shenzhen.util.*;

import com.bonc.shenzhen.util.HttpPost;
import com.bonc.shenzhen.util.UnZipFromPath;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Value("${config.taskFlowUrl}")
    String taskFlowUrl;

//    @Value("${config.taskFlowUrl}")
    String dataTableUrl;

    @Value("${config.saveMetaRelationsUrl}")
    String saveMetaRelationsUrl;

    @Value("${config.entityTableCodeUrl}")
    String entityTableCodeUrl;

    @Value("${config.downloadPath.datatable}")
    String dataTableExport;

    @Value("${config.downloadPath.datacollection}")
    String dataCollectionExport;

    @Value("${config.downloadPath.dataflow}")
    String dataFlowExport;

    @Value("${config.downloadPath.datasource}")
    String datasourceExport;

    @Value("${config.downloadPath.taskflow}")
    String taskFlowExport;


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
            result = HttpPost.doPost(entityTableCodeUrl, anInterface.toString());
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
        System.out.println(dataTableUrl);
        System.out.println(datasourceUrl);
        System.out.println(collectionUrl);
        System.out.println(dataFlowUrl);
        System.out.println(taskFlowUrl);
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
        String result = "{\"returnStatus\": 1, \"returnStatusStr\": \"成功\" }";
        try {
            if (dataSource == null || dataCollection == null || tableCodes == null)  this.setCode();
            List<JSONObject> jsonList = UnZipFromPath.unzip(dataFlowUrl);
            for (JSONObject json : jsonList) {
                try {
                    List<JSONObject> postParams = DataFlowParse.getPostParams(json, dataSource, dataCollection,tableCodes);
                    for (JSONObject postParam : postParams) {
                        String s = HttpPost.doPost(saveMetaRelationsUrl, postParam.toString());
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
                    String s = HttpPost.doPost(saveMetaRelationsUrl, o.toString());
                    logger.info(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"returnStatus\": 0, \"returnStatusStr\": \"失败:"+e.toString()+"\" }";
        }
        return result;
    }

    @RequestMapping("/saveRelationFromTaskFlow")
    public String saveRelationFromTaskFlow(){

        try {
            if (dataSource == null || dataCollection == null || tableCodes == null)  this.setCode();
            List<JSONObject> taskList = UnZipFromPath.unzip(taskFlowUrl);
            List<JSONObject> dataFlowList = UnZipFromPath.unzip(dataFlowUrl);
            for (JSONObject taskJson : taskList) {
                try {
                    List<JSONObject> taskFlowParams = TaskFlowParse.getTaskflowParams(taskJson, dataFlowList);
                    for (JSONObject taskFlowParam : taskFlowParams) {
                        logger.info("taskFlowParam---->"+taskFlowParam);
                        String s = HttpPost.doPost(saveMetaRelationsUrl, taskFlowParam.toString());
                        logger.info(s);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"returnStatus\": 0, \"returnStatusStr\": \"失败:"+e.toString()+"\" }";
        }

        return "{\"returnStatus\": 1, \"returnStatusStr\": \"成功\" }";
    }


    @RequestMapping("/getZip")
    public String getZip(){
        String value = "下载成功";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = df.format(System.currentTimeMillis());
        datasourceUrl = dirUrl+File.separatorChar+"DatasourceExport_"+format+".zip";
        dataTableUrl= dirUrl+File.separatorChar+"DataTableExport_"+format+".zip";
        collectionUrl = dirUrl+File.separatorChar+"DataCollectionExport_"+format+".zip";
        dataFlowUrl = dirUrl+File.separatorChar+"DataFlowExport_"+format+".zip";
        taskFlowUrl = dirUrl+File.separatorChar+"TaskFlowExport_"+format+".zip";
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put(datasourceExport,datasourceUrl);
        urlMap.put(dataTableExport,dataTableUrl);
        urlMap.put(dataCollectionExport,collectionUrl);
        urlMap.put(dataFlowExport,dataFlowUrl);
        urlMap.put(taskFlowExport,taskFlowUrl);

        for (String exportUrl : urlMap.keySet()) {
            try {
                HttpDownload.download(exportUrl,urlMap.get(exportUrl));
            } catch (Exception e) {
                e.printStackTrace();
                value = "下载失败";
                continue;
            }
        }
        return value;
    }

    @RequestMapping("/delAllFile")
    public String delAllFile(){
        boolean b = FileUtil.delAllFile(dirUrl);
        if (b) return "删除成功";
        return "删除失败";
    }
}
