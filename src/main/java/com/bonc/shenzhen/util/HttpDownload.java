package com.bonc.shenzhen.util;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.client.HttpClient;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static jdk.nashorn.tools.Shell.SUCCESS;

/**
 * Java原生的API可用于发送HTTP请求，即java.net.URL、java.net.URLConnection，这些API很好用、很常用，
 * 但不够简便；
 *
 * 1.通过统一资源定位器（java.net.URL）获取连接器（java.net.URLConnection） 2.设置请求的参数 3.发送请求
 * 4.以输入流的形式获取返回内容 5.关闭输入流
 *
 * @author H__D
 *
 */
public class HttpDownload {


    /**
     *
     * @param urlPath
     *            下载路径
     * @param downloadDir
     *            下载存放目录
     * @return 返回下载文件
     */
    public static File downloadFile(String urlPath, String downloadDir) {
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            // 文件名
            String filePathUrl = httpURLConnection.getURL().getFile();
            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

            System.out.println("file length---->" + fileLength);

            URLConnection con = url.openConnection();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir  + fileFullName;
//            String path = downloadDir + File.separatorChar + fileFullName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }

    }
    public static  void download(String path,String downloadUrl) throws Exception{
        String getURL="http://172.16.36.161:8082/DataFlowExport_20180710100847.zip";               //这写你自己的
        URL getUrl = new URL(path);
// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
// 服务器
        connection.setConnectTimeout(25000);
        connection.setReadTimeout(25000);
        connection.connect();

        int status = connection.getResponseCode();
        if (status == 200) {
            DataInputStream in = new DataInputStream( connection.getInputStream());

           /* try {
                Charset gbk = Charset.forName("gbk");
                ZipInputStream Zin=new ZipInputStream(in,gbk);//输入源zip路径
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
                                System.out.println(str);
//                                jsonList.add(JSONObject.fromObject(str));
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
*/

            DataOutputStream out = new DataOutputStream(new FileOutputStream(downloadUrl));
            byte[] buffer = new byte[400000];
            int count = 0;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            out.close();
            in.close();
        } else {
            String strResponse = "error";
        }
        connection.disconnect();
    }

    public static void main(String[] args) {

        // 下载文件测试
//        downloadFile("http://172.16.74.74:8080/restApi/Koala.jpg", "C:/Users/BONC/AppData/Local/Temp/tomcat-docbase.6673209967711873770.8080/");
//        System.out.println();

        try {
            download("http://172.22.201.69:7001/datatable/rest/dataTables/export?directoryId=1","C:\\Users\\BONC\\Desktop\\018950\\asgfaisdgflkasdfasgfalsd.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

