package com.bonc.shenzhen.util;

import net.sf.json.JSONObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by liuhaolong on 2018/8/1.
 */
public class UnZipFromPath {
    public void test() {

    }

    public static void main(String[] args) {
        String zip = "C:\\Users\\BONC\\Desktop\\018950\\深圳\\数据样例\\DataFlowExport_20180710100847.zip";
        System.out.println(unzip(zip));
    }

    public static synchronized List<JSONObject> unzip(String zipFileName){
        List<JSONObject> jsonList = new ArrayList<>();
        long startTime=System.currentTimeMillis();
        try {
            Charset gbk = Charset.forName("gbk");
            ZipInputStream Zin=new ZipInputStream(new FileInputStream(zipFileName),gbk);//输入源zip路径
            BufferedInputStream Bin=new BufferedInputStream(Zin);
            ZipEntry entry;
            while((entry = Zin.getNextEntry())!=null){
                try {
                    if (!entry.isDirectory()){
                        System.out.println(entry.getName());
                        if (!entry.getName().contains("DirMapping")){
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Bin));
                            String str = "";
                            String s;
                            while ((s = bufferedReader.readLine())!=null) {
                                str = str + s ;
                            }
                            jsonList.add(JSONObject.fromObject(str));
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
            Bin.close();
            Zin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("耗费时间： "+(endTime-startTime)+" ms");
        System.out.println(jsonList.size());
        return jsonList;
    }
}