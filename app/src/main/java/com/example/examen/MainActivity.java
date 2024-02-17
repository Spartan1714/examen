package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import Configuracion.Transacciones;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextTelefono, editTextNota;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Uri imageUri;
    private Transacciones transacciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Transacciones
        transacciones = new Transacciones(this);

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

        // Obtener referencias a los elementos de la interfaz
        editTextNombre = findViewById(R.id.TetxtNombre);
        editTextTelefono = findViewById(R.id.TextPhone);
        editTextNota = findViewById(R.id.TextNota);
        imageView = findViewById(R.id.imageView);

        // Configurar el botón Guardar
        Button btnGuardar = findViewById(R.id.buttonSave);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarContacto();
            }
        });

        // Configurar el botón para abrir la galería
        Button btnAbrirGaleria = findViewById(R.id.selectImageButton);
        btnAbrirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        // Configurar el botón para ver los contactos salvados
        Button btnContactosSalvados = findViewById(R.id.button3);
        btnContactosSalvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verContactosSalvados();
            }
        });
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void guardarContacto() {
        String nombre = editTextNombre.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String nota = editTextNota.getText().toString();

        // Verificar que se haya seleccionado una imagen
        if (imageUri != null) {
            String rutaImagen = obtenerRutaDeImagen(imageUri);

            // Verificar que los campos no estén vacíos y la ruta de la imagen sea válida
            if (!nombre.isEmpty() && !telefono.isEmpty() && !nota.isEmpty() && rutaImagen != null) {
                // Insertar el contacto en la base de datos
                boolean insertado = transacciones.insertarContacto(nombre, telefono, nota, rutaImagen);

                if (insertado) {
                    Toast.makeText(this, "Contacto guardado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(this, "Error al guardar el contacto", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Por favor, llene todos los campos y seleccione una imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor, seleccione una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void verContactosSalvados() {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    private String obtenerRutaDeImagen(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String rutaImagen = cursor.getString(columnIndex);
            cursor.close();
            return rutaImagen;
        }
        return null;
    }

    private void limpiarCampos() {
        editTextNombre.setText("");
        editTextTelefono.setText("");
        editTextNota.setText("");
        imageView.setImageURI(null);
    }
}







