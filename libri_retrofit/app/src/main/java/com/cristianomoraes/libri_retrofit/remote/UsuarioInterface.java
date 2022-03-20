package com.cristianomoraes.libri_retrofit.remote;

import com.cristianomoraes.libri_retrofit.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioInterface {

    @GET("/usuario/listarUsuario/{login}/{senha}")
    Call<List<Usuario>>getUsuario(@Path("login") String login, @Path("senha") String senha);

    @POST("/usuario/cadastrarUsuario")
    Call<Usuario> addUsuario(@Body Usuario usuario);

}
