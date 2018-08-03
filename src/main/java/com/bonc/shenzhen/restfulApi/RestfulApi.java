package com.bonc.shenzhen.restfulApi;

import com.bonc.shenzhen.util.Httppost;
import com.bonc.shenzhen.util.JsonReader;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liuhaolong on 2018/8/1.
 */
@RestController
public class RestfulApi {

    @Value("${file.url}")
    String url;

    @Value("${file.name}")
    String[] fileNames;

    @Value("${config.saveMetaRelationsUrl}")
    String saveMetaRelationsUrl;

    @RequestMapping(value = "/hello/{thing}",method = {RequestMethod.GET,RequestMethod.POST})
    public String hello(@PathVariable String thing){
        System.out.println(thing);
        HashMap<String, Object> hashMap = new HashMap<>();
        System.out.println("url:"+url);
        for (String fileName : fileNames) {
            System.out.println("fileName:"+fileName);
            hashMap.put(fileName,fileName);
        }
        return JSONObject.fromObject(hashMap).toString();
    }

    @RequestMapping(value = "/saveMetaRelation")
    public String saveMetaRelation(){
        System.out.println(saveMetaRelationsUrl);
//        List<JSONObject> postParams = JsonReader.getPostParams(null);
//        for (JSONObject postParam : postParams) {
//            String result = Httppost.doPost(saveMetaRelationsUrl, postParam);
//            JSONObject resultJson = JSONObject.fromObject(result);
//            if (Integer.parseInt(resultJson.get("returnStatus").toString())==200){
//                continue;
//            }
//        }
        return "success";
    }
}
