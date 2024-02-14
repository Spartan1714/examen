package com.example.examen;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtén una referencia al Spinner en tu actividad
        Spinner spinner = findViewById(R.id.spinner1);

        // Crea un ArrayAdapter utilizando el recurso de cadena y un diseño de spinner por defecto
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opciones_array, android.R.layout.simple_spinner_item);

        // Especifica el diseño que quieres utilizar cuando se desplieguen las opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Aplica el adaptador al Spinner
        spinner.setAdapter(adapter);


    }
}