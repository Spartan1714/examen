package com.example.examen;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;

import Configuracion.Transacciones;

public class Editar extends AppCompatActivity {
    private EditText editTextNombre;
    private EditText editTextTelefono;
    private EditText editTextNota;
    private String nombreAnterior;
    private ImageView imageView;
    private String  rutaImagen;




    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        if (rutaImagen != null) {
            Picasso.get().load(new File("rutaImagen")).into(imageView);


        }

        Intent intent = getIntent();

        rutaImagen = intent.getStringExtra("rutaImagen");
        Toast.makeText(Editar.this, "RUTA" + rutaImagen, Toast.LENGTH_SHORT).show();





        imageView = findViewById(R.id.imageView);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextNota = findViewById(R.id.editTextNota);

        // Obtener datos del intent
        nombreAnterior = getIntent().getStringExtra("nombre");
        String telefono = getIntent().getStringExtra("telefono");
        String nota = getIntent().getStringExtra("nota");









        // Mostrar los datos en las vistas
        editTextNombre.setText(nombreAnterior);
        editTextTelefono.setText(telefono);
        editTextNota.setText(nota);

        // Configurar el botón Cambiar Foto
        Button btnCambiarFoto = findViewById(R.id.btnCambiarFoto);
        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        // Configurar el botón Guardar Cambios
        Button btnGuardarCambios = findViewById(R.id.btnGuardar);
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Editar.this, MainActivity2.class);
                startActivity(intent);
                // Obtener los datos editados
                String nuevoNombre = editTextNombre.getText().toString().trim();
                String nuevoTelefono = editTextTelefono.getText().toString().trim();
                String nuevaNota = editTextNota.getText().toString().trim();

                // Validar si los campos están vacíos
                if (nuevoNombre.isEmpty() || nuevoTelefono.isEmpty() || nuevaNota.isEmpty()) {
                    Toast.makeText(Editar.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Guardar los cambios en la base de datos
                    guardarCambios(nuevoNombre, nuevoTelefono, nuevaNota);
                }
            }
        });


    }



    private void cargarImagenDesdeRuta(String rutaImagen) {
        if (rutaImagen != null) {
            // Cargar la imagen desde la ruta y establecerla en el ImageView
            Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);
            imageView.setImageBitmap(bitmap);
        } else {
            // Manejar el caso en el que la ruta de la imagen sea nula
            Toast.makeText(this, "La ruta de la imagen es nula", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data != null) {
            Uri imageUri = data.getData();
            rutaImagen = obtenerRutaDeImagen(imageUri);
            cargarImagenDesdeUri(imageUri);
        }
    }

    private String obtenerRutaDeImagen(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = null;
        String ruta = null;
        try {
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                ruta = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return ruta;
    }

    private void cargarImagenDesdeUri(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarCambios(String nuevoNombre, String nuevoTelefono, String nuevaNota) {
        Transacciones transacciones = new Transacciones(this);
        boolean exito = transacciones.actualizarContacto(nombreAnterior, nuevoNombre, nuevoTelefono, nuevaNota, rutaImagen);

        if (exito) {
            Toast.makeText(this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al guardar los cambios", Toast.LENGTH_SHORT).show();
        }
    }
}
