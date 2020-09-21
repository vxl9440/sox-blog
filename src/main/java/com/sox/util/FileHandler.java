package com.sox.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {


    /**
     * @param filePath path of a file
     * @return content of a file
     */
    public static String readFile(String filePath){
        StringBuilder content = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = br.readLine()) != null){
                content.append(line);
            }
        }catch(IOException ioe){
            System.out.println(ioe.toString());
        }
        return content.toString();
    }
}
