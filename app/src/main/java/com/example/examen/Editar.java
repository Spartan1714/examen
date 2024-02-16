package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Configuracion.Transacciones;

public class Editar extends AppCompatActivity {
    private EditText editTextNombre;
    private EditText editTextTelefono;
    private EditText editTextNota;

    private String nombreAnterior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
          nombreAnterior = getIntent().getStringExtra("nombreAnterior");


                // Inicializar vistas
                editTextNombre = findViewById(R.id.editTextNombre);
                editTextTelefono = findViewById(R.id.editTextTelefono);
                editTextNota = findViewById(R.id.editTextNota);

                // Obtener datos del intent
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    nombreAnterior = extras.getString("nombre");
                    String telefono = extras.getString("telefono");
                    String nota = extras.getString("nota");

                    // Mostrar los datos en las vistas
                    editTextNombre.setText(nombreAnterior);
                    editTextTelefono.setText(telefono);
                    editTextNota.setText(nota);
                }

                // Configurar el botón Guardar Cambios
                Button btnGuardarCambios = findViewById(R.id.btnGuardar);
                btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Obtener los datos editados
                        String nuevoNombre = editTextNombre.getText().toString().trim();
                        String nuevoTelefono = editTextTelefono.getText().toString().trim();
                        String nuevaNota = editTextNota.getText().toString().trim();

                        // Validar si los campos están vacíos
                        if (nuevoNombre.isEmpty() || nuevoTelefono.isEmpty() || nuevaNota.isEmpty()) {
                            Toast.makeText(Editar.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                        } else {
                            // Guardar los cambios en la base de datos
                            guardarCambios(nombreAnterior,nuevoNombre, nuevoTelefono, nuevaNota);
                        }
                    }
                });
            }

    private void guardarCambios(String nombreAnterior, String nuevoNombre, String nuevoTelefono, String nuevaNota) {
        // Aquí deberías implementar la lógica para guardar los cambios en la base de datos
        Transacciones transacciones = new Transacciones(this);
        boolean exito = transacciones.actualizarContacto(nombreAnterior, nuevoNombre, nuevoTelefono, nuevaNota);

        if (exito) {
            Toast.makeText(this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
            // Finalizar la actividad después de guardar los cambios
            finish();
            // Reiniciar la Activity2 para refrescar los datos
            Intent intent = new Intent(Editar.this, MainActivity2.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al guardar los cambios", Toast.LENGTH_SHORT).show();
        }
    }
        }







