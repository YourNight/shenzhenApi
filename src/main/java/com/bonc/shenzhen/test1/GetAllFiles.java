package com.bonc.shenzhen.test1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetAllFiles {
    static List<String> s = new ArrayList<>();
    static List<String> z = new ArrayList<>();

    UnZip unZip = new UnZip();

    String x = unZip.input1;
    String y = unZip.input2;

    /**
     * @Description: 在数据采集处，获取相应字段
     * @Author: gwh
     * @Date: 2018-08-06
     */

    public List<String> getJsonFiles1() {
        new UnZip().unzipfile1();
        getFiles1(x);
        return s;
    }

    /**
     * @Description: 在数据源处获取 ，获取相应字段
     * @Author: gwh
     * @Date: 2018-08-06
     */

    public List<String> getJsonFiles2() {
        new UnZip().unzipfile2();
        getFiles2(y);
        return z;
    }

    /**
     * 递归获取某路径下的所有文件，文件夹，并输出
     */

    public void getFiles1(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
//                    System.out.println("目录：" + files[i].getPath());
                    getFiles1(files[i].getPath());
                } else {
                    System.out.println("文件：" + files[i].getPath());
                    s.add(files[i].getPath());

                }
            }
        } else {
            s.add(file.getPath());
            System.out.println("文件：" + file.getPath());
        }
    }

    public void getFiles2(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
//                    System.out.println("目录：" + files[i].getPath());
                    getFiles2(files[i].getPath());
                } else {
                    System.out.println("文件：" + files[i].getPath());
                    z.add(files[i].getPath());

                }
            }
        } else {
            z.add(file.getPath());
            System.out.println("文件：" + file.getPath());
        }
    }
   /* public static void main(String[] args) {
        new GetAllFiles().getJsonFiles1();
        for (String x : s) {
            System.out.println(x);
        }

        new GetAllFiles().getJsonFiles2();
        for (String x : s) {
            System.out.println(x);
        }
    }*/
}