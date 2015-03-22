package com.grey.test.javabasic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Grey on 22.03.2015.
 */
public class MainTest {

    @Before
    public void setUp() {

    }


    @Test
    public void testMainTest() {
        assertEquals(5, new Main().test());
    }


    @After
    public void release() {

    }

}
