package com.cristianomoraes.libri_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristianomoraes.libri_retrofit.model.Livro;
import com.cristianomoraes.libri_retrofit.remote.APIUtils;
import com.cristianomoraes.libri_retrofit.remote.RouterInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlterarLivro extends AppCompatActivity {

    private int cod_livro;

    RouterInterface routerInterface;
    List<Livro> list = new ArrayList<Livro>();

    EditText txtTitulo;
    EditText txtLivroDescricao;
    EditText txtFoto;
    Button btnEditarrLivro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_livro);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtLivroDescricao = findViewById(R.id.txtLivroDescricao);
        txtFoto = findViewById(R.id.txtFoto);
        btnEditarrLivro = findViewById(R.id.btnEditarLivro);

        cod_livro = getIntent().getExtras().getInt("cod_livro");

        routerInterface = APIUtils.getUsuarioInterface();
        Call<List<Livro>> callGetLivroId = routerInterface.getLivrosId(cod_livro);

        callGetLivroId.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {

                if(response.isSuccessful()){

                    list = response.body();

                    txtTitulo.setText(list.get(0).getTitulo());
                    txtLivroDescricao.setText(list.get(0).getDescricao());
                    txtFoto.setText(list.get(0).getImagem());

                    btnEditarrLivro.setOnClickListener(view -> {

                        Livro livro = new Livro();

                        livro.setTitulo(txtTitulo.getText().toString());
                        livro.setDescricao(txtLivroDescricao.getText().toString());
                        livro.setImagem(txtFoto.getText().toString());
                        livro.setCod_livro(cod_livro);
                        livro.setTblUsuarioCodUsuario(1);

                        Call<Livro> callUpdateLivro = routerInterface.updateLivro(livro);

                        callUpdateLivro.enqueue(new Callback<Livro>() {
                            @Override
                            public void onResponse(Call<Livro> call, Response<Livro> response) {
                                Toast.makeText(AlterarLivro.this,
                                        "LIVRO ALTERADO COM SUCESSO",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AlterarLivro.this, FeedLivro.class));
                            }

                            @Override
                            public void onFailure(Call<Livro> call, Throwable t) {
                                Log.d("ERRO-", t.getMessage());
                            }
                        });

                    });
                }

            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                Log.d("ERRO-", t.getMessage());
            }
        });

    }
}