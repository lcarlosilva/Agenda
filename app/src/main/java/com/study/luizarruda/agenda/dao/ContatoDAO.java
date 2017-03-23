package com.study.luizarruda.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.study.luizarruda.agenda.model.Contato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luiz.arruda on 09/03/2017.
 */

public class ContatoDAO {

    Context context;
    BancoDAO dao;

    private static final String BANCO_NOME ="Contato";

    public ContatoDAO(Context context) {
        this.context = context;
    }

    public void inserir(Contato contato){
        dao = new BancoDAO(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues dados = pegaDadosContato(contato);
        long inserir = db.insert(BANCO_NOME, null, dados);
        db.close();

        Log.i(BANCO_NOME, inserir + "");
    }

    public List<Contato> buscarContatos(){
        String sql = "SELECT * FROM " + BANCO_NOME;
        dao = new BancoDAO(context);
        SQLiteDatabase db = dao.getReadableDatabase();

        //AUXILIA A PEGAR OS REGISTROS DA CONSULTA QUE EFETUEI
        Cursor cursor = db.rawQuery(sql, null);

        List<Contato> contatos = new ArrayList<Contato>();

        while (cursor.moveToNext()){
            Contato contato = new Contato();
            contato.setId(cursor.getInt(cursor.getColumnIndex("id")));
            contato.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            contato.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            contato.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));

            contatos.add(contato);
        }
        cursor.close();
        return contatos;
    }

    private ContentValues pegaDadosContato(Contato contato) {
        ContentValues dados = new ContentValues();
        dados.put("nome", contato.getNome());
        dados.put("email", contato.getEmail());
        dados.put("telefone", contato.getTelefone());
        return dados;
    }
}
