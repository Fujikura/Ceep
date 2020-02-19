package br.com.alura.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;
import br.com.alura.ceep.ui.recyclerview.viewholder.NotaViewHolder;

public class ListaNotasAdapter extends RecyclerView.Adapter<NotaViewHolder> {

    private final List<Nota> notas;
    private final Context contexto;
    private OnItemClickListener onItemClickListener;

    public ListaNotasAdapter(Context contexto, List<Nota> notas) {
        this.notas = notas;
        this.contexto = contexto;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = criaView(parent);
        return new NotaViewHolder(viewCriada, contexto, onItemClickListener);
    }

    private View criaView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(contexto)
                    .inflate(R.layout.item_nota, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        Nota nota = notas.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void adiciona(Nota nota){
        notas.add(nota);
        notifyDataSetChanged();
    }

    public void altera(int posicao, Nota nota) {
        notas.set(posicao,nota);
        notifyDataSetChanged();
    }
}
