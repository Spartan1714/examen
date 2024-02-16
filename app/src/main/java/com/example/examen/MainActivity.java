package com.example.examen;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import Configuracion.Transacciones;


public class MainActivity extends AppCompatActivity {

    // Declaración de variables
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;
    private EditText editTextNombre, editTextTelefono, editTextNota;
    private Button btnGuardar;

    private Transacciones transacciones;
    private static final int PICK_IMAGE_REQUEST = 1;

    public void selectImageFromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtén referencia al Spinner
        Spinner spinner = findViewById(R.id.spinner1);

        // Carga los elementos del Spinner desde strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.opciones_array, // El array definido en strings.xml
                android.R.layout.simple_spinner_item // Diseño de cada elemento del Spinner
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Diseño del menú desplegable
        spinner.setAdapter(adapter);
        editTextNombre = findViewById(R.id.TetxtNombre);
        editTextTelefono = findViewById(R.id.TextPhone);
        editTextNota = findViewById(R.id.TextNota);
        btnGuardar = findViewById(R.id.buttonSave);

        // Crear una instancia de Transacciones pasando el contexto de MainActivity
        transacciones = new Transacciones(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarContacto();
            }
        });
        Button btnContactosSalvados = findViewById(R.id.button3);

        btnContactosSalvados.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Lógica para cambiar a la actividad Activity2
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                startActivity(intent);
            }
        });






    }

    private void guardarContacto() {
        String nombre = editTextNombre.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String nota = editTextNota.getText().toString();

        // Verificar que los campos no estén vacíos
        if (!nombre.isEmpty() && !telefono.isEmpty() && !nota.isEmpty()) {
            // Insertar el contacto en la base de datos
            boolean insertado = transacciones.insertarContacto(nombre, telefono, nota);

            if (insertado) {
                // Mostrar un mensaje de éxito si la inserción fue exitosa
                Toast.makeText(this, "Contacto guardado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                // Mostrar un mensaje de error si la inserción falló
                Toast.makeText(this, "Error al guardar el contacto", Toast.LENGTH_SHORT).show();
            }

            // Limpiar los campos después de guardar
            editTextNombre.setText("");
            editTextTelefono.setText("");
            editTextNota.setText("");
        } else {
            // Mostrar un mensaje de error o requerir al usuario que llene todos los campos
            Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }




}





