/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   Ordenador - VERSION 0.1 INCOMPLETA

   La semana pasada descubriste que la busqueda binaria exige
   datos ordenados. Esta semana averiguas cuanto cuesta eso.

   Todos los metodos estan instrumentados: cuentan comparaciones
   e intercambios. No quites los contadores.

   ADVERTENCIA: dos de estos algoritmos tienen defectos y uno
   de ellos hace explotar el programa con los datos REALES de
   la red, no con datos raros.
   ============================================================ */

package sorting;

import model.LecturaSensor;

public class Ordenador {

    private static long comparaciones = 0;
    private static long intercambios = 0;

    public static long getComparaciones() { return comparaciones; }
    public static long getIntercambios()  { return intercambios; }
    public static void reiniciarContadores() { comparaciones = 0; intercambios = 0; }

    // =========================================================
    //  UTILIDADES
    // =========================================================

    private static void intercambiar(LecturaSensor[] datos, int i, int j) {
        LecturaSensor temporal = datos[i];
        datos[i] = datos[j];
        datos[j] = temporal;
        intercambios++;
    }

    /** Compara dos lecturas por su timestamp. */
    private static int comparar(LecturaSensor a, LecturaSensor b) {
        comparaciones++;
        return a.getTimestamp().compareTo(b.getTimestamp());
    }

    /** Compara dos lecturas por su valor de PM2.5. */
    private static int compararPorPm25(LecturaSensor a, LecturaSensor b) {
        comparaciones++;
        return Double.compare(a.getPm25(), b.getPm25());
    }

    // =========================================================
    //  2.7  ALGORITMOS SIMPLES
    // =========================================================

