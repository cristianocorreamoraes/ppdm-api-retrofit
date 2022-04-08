package com.cristianomoraes.libri_retrofit.remote;

public class APIUtils {

    public APIUtils() {
    }

    public static final String API_URL = "http://10.107.131.86:3000/";

    public static RouterInterface getUsuarioInterface(){

        return RetrofitClient.getClient(API_URL).create(RouterInterface.class);

    }

}
