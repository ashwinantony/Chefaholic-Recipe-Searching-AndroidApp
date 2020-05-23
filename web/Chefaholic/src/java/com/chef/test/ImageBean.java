
package com.chef.test;

import java.io.Serializable;


public class ImageBean implements Serializable{
    public static int serialVersionUID = 1;
    byte[] image = null;
    String username = "";

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public static int getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(int serialVersionUID) {
        ImageBean.serialVersionUID = serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
