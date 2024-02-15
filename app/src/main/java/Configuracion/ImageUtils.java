package Configuracion;

import android.graphics.Bitmap;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    public static String guardarImagen(Bitmap bitmap) {
        String rutaArchivo = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/nombre_carpeta";
        File directorio = new File(rutaArchivo);

        // Si el directorio no existe, crearlo
        if (!directorio.exists()) {
            directorio.mkdirs();
        }


        // Crear el archivo de imagen
        File archivoImagen = new File(directorio, "nombre_imagen.jpg");
        try {
            FileOutputStream out = new FileOutputStream(archivoImagen);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // Comprimir la imagen
            out.flush();
            out.close();
            return archivoImagen.getAbsolutePath(); // Devolver la ruta del archivo de imagen
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