    /**
     * Ordenamiento burbuja.
     *
     * TODO 1: este metodo hace el mismo trabajo aunque el arreglo
     * ya venga ordenado. Agregale una bandera que detecte que en
     * una pasada completa no hubo ningun intercambio, y corte ahi.
     * Mide el antes y el despues con datos ya ordenados.
     */
    public static void burbuja(LecturaSensor[] datos) {
        reiniciarContadores();
        int n = datos.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparar(datos[j], datos[j + 1]) > 0) {
                    intercambiar(datos, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                // No hubo intercambios en esta pasada; el arreglo ya está ordenado.
                break;
            }
        }
    }

    /**
     * Ordenamiento por seleccion. Busca el menor y lo pone al inicio.
     * Este metodo esta correcto. Observa cuantos intercambios hace.
     */
    public static void seleccion(LecturaSensor[] datos) {
        reiniciarContadores();
        int n = datos.length;
        for (int i = 0; i < n - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < n; j++) {
                if (comparar(datos[j], datos[menor]) < 0) {
                    menor = j;
                }
            }
            if (menor != i) {
                intercambiar(datos, i, menor);
            }
        }
    }

    /**
     * Ordenamiento por insercion. Inserta cada elemento en su lugar
     * dentro de la parte ya ordenada, como quien organiza cartas.
     * Este metodo esta correcto.
     */
    public static void insercion(LecturaSensor[] datos) {
        reiniciarContadores();
        for (int i = 1; i < datos.length; i++) {
            LecturaSensor actual = datos[i];
            int j = i - 1;
            while (j >= 0 && comparar(datos[j], actual) > 0) {
                datos[j + 1] = datos[j];
                intercambios++;
                j--;
            }
            datos[j + 1] = actual;
        }
    }

    // =========================================================
    //  2.8  ALGORITMOS AVANZADOS
    // =========================================================

    /**
     * MergeSort. Divide el arreglo por la mitad, ordena cada parte
     * y luego fusiona las dos partes ordenadas. Este metodo esta correcto.
     *
     * Fijate en la idea: es la misma de la busqueda binaria, dividir
     * el problema en dos, pero aplicada a ordenar en vez de a buscar.
     */
    public static void mergeSort(LecturaSensor[] datos) {
        reiniciarContadores();
        LecturaSensor[] auxiliar = new LecturaSensor[datos.length];
        mergeSortRecursivo(datos, auxiliar, 0, datos.length - 1);
    }

    private static void mergeSortRecursivo(LecturaSensor[] datos, LecturaSensor[] aux,
                                           int inicio, int fin) {
        if (inicio >= fin) return;
        int medio = inicio + (fin - inicio) / 2;
        mergeSortRecursivo(datos, aux, inicio, medio);
        mergeSortRecursivo(datos, aux, medio + 1, fin);
        fusionar(datos, aux, inicio, medio, fin);
    }

    private static void fusionar(LecturaSensor[] datos, LecturaSensor[] aux,
                                 int inicio, int medio, int fin) {
        for (int i = inicio; i <= fin; i++) aux[i] = datos[i];
        int izq = inicio, der = medio + 1;
        for (int k = inicio; k <= fin; k++) {
            if (izq > medio) {
                datos[k] = aux[der++];
            } else if (der > fin) {
                datos[k] = aux[izq++];
            } else if (comparar(aux[der], aux[izq]) < 0) {
                datos[k] = aux[der++];
            } else {
                datos[k] = aux[izq++];
            }
            intercambios++;
        }
    }

    /**
     * QuickSort con el PRIMER elemento como pivote.
     *
     * TODO 2: este metodo funciona muy bien con datos desordenados
     * y hace explotar el programa con los datos reales de la red.
     * Ejecuta el experimento 4 antes de leer mas.
     *
     * Cuando entiendas por que, corrigelo eligiendo mejor el pivote.
     */
    public static void quickSortPivotePrimero(LecturaSensor[] datos) {
        reiniciarContadores();
        quickRecursivo(datos, 0, datos.length - 1);
    }

    private static java.util.Random random = new java.util.Random();

    private static void quickRecursivo(LecturaSensor[] datos, int inicio, int fin) {
        if (inicio >= fin) return;
        // Elegir pivote aleatorio y colocar al inicio
        int indicePivote = inicio + random.nextInt(fin - inicio + 1);
        intercambiar(datos, inicio, indicePivote);
        int posicionPivote = particionar(datos, inicio, fin);
        quickRecursivo(datos, inicio, posicionPivote - 1);
        quickRecursivo(datos, posicionPivote + 1, fin);
    }

    private static int particionar(LecturaSensor[] datos, int inicio, int fin) {
        LecturaSensor pivote = datos[inicio];
        int limite = inicio;
        for (int i = inicio + 1; i <= fin; i++) {
            if (comparar(datos[i], pivote) < 0) {
                limite++;
                intercambiar(datos, limite, i);
            }
        }
        intercambiar(datos, inicio, limite);
        return limite;
    }

    /**
     * HeapSort. Ordena usando una estructura llamada monticulo.
     * Este metodo esta correcto y funciona como caja negra por ahora:
     * la estructura que usa por dentro la vas a construir en la semana 6.
     */
    public static void heapSort(LecturaSensor[] datos) {
        reiniciarContadores();
        int n = datos.length;
        for (int i = n / 2 - 1; i >= 0; i--) hundir(datos, n, i);
        for (int i = n - 1; i > 0; i--) {
            intercambiar(datos, 0, i);
            hundir(datos, i, 0);
        }
    }

    private static void hundir(LecturaSensor[] datos, int tamano, int raiz) {
        int mayor = raiz;
        int izq = 2 * raiz + 1;
        int der = 2 * raiz + 2;
        if (izq < tamano && comparar(datos[izq], datos[mayor]) > 0) mayor = izq;
        if (der < tamano && comparar(datos[der], datos[mayor]) > 0) mayor = der;
        if (mayor != raiz) {
            intercambiar(datos, raiz, mayor);
            hundir(datos, tamano, mayor);
        }
    }

    // =========================================================
    //  ORDENAR POR OTRO CRITERIO
    // =========================================================

    /**
     * Ordena las lecturas por concentracion de PM2.5, de menor a mayor.
     * Sirve para construir el ranking de estaciones mas contaminadas.
     *
     * TODO 3: este metodo hace exactamente lo que promete.
     * Ejecuta el experimento 5 y averigua que rompe.
     */
    public static void ordenarPorPm25(LecturaSensor[] datos) {
        reiniciarContadores();
        for (int i = 1; i < datos.length; i++) {
            LecturaSensor actual = datos[i];
            int j = i - 1;
            while (j >= 0 && compararPorPm25(datos[j], actual) > 0) {
                datos[j + 1] = datos[j];
                intercambios++;
                j--;
            }
            datos[j + 1] = actual;
        }
    }

    /** Verifica si un arreglo esta ordenado ascendentemente por timestamp. */
    public static boolean estaOrdenadoPorTimestamp(LecturaSensor[] datos) {
        for (int i = 1; i < datos.length; i++) {
            if (datos[i - 1].getTimestamp().compareTo(datos[i].getTimestamp()) > 0) {
                return false;
            }
        }
        return true;
    }
}
