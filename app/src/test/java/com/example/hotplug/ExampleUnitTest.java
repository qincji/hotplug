package com.example.hotplug;

import org.junit.Test;

import static okhttp3.internal.Util.skipLeadingAsciiWhitespace;
import static okhttp3.internal.Util.skipTrailingAsciiWhitespace;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String input = "  http://www.baidu.  com";
        int pos = skipLeadingAsciiWhitespace(input, 0, input.length());
        System.out.println(pos);
        int limit = skipTrailingAsciiWhitespace(input, pos, input.length());
        System.out.println(limit);
    }
}