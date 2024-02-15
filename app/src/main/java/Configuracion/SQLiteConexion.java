package Configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteConexion extends SQLiteOpenHelper {

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "mi_base_de_datos";
    // Versión de la base de datos
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public SQLiteConexion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método llamado al crear la base de datos por primera vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla personas
        db.execSQL(Transacciones.CreateTablePersonas);
    }

    // Método llamado cuando se necesita actualizar la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Este método se llama si la versión de la base de datos cambia
        // Aquí puedes realizar las actualizaciones necesarias o eliminar y volver a crear la tabla
        db.execSQL(Transacciones.DropTablePersonas);
        onCreate(db);
    }
}
