package com.cristianomoraes.libri_retrofit.remote;

import com.cristianomoraes.libri_retrofit.model.Livro;
import com.cristianomoraes.libri_retrofit.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RouterInterface {

    /***** ROTAS DE USUÁRIOS *****/
    @GET("/usuario/logarUsuario/{login}/{senha}")
    Call<List<Usuario>>getUsuario(@Path("login") String login, @Path("senha") String senha);

    @POST("/usuario/cadastrarUsuario")
    Call<Usuario> addUsuario(@Body Usuario usuario);

    /***** ROTAS DE LIVROS *****/
    @GET("/livro/listarLivro")
    Call<List<Livro>>getLivros();

    @GET("/livro/listarLivroId/{cod_livro}")
    Call<List<Livro>>getLivrosId(@Path("cod_livro") int cod_livro);

    @POST("/livro/cadastrarLivro")
    Call<Livro> addLivro(@Body Livro livro);

    @PUT("/livro/alterarLivro")
    Call<Livro> updateLivro(@Body Livro livro);

    @DELETE("/livro/excluirLivro/{cod_livro}")
    Call<Livro> deleteLivro(@Path("cod_livro") int cod_livro);

}
