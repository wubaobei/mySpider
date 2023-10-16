package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FenZu {
    private List<String> list = new ArrayList<>();

    public void createData() throws IOException {
        File f = new File("D:\\cate");
        for (File file : f.listFiles()) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String s;
            while ((s = br.readLine()) != null) {
                if (s.contains("http")) {
                    int ind = s.indexOf("http");
                    String st = s.substring(ind).split(" ")[0];
                    list.add(st);
                }
            }

            br.close();
            fr.close();
        }
        System.out.println("创建数量：" + list.size());
    }

    public void group(int num) {
        int[] arr = new int[num];
        for (String s : list) {
            arr[f1(s, num)]++;
        }
        for (int i = 0; i < num; i++) {
            System.out.println(i + ":" + arr[i]);
        }
        System.out.print("标准差：" + bc(arr));
    }

    private double bc(int[] arr) {
        double sum = 0;
        for (int a : arr) {
            sum += a;
        }
        double ave = sum / arr.length;
        double fc = 0d;
        for (int a : arr) {
            fc += (a - ave) * (a - ave);
        }
        fc /= arr.length;
        return Math.sqrt(fc);
    }

    private int f1(String s, int max) {
        int n = 0;
        for (char c : s.toCharArray()) {
            n += c;
        }
        return n % max;
    }

    private int f2(String s, int max) {
        byte[] s2= org.apache.commons.codec.digest.DigestUtils.md5(s);
        int n = 0;
        for (byte c : s2) {
            n += c+1000;
        }
        return n % max;
    }
}
