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
public class UnZip {
    public void test() {

    }

    public static void main(String[] args) {
        String zip = "C:\\Users\\BONC\\Desktop\\018950\\深圳\\数据样例\\DataFlowExport_20180710100847.zip";
//        String input = "C:\\Users\\BONC\\Desktop\\018950\\脚本\\DataFlowExport";
        unzip(zip/*,input*/);
    }

    public static synchronized List<JSONObject> unzip(String zipFileName/*, String inputFile*/){
        List<JSONObject> jsonList = new ArrayList<>();
        long startTime=System.currentTimeMillis();
        try {
            Charset gbk = Charset.forName("gbk");
            ZipInputStream Zin=new ZipInputStream(new FileInputStream(
                    zipFileName),gbk);//输入源zip路径
            BufferedInputStream Bin=new BufferedInputStream(Zin);
//            String Parent=inputFile; //输出路径（文件夹目录）
//            File Fout=null;
            ZipEntry entry;

//            File file = new File(zipFileName);
//
//            System.out.println("filename:---->"+file.getName());
            while((entry = Zin.getNextEntry())!=null){
                try {
                    if (!entry.isDirectory()){
                        System.out.println(entry.getName());
                        if (!entry.getName().contains("DirMapping")){
//                            Fout=new File(Parent,entry.getName());
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Bin));
                            String str = "";
                            String s;
                            while ((s = bufferedReader.readLine())!=null) {
                                str = str + s ;
                            }
//                            System.out.println(JSONObject.fromObject(str));
                            jsonList.add(JSONObject.fromObject(str));
                        }
//                        Fout=new File(Parent,entry.getName());
//                        if(!Fout.exists()){
//                            (new File(Fout.getParent())).mkdirs();
//                        }
//                        FileOutputStream out=new FileOutputStream(Fout);
//                        BufferedOutputStream Bout=new BufferedOutputStream(out);
//                        int b;
//                        while((b=Bin.read())!=-1){
//                            Bout.write(b);
//                        }
//                        Bout.close();
//                        out.close();
//                        System.out.println(Fout+"解压成功");

                    }/*else {
                        Fout=new File(Parent,entry.getName());
                        if(!Fout.exists()){
                            Fout.mkdirs();
                        }
                        continue;
                    }*/
                }catch (Exception e){
                    e.printStackTrace();
//                    System.out.println(Fout+"解压失败，跳过");
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
