/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   BancoDePruebas - SEMANA 3

   IMPORTANTE:
   Esta clase NO tiene main.
   Es un componente auxiliar de la plataforma.

   El único punto de entrada del proyecto es:
       IngestaSensores.main()
   ============================================================ */

public class BancoDePruebas {

    private static final int[] TAMANOS = {1_000, 100_000, 1_000_000};

    /**
     * Experimento 1: búsqueda lineal en el peor caso.
     */
    public static void experimentoUno() {
        System.out.println("=== EXPERIMENTO 1: BUSQUEDA LINEAL, PEOR CASO ===");
        System.out.printf("%12s %16s %14s%n",
                "lecturas", "comparaciones", "tiempo (ms)");

        for (int n : TAMANOS) {
            LecturaSensor[] datos = GeneradorDatos.generar(n);
            String objetivo = GeneradorDatos.timestampEnPosicion(n - 1);

            long inicio = System.nanoTime();
            int pos = BuscadorLecturas.busquedaLinealPorTimestamp(datos, objetivo);
            long fin = System.nanoTime();

            System.out.printf("%12d %16d %14.3f%n",
                    n,
                    BuscadorLecturas.getComparaciones(),
                    (fin - inicio) / 1_000_000.0);

            if (pos < 0) {
                System.out.println(
                        "   ATENCION: no encontro una lectura que si existe.");
            }
        }

        System.out.println();
    }

    /**
     * Experimento 2: comparación de String mediante datos manuales
     * y datos generados.
     */
    public static void experimentoDos() {
        System.out.println("=== EXPERIMENTO 2: BUSQUEDA POR ESTACION ===");

        LecturaSensor[] aMano = {
            new LecturaSensor("EST-001", "0000000000", 15.0, 70.0, 20.0),
            new LecturaSensor("EST-002", "0000000001", 16.0, 68.0, 22.0),
            new LecturaSensor("EST-003", "0000000002", 17.0, 66.0, 24.0)
        };

        int posA = BuscadorLecturas.buscarPorEstacion(aMano, "EST-002");
        System.out.println(
                "Datos escritos a mano -> posicion de EST-002: " + posA);

        LecturaSensor[] generados = GeneradorDatos.generar(1_000);
        int posB = BuscadorLecturas.buscarPorEstacion(generados, "EST-002");
        System.out.println(
                "Datos generados       -> posicion de EST-002: " + posB);

        System.out.println();
    }

    /**
     * Experimento 3: comparación de búsqueda lineal y binaria por timestamp.
     *
     * Los timestamps generados están ordenados cronológicamente, por lo
     * que se cumple la precondición de la búsqueda binaria.
     */
    public static void experimentoTres() {
        System.out.println(
                "=== EXPERIMENTO 3: BINARIA vs LINEAL, POR TIMESTAMP ===");

        System.out.printf("%12s %14s %14s %12s%n",
                "lecturas", "comp. lineal", "comp. binaria", "veces mejor");

        for (int n : TAMANOS) {
            LecturaSensor[] datos = GeneradorDatos.generar(n);
            String objetivo = GeneradorDatos.timestampEnPosicion(n - 1);

            BuscadorLecturas.busquedaLinealPorTimestamp(datos, objetivo);
            int lineal = BuscadorLecturas.getComparaciones();

            BuscadorLecturas.busquedaBinariaPorTimestamp(datos, objetivo);
            int binaria = BuscadorLecturas.getComparaciones();

            System.out.printf("%12d %14d %14d %12.1f%n",
                    n,
                    lineal,
                    binaria,
                    (double) lineal / binaria);
        }

        System.out.println();
        System.out.println("-- Caso: el timestamp NO existe --");

        LecturaSensor[] datos = GeneradorDatos.generar(100_000);
        String fantasma = GeneradorDatos.timestampInexistente();

        int posLineal =
                BuscadorLecturas.busquedaLinealPorTimestamp(datos, fantasma);
        int compLineal = BuscadorLecturas.getComparaciones();

        int posBinaria =
                BuscadorLecturas.busquedaBinariaPorTimestamp(datos, fantasma);
        int compBinaria = BuscadorLecturas.getComparaciones();

        System.out.println(
                "Lineal  -> posicion: " + posLineal
                + ", comparaciones: " + compLineal);

        System.out.println(
                "Binaria -> posicion: " + posBinaria
                + ", comparaciones: " + compBinaria);

        System.out.println();
    }

    /**
     * Experimento 4: búsqueda binaria por PM2.5.
     *
     * Los valores buscados existen, pero los datos NO están ordenados
     * por PM2.5. El experimento demuestra la importancia de la
     * precondición de la búsqueda binaria.
     */
    public static void experimentoCuatro() {
        System.out.println("=== EXPERIMENTO 4: BINARIA POR PM2.5 ===");

        LecturaSensor[] datos = GeneradorDatos.generar(10_000);

        int aciertosLineal = 0;
        int aciertosBinaria = 0;

        // Tomamos 20 valores de PM2.5 que sí están en el arreglo.
        for (int i = 0; i < 20; i++) {
            double valor = datos[i * 137].getPm25();

            int posLineal = -1;

            for (int j = 0; j < datos.length; j++) {
                if (datos[j].getPm25() == valor) {
                    posLineal = j;
                    break;
                }
            }

            int posBinaria =
                    BuscadorLecturas.busquedaBinariaPorPm25(datos, valor);

            if (posLineal >= 0) {
                aciertosLineal++;
            }

            if (posBinaria >= 0) {
                aciertosBinaria++;
            }
        }

        System.out.println("Valores buscados que SI existen: " + 20);
        System.out.println(
                "Encontrados por busqueda lineal:  " + aciertosLineal);
        System.out.println(
                "Encontrados por busqueda binaria: " + aciertosBinaria);
        System.out.println();
    }
}
