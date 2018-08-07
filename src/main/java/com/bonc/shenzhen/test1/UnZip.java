package com.bonc.shenzhen.test1;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by liuhaolong on 2018/8/1.
 */
public class UnZip {

   /* public static void main(String[] args) {
        new UnZip().unzipfile1();
        new UnZip().unzipfile2();
    }*/

    String zip1 = "H:\\SoftwareCache\\OneDriver\\OneDrive\\工作资料\\项目\\63所\\解析测试\\DataCollectionExport_20180710102020.zip";
    String zip2 = "H:\\SoftwareCache\\OneDriver\\OneDrive\\工作资料\\项目\\63所\\解析测试\\DatasourceExport_20180710094013.zip";
    String input1 = "H:\\SoftwareCache\\OneDriver\\OneDrive\\工作资料\\项目\\63所\\解析测试\\ad";
    String input2 = "H:\\SoftwareCache\\OneDriver\\OneDrive\\工作资料\\项目\\63所\\解析测试\\ae";

    /**
     * @Description: 解压数据采集
     * @Param: []
     * @return: void
     */
    public void unzipfile1() {
        unzip(zip1, input1);
    }

    /**
     * @Description: 解压数据源
     * @Param: []
     * @return: void
     */

    public void unzipfile2() {
        unzip(zip2, input2);
    }

    public void unzip(String zipFileName, String inputFile) {
        long startTime = System.currentTimeMillis();
        try {
            Charset gbk = Charset.forName("gbk");
            ZipInputStream Zin = new ZipInputStream(new FileInputStream(
                    zipFileName), gbk);//输入源zip路径
            BufferedInputStream Bin = new BufferedInputStream(Zin);
            String Parent = inputFile; //输出路径（文件夹目录）
            File Fout = null;
            ZipEntry entry;

            File file = new File(zipFileName);

            while ((entry = Zin.getNextEntry()) != null) {
                try {
                    if (!entry.isDirectory()) {
                        Fout = new File(Parent, entry.getName());
                        if (!Fout.exists()) {
                            (new File(Fout.getParent())).mkdirs();
                        }
                        FileOutputStream out = new FileOutputStream(Fout);
                        BufferedOutputStream Bout = new BufferedOutputStream(out);
                        int b;
                        while ((b = Bin.read()) != -1) {
                            Bout.write(b);
                        }
                        Bout.close();
                        out.close();
                        System.out.println(Fout + "解压成功");

                    } else {
                        Fout = new File(Parent, entry.getName());
                        if (!Fout.exists()) {
                            Fout.mkdirs();
                        }
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(Fout + "解压失败，跳过");
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
        long endTime = System.currentTimeMillis();
        System.out.println("耗费时间： " + (endTime - startTime) + " ms");
    }


}
