package com.study.luizarruda.agenda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by luiz.arruda on 15/03/2017.
 */

public class ContatoActivity extends AppCompatActivity {

    private EditText contaoEdtNome,
                     contaoEdtEmail,
                     contaoEdtTelefone;

    private Button contatoBtnSalvar;
    private LinearLayout activityContato;
    private ProgressBar contatoProgressBar;

    private String nome,
                   email,
                   telefone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        bindElements();

        contatoBtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValuesFields();

                habilitarProgress(View.VISIBLE, false);

                if (TextUtils.isEmpty(nome)){
                    Snackbar.make(activityContato, R.string.digite_seu_nome, Snackbar.LENGTH_LONG).show();
                    habilitarProgress(View.GONE, true);
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    Snackbar.make(activityContato, R.string.digite_seu_email, Snackbar.LENGTH_LONG).show();
                    habilitarProgress(View.GONE, true);
                    return;
                }

                if (TextUtils.isEmpty(telefone)){
                    Snackbar.make(activityContato, R.string.digite_seu_telefone, Snackbar.LENGTH_LONG).show();
                    habilitarProgress(View.GONE, true);
                    return;
                }
            }

            private void habilitarProgress(int visible, boolean b) {
                contatoProgressBar.setVisibility(visible);
                contatoBtnSalvar.setClickable(b);
            }

            private void getValuesFields() {
                nome = contaoEdtNome.getText().toString();
                email = contaoEdtEmail.getText().toString();
                telefone = contaoEdtTelefone.getText().toString();
            }
        });
    }

    private void bindElements() {
        contaoEdtNome = (EditText) findViewById(R.id.contato_edtNome);
        contaoEdtEmail = (EditText) findViewById(R.id.contato_edtEmail);
        contaoEdtTelefone = (EditText) findViewById(R.id.contato_edtTelefone);
        contatoBtnSalvar = (Button) findViewById(R.id.btn_salvar);
        activityContato = (LinearLayout) findViewById(R.id.activity_contato);
        contatoProgressBar = (ProgressBar) findViewById(R.id.contato_progress_bar);
    }
}
