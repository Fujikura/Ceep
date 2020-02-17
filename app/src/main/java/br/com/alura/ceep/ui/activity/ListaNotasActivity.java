package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private List<Nota> todasNotas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lista_notas);

        todasNotas = notasDeExemplo();
        configuraRecyclerView(todasNotas);

        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);
        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciaFormularioNota =
                        new Intent(ListaNotasActivity.this,
                                FormularioNotaActivity.class);

                startActivityForResult(iniciaFormularioNota, 1);
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == 2 && data.hasExtra("nota")){

            Nota notaRecebida = (Nota) data.getSerializableExtra("nota");
            adapter.adiciona(notaRecebida);
            
            new NotaDAO().insere(notaRecebida);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<Nota> notasDeExemplo() {
        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("Primeira nota ", "Descrição pequena"),
                new Nota("Segunda nota", "Segunda descrição é bem maior que a da primeira nota"));
        return dao.todos();
    }

    private void configuraRecyclerView(List<Nota> notas) {
        RecyclerView listaDeNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdater(notas, listaDeNotas);
    }

    private void configuraAdater(List<Nota> notas, RecyclerView listaDeNotas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaDeNotas.setAdapter(adapter);
    }
}
