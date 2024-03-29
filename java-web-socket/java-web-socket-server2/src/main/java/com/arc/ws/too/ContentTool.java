package com.arc.ws.too;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class ContentTool {


    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch ( Exception exception) {
            exception.printStackTrace();
        }
        return buffer;
    }
}
