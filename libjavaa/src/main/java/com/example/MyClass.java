package com.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyClass {

    public static void main(String[] args){
        File file = new File("C:\\Users\\User\\Desktop\\ic_ratingstar_filled.png");

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            System.out.println("SILICON_FLICKR exception :::: " + ex);
            //Logger.getLogger(ConvertImage.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] bytes = bos.toByteArray();

        System.out.println("SILICON_FLICKR  bytes ::::::  " + bytes);

    }
}
