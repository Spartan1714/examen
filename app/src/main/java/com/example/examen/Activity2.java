package com.example.examen;
import androidx.appcompat.app.AppCompatActivity;

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
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridLayout;


public class Activity2 extends AppCompatActivity {
    private Transacciones transacciones;
    ListView listViewContactos;
    ArrayAdapter<String> adaptador;
    ArrayList<String> listaContactos;
    private GridLayout gridLayoutContactos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        gridLayoutContactos = findViewById(R.id.gridLayoutContactos);

        cargarDatosDesdeBaseDeDatos();
    }

    private void cargarDatosDesdeBaseDeDatos() {
        Transacciones transacciones = new Transacciones(this);
        Cursor cursor = transacciones.obtenerTodosLosContactos();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(Transacciones.nombre));
                @SuppressLint("Range") String telefono = cursor.getString(cursor.getColumnIndex(Transacciones.telefono));
                @SuppressLint("Range") String nota = cursor.getString(cursor.getColumnIndex(Transacciones.nota));

                agregarContactoAGridLayout(nombre, telefono, nota);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void agregarContactoAGridLayout(String nombre, String telefono, String nota) {
        // Crear TextViews para cada dato del contacto
        TextView textViewNombre = crearTextView(nombre, 18);
        TextView textViewTelefono = crearTextView(telefono, 16);
        TextView textViewNota = crearTextView(nota, 14);

        // Agregar TextViews al GridLayout
        gridLayoutContactos.addView(textViewNombre);
        gridLayoutContactos.addView(textViewTelefono);
        gridLayoutContactos.addView(textViewNota);
    }

    private TextView crearTextView(String texto, int textSize) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setTextSize(textSize);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        textView.setLayoutParams(layoutParams);
        return textView;
    }


    }






