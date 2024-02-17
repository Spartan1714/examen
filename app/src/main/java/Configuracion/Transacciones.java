package Configuracion;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class Transacciones {
    public static final String TablePersonas = "personas";
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    public static final String imagen = "imagen";

    private static SQLiteConexion dbHelper;

    //private android.content.Context Context;


    public Transacciones(Context context) {
        dbHelper = new SQLiteConexion(context);
    }

    public boolean insertarContacto(String nombre, String telefono, String nota, String imagen) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("telefono", telefono);
        values.put("nota", nota);
        values.put("imagen", imagen);

        try {
            long result = db.insertOrThrow("personas", null, values);
            db.close();
            return result != -1;
        } catch (SQLException e) {
            // Captura la excepción y muestra el mensaje de error
            Log.e("Transacciones", "Error al insertar contacto: " + e.getMessage());
            return false;
        }
    }
    public static final String CreateTablePersonas =
            "CREATE TABLE " + TablePersonas + " (" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    nombre + " TEXT, " +
                    telefono + " TEXT, " +
                    nota + " TEXT, " +
                    "imagen TEXT)";
    public static final String DropTablePersonas = "DROP TABLE IF EXISTS " + TablePersonas;




    public static Cursor obtenerTodosLosContactos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM personas", null);
    }



    public static void eliminarContacto(String nombreContacto) {
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        db.delete(TablePersonas, nombre + " = ?", new String[]{nombreContacto});
        db.close();
    }



    public boolean actualizarContacto(String nombreAnterior, String nuevoNombre, String nuevoTelefono, String nuevaNota, String rutaImagen) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nuevoNombre);
        values.put("telefono", nuevoTelefono);
        values.put("nota", nuevaNota);
        values.put("imagen", rutaImagen);

        // Actualizar el registro en la base de datos
        int filasActualizadas = db.update("personas", values, "nombre = ?", new String[]{nombreAnterior});
        db.close();

        // Verificar si se actualizó al menos una fila
        return filasActualizadas > 0;
    }
    public String obtenerRutaImagen(String nombreContacto) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {imagen};
        String selection = nombre + " = ?";
        String[] selectionArgs = {nombreContacto};
        Cursor cursor = db.query(TablePersonas, projection, selection, selectionArgs, null, null, null);
        String rutaImagen = null;
        if (cursor != null && cursor.moveToFirst()) {
            int index = cursor.getColumnIndexOrThrow(imagen);
            rutaImagen = cursor.getString(index);
            cursor.close();
        }
        return rutaImagen;
    }

    public boolean actualizarRutaImagen(String nombreContacto, String nuevaRutaImagen) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(imagen, nuevaRutaImagen);
        String selection = nombre + " = ?";
        String[] selectionArgs = {nombreContacto};
        int rowsUpdated = db.update(TablePersonas, values, selection, selectionArgs);
        return rowsUpdated > 0;
    }

}










