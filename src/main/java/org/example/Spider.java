package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Spider {
    private String a(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL url = new URL(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        reader.close();
        return sb.toString();
    }

    private String toFile(String path) {
        return path.replace(":", "").replace("/", "").replace("\\", "");
    }

    private String getHtml(String path) throws IOException {
        String fileName = toFile(path);
        File file = new File(fileName);
        if (file.exists()) {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            reader.close();
            return sb.toString();
        }
        String save = a(path);
        write(save, file);
        return save;
    }

    private void write(String s, File f) throws IOException {
        FileWriter fw = new FileWriter(f);
        fw.write(s);
        fw.close();
    }

    public List<String> b(String path) throws IOException {
        List<String> res = new ArrayList<>();
        Document document = Jsoup.parse(getHtml(path));

        // 获取所有的链接
        Elements links = document.select("a[href]");

        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < links.size(); i++) {
            String att = getAtt(links.get(i));
            if (att == null) {
                continue;
            }
            boolean exist = isExist(att, list);
            if (!exist) {
                List<String> l = new ArrayList<>();
                l.add(att);
                list.add(l);
            }
        }
        List<String> max = new ArrayList<>();
        for (List<String> l : list) {
            if (l.size() > max.size()) {
                max = l;
            } else if (l.size() == max.size()) {
                if (l.get(0).length() < max.get(0).length()) {
                    max = l;
                }
            }
        }
        return max;
    }

    private boolean isExist(String att, List<List<String>> list) {
        for (List<String> l : list) {
            if (isSameGroup(l.get(0), att)) {
                l.add(att);
                return true;
            }
        }
        return false;
    }

    private String getAtt(Element element) {
        if (!element.attributes().isEmpty()) {
            return element.attributes().iterator().next().getValue();
        }
        return null;
    }

    private boolean isSameGroup(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        for (int i = 0; i < a.length(); i++) {
            char a0 = a.charAt(i);
            char b0 = b.charAt(i);
            if (a0 == b0) {
                continue;
            }
            if (a0 >= '0' && a0 <= '9' && b0 >= '0' && b0 <= '9') {
                continue;
            }
            return false;
        }
        return true;
    }
}
