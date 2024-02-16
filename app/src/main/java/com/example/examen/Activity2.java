package com.example.examen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Configuracion.Transacciones;

public class Activity2 extends AppCompatActivity {
    private Transacciones transacciones;
    ListView listViewContactos;
    ArrayAdapter<String> adaptador;
    ArrayList<String> listaContactos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);



        // Obtener la instancia del ListView
        listViewContactos = findViewById(R.id.listViewContactos);

        // Inicializar Transacciones
        transacciones = new Transacciones(this);

        // Obtener los datos de la base de datos
        Cursor cursor = transacciones.obtenerTodosLosContactos();

        // Configurar un adaptador para el ListView
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2, // Layout para cada elemento de la lista
                cursor,
                new String[] {Transacciones.nombre, Transacciones.telefono,Transacciones.nota}, // Columnas a mostrar
                new int[] {android.R.id.text1, android.R.id.text2}, // IDs de los TextViews en el layout
                0
        );

        // Establecer el adaptador en el ListView
        listViewContactos.setAdapter(adapter);
    }

    }




