package Configuracion;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import android.widget.Toast;

public class Transacciones {
    public static final String TablePersonas = "personas";
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    private static SQLiteConexion dbHelper;
    private Context context;
    //private android.content.Context Context;


    public  Transacciones(Context context) {
        dbHelper = new SQLiteConexion(context);
    }

    public boolean insertarContacto(String nombre, String telefono, String nota) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("telefono", telefono);
        values.put("nota", nota);

        long result = db.insert("personas", null, values);
        db.close();

        // Si result es igual a -1, significa que la inserción falló
        return result != -1;
    }
    public static final String CreateTablePersonas =
            "CREATE TABLE " + TablePersonas + " (" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    nombre + " TEXT, " +
                    telefono + " TEXT, " +
                    nota + " TEXT)";
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
    public boolean actualizarContacto(String nombreAnterior, String nuevoNombre, String nuevoTelefono, String nuevaNota) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nuevoNombre);
        values.put("telefono", nuevoTelefono);
        values.put("nota", nuevaNota);

        // Actualizar el registro en la base de datos
        int filasActualizadas = db.update("personas", values, "nombre = ?", new String[]{nombreAnterior});
        db.close();

        // Verificar si se actualizó al menos una fila
        return filasActualizadas > 0;
    }






}



