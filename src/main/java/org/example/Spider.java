package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Spider {
    public String a(String path) throws IOException {
        StringBuilder sb=new StringBuilder();
        URL url = new URL(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        reader.close();
        return sb.toString();
    }

    public List<String> b(String path) throws IOException {
        List<String> res=new ArrayList<>();
        Document document = Jsoup.connect(path).get();

        // 获取所有的链接
        Elements links = document.select("a[href]");
        for(int i=0;i<links.size();i++){
            res.add(links.get(i).toString());
        }
        return res;
    }
}
