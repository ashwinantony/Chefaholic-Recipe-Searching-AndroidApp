/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chef.bean;

import java.io.Serializable;

/**
 *
 * @author Staff
 */
public class RecipieBean implements Serializable {

    private static final long serialVersionUID = 1l;
    String ingredients = "";
    String making = "";
    String foodName = "";
    String categories = "";
    String date = "";
    String comments = "";
    int recipieId = 0;
    String username = "";
    byte[] pic = null;
    

    
    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRecipieId() {
        return recipieId;
    }

    public void setRecipieId(int recipieId) {
        this.recipieId = recipieId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMaking() {
        return making;
    }

    public void setMaking(String making) {
        this.making = making;
    }
}
