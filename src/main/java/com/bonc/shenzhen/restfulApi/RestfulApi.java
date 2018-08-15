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
import java.util.ArrayList;
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

    @Value("${config.dataTableUrl}")
    String dataTableUrl;

    @Value("${config.saveMetaRelationsUrl}")
    String saveMetaRelationsUrl;

    @Value("${config.entityTableCodeUrl}")
    String entityTableCodeUrl;

    @Value("${config.addEntityTableUrl}")
    String addEntityTableUrl;

    @Value("${config.addDatabaseUrl}")
    String addDatabaseUrl;

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

    @Value("${config.databaseDir}")
    String databaseDir;

    @Value("${config.tableDir}")
    String tableDir;

    public static Map<String, String> tableDatabaseIdMap;
    public static Map<String, String> databaseIdNameMap;
    public static JSONObject tableCodes;

    @RequestMapping("/setCode")
    public String setCode(){
        String result = "";
        tableDatabaseIdMap = DataTableParse.getTableDatabaseIdMap(UnZipFromPath.unzip(dataTableUrl));
        databaseIdNameMap = DatabaseParse.getIdNameMap(UnZipFromPath.unzip(datasourceUrl));
        List<Map> queryTableCodeParams = new ArrayList<>();
        for (String tableName : tableDatabaseIdMap.keySet()) {
            Map<String, Object> queryParam = new HashMap<>();
            queryParam.put("tableCode",tableName);
            queryParam.put("schema","default");
            queryParam.put("resourceCode",databaseIdNameMap.get(tableDatabaseIdMap.get(tableName)));
            queryTableCodeParams.add(queryParam);
        }
        try {
            result = HttpPost.doPost(entityTableCodeUrl, JSONArray.fromObject( queryTableCodeParams).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableCodes = JSONObject.fromObject(result);
        return tableCodes.toString();
    }


    @RequestMapping(value = "/saveRelationFromDataFlow")
    public String saveRelationFromDataFlow() {
        logger.info("**********************数据流保存血缘关系开始**********************");
        String result = "{\"returnStatus\": 1, \"returnStatusStr\": \"成功\" }";
        try {
            if (tableDatabaseIdMap == null || databaseIdNameMap == null || tableCodes == null)  this.setCode();
            List<JSONObject> jsonList = UnZipFromPath.unzip(dataFlowUrl);
            for (JSONObject json : jsonList) {
                try {
                    List<JSONObject> postParams = DataFlowParse.getPostParams(json, databaseIdNameMap,tableCodes);
                    for (JSONObject postParam : postParams) {
                        logger.info("dataFlowParams---->"+postParam);
                        String s = HttpPost.doPost(saveMetaRelationsUrl, postParam.toString());
                        logger.info(s);
                    }
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage());
                    e.printStackTrace();
                    result = "{\"returnStatus\": 0, \"returnStatusStr\": \"存在失败:"+e.toString()+"\" }";
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("**********************数据流保存血缘关系结束**********************");
        return result;
    }



    @RequestMapping("/saveRelationFromDataCollection")
    public String saveRelationFromDataCollection(){
        logger.info("**********************数据采集保存血缘关系开始**********************");
        String result = "{\"returnStatus\": 1, \"returnStatusStr\": \"成功\" }";
        try {
            if (tableDatabaseIdMap == null || databaseIdNameMap == null || tableCodes == null)  this.setCode();
            JSONArray boold = ParamsBoold.getBoold(collectionUrl,tableCodes,databaseIdNameMap);
            for (Object o : boold) {
                try {
                    logger.info("dataCollectionParams---->"+o.toString());
                    String s = HttpPost.doPost(saveMetaRelationsUrl, o.toString());
                    logger.info(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    result = "{\"returnStatus\": 0, \"returnStatusStr\": \"存在失败:"+e.toString()+"\" }";
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("**********************数据采集保存血缘关系结束**********************");
        return result;
    }

    @RequestMapping("/saveRelationFromTaskFlow")
    public String saveRelationFromTaskFlow(){
        logger.info("**********************任务流保存血缘关系开始**********************");
        String result = "{\"returnStatus\": 1, \"returnStatusStr\": \"成功\" }";
        try {
            if (tableDatabaseIdMap == null || databaseIdNameMap == null || tableCodes == null)  this.setCode();
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
                    result = "{\"returnStatus\": 0, \"returnStatusStr\": \"存在失败:"+e.toString()+"\" }";
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("**********************任务流保存血缘关系结束**********************");
        return result;
    }

    @RequestMapping("/saveDatabaseAndTable")
    public String saveDatabaseAndTable(){
        String result = "{\"returnStatus\": 1, \"returnStatusStr\": \"成功\" }";
        List<JSONObject> dataSourceList = UnZipFromPath.unzip(datasourceUrl);
        List<JSONObject> tableList = UnZipFromPath.unzip(dataTableUrl);
        Map<String, String> idNameMap = DatabaseParse.getIdNameMap(dataSourceList);
        List<JSONObject> databaseParams = DatabaseParse.getDatabaseParams(dataSourceList,databaseDir);
        System.out.println(databaseParams.size());
        List<JSONObject> tableParams = DataTableParse.getTableParams(tableList, idNameMap,tableDir);
        for (JSONObject databaseParam : databaseParams) {
            try {
                String s = HttpPost.doPost(addDatabaseUrl, databaseParam.toString());
                logger.info("新增数据库id---->"+s);
            } catch (Exception e) {
                e.printStackTrace();
                result = "{\"returnStatus\": 0, \"returnStatusStr\": \"存在失败:"+e.toString()+"\" }";
                continue;
            }
        }
        for (JSONObject tableParam : tableParams) {
            try {
                String s = HttpPost.doPost(addEntityTableUrl, tableParam.toString());
                logger.info("新增实体表id---->"+s);
            } catch (Exception e) {
                result = "{\"returnStatus\": 0, \"returnStatusStr\": \"存在失败:"+e.toString()+"\" }";
                e.printStackTrace();
                continue;
            }
        }
        return result;
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
                logger.info("开始下载--->"+exportUrl);
                HttpDownload.download(exportUrl,urlMap.get(exportUrl));
                logger.info("下载结束");
            } catch (Exception e) {
                e.printStackTrace();
                value = "下载存在失败";
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

    @RequestMapping("/startParse")
    public String startParse(){
        this.getZip();
        this.saveDatabaseAndTable();
        this.setCode();
        this.saveRelationFromDataCollection();
        this.saveRelationFromDataFlow();
        this.saveRelationFromTaskFlow();
        return "{\"returnStatus\": 1, \"returnStatusStr\": \"成功\" }";
    }
}
