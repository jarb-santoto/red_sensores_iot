/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   BancoDeOrdenamiento - VERSION COMPLETA

   Cinco experimentos. Los vas descomentando por fases.
   Ejecuta:  java BancoDeOrdenamiento
   ============================================================ */

package experiment;

import java.util.Random;
import model.LecturaSensor;
import search.BuscadorLecturas;
import sorting.Ordenador;
import generator.GeneradorDatos;

public class BancoDeOrdenamiento {

    public static void main(String[] args) {
        experimentoUno();
        // experimentoDos();     // <- FASE 2
        // experimentoTres();    // <- FASE 3
        // experimentoCuatro(); // <- FASE 4
        // experimentoCinco();   // <- FASE 5
    }

    // ---------- utilidades ----------

    /** Copia el arreglo para que cada algoritmo empiece en igualdad de condiciones. */
    private static LecturaSensor[] copiar(LecturaSensor[] original) {
        LecturaSensor[] copia = new LecturaSensor[original.length];
        System.arraycopy(original, 0, copia, 0, original.length);
        return copia;
    }

    /** Desordena un arreglo con una semilla fija, para que el experimento sea repetible. */
    private static LecturaSensor[] desordenar(LecturaSensor[] original) {
        LecturaSensor[] copia = copiar(original);
        Random azar = new Random(777L);
        for (int i = copia.length - 1; i > 0; i--) {
            int j = azar.nextInt(i + 1);
            LecturaSensor tmp = copia[i];
            copia[i] = copia[j];
            copia[j] = tmp;
        }
        return copia;
    }

    private static void reportar(String nombre, long milis) {
        System.out.printf("%-14s comparaciones: %,14d   intercambios: %,14d   %6d ms%n",
                nombre, Ordenador.getComparaciones(), Ordenador.getIntercambios(), milis);
    }

    // ---------- EXPERIMENTO 1 ----------

    /** Los tres algoritmos simples sobre 10.000 lecturas DESORDENADAS. */
    private static void experimentoUno() {
        System.out.println("=== EXP 1: ALGORITMOS SIMPLES, 10.000 LECTURAS DESORDENADAS ===");
        LecturaSensor[] base = desordenar(GeneradorDatos.generar(10_000));

        LecturaSensor[] a = copiar(base);
        long t = System.currentTimeMillis();
        Ordenador.burbuja(a);
        reportar("Burbuja", System.currentTimeMillis() - t);

        LecturaSensor[] b = copiar(base);
        t = System.currentTimeMillis();
        Ordenador.seleccion(b);
        reportar("Seleccion", System.currentTimeMillis() - t);

        LecturaSensor[] c = copiar(base);
        t = System.currentTimeMillis();
        Ordenador.insercion(c);
        reportar("Insercion", System.currentTimeMillis() - t);
        System.out.println();
    }

    // ---------- EXPERIMENTO 2 ----------

    /** Los mismos tres algoritmos sobre datos que YA VIENEN ORDENADOS. */
    private static void experimentoDos() {
        System.out.println("=== EXP 2: LOS MISMOS TRES, PERO CON DATOS YA ORDENADOS ===");
        System.out.println("(asi es como llegan de la red de sensores: en orden cronologico)");
        LecturaSensor[] base = GeneradorDatos.generar(10_000);

        LecturaSensor[] a = copiar(base);
        long t = System.currentTimeMillis();
        Ordenador.burbuja(a);
        reportar("Burbuja", System.currentTimeMillis() - t);

        LecturaSensor[] b = copiar(base);
        t = System.currentTimeMillis();
        Ordenador.seleccion(b);
        reportar("Seleccion", System.currentTimeMillis() - t);

        LecturaSensor[] c = copiar(base);
        t = System.currentTimeMillis();
        Ordenador.insercion(c);
        reportar("Insercion", System.currentTimeMillis() - t);
        System.out.println();
    }

    // ---------- EXPERIMENTO 3 ----------

