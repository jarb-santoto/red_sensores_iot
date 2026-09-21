/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   IngestaSensores - SEMANA 3

   Este es el ÚNICO punto de entrada de todo el proyecto.

   Las semanas no crean aplicaciones independientes:
   cada semana agrega capacidades a esta misma plataforma.
   ============================================================ */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IngestaSensores {

    private static final String ARCHIVO = "data/lecturas_ampliadas.csv";
    private static final int CAMPOS_ESPERADOS = 5;

    private static int descartadasPorFormato = 0;
    private static int descartadasPorRango = 0;

    public static void main(String[] args) throws IOException {

        RepositorioLecturas repositorio = new RepositorioLecturas();
        AnalizadorMatriz analizador = new AnalizadorMatriz();

        cargarArchivo(repositorio, analizador);

        imprimirResumenIngesta(repositorio);
        imprimirPerfilHorario(analizador);

        // =====================================================
        // SEMANA 3 - BÚSQUEDA Y ANÁLISIS DE EFICIENCIA
        // =====================================================
        //
        // BancoDePruebas NO tiene main.
        // Los experimentos son parte de esta misma aplicación.
        //
        ejecutarExperimentosSemanaTres();
    }

    /**
     * Ejecuta las pruebas de la Semana 3 desde el único main
     * del proyecto.
     */
    private static void ejecutarExperimentosSemanaTres() {
        System.out.println();
        System.out.println("====================================================");
        System.out.println("       SEMANA 3 - BUSQUEDA Y EFICIENCIA");
        System.out.println("====================================================");
        System.out.println();

        BancoDePruebas.experimentoUno();
        BancoDePruebas.experimentoDos();
        BancoDePruebas.experimentoTres();
        BancoDePruebas.experimentoCuatro();
    }

    private static void imprimirResumenIngesta(
            RepositorioLecturas repositorio) {

        System.out.println();
        System.out.println("=== INGESTA ===");
        System.out.println(
                "Lecturas almacenadas:      " + repositorio.tamano());
        System.out.println(
                "Descartadas por formato:   " + descartadasPorFormato);
        System.out.println(
                "Descartadas por rango:     " + descartadasPorRango);
        System.out.println();
        System.out.println(
                "PM2.5 promedio (repositorio): "
                + repositorio.promedioPm25());
    }

    private static void imprimirPerfilHorario(
            AnalizadorMatriz analizador) {

        System.out.println();
        System.out.println("=== PERFIL HORARIO DE LA CIUDAD ===");

        for (int h = 0; h < 24; h++) {
            System.out.printf(
                    "Hora %02d -> PM2.5 promedio: %.2f%n",
                    h,
                    analizador.promedioDeHora(h));
        }
    }

    /**
     * Lee el archivo línea por línea y alimenta el repositorio y la matriz.
     */
    private static void cargarArchivo(
            RepositorioLecturas repositorio,
            AnalizadorMatriz analizador) throws IOException {

        try (BufferedReader lector =
                     new BufferedReader(new FileReader(ARCHIVO))) {

            lector.readLine(); // encabezado

            String linea;

            while ((linea = lector.readLine()) != null) {

                LecturaSensor lectura = construirLectura(linea);

                if (lectura == null) {
                    continue;
                }

                if (!lectura.esValida()) {
                    descartadasPorRango++;
                    continue;
                }

                if (!repositorio.agregar(lectura)) {
                    System.err.println(
                            "ADVERTENCIA: no se pudo almacenar "
                            + lectura.getIdSensor());
                    continue;
                }

                analizador.registrar(lectura);
            }
        }
    }

    /**
     * Convierte una línea del CSV en un objeto LecturaSensor.
     *
     * @return la lectura, o null si la línea está mal formada
     */
    private static LecturaSensor construirLectura(String linea) {

        String[] campos = linea.split(",");

        if (campos.length != CAMPOS_ESPERADOS) {
            descartadasPorFormato++;
            return null;
        }

        try {
            double temperatura = Double.parseDouble(campos[2]);
            double humedad = Double.parseDouble(campos[3]);
            double pm25 = Double.parseDouble(campos[4]);

            return new LecturaSensor(
                    campos[0],
                    campos[1],
                    temperatura,
                    humedad,
                    pm25);

        } catch (NumberFormatException e) {
            descartadasPorFormato++;
            return null;
        }
    }
}
