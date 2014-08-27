package archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Archivos {

    private String Comprobar_archivo(String path) {
        String inf;
        File archivo = new File(path);
        if (archivo.exists()) {
            if (archivo.canWrite()) {
                inf = "El archivo existe y puedo escribir:";
            } else {
                inf = "El archivo existe, pero es de solo lectura:";
            }
        } else {
            inf = "El archivo NO existe:";
        }
        inf = inf + "  path: " + archivo.getAbsolutePath();
        return inf;
    }

    public String Crear_archivo(String path) {
        String s;
        final Formatter nuevoarchivo;
        try {//si no existe el archivo crearlo y escribir en el.
            // con este metodo de escritura se reescribe todo el archivo (se pierden todos los datos anteriores)
            nuevoarchivo = new Formatter(path);
            s = "El archivo ha sido creado";
            nuevoarchivo.format("%s", "linea 1");
            nuevoarchivo.close();
        } catch (FileNotFoundException e) {
            s = "Error al crear archivo " + e;
        }
        return s;
    }

    public String Leer_Archivo(String path) {
        String inf = "";
        ArrayList<String> lineas = new ArrayList<>();

        //Lectura 
        try (Scanner archivo_entrada = new Scanner(new File(path));) {
            while (archivo_entrada.hasNext()) {
                lineas.add(archivo_entrada.nextLine());
            }
            archivo_entrada.close();
        } catch (FileNotFoundException e) {
            inf = "Error de lectura" + e;
        }
        //recorrer el ArrayList lineas
        for (String string : lineas) {
            inf += string + "\n";
        }
        return inf;
    }

    public void Escribir_en_archivos_sin_borrar(String path, String datos) {

        //segundo metodo(forma) para guardar (escribe despues del texto que ya existia en el archivo)
        try {

            BufferedWriter out = new BufferedWriter(new FileWriter(path, true));
            out.write(datos + "\n");
            out.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String Contenido_de_carpeta(String path) {
        String contenido = "";
        //        String sDirectorio = "/home/lp-ub-14/Documentos/catagolo/catalogos";
        String sDirectorio = path;
        File f = new File(sDirectorio);
        if (f.exists()) {
            System.out.println("Directorio existe ");
            File[] ficheros = f.listFiles();
            System.out.println("Elementos que contiene = " + ficheros.length);
            for (int x = 0; x < ficheros.length; x++) {
                System.out.println(ficheros[x].getName());
            }
        } else {
            System.out.println("Directorio no existe ");
        }
        String path_catalogos = f.getAbsolutePath();
        System.out.println("ubicacion:    " + path_catalogos);
        return contenido;
    }

    public boolean Expresion_regular(String dato, String Expresion_regular) {
        // Si dato coincide con la Expresion_regular devolvera TRUE
        boolean a = false;
        Pattern pat = Pattern.compile(Expresion_regular);
        Matcher mat = pat.matcher(dato);
        if (mat.matches()) {
            a = true;
        }
        return a;
    }

    public static void main(String[] args) {
        Archivos archivos = new Archivos();
        String path = "documentos/archivo.txt";
        String path_carpeta = "documentos";
        String inf;

        System.out.println("Comprobar si existe el archivo:\n");
        inf = archivos.Comprobar_archivo(path);
        System.out.println(inf);

        if (archivos.Expresion_regular(inf, "El archivo NO existe.*")) {
            // La carpeta donde se creara el archivo si tiene que existir 
            String a;
            a = archivos.Crear_archivo(path);
            System.out.println(a);
            inf = archivos.Comprobar_archivo(path);
        }
        if (archivos.Expresion_regular(inf, "El archivo existe.*")) {
            System.out.println("Leer el archivo");

            String lineas = archivos.Leer_Archivo(path);
            System.out.println(lineas);
        }

        if (archivos.Expresion_regular(inf, "El archivo existe y puedo escribir.*")) {
            System.out.println("Escribir en el archivo");
            archivos.Escribir_en_archivos_sin_borrar(path, "nueva linea");
            String lineas = archivos.Leer_Archivo(path);
            System.out.println(lineas);
        }
        
        archivos.Contenido_de_carpeta(path_carpeta);
    }

}
