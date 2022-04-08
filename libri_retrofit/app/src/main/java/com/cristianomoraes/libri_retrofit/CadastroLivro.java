package com.cristianomoraes.libri_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristianomoraes.libri_retrofit.model.Livro;
import com.cristianomoraes.libri_retrofit.remote.APIUtils;
import com.cristianomoraes.libri_retrofit.remote.RouterInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroLivro extends AppCompatActivity {

    RouterInterface routerInterface;
    EditText txtTitulo;
    EditText txtLivroDescricao;
    EditText txtFoto;
    Button btnCadastrarLivro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtLivroDescricao = findViewById(R.id.txtLivroDescricao);
        txtFoto = findViewById(R.id.txtFoto);
        btnCadastrarLivro = findViewById(R.id.btnCadastrarLivro);

        routerInterface = APIUtils.getUsuarioInterface();

        btnCadastrarLivro.setOnClickListener(view -> {

            Livro livro = new Livro();

            livro.setTitulo(txtTitulo.getText().toString());
            livro.setDescricao(txtLivroDescricao.getText().toString());
            livro.setImagem(txtFoto.getText().toString());
            livro.setTblUsuarioCodUsuario(1);

            addLivro(livro);

        });
    }

    public void addLivro(Livro livro){

        Call<Livro> call = routerInterface.addLivro(livro);

        call.enqueue(new Callback<Livro>() {
            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {

                if(response.isSuccessful()){
                    Toast.makeText(CadastroLivro.this,
                            "LIVRO INSERIDO COM SUCESSO",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Livro> call, Throwable t) {

                Log.d("ERRO-", t.getMessage());

            }
        });

    }

}