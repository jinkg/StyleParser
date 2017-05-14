package com.yalin.stylepaser.test;

import com.yalin.styleparser.ResponseParser;
import com.yalin.styleparser.StyleParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * jinyalin
 * on 2017/5/14.
 */
public class ParserTest {
    @Test
    public void testParser() throws Exception {
        File file = new File("test.txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        StringBuilder sb = new StringBuilder();
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            sb.append(decodedString);
        }
        in.close();
        ResponseParser.parseResponse(sb.toString());
    }

    @Test
    public void testDemos() throws Exception{
        String[] demo1 = new String[]{"https://www.wikiart.org/en/john-miller/coconut-beach"};
        String[] demo2 = new String[]{"https://www.wikiart.org/en/agnes-lawrence-pelton/untitled-1931"};
        String[] demo3 = new String[]{"https://www.wikiart.org/en/agnes-lawrence-pelton/even-song-1930"};
        String[] demo4 = new String[]{"https://www.wikiart.org/en/agnes-lawrence-pelton/fire-sounds-1930"};
        String[] demo5 = new String[]{"https://www.wikiart.org/en/agnes-lawrence-pelton/spring-moon-1942"};
        StyleParser.main(demo1);
        StyleParser.main(demo2);
        StyleParser.main(demo3);
        StyleParser.main(demo4);
        StyleParser.main(demo5);
    }
}
