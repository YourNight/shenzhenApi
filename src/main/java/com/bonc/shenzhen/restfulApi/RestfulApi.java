package com.bonc.shenzhen.restfulApi;

import com.bonc.shenzhen.test1.Params;
import com.bonc.shenzhen.test1.ParseDataCollection;
import com.bonc.shenzhen.test1.ParseDataSource;
import com.bonc.shenzhen.util.Httppost;
import com.bonc.shenzhen.util.JsonReader;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuhaolong on 2018/8/1.
 */
@RestController
public class RestfulApi {

    private static Logger logger = Logger.getLogger(RestfulApi.class);
    @Value("${file.url}")
    String url;

    @Value("${config.saveMetaRelationsUrl}")
    String saveMetaRelationsUrl;
    @Value("${config.entityTableCodeUrl}")
    String entityTableCodeUrl;

    public static JSONArray dataSource;
    public static JSONArray dataCollection;
    public static JSONObject tableCodes;
    public static JSONArray booldParam;

    @RequestMapping(value = "/hello/{thing}", method = {RequestMethod.GET, RequestMethod.POST})
    public String hello(@PathVariable String thing) {
        System.out.println(thing);
        HashMap<String, Object> hashMap = new HashMap<>();
        return "you say :"+thing;
    }

    @RequestMapping(value = "/saveMetaRelation")
    public String saveMetaRelation(HttpServletRequest request) {

//        dataSource = new ParseDataSource().getJson();

        String result = null;
        try {
            if (dataSource == null || dataCollection == null || tableCodes == null) {
                Params params = new Params();
                JSONArray anInterface = params.getInterface();
                tableCodes = JSONObject.fromObject(Httppost.doPost(entityTableCodeUrl, anInterface.toString()));
            }

            List<JSONObject> jsonList = UnZipFromPath.unzip(url);
            List<JSONObject> saveMetaRelationParamsList = new ArrayList<>();
            for (JSONObject json : jsonList) {
                try {

                    List<JSONObject> postParams = JsonReader.getPostParams(json, dataSource, dataCollection,tableCodes);
                    saveMetaRelationParamsList.addAll(postParams);
                    for (JSONObject postParam : postParams) {
//                    String result = Httppost.doPost(saveMetaRelationsUrl, postParam);
//                    JSONObject resultJson = JSONObject.fromObject(result);
//                    if (Integer.parseInt(resultJson.get("returnStatus").toString())==200){
//                        continue;
//                    }
//                    logger.info(postParam);
                    }
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage());
                    e.printStackTrace();
                    continue;
                }
            }
            logger.info(saveMetaRelationParamsList);


            result = Httppost.doPost(saveMetaRelationsUrl, JSONArray.fromObject(saveMetaRelationParamsList).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/sssss")
    public String getBoold() {
        try {
            if (dataSource == null || dataCollection == null || tableCodes == null) {
                Params params = new Params();
                JSONArray anInterface = params.getInterface();
                tableCodes = JSONObject.fromObject(Httppost.doPost(entityTableCodeUrl, anInterface.toString()));
                ParseDataCollection parseDataCollection = new ParseDataCollection();
                booldParam = parseDataCollection.getBooldParam();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booldParam.toString();
    }

    @RequestMapping("/getEntityTableCodes")
    public String getparam() throws IOException {
        Params params = new Params();
        JSONArray anInterface = params.getInterface();
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
