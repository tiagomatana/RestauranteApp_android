package com.example.tiago.restauranteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class ModuloActivity extends android.app.TabActivity implements TabHost.OnTabChangeListener, TabHost.TabContentFactory {

    private static final int MENU_EDITAR = 1;
    private static final int MENU_EXCLUIR = 2;
    private static Boolean status = true;
    List<Restaurante> listaRestaurantes = new ArrayList<Restaurante>();
    ModuloActivity.AdaptadorRestaurante adaptador = null;
    private String[] combo = new String[]{"Rodizio","Fast Food", "A Domicilio"};
    EditText nome = null;
    EditText endereco = null;
    Spinner tipos = null;
    Restaurante rest = null;
    ListView lista = null;
    ArrayAdapter<String> adaptarSpinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        nome = (EditText) findViewById(R.id.nome);
        endereco = (EditText) findViewById(R.id.end);
        tipos = (Spinner) findViewById(R.id.tipos);
        lista = (ListView) findViewById(R.id.restaurantes);
        Button salvar = (Button) findViewById(R.id.salvar);
        salvar.setOnClickListener(SalvarRestaurante);


        adaptarSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, combo);
        adaptarSpinner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        tipos.setAdapter(adaptarSpinner);

        atualizarLista();

        registerForContextMenu(lista);
        //lista.setOnItemClickListener(ListagemRestaurante);

        TabSpec tabSpec1 = getTabHost().newTabSpec("Tab1");
        tabSpec1.setContent(R.id.restaurantes);
        tabSpec1.setIndicator("Lista", getResources().getDrawable(R.drawable.lista));
        getTabHost().addTab(tabSpec1);

        TabSpec tabSpec2 = getTabHost().newTabSpec("Tab2");
        tabSpec2.setContent(R.id.detalhes);
        tabSpec2.setIndicator("Novo", getResources().getDrawable(R.drawable.restaurante));
        getTabHost().addTab(tabSpec2);

        getTabHost().setCurrentTab(0);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem menuEditar = menu.add(0, MENU_EDITAR, 10, R.string.editarMenu);
        menuEditar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                rest = null;
                status = false;
                AdapterView.AdapterContextMenuInfo info
                        = (AdapterView.AdapterContextMenuInfo) menuInfo;
                rest = (Restaurante) lista.getItemAtPosition(info.position);
                Toast.makeText(ModuloActivity.this,"Restaurante " + rest.getId().toString() + " selecionado.",Toast.LENGTH_LONG).show();
                nome.setText(rest.getNome());
                endereco.setText(rest.getEndereco());

                switch (rest.getTipo()){
                    case "rodizio":
                        tipos.setSelection(0);
                        break;
                    case "fast_food":
                        tipos.setSelection(1);
                        break;
                    case "a_domicilio":
                        tipos.setSelection(2);
                        break;

                }
                getTabHost().setCurrentTab(1);


                return false;
            }
        });
        MenuItem menuExcluir = menu.add(0, MENU_EXCLUIR, 10, R.string.excluirMenu);
        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info
                        = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Restaurante rest = (Restaurante) lista.getItemAtPosition(info.position);
                DAO.excluirRestaurante(rest);
                atualizarLista();
                //atualizarLista();
                return false;
            }
        });
    }
    @Override
    public void onTabChanged(String tabId) {
        //----
    }

    @Override
    public View createTabContent(String tabId) {
        TextView tv = new TextView(this);

        return tv;
    }


    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    public void atualizarLista(){
        lista.setAdapter( new AdaptadorRestaurante());
    }

    private View.OnClickListener SalvarRestaurante = new View.OnClickListener() {

        public void onClick(View arg0) {

            boolean novo = false;
            if(rest == null) {
                rest = new Restaurante();
                novo = true;
            }
            EditText nome = (EditText) findViewById(R.id.nome);
            EditText endereco = (EditText) findViewById(R.id.end);

            rest.setNome(nome.getText().toString());
            rest.setEndereco(endereco.getText().toString());

            Spinner tipos = (Spinner) findViewById(R.id.tipos);

            switch (tipos.getSelectedItemPosition()) {
                case 0:
                    rest.setTipo("rodizio");
                    break;
                case 1:
                    rest.setTipo("fast_food");
                    break;
                case 2:
                    rest.setTipo("a_domicilio");
                    break;
            }
            nome.setText("");
            endereco.setText("");
            tipos.setSelection(0);

            try {
                if (novo) {
                    DAO.incluirRestaurante(rest);
                } else {
                    DAO.alterarRestaurante(rest);
                }
                getTabHost().setCurrentTab(0);
                Toast.makeText(ModuloActivity.this, "Restaurante " + rest.getNome() + " gravado com sucesso", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                Toast.makeText(ModuloActivity.this, "Ocorreu um erro ao tentar salvar " + nome.getText().toString(), Toast.LENGTH_LONG).show();
            }

            atualizarLista();


        }
    };

    static class SimulaBanco {
        private TextView nome = null;
        private TextView endereco = null;
        private ImageView icone = null;

        SimulaBanco(View linha) {
            nome = (TextView) linha.findViewById(R.id.titulo);
            endereco = (TextView) linha.findViewById(R.id.endereco);
            icone = (ImageView) linha.findViewById(R.id.icone);
        }

        void popularFormulario(Restaurante restaurante) {
            nome.setText(restaurante.getNome());
            endereco.setText(restaurante.getEndereco());

            switch (restaurante.getTipo()){
                case "rodizio":
                    icone.setImageResource(R.drawable.rodizio);
                    break;
                case "fast_food":
                    icone.setImageResource(R.drawable.fast_food);
                    break;
                case "a_domicilio":
                    icone.setImageResource(R.drawable.entrega);
                    break;
                default:
                    icone.setImageResource(R.drawable.ic_launcher);
            }

        }
    }

    class AdaptadorRestaurante extends ArrayAdapter<Restaurante> {
        AdaptadorRestaurante() {
            super(ModuloActivity.this, R.layout.linha,
                    DAO.getRestaurantes());
        }

        @Override
        public View getView(int posicao, View view, ViewGroup parent) {

            View linha = view;
            ModuloActivity.SimulaBanco banco = null;

            if (linha == null) {
                LayoutInflater inflater = getLayoutInflater();
                linha = inflater.inflate(R.layout.linha, parent, false);
                banco = new ModuloActivity.SimulaBanco(linha);
                linha.setTag(banco);
            } else {
                banco = (ModuloActivity.SimulaBanco) linha.getTag();
            }


            banco.popularFormulario(DAO.getRestaurantes().get(posicao));

            return linha;
        }
    }
}