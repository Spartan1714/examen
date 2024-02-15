package com.example.examen;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

import Configuracion.ImageUtils;
import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;


public class MainActivity extends AppCompatActivity {

    // Declaración de variables
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    private static final int PICK_IMAGE_REQUEST = 1;
    public void selectImageFromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            // Aquí puedes usar la Uri para mostrar la imagen seleccionada en tu aplicación
            // Por ejemplo, puedes cargar la imagen en un ImageView
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de la base de datos
        dbHelper = new SQLiteConexion(this);
        db = dbHelper.getWritableDatabase();

        // Obtener referencia al ImageView
        View imageView = findViewById(R.id.imageView);

        // Obtener referencia al botón Salvar Contacto
        Button salvarContactoButton = findViewById(R.id.buttonSave);

        // Configurar OnClickListener para el botón Salvar Contacto
        salvarContactoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el Bitmap de la imagen del ImageView
                Bitmap bitmap = imageView.getDrawingCache();

                // Verificar si el Bitmap es nulo
                if (bitmap != null) {
                    // Convertir el Bitmap a una matriz de bytes
                    byte[] imagenBytes = convertirBitmapABytes(bitmap);

                    // Insertar los datos en la base de datos
                    insertarDatos("Juan", "123456789", "Nota de ejemplo", imagenBytes);
                } else {
                    mostrarMensaje("Error: No se pudo obtener el Bitmap de la imagen");
                }
            }
        });
    }

    // Método para convertir un Bitmap a una matriz de bytes
    private byte[] convertirBitmapABytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Comprimir el Bitmap en formato PNG
        return stream.toByteArray(); // Devolver la matriz de bytes resultante
    }

    // Método para insertar datos en la tabla personas
    private void insertarDatos(String nombre, String telefono, String nota, byte[] imagen) {
        // Crear un objeto ContentValues para almacenar los valores que queremos insertar
        ContentValues values = new ContentValues();
        values.put(Transacciones.nombre, nombre);
        values.put(Transacciones.telefono, telefono);
        values.put(Transacciones.nota, nota);
        values.put(Transacciones.imagen, imagen); // Guardar la imagen en la base de datos

        // Insertar los datos en la base de datos
        long newRowId = db.insert(Transacciones.TablePersonas, null, values);

        // Mostrar mensaje dependiendo del resultado de la inserción
        if (newRowId != -1) {
            mostrarMensaje("Datos ingresados");
        } else {
            mostrarMensaje("Error al guardar los datos");
        }
    }

    // Método para mostrar un mensaje con un Toast
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}



