/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chef.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Staff
 */
public class ImageManager {

    public String uploadImage(byte[] decodedImage, String folderPath, String imageName) throws FileNotFoundException, IOException {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
       // byte[] decodedImage = DatatypeConverter.parseBase64Binary(encodedImage);
       // System.out.println("encoded : " + encodedImage);
        System.out.println("decoded : " + decodedImage);
        String path = folder.getAbsolutePath() + File.separator + imageName + ".png";
        File file = new File(path);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(decodedImage);
        fos.close();
        return path;
    }
    
}
