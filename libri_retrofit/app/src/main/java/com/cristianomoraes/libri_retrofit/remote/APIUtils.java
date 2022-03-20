package com.cristianomoraes.libri_retrofit.remote;

public class APIUtils {

    public APIUtils() {
    }

    public static final String API_URL = "http://10.107.131.33:3000/";

    public static UsuarioInterface getUsuarioInterface(){

        return RetrofitClient.getClient(API_URL).create(UsuarioInterface.class);

    }

}
