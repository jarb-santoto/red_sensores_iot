/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   BancoDeColecciones - VERSION COMPLETA

   Seis semanas construyendo a mano lo que Java ya trae.
   Hoy se compara, y sobre todo se aprende a ELEGIR.

   Cinco experimentos, se descomentan por fases.
   Ejecuta:  java BancoDeColecciones
   ============================================================ */

package experiment;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import model.Alerta;
import model.LecturaSensor;
import generator.GeneradorDatos;

public class BancoDeColecciones {

    public static void main(String[] args) {
        experimentoUno();
        experimentoDos();
        experimentoTres();
        experimentoCuatro();
        experimentoCinco();
    }

    // ---------- EXPERIMENTO 1 ----------

    /** ArrayList y LinkedList cumpliendo el mismo contrato List. */
    private static void experimentoUno() {
        System.out.println("=== EXP 1: EL MISMO CONTRATO, OTRA VEZ ===");
        LecturaSensor[] datos = GeneradorDatos.generar(5);

        List<LecturaSensor> comoArreglo = new ArrayList<>();
        List<LecturaSensor> comoCadena  = new LinkedList<>();

        List<List<LecturaSensor>> ambas = new ArrayList<>();
        ambas.add(comoArreglo);
        ambas.add(comoCadena);

        for (List<LecturaSensor> lista : ambas) {
            for (LecturaSensor l : datos) lista.add(l);
            System.out.printf("%-14s tamano: %d   primera: %s%n",
                    lista.getClass().getSimpleName(), lista.size(),
                    lista.get(0).getTimestamp());
        }
        System.out.println();
        System.out.println("Es la interfaz Repositorio de la semana 5, escrita por otros.");
        System.out.println("La suya se llamaba Repositorio; la de Java se llama List.");
        System.out.println();
    }

    // ---------- EXPERIMENTO 2 ----------

    /** Las cuatro combinaciones: dos estructuras por dos formas de recorrer. */
    private static void experimentoDos() {
        System.out.println("=== EXP 2: LAS CUATRO COMBINACIONES ===");
        int n = 50_000;
        LecturaSensor[] datos = GeneradorDatos.generar(n);

        List<LecturaSensor> arrayList = new ArrayList<>();
        List<LecturaSensor> linkedList = new LinkedList<>();
        for (LecturaSensor l : datos) { arrayList.add(l); linkedList.add(l); }

        System.out.println("-- Insertar " + String.format("%,d", n) + " lecturas AL INICIO --");
        medirInsercionAlInicio(new ArrayList<>(), datos, "ArrayList");
        medirInsercionAlInicio(new LinkedList<>(), datos, "LinkedList");

        System.out.println();
        System.out.println("-- Recorrer " + String.format("%,d", n) + " lecturas con get(i) --");
        medirRecorridoPorIndice(arrayList, "ArrayList");
        medirRecorridoPorIndice(linkedList, "LinkedList");

        System.out.println();
        System.out.println("-- Recorrer las mismas con for-each --");
        medirRecorridoForEach(arrayList, "ArrayList");
        medirRecorridoForEach(linkedList, "LinkedList");
        System.out.println();
    }

    private static void medirInsercionAlInicio(List<LecturaSensor> lista,
                                               LecturaSensor[] datos, String nombre) {
        long t = System.currentTimeMillis();
        for (LecturaSensor l : datos) lista.add(0, l);
        System.out.printf("   %-12s %,8d ms%n", nombre, System.currentTimeMillis() - t);
    }

    private static void medirRecorridoPorIndice(List<LecturaSensor> lista, String nombre) {
        long t = System.currentTimeMillis();
        double suma = 0;
        for (int i = 0; i < lista.size(); i++) suma += lista.get(i).getPm25();
        System.out.printf("   %-12s %,8d ms%n", nombre, System.currentTimeMillis() - t);
    }

    private static void medirRecorridoForEach(List<LecturaSensor> lista, String nombre) {
        long t = System.currentTimeMillis();
        double suma = 0;
        for (LecturaSensor l : lista) suma += l.getPm25();
        System.out.printf("   %-12s %,8d ms%n", nombre, System.currentTimeMillis() - t);
    }

    // ---------- EXPERIMENTO 3 ----------

