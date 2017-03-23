package com.study.luizarruda.agenda.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luiz.arruda on 09/03/2017.
 */

public class BancoDAO extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "Agenda";
    private static final int VERSAO_BANCO = 1;

    private static final String TABLE_CONTATO = "CREATE TABLE Contato (" +
                                                                        "id INTEGER PRIMARY KEY" +
                                                                        ",nome TEXT" +
                                                                        ",email TEXT" +
                                                                        ",telefone TEXT);";

    public BancoDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CRIA A TABLEA DO BANCO DE DADOS DA APLICAÇÃO
        db.execSQL(TABLE_CONTATO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //EXCLUI A TABELA CASO ELA EXISTA
        db.execSQL("DROP TABLE IF EXIST Contato;");
    }
}
