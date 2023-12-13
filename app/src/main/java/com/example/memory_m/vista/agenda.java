package com.example.memory_m.vista;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cristobal.memory_m.R;
import com.example.memory_m.controlador.ConexionHelper;
import com.example.memory_m.controlador.utility;
import com.example.memory_m.modelo.medicamentos;

import java.util.ArrayList;

public class agenda extends AppCompatActivity {
    EditText txtTitulo;
    EditText txtDescripcion;
    Button btnBuscarNombre, btnEliminarMedicamento, btnRegistrarActualizarMedicamento; // Cambio de nombre del botón
    ListView listViewMedicamento;
    ArrayList<String> listaInformacion;
    ArrayList<medicamentos> listaMedicamentos;
    ConexionHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        conn = new ConexionHelper(getApplicationContext(), "bd_medicamentos", null, 1);

        txtTitulo = findViewById(R.id.editTextTitulo);
        txtDescripcion = findViewById(R.id.editTextdescription);
        btnBuscarNombre = findViewById(R.id.btnBuscarNombre);
        btnEliminarMedicamento = findViewById(R.id.btnEliminarMedicamento);
        btnRegistrarActualizarMedicamento = findViewById(R.id.btnRegistrarMedicamento); // Cambio de nombre del botón
        listViewMedicamento = findViewById(R.id.listviewMedicamento);

        btnBuscarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarMedicamento();
            }
        });

        btnEliminarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarMedicamento();
            }
        });

        btnRegistrarActualizarMedicamento.setOnClickListener(new View.OnClickListener() { // Cambio de nombre del botón
            @Override
            public void onClick(View v) {
                registrarOActualizarMedicamento(); // Cambio de función para el botón
            }
        });

        listViewMedicamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarDetallesMedicamento(position);
            }
        });

        consultarListaMedicamentos();
    }

    private void consultarListaMedicamentos() {
        SQLiteDatabase db = conn.getReadableDatabase();
        medicamentos medicamento = null;
        listaMedicamentos = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + utility.TABLA_REMEDIO, null);
        while (cursor.moveToNext()) {
            medicamento = new medicamentos();
            medicamento.setTitulo(cursor.getString(0));
            medicamento.setDescripcion(cursor.getString(1));
            listaMedicamentos.add(medicamento);
        }
        obtenerListaInformacion();
    }

    private void obtenerListaInformacion() {
        listaInformacion = new ArrayList<>();
        for (int i = 0; i < listaMedicamentos.size(); i++) {
            listaInformacion.add(listaMedicamentos.get(i).getTitulo() + " - " + listaMedicamentos.get(i).getDescripcion());
        }

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listViewMedicamento.setAdapter(adaptador);
    }

    private void consultarMedicamento() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {txtTitulo.getText().toString()};
        String[] campos = {utility.CAMPO_DESCRIPCION};
        try {
            Cursor cursor = db.query(utility.TABLA_REMEDIO, campos, utility.CAMPO_TITULO + "=?", parametros, null, null, null);
            cursor.moveToFirst();
            txtDescripcion.setText(cursor.getString(0));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ATENCIÓN, el medicamento no existe", Toast.LENGTH_LONG).show();
            txtDescripcion.setText("");
        }
    }

    private void eliminarMedicamento() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {txtTitulo.getText().toString()};
        db.delete(utility.TABLA_REMEDIO, utility.CAMPO_TITULO + "=?", parametros);
        Toast.makeText(getApplicationContext(), "ATENCIÓN, se eliminó el medicamento", Toast.LENGTH_LONG).show();
        txtTitulo.setText("");
        txtDescripcion.setText("");
        db.close();
        consultarListaMedicamentos();
    }

    private void registrarOActualizarMedicamento() {
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utility.CAMPO_TITULO, txtTitulo.getText().toString());
        values.put(utility.CAMPO_DESCRIPCION, txtDescripcion.getText().toString());

        String titulo = txtTitulo.getText().toString();

        if (medicamentoExiste(titulo)) {
            // Si el medicamento ya existe, actualizar
            String[] parametros = {titulo};
            db.update(utility.TABLA_REMEDIO, values, utility.CAMPO_TITULO + "=?", parametros);
            Toast.makeText(getApplicationContext(), "ATENCIÓN, se actualizó el medicamento", Toast.LENGTH_LONG).show();
        } else {
            // Si el medicamento no existe, registrar
            long idResultante = db.insert(utility.TABLA_REMEDIO, null, values);
            if (idResultante != -1) {
                Toast.makeText(getApplicationContext(), "ATENCIÓN, se registró el medicamento", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "ATENCIÓN, no se pudo registrar el medicamento", Toast.LENGTH_LONG).show();
            }
        }

        txtTitulo.setText("");
        txtDescripcion.setText("");
        db.close();
        consultarListaMedicamentos();
    }

    private boolean medicamentoExiste(String titulo) {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {titulo};
        String[] campos = {utility.CAMPO_TITULO};

        Cursor cursor = db.query(utility.TABLA_REMEDIO, campos, utility.CAMPO_TITULO + "=?", parametros, null, null, null);

        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    private void mostrarDetallesMedicamento(int position) {
        medicamentos medicamento = listaMedicamentos.get(position);
        String informacion = "Nombre: " + medicamento.getTitulo() + "\nDescripción: " + medicamento.getDescripcion();
        Toast.makeText(getApplicationContext(), informacion, Toast.LENGTH_LONG).show();
    }
}
