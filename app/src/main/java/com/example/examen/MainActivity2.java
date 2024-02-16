package com.example.examen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;

public class MainActivity2 extends AppCompatActivity {

    private Transacciones transacciones;
    private LinearLayout container;

    SQLiteConexion dbHelper = new SQLiteConexion(this); // Asegúrate de pasar el contexto adecuado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        container = findViewById(R.id.container);
        transacciones = new Transacciones(this);




        // Obtener los registros de la base de datos y mostrarlos en el LinearLayout
        Cursor cursor = Transacciones.obtenerTodosLosContactos();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                @SuppressLint("Range") String telefono = cursor.getString(cursor.getColumnIndex("telefono"));
                @SuppressLint("Range") String nota = cursor.getString(cursor.getColumnIndex("nota"));

                // Crear un TextView para mostrar los datos del registro
                TextView textView = new TextView(this);
                textView.setText("Nombre: " + nombre + "\nTeléfono: " + telefono + "\nNota: " + nota);

                TextView textView2 = new TextView(this);
                textView2.setText("Nombre: " + nombre + "\nTeléfono: " + telefono + "\nNota: " + nota);

                // Agregar el TextView al contenedor
                container.addView(textView);
                // Aplicar el estilo definido a los TextView
                textView.setTextAppearance(this, R.style.RegistroTextView);

                // Agregar un LinearLayout adicional para separación entre registros
                LinearLayout separacion = new LinearLayout(this);
                separacion.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                separacion.setOrientation(LinearLayout.VERTICAL);
                separacion.setBackgroundColor(getResources().getColor(android.R.color.transparent)); // Color de fondo transparente
                separacion.setMinimumHeight(16); // Altura de la separación en píxeles o dp

                // Agregar el LinearLayout de separación al contenedor
                container.addView(separacion);

                // Establecer el listener para manejar los clics en los elementos del LinearLayout
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Aquí puedes realizar la acción deseada cuando se hace clic en el registro
                        // Por ejemplo, mostrar un diálogo para editar o eliminar el registro
                        mostrarDialogoEditarEliminar(nombre, telefono, nota);
                    }
                });
            }
            cursor.close();
        }
    }

    // Método para mostrar un diálogo para editar o eliminar el registro seleccionado
    private void mostrarDialogoEditarEliminar(final String nombre, final String telefono, final String nota) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Qué acción deseas realizar con este registro?")
                .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Crear un Intent para iniciar la actividad de edición
                        Intent intent = new Intent(MainActivity2.this, Editar.class);
                        // Pasar los datos del registro como extras en el Intent
                        intent.putExtra("nombreAnterior", nombre);
                        intent.putExtra("nombre", nombre);
                        intent.putExtra("telefono", telefono);
                        intent.putExtra("nota", nota);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Aquí puedes implementar la lógica para eliminar el registro
                        transacciones.eliminarContacto(nombre);
                        // Después de eliminar el registro, actualiza la vista para reflejar los cambios
                        recreate();
                    }
                })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción neutral, no se realiza ninguna acción
                    }
                });
        builder.create().show();
    }


    // Método para manejar el clic en el botón "Eliminar"
    public void eliminarRegistro(View view) {
        // Este método no es necesario ya que la lógica de eliminación está en el diálogo de confirmación
    }

    // Método para manejar el clic en el botón "Editar"
    public void editarRegistro(View view) {
        // Este método no es necesario ya que la lógica de edición está en el diálogo de confirmación
    }



}

