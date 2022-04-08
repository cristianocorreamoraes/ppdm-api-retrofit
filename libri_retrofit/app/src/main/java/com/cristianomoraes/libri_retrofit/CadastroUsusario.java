package com.cristianomoraes.libri_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristianomoraes.libri_retrofit.model.Usuario;
import com.cristianomoraes.libri_retrofit.remote.APIUtils;
import com.cristianomoraes.libri_retrofit.remote.RouterInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroUsusario extends AppCompatActivity {

    RouterInterface routerInterface;
    EditText txtNome;
    EditText txtSobrenome;
    EditText txtEmail;
    EditText txtLogin;
    EditText txtSenha;
    Button btnInserir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ususario);

        txtNome = findViewById(R.id.txtNome);
        txtSobrenome = findViewById(R.id.txtSobrenome);
        txtEmail = findViewById(R.id.txtEmail);
        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);

        btnInserir = findViewById(R.id.btnCadastrarUsuario);

        routerInterface = APIUtils.getUsuarioInterface();

        btnInserir.setOnClickListener(view -> {

            Usuario usuario = new Usuario();

            usuario.setNome(txtNome.getText().toString());
            usuario.setSobrenome(txtSobrenome.getText().toString());
            usuario.setEmail(txtEmail.getText().toString());
            usuario.setFoto("IMAGEM DE USUÁRIO");
            usuario.setLogin(txtLogin.getText().toString());
            usuario.setSenha(txtSenha.getText().toString());

            addUsuario(usuario);

        });

    }

    public void addUsuario(Usuario usuario){

        Call<Usuario> call = routerInterface.addUsuario(usuario);

        call.enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if(response.isSuccessful()){
                    Toast.makeText(CadastroUsusario.this,
                            "USUÁRIO INSERIDO COM SUCESSO",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

                Log.d("ERRO-", t.getMessage());

            }

        });

    }

}