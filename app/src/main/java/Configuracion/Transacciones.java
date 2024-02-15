package Configuracion;



public class Transacciones {
    // Nombre de la base datos
    public static final String DBName = "PM012024";

    // Creacion de las tablas de base de datos
    public static final String TablePersonas = "personas";

    // Creacion de los campos de base de datos
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    public static final String imagen = "imagen";

    // DDL Create
    public static final String CreateTablePersonas = "CREATE TABLE " + TablePersonas + " (" +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            nombre + " TEXT, " +
            telefono + " TEXT, " +
            nota + " TEXT, " +
            imagen + " BLOB)";

    // DDL Drop
    public static final String DropTablePersonas = "DROP TABLE IF EXISTS " + TablePersonas;

    // DML
    public static final String SelectAllPersonas = "SELECT * FROM " + TablePersonas;
}
