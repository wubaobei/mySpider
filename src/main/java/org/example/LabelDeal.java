package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LabelDeal implements IDoit {
    @Override
    public void doit() {
        String path = "d://example.html";
        List<String> list = getContent(path);
        String[] dealList = new String[]{"text7"};
        String s = String.join("\n", list);
        String midStr = "BNBNBN";


        for (String dl : dealList) {
            for (String one : oneMore(dl)) {
                while (s.contains(one)) {
                    String[] arr = spClass(s, one);
                    arr[1] = classMore(arr[1], one);
                    arr[1] = arr[1].replace(one, one.replace(dl, dl.substring(0, 2) + midStr + dl.substring(2)));
                    s = arr[0] + arr[1] + arr[2];
                }
            }
        }

        for (String dl : dealList) {
            s = s.replace(dl.substring(0, 2) + midStr + dl.substring(2), dl);
        }
        System.out.print(s);
    }

    /**
     * 在标签字符串每一行都加上class
     *
     * @param s
     * @param label
     * @return
     */
    private String classMore(String s, String label) {
        int ind = s.indexOf(" ");
        String t = s.substring(1, ind);
        String startT="<"+t+" "+label+">";
        String endT = "</" + t + ">";
        String[] arr = s.split("\n");
        int a = 0, b = 0;
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].contains(label) && !arr[i].contains(endT)) {
                arr[i] = startT + arr[i] + endT;
            } else if (!arr[i].contains(label)) {
                arr[i] = startT + arr[i];
                a++;
            }
            if (!arr[i].contains(endT)) {
                arr[i] = arr[i] + endT;
                b++;
            }
        }
        if (a != b) {
            System.out.println("便签异常，请处理");
        }
        return String.join("\n", arr);
    }

    /**
     * 根据标签内的值，抽取出整个标签
     *
     * @param one
     * @return
     */
    private String[] spClass(String s, String one) {
        int ind = s.indexOf(one);
        String s0 = s.substring(0, ind);
        String s1 = s.substring(ind);
        int ind0 = s0.lastIndexOf("<");
        String s00 = s0.substring(0, ind0);
        String s01 = s0.substring(ind0);
        int ind1 = s01.indexOf(" ");
        String t = s01.substring(1, ind1).trim();
        String endT = "</" + t + ">";
        if (!s1.contains(endT)) {
            return null;
        }
        int ind2 = s1.indexOf(endT) + endT.length();
        String s10 = s1.substring(0, ind2);
        String s11 = s1.substring(ind2);
        return new String[]{s00, s01 + s10, s11};
    }

    private String[] oneMore(String s) {
        return new String[]{"class=" + s, "class='" + s + "'", "class=\"" + s + "\""};
    }

    private List<String> getContent(String path) {
        List<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String t;
            while ((t = br.readLine()) != null) {
                if (t.trim().length() > 0) {
                    list.add(t.trim());
                }
            }
            br.close();
            fr.close();
            return list;
        } catch (Exception e) {
            return null;
        }
    }
}
