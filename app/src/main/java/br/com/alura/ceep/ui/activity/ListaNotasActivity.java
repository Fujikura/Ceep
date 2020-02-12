package br.com.alura.ceep.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        List<Nota> notas = notasDeExemplo();
        configuraRecyclerView(notas);
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
        listaDeNotas.setAdapter(new ListaNotasAdapter(this, notas));
    }
}
