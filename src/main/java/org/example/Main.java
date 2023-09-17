package org.example;

import java.io.IOException;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        Spider spider = new Spider();
        System.out.println("begin");
        List<String> as = spider.b("http://mana.stmn1.com/Nee/index.htm");
        for (String s : as)
            System.out.println(s);
        System.out.println("over");
    }
}