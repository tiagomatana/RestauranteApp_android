package com.example.tiago.restauranteapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditarActivity extends AppCompatActivity {

    private String[] tipos = new String[]{"Rodizio","Fast Food", "Domicilio"};
    Spinner opcoes;
    Button cancelButton;
    Button updateButton;
    Restaurante restaurante;
    EditText exNome, exEndereco;
    Spinner exTipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao);

        opcoes = (Spinner) findViewById(R.id.combo_tipos);
        cancelButton = (Button) findViewById(R.id.cancelar);
        updateButton = (Button) findViewById(R.id.Alterar);
        exNome = (EditText) findViewById(R.id.nome);
        exEndereco = (EditText) findViewById(R.id.endereco);
        exTipo = (Spinner) findViewById(R.id.combo_tipos);

        Intent intent = getIntent();
        restaurante = (Restaurante) intent.getSerializableExtra("rest");
        if (restaurante != null) {
            exNome.setText(restaurante.getNome());
            exEndereco.setText(restaurante.getEndereco());
            switch (restaurante.getTipo()){
                case "Rodizio":
                    exTipo.setSelection(0);
                    break;
                case "Fast Food":
                    exTipo.setSelection(1);
                    break;
                case "A Domicilio":
                    exTipo.setSelection(2);
                    break;

            }

        }

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
        adaptador.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        opcoes.setAdapter(adaptador);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
