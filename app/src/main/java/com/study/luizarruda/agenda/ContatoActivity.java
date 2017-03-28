package com.study.luizarruda.agenda;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.study.luizarruda.agenda.dao.ContatoDAO;
import com.study.luizarruda.agenda.model.Contato;

/**
 * Created by luiz.arruda on 15/03/2017.
 */

public class ContatoActivity extends AppCompatActivity {

    private EditText contatoEdtNome,
                     contatoEdtEmail,
                     contatoEdtTelefone;

    private String nome,
                   email,
                   telefone;

    private Button contatoBtnSalvar;
    private LinearLayout activityContato;
    private ProgressBar contatoProgressBar;

    private Contato mContato;
    private ContatoDAO mContatoDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        bindElements();

        Intent intent = getIntent();
        mContato = (Contato) intent.getSerializableExtra("contato");
        if (mContato != null){
            contatoEdtNome.setText(mContato.getNome());
            contatoEdtEmail.setText(mContato.getEmail());
            contatoEdtTelefone.setText(mContato.getTelefone());
        } else {
            mContato = getInstanceContato();
        }

        bindListener();
    }

    private void bindListener() {
        contatoBtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValuesFields();

                habilitarProgress(View.VISIBLE, false);

                if (TextUtils.isEmpty(nome)) {
                    Snackbar.make(activityContato, R.string.digite_seu_nome, Snackbar.LENGTH_LONG).show();
                    habilitarProgress(View.GONE, true);
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Snackbar.make(activityContato, R.string.digite_seu_email, Snackbar.LENGTH_LONG).show();
                    habilitarProgress(View.GONE, true);
                    return;
                }

                if (TextUtils.isEmpty(telefone)) {
                    Snackbar.make(activityContato, R.string.digite_seu_telefone, Snackbar.LENGTH_LONG).show();
                    habilitarProgress(View.GONE, true);
                    return;
                }

                mContato.setNome(nome);
                mContato.setEmail(email);
                mContato.setTelefone(telefone);

                mContatoDAO = getInstanceContatoDAO(getApplicationContext());
                if (mContato.getId() > 0){
                    editar();
                } else {
                    insert();
                }
            }

            private void getValuesFields() {
                nome = contatoEdtNome.getText().toString();
                email = contatoEdtEmail.getText().toString();
                telefone = contatoEdtTelefone.getText().toString();
            }
        });
    }

    private void editar() {
        mContatoDAO.alterar(mContato);
        Toast.makeText(getApplicationContext(), R.string.salvo_com_sucesso, Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private void insert() {
        if (mContatoDAO.inserir(mContato) > 0){
            Toast.makeText(getApplicationContext(), R.string.salvo_com_sucesso, Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            Snackbar.make(activityContato, R.string.nao_foi_possivel_salvar, Snackbar.LENGTH_LONG).show();
            habilitarProgress(View.GONE, true);
        }
    }

    private void bindElements() {
        contatoEdtNome = (EditText) findViewById(R.id.contato_edtNome);
        contatoEdtEmail = (EditText) findViewById(R.id.contato_edtEmail);
        contatoEdtTelefone = (EditText) findViewById(R.id.contato_edtTelefone);
        contatoBtnSalvar = (Button) findViewById(R.id.btn_salvar);
        activityContato = (LinearLayout) findViewById(R.id.activity_contato);
        contatoProgressBar = (ProgressBar) findViewById(R.id.contato_progress_bar);
    }

    private void habilitarProgress(int visible, boolean b) {
        contatoProgressBar.setVisibility(visible);
        contatoBtnSalvar.setClickable(b);
    }

    @NonNull
    private Contato getInstanceContato() {
        return new Contato();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mContato.getId() > 0){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_contato, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.contato_ligar :
                Intent chamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mContato.getTelefone()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(chamada);
                }
                return true;

            case R.id.contato_apagar :
                new AlertDialog.Builder(this)
                        .setMessage(R.string.deseja_apagar)
                        .setCancelable(false)
                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mContatoDAO = getInstanceContatoDAO(getApplicationContext());
                                mContatoDAO.deletar(mContato);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                Toast.makeText(getApplicationContext(), R.string.apagado_sucesso, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.nao, null).show();
                return true;

            case R.id.contato_email :
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{mContato.getEmail()});
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, R.string.enviar_email + ""));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ContatoDAO getInstanceContatoDAO(Context context) {
        return new ContatoDAO(context);
    }
}
