package com.chefaholic.utils;

public interface AppConstants {

	String IP = "192.168.1.54";
	String PORT = "8084";
	String URL = "http://" + IP + ":" + PORT + "/Chefaholic/";
	String LOGIN_SERVLET = URL + "LoginServlet";
	String REGISTER_SERVLET = URL + "RegisterationServlet";
	String RETRIEVE_USER_DETAILS_SERVLET = URL + "GetUserDetailsServlet";
	String USER_IMAGE_FOLDER = URL + "LoadImageServlet?path=";
	Object USERSERVLET = URL + "GetAllUsersDetailsServlet";

	String NAME = "NAME";
	String EMAIL_ID = "EMAIL_ID";
	String PASSWORD = "PASSWORD";
	String MOBILE = "MOBILE";
	String USERNAME = "USERNAME";
	String FOLLOW_UNFOLLOW_SERVLET = URL + "FollowOrUnfollowServlet";
	Object UPLOAD_IMAGE_SERVLET = URL + "UploadUserImageServlet";
	Object ADMIN_RECEIPE_SERVLET = URL +"AdminAddedRecipieServlet";
	Object FOOD_DETAIL = URL + "GetRecipieDetailsServlet";
	Object GET_ALL_USERS_PRODUCTS = URL + "GetAllRecipieServlet";
	Object GET_FOLLOWERS_LIST = URL + "ViewFollowersServlet";
	Object ADD_TO_FAVOURATE = URL + "AddFavouritesServlet";
	Object USERS_RECIPIE_DETAILS = URL + "ViewUsersRecipieServlet";
	Object SEARCH_BY_TAG_SERVLET = URL + "SearchUsingTagsServlet";
	Object SEARCH_BY_DATE_SERVLET = URL+ "SearchUsingDateServlet";
	Object VIEW_FAVOURATES = URL + "ViewFavouritesServlet";
	Object COMMENTS_SERVLET = URL + "GetCommentsServlet";
	Object ADD_COMMENTS = URL + "AddCommentsServlet";
	Object CHANGE_PASSWORD = URL+ "ChangePasswordServlet";
	Object ADD_STORE_SERVLET = URL + "AddStoreServlet";
	Object VIEW_STORE_SERVLET = URL + "ViewStoreServlet";
	

}
