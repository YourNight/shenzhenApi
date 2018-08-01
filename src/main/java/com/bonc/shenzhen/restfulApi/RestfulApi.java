package com.bonc.shenzhen.restfulApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuhaolong on 2018/8/1.
 */
@RestController
public class RestfulApi {

    @Value("${file.url}")
    String url;

    @Value("${file.name}")
    String[] fileNames;

    @RequestMapping(value = "/hello/{thing}",method = RequestMethod.GET)
    public String hello(@PathVariable String thing){
        System.out.println(url);
        for (String fileName : fileNames) {
            System.out.println(fileName);
        }
        return thing;
    }
}