    /** Comparator, ordenamiento por varios criterios y estabilidad. */
    private static void experimentoTres() {
        System.out.println("=== EXP 3: UN CRITERIO QUE VIAJA COMO PARAMETRO ===");

        List<Alerta> alertas = new ArrayList<>();
        alertas.add(new Alerta("EST-002", "PM2.5 en 180 - zona escolar",       Alerta.CRITICA, 1));
        alertas.add(new Alerta("EST-005", "Sensor sin reportar 2 horas",       Alerta.ADVERTENCIA, 2));
        alertas.add(new Alerta("EST-004", "PM2.5 en 210 - zona hospitalaria",  Alerta.CRITICA, 3));
        alertas.add(new Alerta("EST-009", "Bateria baja",                      Alerta.INFORMATIVA, 4));
        alertas.add(new Alerta("EST-001", "PM2.5 en 165 - zona residencial",   Alerta.CRITICA, 5));

        Comparator<Alerta> porSeveridadDesc =
                Comparator.comparingInt(Alerta::getSeveridad).reversed();

        List<Alerta> copia = new ArrayList<>(alertas);
        copia.sort(porSeveridadDesc);

        System.out.println("Ordenadas SOLO por severidad (Collections.sort es ESTABLE):");
        for (Alerta a : copia) System.out.println("   " + a);
        System.out.println();
        System.out.println("Compara con el resultado del monticulo de la semana 6.");
        System.out.println();
    }

    // ---------- EXPERIMENTO 4 ----------

    /** Tres trampas de la biblioteca estandar. */
    private static void experimentoCuatro() {
        System.out.println("=== EXP 4: TRES TRAMPAS DE la BIBLIOTECA ===");

        // Trampa A: Stack se itera al reves de como uno espera
        System.out.println("-- Trampa A: recorrer un Stack --");
        Stack<String> pila = new Stack<>();
        pila.push("Marcar EST-003 como sospechosa");
        pila.push("Cambiar umbral de PM2.5 a 35");
        pila.push("Desactivar EST-007");
        System.out.print("   for-each sobre el Stack: ");
        for (String accion : pila) System.out.print("[" + accion.substring(0, 8) + "] ");
        System.out.println();
        System.out.println("   pop() devuelve:          [" + pila.peek().substring(0, 8) + "]");
        System.out.println();

        // Trampa B: modificar mientras se recorre
        System.out.println("-- Trampa B: eliminar mientras se recorre --");
        List<LecturaSensor> lecturas = new ArrayList<>();
        for (LecturaSensor l : GeneradorDatos.generar(20)) lecturas.add(l);
        try {
            for (LecturaSensor l : lecturas) {
                if (l.getPm25() > 30) lecturas.remove(l);
            }
            System.out.println("   Termino sin problema. Quedaron: " + lecturas.size());
        } catch (Exception e) {
            System.out.println("   " + e.getClass().getSimpleName() + " al eliminar dentro del for-each.");
        }
        System.out.println();

        // Trampa C: PriorityQueue y los empates
        System.out.println("-- Trampa C: PriorityQueue con empates --");
        Queue<Alerta> cola = new PriorityQueue<>(
                Comparator.comparingInt(Alerta::getSeveridad).reversed());
        cola.add(new Alerta("EST-002", "zona escolar",      Alerta.CRITICA, 1));
        cola.add(new Alerta("EST-005", "sensor caido",      Alerta.ADVERTENCIA, 2));
        cola.add(new Alerta("EST-004", "zona hospitalaria", Alerta.CRITICA, 3));
        cola.add(new Alerta("EST-009", "bateria baja",      Alerta.INFORMATIVA, 4));
        cola.add(new Alerta("EST-001", "zona residencial",  Alerta.CRITICA, 5));

        System.out.println("   Orden de atencion segun PriorityQueue de Java:");
        Alerta a;
        int turno = 1;
        while ((a = cola.poll()) != null) {
            System.out.println("      " + turno + ". " + a);
            turno++;
        }
        System.out.println();
    }

    // ---------- EXPERIMENTO 5 ----------

    /** El limite de las listas: buscar sin indice. */
    private static void experimentoCinco() {
        System.out.println("=== EXP 5: DETECTAR DUPLICADOS CON UNA LISTA ===");
        int[] tamanos = {5_000, 20_000, 50_000};

        for (int n : tamanos) {
            LecturaSensor[] datos = GeneradorDatos.generar(n);
            List<String> vistos = new ArrayList<>();
            long t = System.currentTimeMillis();
            int duplicados = 0;
            for (LecturaSensor l : datos) {
                if (vistos.contains(l.getTimestamp())) duplicados++;
                else vistos.add(l.getTimestamp());
            }
            System.out.printf("   %,8d lecturas -> %,8d ms  (duplicados: %d)%n",
                    n, System.currentTimeMillis() - t, duplicados);
        }
        System.out.println();
        System.out.println("contains() sobre una lista recorre uno por uno.");
        System.out.println("Recuerden la fila duplicada de la semana 1, todavia sin detectar.");
        System.out.println();
    }
}
