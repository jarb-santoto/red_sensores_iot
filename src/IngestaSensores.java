/* ============================================================
   RED DE MONITOREO AMBIENTAL URBANO - MODULO DE INGESTA
   Version 0.1 - "funciona en mi maquina"

   Este programa lee el archivo lecturas.csv con los datos
   crudos de las estaciones de sensores y produce un reporte
   de calidad del aire.

   NO MODIFIQUES ESTE ARCHIVO ANTES DE LA FASE 0.
   Primero se predice, despues se ejecuta.
   ============================================================ */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Lee mediciones ambientales desde un archivo CSV y genera un reporte.
 *
 * <p>El programa calcula promedios de temperatura, humedad y PM2.5.
 * Tambien identifica la estacion que tiene la lectura de PM2.5 mas alta.</p>
 */
public class IngestaSensores {

    /**
     * Punto de entrada del programa.
     *
     * @param args argumentos recibidos desde la linea de comandos; este
     *             programa no necesita argumentos
     * @throws IOException si ocurre un problema al abrir, leer o cerrar el CSV
     */
    public static void main(String[] args) throws IOException {

        // Nombre del archivo que contiene las lecturas de los sensores.
        String archivo = "lecturas.csv";

        // BufferedReader permite leer el archivo una linea a la vez.
        BufferedReader br = new BufferedReader(new FileReader(archivo));

        // Variables de control y acumuladores para preparar el reporte.
        String linea;
        int c = 0;
        double s1 = 0;
        double s2 = 0;
        double s3 = 0;
        double max = 0;
        String est = "";

        // La primera linea contiene los nombres de las columnas, no una lectura.
        linea = br.readLine();

        // Se repite mientras existan lineas pendientes en el archivo.
        while ((linea = br.readLine()) != null) {

            // Separa la linea usando la coma y guarda cada dato en una posicion.
            String[] p = linea.split(",");

            // Las posiciones corresponden a: id, fecha/hora, temperatura,
            // humedad y PM2.5. Los tres ultimos valores se convierten a double.
            String id = p[0];
            String t = p[1];
            double x1 = Double.parseDouble(p[2]);
            double x2 = Double.parseDouble(p[3]);
            double x3 = Double.parseDouble(p[4]);

            // Se suman las mediciones para calcular sus promedios al final.
            s1 = s1 + x1;
            s2 = s2 + x2;
            s3 = s3 + x3;
            c = c + 1;

            // Si PM2.5 supera el maximo anterior, se guarda el nuevo maximo
            // junto con el identificador de la estacion que lo produjo.
            if (x3 > max) {
                max = x3;
                est = id;
            }

            // Muestra cada lectura procesada en la consola.
            System.out.println(id + " | " + t + " | T=" + x1 + " | H=" + x2 + " | PM=" + x3);
        }

        // El archivo ya no se necesita y se libera el recurso asociado.
        br.close();

        // Presenta los resultados acumulados. Un promedio es suma / cantidad.
        System.out.println("");
        System.out.println("=== REPORTE DE CALIDAD DEL AIRE ===");
        System.out.println("Registros procesados: " + c);
        System.out.println("Temperatura promedio: " + (s1 / c) + " C");
        System.out.println("Humedad promedio: " + (s2 / c) + " %");
        System.out.println("PM2.5 promedio: " + (s3 / c) + " ug/m3");
        System.out.println("Estacion mas contaminada: " + est + " con " + max + " ug/m3");
        System.out.println("===================================");
    }
}
