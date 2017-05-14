package com.yalin.stylepaser.test;

import com.yalin.styleparser.ResponseParser;
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
}
