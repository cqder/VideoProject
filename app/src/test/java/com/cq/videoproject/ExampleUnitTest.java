package com.cq.videoproject;

import android.content.SyncStatusObserver;
import android.util.Log;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
//        String  path = "/storage/emulated/0/DCIM/Camera/VID_20171212_141501.mp4";
//        String reg ="^([\\S]+[/])([\\S]+\\.[mp4|MP4])$";
        String path = "q.mp4";
        String reg ="^q[\\.]mp4$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(path);
//        Log.w("test","result "+ matcher );
        String test  = "/storage/emulated/0/DCIM/Camera/VID_20171212_141501.mp4";
                //split
        System.out.println("find-> "+matcher.find());
        String[] name = test.split("\\/");

        for (String s : name){
            System.out.println("find-> "+s);
        }
        System.out.println("find-> "+name[name.length-1]);
    }

    @Test
    public void test (){
        int a = 45648;
        int b = 456480;
        System.out.println("---------------------"+b/a);
    }
}