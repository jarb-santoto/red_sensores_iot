/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   BuscadorLecturas - SEMANA 3 - VERSION CORREGIDA

   La búsqueda es una nueva capacidad de la plataforma.
   No es una aplicación independiente.

   Cada método registra la cantidad de comparaciones de la
   última búsqueda ejecutada.
   ============================================================ */

public class BuscadorLecturas {

    /** Comparaciones de la última búsqueda ejecutada. */
    private static int comparaciones = 0;

    public static int getComparaciones() {
        return comparaciones;
    }

    // =========================================================
    // BÚSQUEDA LINEAL
    // =========================================================

    /**
     * Recorre el arreglo desde el inicio hasta encontrar el timestamp.
     * No requiere que los datos estén ordenados.
     *
     * @return posición de la lectura, o -1 si no existe
     */
    public static int busquedaLinealPorTimestamp(
            LecturaSensor[] datos, String timestamp) {

        comparaciones = 0;

        for (int i = 0; i < datos.length; i++) {
            comparaciones++;

            if (datos[i].getTimestamp().equals(timestamp)) {
                return i;
            }
        }

        return -1;
    }

    // =========================================================
    // BÚSQUEDA BINARIA POR TIMESTAMP
    // =========================================================

    /**
     * Busca un timestamp mediante búsqueda binaria.
     *
     * PRECONDICIÓN:
     * las lecturas deben estar ordenadas ascendentemente por timestamp.
     *
     * @return posición de la lectura, o -1 si no existe
     */
    public static int busquedaBinariaPorTimestamp(
            LecturaSensor[] datos, String timestamp) {

        comparaciones = 0;

        int inicio = 0;
        int fin = datos.length - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            comparaciones++;

            int cmp = datos[medio].getTimestamp().compareTo(timestamp);

            if (cmp == 0) {
                return medio;
            } else if (cmp < 0) {
                // El objetivo está a la derecha de medio.
                inicio = medio + 1;
            } else {
                // El objetivo está a la izquierda de medio.
                fin = medio - 1;
            }
        }

        return -1;
    }

    // =========================================================
    // BÚSQUEDA BINARIA POR PM2.5
    // =========================================================

    /**
     * Busca un valor de PM2.5 mediante búsqueda binaria.
     *
     * PRECONDICIÓN:
     * las lecturas deben estar ordenadas ascendentemente por PM2.5.
     *
     * El GeneradorDatos de esta semana NO garantiza ese orden.
     * Por eso este método se utiliza como experimento para demostrar
     * qué ocurre cuando una precondición de búsqueda binaria no se cumple.
     *
     * @return posición de una lectura con ese PM2.5, o -1 si no la halla
     */
    public static int busquedaBinariaPorPm25(
            LecturaSensor[] datos, double pm25) {

        comparaciones = 0;

        int inicio = 0;
        int fin = datos.length - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            comparaciones++;

            if (datos[medio].getPm25() == pm25) {
                return medio;
            } else if (datos[medio].getPm25() < pm25) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return -1;
    }

    // =========================================================
    // BÚSQUEDA LINEAL POR ESTACIÓN
    // =========================================================

    /**
     * Busca la primera lectura de una estación.
     *
     * La comparación de String se realiza por contenido mediante
     * equals(), no mediante ==.
     *
     * @return posición de la primera lectura de la estación,
     *         o -1 si no existe
     */
    public static int buscarPorEstacion(
            LecturaSensor[] datos, String idSensor) {

        comparaciones = 0;

        for (int i = 0; i < datos.length; i++) {
            comparaciones++;

            if (datos[i].getIdSensor().equals(idSensor)) {
                return i;
            }
        }

        return -1;
    }
}
