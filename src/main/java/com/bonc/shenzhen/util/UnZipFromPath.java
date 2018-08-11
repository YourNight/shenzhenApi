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

  /*  public static void main(String[] args) {
        read();
    }*/

    public static  List<JSONObject> read(){
        String zip = "H:\\SoftwareCache\\OneDriver\\OneDrive\\工作资料\\项目\\63所\\解析测试\\DataCollectionExport_20180710102020.zip";
        return  unzip(zip);
    }

    public static  List<JSONObject> readdata(){
        String zip = "H:\\SoftwareCache\\OneDriver\\OneDrive\\工作资料\\项目\\63所\\解析测试\\DatasourceExport_20180710094013.zip";
        return  unzip(zip);
    }

    public static void main(String[] args) {
        String zip = "H:\\SoftwareCache\\OneDriver\\OneDrive\\工作资料\\项目\\63所\\解析测试\\DataCollectionExport_20180710102020.zip";
        System.out.println(unzip(zip));
        try {
            System.out.println("******************开始******************");
//            String s = Httppost.doPost("http://localhost:8080/restApi/hello/aaaaa", "[{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_TRAILIFNO\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_TRAILIFNO\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_EXTERNAL_PEOPLELIB\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_EXTERNAL_PEOPLELIB\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_PEOPLERISK\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_PEOPLERISK\"},{\"schema\":\"\",\"resourceCode\":\"hjw_test\",\"tableCode\":\"iae_designer_template\"},{\"schema\":\"\",\"resourceCode\":\"hjw_test\",\"tableCode\":\"iae_designer_template_copy_1\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_goal\",\"tableCode\":\"T_TRAILIFNO\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_goal\",\"tableCode\":\"t_trailifno_py\"},{\"schema\":\"CD\",\"resourceCode\":\"hanxu_oracle\",\"tableCode\":\"TB_DPSR_IDCARD\"},{\"schema\":\"CD\",\"resourceCode\":\"hanxu_oracle\",\"tableCode\":\"TB_DPSR_IDCARD_bak\"},{\"schema\":\"\",\"resourceCode\":\"hjw_test\",\"tableCode\":\"iae_designer_template\"},{\"schema\":\"\",\"resourceCode\":\"hjw_test\",\"tableCode\":\"test_2\"},{\"schema\":\"\",\"resourceCode\":\"hjw_mysql_test\",\"tableCode\":\"iae_designer_template\"},{\"schema\":\"\",\"resourceCode\":\"hjw_mysql_test\",\"tableCode\":\"test_2\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_TRAILIFNO\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"t_trailifno\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_EXTERNAL_PEOPLELIB\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"t_external_peoplelib\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_PEOPLERISK\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"t_peoplerisk\"}]");
            String s = Httppost.doPost("http://172.16.11.9:8189/webapp/rest/entityTableService/getEntityTableByCode/tenant1",
                    "[{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_TRAILIFNO\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_TRAILIFNO\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_EXTERNAL_PEOPLELIB\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_EXTERNAL_PEOPLELIB\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_PEOPLERISK\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_PEOPLERISK\"},{\"schema\":\"\",\"resourceCode\":\"hjw_test\",\"tableCode\":\"iae_designer_template\"},{\"schema\":\"\",\"resourceCode\":\"hjw_test\",\"tableCode\":\"iae_designer_template_copy_1\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_goal\",\"tableCode\":\"T_TRAILIFNO\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_goal\",\"tableCode\":\"t_trailifno_py\"},{\"schema\":\"CD\",\"resourceCode\":\"hanxu_oracle\",\"tableCode\":\"TB_DPSR_IDCARD\"},{\"schema\":\"CD\",\"resourceCode\":\"hanxu_oracle\",\"tableCode\":\"TB_DPSR_IDCARD_bak\"},{\"schema\":\"\",\"resourceCode\":\"hjw_test\",\"tableCode\":\"iae_designer_template\"},{\"schema\":\"\",\"resourceCode\":\"hjw_test\",\"tableCode\":\"test_2\"},{\"schema\":\"\",\"resourceCode\":\"hjw_mysql_test\",\"tableCode\":\"iae_designer_template\"},{\"schema\":\"\",\"resourceCode\":\"hjw_mysql_test\",\"tableCode\":\"test_2\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_TRAILIFNO\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"t_trailifno\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_EXTERNAL_PEOPLELIB\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"t_external_peoplelib\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"T_PEOPLERISK\"},{\"schema\":\"ENGINEDB\",\"resourceCode\":\"qingdao_oracle_source\",\"tableCode\":\"t_peoplerisk\"}]");
            System.out.println(s);
            System.out.println("******************结束******************");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            System.out.println(JSONObject.fromObject(str));
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
