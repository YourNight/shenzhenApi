package com.bonc.shenzhen.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.SocketException;
import java.util.List;

/**
 * Created by liuhaolong on 2018/8/3.
 */
public class FtpOperateUtil {
    private static  String LOCAL_CHARSET = "";
    private static Logger log = Logger.getLogger(FtpOperateUtil.class);
    //连接ftp获取文件
    public static void getftpFileByPath( String path, String localPath, List<String> fileNames){
        try {
            FTPClient ftpClient= FtpOperateUtil.getFTPClient("", 0,"","");
            log.info("----------------开始从FTP上获取zip文件-----------------");
            getftpFile(ftpClient ,path, localPath, fileNames);
            log.info("----------------从FTP上获取zip文件结束-----------------");
            ftpClient.logout();
        } catch (IOException e) {
            log.info("----------------连接FTP出现异常-----------------");
            e.printStackTrace();
        }
    }


    /**
     *
     * Description: 获取FTP客户端
     * @param url 服务器ip
     * @param port 端口号
     * @param username 用户名
     * @param password 密码
     * @return FTPClient
     * @throws IOException
     * @throws SocketException
     * @see
     */
    public static FTPClient getFTPClient(String url, int port, String username, String password)
            throws SocketException, IOException {
        FTPClient ftp = new FTPClient();
        int reply;
        //连接FTP服务器
        ftp.setConnectTimeout(2000);
        ftp.connect(url, port);
        //登录
        ftp.login(username, password);
        reply = ftp.getReplyCode();
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return ftp;
        }
        if (FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))) {// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
            LOCAL_CHARSET = "UTF-8";
        } else {
            LOCAL_CHARSET = System.getProperty("file.encoding");
        }
        log.info("系统默认编码：" + LOCAL_CHARSET);
        ftp.setControlEncoding(LOCAL_CHARSET);

        return ftp;
    }


    //从ftp上获取文件
    public static void getftpFile(FTPClient ftpClient ,String path, String localPath, List<String> fileNames) {
        log.info("path="+path+" localPath="+localPath);
        FTPFile[] ftpFiles =null;
        String workdir ="";
        if (!new File(localPath).exists()){
            mkdirIfNotExist(localPath);
        }
        try {
            ftpFiles = ftpClient.listFiles(workdir+path);
        } catch (IOException e) {
            try {
                ftpClient.logout();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            log.error("获取FTP下的目录列表失败");
            e.printStackTrace();
        }

        for (FTPFile ftpFile : ftpFiles){
            log.info("进入到FTP目录"+workdir+path+"下");
            if (ftpFile.isDirectory()){
                if(!".".equals(ftpFile.getName())&&!"..".equals(ftpFile.getName())) {
                    String dirPath = path+'/'+ftpFile.getName();
                    getftpFile(ftpClient, dirPath, localPath, fileNames);
                }else {
                    log.info("目录为:"+ftpFile.getName()+"   不进入目录解析。");
                }
            }else {
                OutputStream is =null;
                //下载
                log.info("开始下载FTP目录"+workdir+path+"下的文件"+ftpFile.getName());
                try {
                    File localFile = new File(localPath + "/" + ftpFile.getName());
                    is = new FileOutputStream(localFile);
                    ftpClient.enterLocalPassiveMode();
                    String ftpFilePath = path+"/"+ftpFile.getName();
                    ftpClient.retrieveFile(ftpFilePath, is);
                    if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                        log.info("FTP目录下文件"+workdir+ftpFilePath+"下载失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        is.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                fileNames.add(localPath+ftpFile.getName());
                log.info("获取到文件"+localPath+ftpFile.getName());
            }
        }

    }

    private static synchronized boolean mkdirIfNotExist(String localPath){
        File localPathFile = new File(localPath);
        if (!localPathFile.exists()){
            return localPathFile.mkdirs();
        }
        return true;
    }




    public static void main(String[] arg)
            throws FileNotFoundException {
        String url = "192.168.50.39";
        int port = 21;
        String username = "test";
        String password = "test";
        File file = new File("D:\\文档\\我的string.txt");
        FileInputStream inputStream = new FileInputStream(file);
        try {
            FTPClient ftpClient = FtpOperateUtil.getFTPClient(url, port, username, password);
            FtpOperateUtil.getftpFile(ftpClient,"","",null);
//            FtpOperateUtil.uploadFile(ftpClient,"/metafiletest/test/yuan/peng", "我的string.txt", inputStream);
            //            FTPClient ftpClient2 = FtpUtil2.getFTPClient(url, port, username, password);
            //            ftpClient2.changeWorkingDirectory("metafiletest/test");
            //            ftpClient2.setFileType(FTPClient.BINARY_FILE_TYPE);
            //            String str =FtpUtil2.readFile(ftpClient2,"我的string.txt");
            //ftpClient.changeWorkingDirectory("metafiletest/test");
            //ftpClient.changeWorkingDirectory("/metafiletest/test");
            //System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
