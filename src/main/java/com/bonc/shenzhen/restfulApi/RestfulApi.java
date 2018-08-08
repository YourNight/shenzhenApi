package com.bonc.shenzhen.restfulApi;

import com.bonc.shenzhen.test1.Params;
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

    static JSONArray dataSource;
    static JSONArray dataCollection;

    @RequestMapping(value = "/hello/{thing}",method = {RequestMethod.GET,RequestMethod.POST})
    public String hello(@PathVariable String thing){
        System.out.println(thing);
        HashMap<String, Object> hashMap = new HashMap<>();
        System.out.println("url:"+url);
        return JSONObject.fromObject(hashMap).toString();
    }

    @RequestMapping(value = "/saveMetaRelation")
    public String saveMetaRelation(HttpServletRequest request){
        System.out.println(saveMetaRelationsUrl);
        String path = request.getServletContext().getRealPath("/");
        path = path.replaceAll("\\\\", "/");
        System.out.println(path);

        logger.info(path);
        System.out.println(request.getContextPath());
        System.out.println(request.getRequestURI());

//        dataSource = new ParseDataSource().getJson();

        if (dataSource==null||dataCollection==null) return "数据源信息为空，请先调用getEntityTableCodes接口";

        List<JSONObject> jsonList = UnZipFromPath.unzip(url);
        List<JSONObject> saveMetaRelationParamsList = new ArrayList<>();
        for (JSONObject json : jsonList) {
            try {

                List<JSONObject> postParams = JsonReader.getPostParams(json,dataSource,dataCollection);
                saveMetaRelationParamsList.addAll(postParams);
                for (JSONObject postParam : postParams) {
//                    String result = Httppost.doPost(saveMetaRelationsUrl, postParam);
//                    JSONObject resultJson = JSONObject.fromObject(result);
//                    if (Integer.parseInt(resultJson.get("returnStatus").toString())==200){
//                        continue;
//                    }
//                    logger.info(postParam);
                }
            }catch (Exception e){
                logger.error(e.getLocalizedMessage());
                e.printStackTrace();
                continue;
            }
        }
            logger.info(saveMetaRelationParamsList);
        String result = null;
        try {
            result = Httppost.doPost(saveMetaRelationsUrl, JSONArray.fromObject(saveMetaRelationParamsList).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/getEntityTableCodes")
    public String getparam() throws IOException {
              Params params = new Params();
               JSONArray anInterface = params.getInterface();
               String s = JSONArray.fromObject(anInterface).toString();
              logger.info("入参------>"+s);
             String result = "";
              try {
                      result = Httppost.doPost(entityTableCodeUrl, s);
                  } catch (Exception e) {
                       e.printStackTrace();
                   }
               return result;
           }

    public JSONArray getDataSource() {
        return dataSource;
    }

    public static void setDataSource(JSONArray dataSource) {
        dataSource = dataSource;
    }

    public JSONArray getDataCollection() {
        return dataCollection;
    }

    public static void setDataCollection(JSONArray dataCollection) {
        dataCollection = dataCollection;
    }
}
