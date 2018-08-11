package com.bonc.shenzhen.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsBoold {

    public static  JSONArray getBoold(String collectionUrl ,JSONObject tableCodes) {

        JSONArray object = null;
        List<Object> metaRelations = new ArrayList();
        List<Object> saveRelationParams = new ArrayList();
        List<JSONObject> list  = UnZipFromPath.unzip(collectionUrl);

        for (int i = 0; i < list.size(); i++) {
            JSONObject dataCollectionJson = list.get(i);
            String sourceTable = dataCollectionJson.getString("sourceTable");
            String targetTable = dataCollectionJson.getString("targetTable");
            String resourceCode = dataCollectionJson.getString("sourceDataSourceId");
            String schema = dataCollectionJson.getString("sourceTableSchema");
            String dataCollectionTaskId = dataCollectionJson.getString("dataCollectionTaskId");
            JSONArray fieldMappings = dataCollectionJson.getJSONArray("fieldMappings");
            List<Object> metaRelDetails = new ArrayList();
            for (int k = 0; k < fieldMappings.size(); k++) {
                Map relDetail = new HashMap();
                Map calcRule = new HashMap();
                Object obj = fieldMappings.get(k);
                JSONObject fieldMapping = JSONObject.fromObject(obj);
                String src = fieldMapping.get("sourceField").toString();
                String dest = fieldMapping.get("targetField").toString();
                calcRule.put("id", "");
                calcRule.put("value", "");
                calcRule.put("lobValue", "");
                calcRule.put("desc", "");
                calcRule.put("breifSql", "");
                relDetail.put("srcColumn", src);
                relDetail.put("desColumn", dest);
                relDetail.put("ruleType", "0");
                relDetail.put("calcRule", calcRule);
                metaRelDetails.add(relDetail);
            }
            Map relationMap = new HashMap();

            String desId = tableCodes.get(resourceCode + "-" + schema + "-" + targetTable)!=null?tableCodes.get(resourceCode + "-" + schema + "-" + targetTable).toString():"";
            String srcId = tableCodes.get(resourceCode + "-" + schema + "-" + sourceTable)!=null?tableCodes.get(resourceCode + "-" + schema + "-" + sourceTable).toString():"";
            relationMap.put("desEntityId", desId);
            relationMap.put("desEntityCode", targetTable);
            relationMap.put("srcEntityId", srcId);
            relationMap.put("srcEntityCode", sourceTable);
            relationMap.put("metaRelDetails", metaRelDetails);
            metaRelations.add(relationMap);
            Map objectInfo = new HashMap();
            objectInfo.put("systemId", 2);
            objectInfo.put("processId", dataCollectionTaskId);
            objectInfo.put("relationType", 0);
            objectInfo.put("tenantId", "tenant1");
            objectInfo.put("metaRelations",metaRelations);

            Map<String, Object> paramObject = new HashMap<>();
            paramObject.put("token","1");
            paramObject.put("objectInfo",objectInfo);
            saveRelationParams.add(paramObject);
            object = JSONArray.fromObject(saveRelationParams);
        }

        return object;
    }

}
