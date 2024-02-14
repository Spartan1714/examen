package com.example.examen;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;




public class MainActivity extends AppCompatActivity {

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
        Spinner spinner = findViewById(R.id.spinner1);

        // Obtener el array de opciones del archivo de recursos
        String[] opciones = getResources().getStringArray(R.array.opciones_array);

        // Crear un adaptador para el Spinner usando el array de    opciones
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);

        // Especificar el diseño a usar cuando se despliega el Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el adaptador al Spinner
        spinner.setAdapter(adapter);
    }






}


