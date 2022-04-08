package com.cristianomoraes.libri_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.cristianomoraes.libri_retrofit.model.Usuario;

import com.cristianomoraes.libri_retrofit.remote.APIUtils;
import com.cristianomoraes.libri_retrofit.remote.RouterInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RouterInterface routerInterface;
    Button btnCadastrar;
    Button btnLogar;
    EditText txtLogin;
    EditText txtSenha;

    List<Usuario> list = new ArrayList<Usuario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        routerInterface = APIUtils.getUsuarioInterface();

        this.btnCadastrar = findViewById(R.id.btnCadastrarUsuario);

        this.btnCadastrar.setOnClickListener(view->{

            startActivity(new Intent(MainActivity.this, CadastroUsusario.class));
        });

        this.btnLogar = findViewById(R.id.btnLogar);
        this.txtLogin = findViewById(R.id.txtLogin);
        this.txtSenha = findViewById(R.id.txtSenha);

        btnLogar.setOnClickListener(view -> {

            String login = txtLogin.getText().toString();
            String senha =  txtSenha.getText().toString();

            Call<List<Usuario>> call = routerInterface.getUsuario(login, senha);

            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {

                    if(response.isSuccessful()){

                        list = response.body();
                        Usuario usuario = new Usuario();
                        usuario.setCod_usuario(response.body().get(0).getCod_usuario());
                        usuario.setNome(response.body().get(0).getNome());
                        usuario.setSobrenome(response.body().get(0).getSobrenome());
                        usuario.setEmail(response.body().get(0).getEmail());
                        usuario.setFoto(response.body().get(0).getFoto());
                        usuario.setLogin(response.body().get(0).getLogin());
                        usuario.setSenha(response.body().get(0).getSenha());

                        list.add(usuario);
                    }

                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {

                    Log.d("ERROR-", t.getMessage());

                }
            });

            if(!list.isEmpty()){

                startActivity(new Intent(MainActivity.this, FeedLivro.class));

            }

        });

    }

}