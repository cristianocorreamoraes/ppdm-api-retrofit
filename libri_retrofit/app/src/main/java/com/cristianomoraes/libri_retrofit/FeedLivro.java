package com.cristianomoraes.libri_retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cristianomoraes.libri_retrofit.model.Item;
import com.cristianomoraes.libri_retrofit.model.Livro;
import com.cristianomoraes.libri_retrofit.remote.APIUtils;
import com.cristianomoraes.libri_retrofit.remote.RouterInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedLivro extends AppCompatActivity {

    RouterInterface routerInterface;
    List<Livro> list = new ArrayList<Livro>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_livro);

        routerInterface = APIUtils.getUsuarioInterface();

        Call<List<Livro>> call = routerInterface.getLivros();

        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {

                if(response.isSuccessful()){

                    List<Item> itens = new ArrayList<>();
                    list = response.body();

                    /** INICIO DO CARREGAMENTO DE ITENS **/
                    for (int i = 0; i < list.size(); i++){
                        itens.add(new Item(0, list.get(i)));
                    }
                    /** FIM DO CARREGAMENTO DE ITENS **/

                    /** INICIO DO RECYCLERVIEW **/
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setAdapter(new LivroAdapter(itens));
                    /** INICIO DORECYCLERVIEW **/

                }

            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }

    private class LivroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<Item> itens;

        public LivroAdapter(List<Item> itens) {
            this.itens = itens;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if(viewType == 0){

                return new LivroAdapter.LivroViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_livro,
                        parent,
                        false
                ));

            }

            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if(getItemViewType(position) == 0){
                Livro livro = (Livro) itens.get(position).getObject();
                ((LivroAdapter.LivroViewHolder) holder).setLivroData(livro);
            }

        }

        @Override
        public int getItemCount() {
            return itens.size();
        }

        @Override
        public int getItemViewType(int position) {
            return itens.get(position).getType();
        }

        class LivroViewHolder extends RecyclerView.ViewHolder{

            private TextView textLivroTitulo, textLivroDescricao;
            private int cod_livro;

            public LivroViewHolder(@NonNull View itemView) {
                super(itemView);

                textLivroTitulo = itemView.findViewById(R.id.textLivroTitulo);
                textLivroDescricao =    itemView.findViewById(R.id.textLivroDescricao);

                itemView.setOnClickListener(view->{

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(FeedLivro.this)
                            .setMessage("ESCOLHA A AÇÃO QUE DESEJA EXECUTAR")
                            .setPositiveButton("ALTERAR", (dialog1, witch)->{

                                Intent intent = new Intent(FeedLivro.this, AlterarLivro.class);
                                intent.putExtra("cod_livro", cod_livro);
                                startActivity(intent);

                            })
                            .setNegativeButton("EXCLUIR", (dialog1, witch)->{

                                routerInterface = APIUtils.getUsuarioInterface();

                                Call<Livro> call = routerInterface.deleteLivro(cod_livro);

                                call.enqueue(new Callback<Livro>() {
                                    @Override
                                    public void onResponse(Call<Livro> call,
                                                           Response<Livro> response) {
                                        Toast.makeText(FeedLivro.this,
                                                "LIVRO EXLCLUÍDO COM SUCESSO",
                                                Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(FeedLivro.this, FeedLivro.class));
                                    }

                                    @Override
                                    public void onFailure(Call<Livro> call, Throwable t) {
                                        Log.d("ERRO-", t.getMessage());
                                    }
                                });

                            });

                    alertDialog.show();

                });

            }

            void setLivroData(Livro livro){
                textLivroTitulo.setText(livro.getTitulo());
                textLivroDescricao.setText(livro.getDescricao());
                cod_livro = livro.getCod_livro();
                //  livroImagem.setImageResource(livro.getImagem());
            }

        }

    }

}