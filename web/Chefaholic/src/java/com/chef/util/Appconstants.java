package com.chef.util;

public interface Appconstants {
    //--------------------------mysql connection--------------------------------

    public static String DRIVER = "com.mysql.jdbc.Driver";
    public static String URL = "jdbc:mysql://localhost:3306/chefaholicdb";
    public static String MYSQL_USERNAME = "root";
    public static String MYSQL_PASSWORD = "mysql";
    //--------------------------------------------------------------------------
    //---------------------------login------------------------------------------
    public static String INVALID = "invalid";
    public static String USER_TYPE = "user";
    //--------------------------------------------------------------------------
    //-----------------------registration---------------------------------------
    public static String FALSE = "false";
    public static String TRUE = "true";
    public static String EXIST = "exist";
    public static String NOT_EXIST = "not exist";
    //--------------------------------------------------------------------------
    //----------------------------add recipie-----------------------------------
    public static String FAILED = "failed";
    public static String SUCCESS = "success";
    //--------------------------------------------------------------------------
    //--------------------------get user details--------------------------------
    public static String NAME = "name";
    public static String PASSWORD = "password";
    public static String EMAIL = "email";
    public static String MOBILE = "mobile";
    public static String ADDRESS = "address";
    public static String USERNAME = "username";
    public static String PIC = "pic";
    public static String FOLLOWERS = "followers";
    //--------------------------------------------------------------------------
    //-------------------------upload profile pic-------------------------------
    public static String PROFILE_PIC = "profile_pic";
    public static String NOT_UPLOADED = "not uploaded";
    public static String UPLOADED = "uploaded";
    public static int NO_FOLLOWER = 0;
    //--------------------------------------------------------------------------
    //------------------------follow or unfollow--------------------------------
    public static String UNFOLLOW = "unfollow";
    public static String FOLLOW = "follow";
    //--------------------------------------------------------------------------
   //--------------------get admin added recipie--------------------------------
    public static String ADMIN = "admin";
    public static String RECIPIE_ID = "recipieId";
    public static String FOOD_NAME = "foodName";
    public static String CATEGORY = "category";
    //--------------------------------------------------------------------------
    //----------------------get recipie details---------------------------------
    public static String DATE = "date";
    public static String INGREDIENTS = "ingredients";
    public static String MAKING = "making";
    //--------------------------------------------------------------------------
    //---------------------------get comments-----------------------------------
    public static String COMMENTS = "comments";
    //--------------------------------------------------------------------------
    //--------------------------add stores--------------------------------------
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";
    public static String STORE_NAME = "storeName";
   
}
