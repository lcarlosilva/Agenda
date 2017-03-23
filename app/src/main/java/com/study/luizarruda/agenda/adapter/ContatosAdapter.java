package com.study.luizarruda.agenda.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.luizarruda.agenda.R;
import com.study.luizarruda.agenda.model.Contato;

import java.util.List;

/**
 * Created by luiz.arruda on 15/03/2017.
 */

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.MyViewHolder>{

    private List<Contato> contatosList;

    public ContatosAdapter(List<Contato> contatosList) {
        // RECEBO ESSA LISTA DO MEU BANDO DE DADOS DO ANDROID
        this.contatosList = contatosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // AQUI ESTOU INFLANDO O LAYOUT DA MINHA LISTA DE 'Contato' PARA QUE POSSUA AS CARACTERISTICAS DO 'item_list_row'
        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.contato_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //RESPONSAVEL POR PEGAR O DADOS QUE VEM DA BASE DE DADOS DO SQLITE E COLOQUE NO 'contato_list_row'
        Contato contato = contatosList.get(position);

        holder.nome.setText(contato.getNome());
        holder.email.setText(contato.getEmail());
        holder.telefone.setText(contato.getTelefone());
    }

    @Override
    public int getItemCount() {
        //TODO -> PARA SABER O TAMANHO DA MINHA LISTA
        return contatosList.size();
    }

    /**
     * TODO -> FUNÇÃO DA CLASSE 'MyViewHolder', criada por mim.
     * CLASSE RESPONSÁVEL POR PEGAR OS
     * MEUS ELEMENTOS DO XML DE 'contato_list_row'
     **/
    public class MyViewHolder extends RecyclerView.ViewHolder {

        //CAMPOS QUE REPRESENTAM OS ELEMENTOS QUE ESTÃO PRESENTES NO MEU XML 'contatos_list_row'
        public TextView nome,
                        email,
                        telefone;

        public MyViewHolder(View itemView) {
            super(itemView);

            // AQUI EU ESTOU TENDO ACESSO AOS ELEMENTOS DE TEXTVIEW QUE POSSUO NO MEU XML 'contato_list_row'
            nome = (TextView) itemView.findViewById(R.id.list_nome);
            email = (TextView) itemView.findViewById(R.id.list_email);
            telefone = (TextView) itemView.findViewById(R.id.list_telefone);
        }
    }
}
