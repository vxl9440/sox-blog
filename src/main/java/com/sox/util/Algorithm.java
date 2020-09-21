package com.sox.util;

import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Algorithm {

    public static String getContentIgnoreImages(String originalText){
        int pivot = originalText.indexOf("<img");
        return originalText.substring(0,pivot);
    }

}
