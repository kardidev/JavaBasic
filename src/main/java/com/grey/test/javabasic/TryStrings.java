package com.grey.test.javabasic;

import java.io.UnsupportedEncodingException;

/**
 * Created by Grey on 22.03.2015.
 */
public class TryStrings {

    public static void testIt() throws UnsupportedEncodingException {
        String str = new String("111");
        String newStr = str.intern();
        String testStr = String.valueOf("111");

        System.out.println(str == newStr);
        System.out.println(newStr == testStr);


        // -----------------------------------------------------------------
        StringBuffer sb = new StringBuffer();
        StringBuilder sbb = new StringBuilder();


        byte[] strBytes = new String("блябля бля").getBytes("UTF-8");
        String string = new String(strBytes, "UTF-8");
        System.out.println(string);
    }

}
