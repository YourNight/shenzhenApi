package com.bonc.shenzhen.restfulApi;

import com.bonc.shenzhen.util.*;

import com.bonc.shenzhen.util.Params;
import com.bonc.shenzhen.util.Httppost;
import com.bonc.shenzhen.util.JsonReader;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    @Value("${file.url}")
    String url;

    @Value("${config.dirUrl}")
    String dirUrl;

    @Value("${datacollection.url}")
    String collectionUrl;

    @Value("${config.saveMetaRelationsUrl}")
    String saveMetaRelationsUrl;
    @Value("${config.entityTableCodeUrl}")
    String entityTableCodeUrl;

    public static JSONArray dataSource;
    public static JSONArray dataCollection;
    public static JSONObject tableCodes;

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
        System.out.println(collectionUrl);
        return "you say :"+thing;
    }

    @RequestMapping(value = "/saveMetaRelation")
    public String saveMetaRelation(HttpServletRequest request) {


        String result = null;
        try {
//            if (dataSource == null || dataCollection == null || tableCodes == null) {
//                Params params = new Params();
//                JSONArray anInterface = params.getInterface();
//                tableCodes = JSONObject.fromObject(Httppost.doPost(entityTableCodeUrl, anInterface.toString()));
//            }

//            List<JSONObject> jsonList = UnZipFromPath.unzip(url);
            List<JSONObject> saveMetaRelationParamsList = new ArrayList<>();
//            for (JSONObject json : jsonList) {
                try {

                    String str = "{\"workflowId\":\"194a96a6-6e84-4f17-a5c8-b58a3f640a62\",\"status\":0,\"name\":\"重点人口入住2\",\"description\":\"\",\"wfArgs\":{},\"executeUser\":\"vbdfiuser\",\"graphData\":{\"2\":{\"id\":\"2\",\"type\":\"extractfrommodel\",\"label\":\"表抽取1\",\"desc\":\"\",\"model\":{\"id\":\"15f9f5e0-13d5-4409-aedc-056d7e707b38\",\"name\":\"T_PEOPLELIST_RISK_TEMP\",\"tableCatalog\":null,\"tableSchema\":null,\"displayName\":\"重点人口分析临时表\",\"type\":\"NORMAL\",\"dataSourceId\":\"4df0a335-8dfb-4ab8-afc7-f62a72c4b042\",\"directoryId\":17,\"dataSourceType\":\"Oracle\",\"tableColumns\":[{\"hbaseColumnFamily\":null,\"name\":\"UUID\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":true,\"defaultValue\":null,\"description\":\"\",\"_uid\":0,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"PEOPLEID\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":1,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"ESTATEID\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":2,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"ESTATENAME\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":3,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"TYPE\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":4,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"IMGURL\",\"displayName\":\"\",\"type\":\"String\",\"length\":250,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":5,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"CREATE_DT\",\"displayName\":\"\",\"type\":\"DateTime\",\"length\":null,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":6,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"UPDATE_DT\",\"displayName\":\"\",\"type\":\"DateTime\",\"length\":null,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":7,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"UPDATE_BY\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":8,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"DELETE_BY\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":9,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"ACT_BY_TYPE\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":10,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"WORKINGDAYS\",\"displayName\":\"\",\"type\":\"Int\",\"length\":null,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":11,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"LIVEDAYS\",\"displayName\":\"\",\"type\":\"Int\",\"length\":null,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":12,\"_checked\":true},{\"hbaseColumnFamily\":null,\"name\":\"PLAYDAYS\",\"displayName\":\"\",\"type\":\"Int\",\"length\":null,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_uid\":13,\"_checked\":true}],\"tablePublishedColumnIdentifiers\":null,\"primaryKeyName\":null,\"primaryKeys\":[\"UUID\"],\"partitionKeys\":null,\"hiveFileFormat\":null,\"hiveOrcCompress\":null,\"hdfsFileFormat\":null,\"hdfsOrcCompress\":null,\"charset\":null,\"cycleType\":null,\"time\":null,\"rowSeparator\":null,\"columnSeparator\":null,\"description\":\"\",\"published\":true,\"version\":3,\"status\":\"PUBLISHED\",\"createdUser\":\"vbduser\",\"createdDate\":1529993977724,\"modifiedUser\":\"vbduser\",\"modifiedDate\":1529993977724},\"directory\":{\"currentId\":17,\"usable\":true},\"where\":\"\",\"hbaseConfig\":{\"minStamp\":\"\",\"maxStamp\":\"\",\"showTimeStampExp\":false},\"sqlProtectWords\":[\"\\\\bcreate\\\\b\\\\s+\\\\bdatabase\\\\b\",\"\\\\binsert\\\\b\\\\s+\\\\binto\\\\b\",\"\\\\binsert\\\\b\\\\s+\\\\boverwrite\\\\b\",\"\\\\bdrop\\\\b\\\\s+\\\\btable\\\\b\",\"\\\\bcreate\\\\b\\\\s+\\\\btable\\\\b\",\"\\\\btruncate\\\\b\",\"\\\\bdelete\\\\b\\\\s+\\\\bfrom\\\\b\",\"\\\\bdelete\\\\b\",\"\\\\bdrop\\\\b\",\"\\\\binsert\\\\b\",\"\\\\bupdate\\\\b\",\"\\\\balter\\\\b\\\\s+\\\\btable\\\\b\",\"\\\\bgrant\\\\b\",\"\\\\brevoke\\\\b\"]},\"3\":{\"id\":\"3\",\"type\":\"loadtomodel\",\"label\":\"表加载1\",\"desc\":\"\",\"model\":{\"id\":\"0e2f741e-381a-480d-86b9-2036600d509d\",\"name\":\"T_PEOPLELIST_RISKIN\",\"tableCatalog\":null,\"tableSchema\":null,\"displayName\":\"重点人口入住\",\"type\":\"NORMAL\",\"dataSourceId\":\"4df0a335-8dfb-4ab8-afc7-f62a72c4b042\",\"directoryId\":17,\"dataSourceType\":\"Oracle\",\"tableColumns\":[{\"hbaseColumnFamily\":null,\"name\":\"UUID\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":true,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"UUID\",\"inputType\":\"String\",\"_fullName\":\"UUID\"},{\"hbaseColumnFamily\":null,\"name\":\"PEOPLEID\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"PEOPLEID\",\"inputType\":\"String\",\"_fullName\":\"PEOPLEID\"},{\"hbaseColumnFamily\":null,\"name\":\"ESTATEID\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"ESTATEID\",\"inputType\":\"String\",\"_fullName\":\"ESTATEID\"},{\"hbaseColumnFamily\":null,\"name\":\"ESTATENAME\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"ESTATENAME\",\"inputType\":\"String\",\"_fullName\":\"ESTATENAME\"},{\"hbaseColumnFamily\":null,\"name\":\"TYPE\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"TYPE\",\"inputType\":\"String\",\"_fullName\":\"TYPE\"},{\"hbaseColumnFamily\":null,\"name\":\"IMGURL\",\"displayName\":\"\",\"type\":\"String\",\"length\":250,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"IMGURL\",\"inputType\":\"String\",\"_fullName\":\"IMGURL\"},{\"hbaseColumnFamily\":null,\"name\":\"CREATE_DT\",\"displayName\":\"\",\"type\":\"DateTime\",\"length\":null,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"CREATE_DT\",\"inputType\":\"DateTime\",\"_fullName\":\"CREATE_DT\"},{\"hbaseColumnFamily\":null,\"name\":\"UPDATE_DT\",\"displayName\":\"\",\"type\":\"DateTime\",\"length\":null,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"UPDATE_DT\",\"inputType\":\"DateTime\",\"_fullName\":\"UPDATE_DT\"},{\"hbaseColumnFamily\":null,\"name\":\"UPDATE_BY\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"UPDATE_BY\",\"inputType\":\"String\",\"_fullName\":\"UPDATE_BY\"},{\"hbaseColumnFamily\":null,\"name\":\"DELETE_BY\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"DELETE_BY\",\"inputType\":\"String\",\"_fullName\":\"DELETE_BY\"},{\"hbaseColumnFamily\":null,\"name\":\"ACT_BY_TYPE\",\"displayName\":\"\",\"type\":\"String\",\"length\":128,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"ACT_BY_TYPE\",\"inputType\":\"String\",\"_fullName\":\"ACT_BY_TYPE\"},{\"hbaseColumnFamily\":null,\"name\":\"WORKINGDAYS\",\"displayName\":\"\",\"type\":\"Int\",\"length\":null,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"WORKINGDAYS\",\"inputType\":\"Int\",\"_fullName\":\"WORKINGDAYS\"},{\"hbaseColumnFamily\":null,\"name\":\"LIVEDAYS\",\"displayName\":\"\",\"type\":\"Int\",\"length\":null,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"LIVEDAYS\",\"inputType\":\"Int\",\"_fullName\":\"LIVEDAYS\"},{\"hbaseColumnFamily\":null,\"name\":\"PLAYDAYS\",\"displayName\":\"\",\"type\":\"Int\",\"length\":null,\"precision\":null,\"scale\":null,\"required\":false,\"defaultValue\":null,\"description\":\"\",\"_checked\":true,\"expression\":\"\",\"inputName\":\"PLAYDAYS\",\"inputType\":\"Int\",\"_fullName\":\"PLAYDAYS\"}],\"tablePublishedColumnIdentifiers\":null,\"primaryKeyName\":null,\"primaryKeys\":[\"UUID\"],\"partitionKeys\":null,\"hiveFileFormat\":null,\"hiveOrcCompress\":null,\"hdfsFileFormat\":null,\"hdfsOrcCompress\":null,\"charset\":null,\"cycleType\":null,\"time\":null,\"rowSeparator\":null,\"columnSeparator\":null,\"description\":\"\",\"published\":true,\"version\":3,\"status\":\"PUBLISHED\",\"createdUser\":\"vbduser\",\"createdDate\":1529998604498,\"modifiedUser\":\"vbduser\",\"modifiedDate\":1529998604498},\"saveMode\":\"1\",\"directory\":{\"currentId\":17,\"usable\":true}}},\"graphXml\":\"<mxGraphModel><root><mxCell id=\\\"0\\\"/><mxCell id=\\\"1\\\" parent=\\\"0\\\"/><mxCell id=\\\"2\\\" value=\\\"&lt;div class=&quot;graph-atom-node&quot;&gt;&lt;div class=&quot;atom-node-back&quot;&gt;&lt;div class=&quot;atom-node-head&quot;&gt;&lt;div class=&quot;atom-icon-extractfrommodel&quot;&gt;&lt;/div&gt;&lt;div class=&quot;atom-node-title&quot;&gt;表抽取1&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;\\\" parent=\\\"1\\\" vertex=\\\"1\\\" type=\\\"extractfrommodel\\\"><mxGeometry x=\\\"250\\\" y=\\\"240\\\" width=\\\"90\\\" height=\\\"90\\\" as=\\\"geometry\\\"/><\\/mxCell><mxCell id=\\\"3\\\" value=\\\"&lt;div class=&quot;graph-atom-node&quot;&gt;&lt;div class=&quot;atom-node-back&quot;&gt;&lt;div class=&quot;atom-node-head&quot;&gt;&lt;div class=&quot;atom-icon-loadtomodel&quot;&gt;&lt;/div&gt;&lt;div class=&quot;atom-node-title&quot;&gt;表加载1&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;\\\" parent=\\\"1\\\" vertex=\\\"1\\\" type=\\\"loadtomodel\\\"><mxGeometry x=\\\"560\\\" y=\\\"240\\\" width=\\\"90\\\" height=\\\"90\\\" as=\\\"geometry\\\"/><\\/mxCell><mxCell id=\\\"4\\\" value=\\\"\\\" style=\\\"exitX=1;exitY=0.5;\\\" parent=\\\"1\\\" source=\\\"2\\\" target=\\\"3\\\" edge=\\\"1\\\" type=\\\"edge\\\"><mxGeometry relative=\\\"1\\\" as=\\\"geometry\\\"/><\\/mxCell><\\/root><\\/mxGraphModel>\",\"createdUserId\":22,\"createTime\":-1,\"directoryId\":3,\"lastUpdateTime\":-1,\"workflowNode\":{\"id\":\"0\",\"type\":\"WORKFLOW\",\"label\":\"重点人口入住2\",\"desc\":\"\",\"directed\":true,\"nodes\":[{\"id\":\"2\",\"label\":\"表抽取1\",\"desc\":\"\",\"metadata\":{\"metaType\":[\"JdbcExtractConfig\",\"JdbcExtractConfig\"],\"modelName\":\"T_PEOPLELIST_RISK_TEMP\",\"modelId\":\"15f9f5e0-13d5-4409-aedc-056d7e707b38\",\"columns\":[{\"inputName\":\"UUID\",\"outputName\":\"UUID\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"PEOPLEID\",\"outputName\":\"PEOPLEID\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"ESTATEID\",\"outputName\":\"ESTATEID\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"ESTATENAME\",\"outputName\":\"ESTATENAME\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"TYPE\",\"outputName\":\"TYPE\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"IMGURL\",\"outputName\":\"IMGURL\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":250,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"CREATE_DT\",\"outputName\":\"CREATE_DT\",\"outputDisplayName\":\"\",\"dataType\":\"DateTime\",\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"UPDATE_DT\",\"outputName\":\"UPDATE_DT\",\"outputDisplayName\":\"\",\"dataType\":\"DateTime\",\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"UPDATE_BY\",\"outputName\":\"UPDATE_BY\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"DELETE_BY\",\"outputName\":\"DELETE_BY\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"ACT_BY_TYPE\",\"outputName\":\"ACT_BY_TYPE\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"WORKINGDAYS\",\"outputName\":\"WORKINGDAYS\",\"outputDisplayName\":\"\",\"dataType\":\"Int\",\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"LIVEDAYS\",\"outputName\":\"LIVEDAYS\",\"outputDisplayName\":\"\",\"dataType\":\"Int\",\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"PLAYDAYS\",\"outputName\":\"PLAYDAYS\",\"outputDisplayName\":\"\",\"dataType\":\"Int\",\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true}],\"basicConfig\":{\"maxErrorCnt\":0,\"saveErrorRecord\":false},\"dataSourceId\":\"4df0a335-8dfb-4ab8-afc7-f62a72c4b042\",\"databaseType\":\"Oracle\",\"url\":\"jdbc:oracle:thin:@100.101.31.153:1521/vbddb\",\"table\":\"T_PEOPLELIST_RISK_TEMP\",\"where\":\"\",\"caseSensitive\":true,\"primaryKeys\":[\"UUID\"]},\"builtIn\":true},{\"id\":\"3\",\"label\":\"表加载1\",\"desc\":\"\",\"metadata\":{\"metaType\":[\"JdbcLoadConfig\",\"JdbcLoadConfig\"],\"modelName\":\"T_PEOPLELIST_RISKIN\",\"modelId\":\"0e2f741e-381a-480d-86b9-2036600d509d\",\"columns\":[{\"inputName\":\"UUID\",\"outputName\":\"UUID\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"PEOPLEID\",\"outputName\":\"PEOPLEID\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"ESTATEID\",\"outputName\":\"ESTATEID\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"ESTATENAME\",\"outputName\":\"ESTATENAME\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"TYPE\",\"outputName\":\"TYPE\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"IMGURL\",\"outputName\":\"IMGURL\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":250,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"CREATE_DT\",\"outputName\":\"CREATE_DT\",\"outputDisplayName\":\"\",\"dataType\":\"DateTime\",\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"UPDATE_DT\",\"outputName\":\"UPDATE_DT\",\"outputDisplayName\":\"\",\"dataType\":\"DateTime\",\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"UPDATE_BY\",\"outputName\":\"UPDATE_BY\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"DELETE_BY\",\"outputName\":\"DELETE_BY\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"ACT_BY_TYPE\",\"outputName\":\"ACT_BY_TYPE\",\"outputDisplayName\":\"\",\"dataType\":\"String\",\"length\":128,\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"WORKINGDAYS\",\"outputName\":\"WORKINGDAYS\",\"outputDisplayName\":\"\",\"dataType\":\"Int\",\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"LIVEDAYS\",\"outputName\":\"LIVEDAYS\",\"outputDisplayName\":\"\",\"dataType\":\"Int\",\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true},{\"inputName\":\"PLAYDAYS\",\"outputName\":\"PLAYDAYS\",\"outputDisplayName\":\"\",\"dataType\":\"Int\",\"check\":\"close\",\"index\":1,\"isNew\":\"false\",\"precision\":15,\"scale\":0,\"isSelected\":true,\"isNull\":true}],\"basicConfig\":{\"maxErrorCnt\":0,\"saveErrorRecord\":false},\"dataSourceId\":\"4df0a335-8dfb-4ab8-afc7-f62a72c4b042\",\"databaseType\":\"Oracle\",\"saveMode\":1,\"url\":\"jdbc:oracle:thin:@100.101.31.153:1521/vbddb\",\"table\":\"T_PEOPLELIST_RISKIN\",\"caseSensitive\":true},\"builtIn\":true}],\"edges\":[{\"id\":\"4\",\"type\":\"EDGE\",\"source\":\"2\",\"target\":\"3\",\"directed\":true,\"builtIn\":true}],\"hasFullInfo\":true,\"builtIn\":true},\"type\":0,\"sparkOptions\":\"\",\"scheduleType\":1,\"redoMissedTask\":false,\"schedulePeriod\":2}";
                    dataSource = JSONArray.fromObject("[{\"4df0a335-8dfb-4ab8-afc7-f62a72c4b042\":\"test_Oracle\"}]");
                    dataCollection = JSONArray.fromObject("[{\"sourceDataSourceId\":\"4df0a335-8dfb-4ab8-afc7-f62a72c4b042\",\"sourceTableSchema\":\"default\",\"sourceTable\":\"T_PEOPLELIST_RISK_TEMP\",\"targetTable\":\"T_PEOPLELIST_RISK_TEMP\"},{\"sourceDataSourceId\":\"4df0a335-8dfb-4ab8-afc7-f62a72c4b042\",\"sourceTableSchema\":\"default\",\"sourceTable\":\"T_PEOPLELIST_RISKIN\",\"targetTable\":\"T_PEOPLELIST_RISKIN\"}]");
                    tableCodes = JSONObject.fromObject("{\"test_Oracle-default-T_PEOPLELIST_RISKIN\":\"89026738\",\"test_Oracle-default-T_PEOPLELIST_RISK_TEMP\":\"89026579\"}");
                    JSONObject json = JSONObject.fromObject(str);
                    List<JSONObject> postParams = JsonReader.getPostParams(json, dataSource, dataCollection,tableCodes);
                    saveMetaRelationParamsList.addAll(postParams);

                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage());
                    e.printStackTrace();
//                    continue;
                }
//            }

            Map<String, Object> params = new HashMap<>();
            params.put("token",1);
            params.put("objectInfo",saveMetaRelationParamsList);
            logger.info(JSONObject.fromObject(params));

            result = Httppost.doPost(saveMetaRelationsUrl, JSONArray.fromObject(saveMetaRelationParamsList).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"returnStatus\": 500,\"returnStatusStr\": \"失败:"+e.toString()+"\" }";
        }
        return result;
    }



    @RequestMapping("/sssss")
    public String getParamBoold(){
        ParamsBoold paramsBoold = new ParamsBoold();

        JSONArray boold = paramsBoold.getBoold(collectionUrl);

        String s = JSONArray.fromObject(boold).toString();
        logger.info("入参------>" + s);
        String result = "";
        try {
            result = Httppost.doPost(entityTableCodeUrl, s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/getEntityTableCodes")
    public String getparam() throws IOException {
        Params params = new Params();
        JSONArray anInterface = params.getInterface(collectionUrl);
        String s = JSONArray.fromObject(anInterface).toString();
        logger.info("入参------>" + s);
        String result = "";
        try {
            result = Httppost.doPost(entityTableCodeUrl, s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
