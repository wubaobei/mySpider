package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        load(NEE);
    }

    private static void load(Pair book) throws IOException {
        Spider spider = new Spider();
        HashMap<String, List<Pair>> map = new HashMap<>();
        map.put(book.getUrl(), spider.b(book.getUrl()));
        while (true) {
            Pair k = null;
            for (Map.Entry<String, List<Pair>> e : map.entrySet()) {
                for (Pair s : e.getValue()) {
                    if (!s.getUrl().contains("index")) {
                        continue;
                    }
                    if (map.containsKey(s.getUrl())) {
                        continue;
                    }
                    k = s;
                    break;
                }
                if (k != null) {
                    break;
                }
            }
            if (k == null) {
                break;
            }
            List<Pair> list = spider.b(k.getUrl());
            if (list == null || list.size() == 0) {
                throw new RuntimeException("获取" + k + "的下属链接失败");
            }
            map.put(k.getUrl(), list);
        }
        showMap(map, book);
    }

    private static void showMap(HashMap<String, List<Pair>> map, Pair book) throws IOException {
        log(book.getTitle() + "\r\n");
        showMap(map, book.getUrl(), 1);

        write(sb.toString(), new File("d://aim//" + book.getTitle() + ".txt"));
    }


    private static void write(String s, File f) throws IOException {
        FileWriter fw = new FileWriter(f);
        fw.write(s);
        fw.close();
    }

    private static void showMap(HashMap<String, List<Pair>> map, String c, int deep) {
        if (!map.containsKey(c)) {
            return;
        }
        for (Pair p : map.get(c)) {
            for (int i = 0; i < deep; i++) {
                log("-");
            }
            if (p.isIndex()) {
                log(p.getTitle() + "\r\n");
            } else {
                log(p.toString() + "\r\n");
            }
            showMap(map, p.getUrl(), deep + 1);
        }
    }

    private static Pair NEE = new Pair("http://mana.stmn1.com/Nee/index.htm", "倪柝声文集");
    private static Pair LS = new Pair("http://mana.stmn1.com/smdj8/index.html", "生命读经");

    private static StringBuilder sb = new StringBuilder();

    private static void log(String s) {
        sb.append(s);
        System.out.print(s);
    }
}