    /** Simples contra avanzados, a escala creciente. */
    private static void experimentoTres() {
        System.out.println("=== EXP 3: SIMPLES CONTRA AVANZADOS ===");
        int[] tamanos = {1_000, 10_000, 100_000};

        for (int n : tamanos) {
            System.out.println("-- " + String.format("%,d", n) + " lecturas desordenadas --");
            LecturaSensor[] base = desordenar(GeneradorDatos.generar(n));

            LecturaSensor[] a = copiar(base);
            long t = System.currentTimeMillis();
            Ordenador.insercion(a);
            reportar("Insercion", System.currentTimeMillis() - t);

            LecturaSensor[] b = copiar(base);
            t = System.currentTimeMillis();
            Ordenador.mergeSort(b);
            reportar("MergeSort", System.currentTimeMillis() - t);

            LecturaSensor[] c = copiar(base);
            t = System.currentTimeMillis();
            Ordenador.heapSort(c);
            reportar("HeapSort", System.currentTimeMillis() - t);
            System.out.println();
        }
    }

    // ---------- EXPERIMENTO 4 ----------

    /** QuickSort con pivote fijo: primero con datos desordenados, luego con datos reales. */
    private static void experimentoCuatro() {
        System.out.println("=== EXP 4: QUICKSORT CON PIVOTE = PRIMER ELEMENTO ===");

        System.out.println("-- Caso A: 50.000 lecturas DESORDENADAS --");
        LecturaSensor[] revueltas = desordenar(GeneradorDatos.generar(50_000));
        long t = System.currentTimeMillis();
        Ordenador.quickSortPivotePrimero(revueltas);
        reportar("QuickSort", System.currentTimeMillis() - t);

        System.out.println();
        System.out.println("-- Caso B: 50.000 lecturas EN ORDEN CRONOLOGICO (como llegan de la red) --");
        LecturaSensor[] enOrden = GeneradorDatos.generar(50_000);
        try {
            t = System.currentTimeMillis();
            Ordenador.quickSortPivotePrimero(enOrden);
            reportar("QuickSort", System.currentTimeMillis() - t);
        } catch (StackOverflowError e) {
            System.out.println("QuickSort      -> StackOverflowError: el programa se quedo sin pila.");
            System.out.println("                  Comparaciones alcanzadas antes de morir: "
                    + String.format("%,d", Ordenador.getComparaciones()));
        }
        System.out.println();
    }

    // ---------- EXPERIMENTO 5 ----------

    /** Ordenar por PM2.5 para el ranking... y consultar por timestamp despues. */
    private static void experimentoCinco() {
        System.out.println("=== EXP 5: EL RANKING Y LA CONSULTA ===");

        LecturaSensor[] datos = GeneradorDatos.generar(100_000);
        String objetivo = GeneradorDatos.timestampEnPosicion(73_412);

        System.out.println("Paso 1. Los datos llegan de la red en orden cronologico.");
        System.out.println("        Ordenado por timestamp: " + Ordenador.estaOrdenadoPorTimestamp(datos));
        int pos = BuscadorLecturas.busquedaBinariaPorTimestamp(datos, objetivo);
        System.out.println("        Consulta binaria por timestamp -> posicion: " + pos
                + "  (comparaciones: " + BuscadorLecturas.getComparaciones() + ")");

        System.out.println();
        System.out.println("Paso 2. El area de comunicaciones pide el ranking de estaciones"
                + " mas contaminadas. Ordenamos por PM2.5.");
        Ordenador.ordenarPorPm25(datos);
        System.out.println("        Ranking listo. PM2.5 mas bajo: " + datos[0].getPm25()
                + " | mas alto: " + datos[datos.length - 1].getPm25());

        System.out.println();
        System.out.println("Paso 3. Otro usuario vuelve a consultar la misma lectura de siempre.");
        System.out.println("        Ordenado por timestamp: " + Ordenador.estaOrdenadoPorTimestamp(datos));
        pos = BuscadorLecturas.busquedaBinariaPorTimestamp(datos, objetivo);
        System.out.println("        Consulta binaria por timestamp -> posicion: " + pos
                + "  (comparaciones: " + BuscadorLecturas.getComparaciones() + ")");

        System.out.println();
        System.out.println("        Verificacion con busqueda lineal -> posicion: "
                + BuscadorLecturas.busquedaLinealPorTimestamp(datos, objetivo));
        System.out.println();
    }
}
