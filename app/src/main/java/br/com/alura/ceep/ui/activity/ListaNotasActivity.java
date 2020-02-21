package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;
import br.com.alura.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Notas";
    private ListaNotasAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lista_notas);
        setTitle(TITULO_APPBAR);

        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNota();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (ehResultadoInsereNota(requestCode, data)) {

            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                adicionaNota(notaRecebida);
            }
        }

        if (ehResultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                if (ehPosicaoValida(posicaoRecebida)) {
                    alteraNota(notaRecebida, posicaoRecebida);
                } else {
                    Toast.makeText(this,
                            "Ocorreu um problema na alteração da nota",
                            Toast.LENGTH_LONG).show();
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void configuraBotaoInsereNota() {
        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);
        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaFormularioNotaActivityInsere();
            }
        });
    }


    private List<Nota> pegaTodasNotas() {
        NotaDAO dao = new NotaDAO();
        for (int i = 0; i < 10; i++) {
            dao.insere(new Nota(
                    "Titulo " + (i + 1),

                    "Descrição " + (i + 1)));
        }
        return dao.todos();
    }

    private void alteraNota(Nota nota, int posicao) {
        new NotaDAO().altera(posicao, nota);
        adapter.altera(posicao, nota);
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) &&
                temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private void adicionaNota(Nota nota) {
        adapter.adiciona(nota);

        new NotaDAO().insere(nota);
    }

    private boolean ehResultadoInsereNota(int requestCode, @Nullable Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                temNota(data);
    }

    private boolean temNota(@Nullable Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> notas) {
        RecyclerView listaDeNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdater(notas, listaDeNotas);
        configuraItemTouchHelper(listaDeNotas);
    }

    private void configuraItemTouchHelper(RecyclerView recyclerView) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void configuraAdater(List<Nota> notas, RecyclerView listaDeNotas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaDeNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                vaiParaFormularioNotaActivityAltera(nota, posicao);

            }
        });
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota, int posicao) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

    private void vaiParaFormularioNotaActivityInsere() {
        Intent iniciaFormularioNota =
                new Intent(ListaNotasActivity.this,
                        FormularioNotaActivity.class);

        startActivityForResult(iniciaFormularioNota, CODIGO_REQUISICAO_INSERE_NOTA);
    }
}
