package br.com.alura.ceep.ui.recyclerview.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;

public class NotaViewHolder extends RecyclerView.ViewHolder {

    private final TextView titulo;
    private final TextView descricao;
    private final Context context;
    private final OnItemClickListener onItemClickListener;
    private Nota nota;

    public NotaViewHolder(@NonNull View itemView, final Context context, final OnItemClickListener onItemClickListener) {
        super(itemView);
        titulo = itemView.findViewById(R.id.item_nota_titulo);
        descricao = itemView.findViewById(R.id.item_nota_descricao);
        this.context = context;
        this.onItemClickListener = onItemClickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(nota, getAdapterPosition());
            }
        });

    }


    public void vincula(Nota nota){
        this.nota = nota;
        preencheCampos(nota);
    }

    private void preencheCampos(Nota nota) {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
    }
}
