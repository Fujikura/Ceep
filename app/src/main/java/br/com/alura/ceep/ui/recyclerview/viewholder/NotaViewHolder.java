package br.com.alura.ceep.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;

public class NotaViewHolder extends RecyclerView.ViewHolder {

    private final TextView titulo;
    private final TextView descricao;

    public NotaViewHolder(@NonNull View itemView) {
        super(itemView);
        titulo = itemView.findViewById(R.id.item_nota_titulo);
        descricao = itemView.findViewById(R.id.item_nota_descricao);

    }


    public void vincula(Nota nota){
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());

    }
}
