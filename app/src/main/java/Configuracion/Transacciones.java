package Configuracion;

public class Transacciones {
    // Nombre de la base de datos
    public static final String DBName = "PM012024";

    // Creación de las tablas de la base de datos
    public static final String TablePersonas = "personas";

    // Creación de los campos de la base de datos
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";
    // Nuevos campos
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    public static final String imagen = "imagen";

    // DDL Create
    public static final String CreateTablePersonas = "CREATE TABLE " + TablePersonas + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombres TEXT, " +
            "apellidos TEXT, " +
            "edad INTEGER, " +
            "correo TEXT, " +
            "telefono TEXT, " +
            "nota TEXT, " +
            "imagen TEXT)";

    // DDL Drop
    public static final String DropTablePersonas = "DROP TABLE IF EXISTS " + TablePersonas;

    // DML
    public static final String SelectAllPersonas = "SELECT * FROM " + TablePersonas;
}
