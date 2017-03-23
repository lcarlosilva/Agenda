package com.study.luizarruda.agenda;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.study.luizarruda.agenda.adapter.ContatosAdapter;
import com.study.luizarruda.agenda.dao.ContatoDAO;
import com.study.luizarruda.agenda.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //ATRIBUTOS UTILIZADOS PARA PODER IMPLEMENTAR A MINHA 'RecyclerView'
    private List<Contato> contatoList = new ArrayList<Contato>();
    private RecyclerView recyclerView;
    private ContatosAdapter mAdapter;
    private Contato contato;
    private ContatoDAO contatoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Contato contato = new Contato("Luiz Carlo", "luizvaldeco@gmail.com", "3333300003");
        new ContatoDAO(this).inserir(contato);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        contatoDAO = new ContatoDAO(this);
        contatoList = contatoDAO.buscarContatos();
        mAdapter = new ContatosAdapter(contatoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
