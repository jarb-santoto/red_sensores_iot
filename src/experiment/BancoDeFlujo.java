/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   BancoDeFlujo - VERSION COMPLETA

   Cinco experimentos. Se descomentan por fases.
   Ejecuta:  java BancoDeFlujo
   ============================================================ */

package experiment;

import model.Alerta;
import model.LecturaSensor;
import control.PilaDeAcciones;
import ingest.ColaDeIngesta;
import repository.ColaDeAlertas;
import generator.GeneradorDatos;

public class BancoDeFlujo {

    public static void main(String[] args) {
        experimentoUno();
        experimentoDos();
        experimentoTres();
        experimentoCuatro();
        experimentoCinco();
    }

    // ---------- EXPERIMENTO 1: LA PILA ----------

    private static void experimentoUno() {
        System.out.println("=== EXP 1: EL DESHACER DEL OPERADOR ===");
        PilaDeAcciones historial = new PilaDeAcciones();

        historial.apilar("Marcar EST-003 como sospechosa");
        historial.apilar("Cambiar umbral de PM2.5 a 35");
        historial.apilar("Desactivar EST-007");
        System.out.println("Acciones registradas: " + historial.tamano());
        System.out.println();

        System.out.println("El operador oprime Ctrl+Z tres veces:");
        for (int i = 1; i <= 3; i++) {
            System.out.println("   deshacer " + i + " -> " + historial.desapilar());
        }
        System.out.println();
        System.out.println("Acciones que quedan en el historial: " + historial.tamano());
        System.out.println();
    }

    // ---------- EXPERIMENTO 2: LA COLA QUE SE LLENA VACIA ----------

    private static void experimentoDos() {
        System.out.println("=== EXP 2: LA COLA QUE SE LLENA ESTANDO CASI VACIA ===");
        ColaDeIngesta cola = new ColaDeIngesta();
        LecturaSensor[] datos = GeneradorDatos.generar(5_000);

        System.out.println("Capacidad del buffer: " + cola.capacidad());
        System.out.println("Simulacion: llega una lectura, se procesa una lectura.");
        System.out.println("Nunca hay mas de 3 lecturas esperando al tiempo.");
        System.out.println();

        int procesadas = 0;
        for (int i = 0; i < datos.length; i++) {
            cola.encolar(datos[i]);
            if (cola.tamano() > 3) {
                cola.desencolar();
                procesadas++;
            }
        }

        System.out.println("Lecturas que llegaron:     " + String.format("%,d", datos.length));
        System.out.println("Lecturas procesadas:       " + String.format("%,d", procesadas));
        System.out.println("Lecturas DESCARTADAS:      " + String.format("%,d", cola.getDescartadas()));
        System.out.println("Lecturas esperando ahora:  " + cola.tamano());
        System.out.println("Casillas del arreglo ocupadas de verdad: " + cola.casillasOcupadas());
        System.out.println("Estado interno: " + cola.estadoIndices());
        System.out.println();
    }

    // ---------- EXPERIMENTO 3: LA COLA CORREGIDA ----------

    private static void experimentoTres() {
        System.out.println("=== EXP 3: VERIFICACION DE LA COLA CIRCULAR ===");
        ColaDeIngesta cola = new ColaDeIngesta();
        LecturaSensor[] datos = GeneradorDatos.generar(200_000);

        int procesadas = 0;
        long t = System.currentTimeMillis();
        for (int i = 0; i < datos.length; i++) {
            cola.encolar(datos[i]);
            if (cola.tamano() > 3) { cola.desencolar(); procesadas++; }
        }
        long ms = System.currentTimeMillis() - t;

        System.out.println("Lecturas que llegaron:  " + String.format("%,d", datos.length));
        System.out.println("Lecturas procesadas:    " + String.format("%,d", procesadas));
        System.out.println("Lecturas DESCARTADAS:   " + String.format("%,d", cola.getDescartadas()));
        System.out.println("Tiempo total:           " + ms + " ms");
        System.out.println();
        System.out.println("Objetivo: cero descartadas, con un buffer de solo 1000 casillas.");
        System.out.println();
    }

    // ---------- EXPERIMENTO 4: HEAP CONTRA LISTA ORDENADA ----------

    private static void experimentoCuatro() {
        System.out.println("=== EXP 4: COLA DE PRIORIDAD, DOS MECANISMOS ===");
        int[] tamanos = {10_000, 50_000, 100_000};
        System.out.printf("%10s | %20s %8s | %20s %8s%n",
                "alertas", "lista: comparaciones", "ms", "heap: comparaciones", "ms");

        for (int n : tamanos) {
            Alerta[] alertas = generarAlertas(n);

            ColaDeAlertas.reiniciarContador();
            ColaDeAlertas.PorListaOrdenada lista = new ColaDeAlertas.PorListaOrdenada();
            long t = System.currentTimeMillis();
            for (Alerta a : alertas) lista.encolar(a);
            long msLista = System.currentTimeMillis() - t;
            long compLista = ColaDeAlertas.getComparaciones();

            ColaDeAlertas.reiniciarContador();
            ColaDeAlertas.PorMonticulo heap = new ColaDeAlertas.PorMonticulo();
            t = System.currentTimeMillis();
            for (Alerta a : alertas) heap.encolar(a);
            long msHeap = System.currentTimeMillis() - t;
            long compHeap = ColaDeAlertas.getComparaciones();

            System.out.printf("%,10d | %,20d %,8d | %,20d %,8d%n",
                    n, compLista, msLista, compHeap, msHeap);
        }
        System.out.println();
    }

    // ---------- EXPERIMENTO 5: EL EMPATE ----------

    private static void experimentoCinco() {
        System.out.println("=== EXP 5: TRES ALERTAS CRITICAS AL TIEMPO ===");
        ColaDeAlertas.PorMonticulo heap = new ColaDeAlertas.PorMonticulo();

        heap.encolar(new Alerta("EST-002", "PM2.5 en 180 - zona escolar", Alerta.CRITICA, 1));
        heap.encolar(new Alerta("EST-005", "Sensor sin reportar 2 horas", Alerta.ADVERTENCIA, 2));
        heap.encolar(new Alerta("EST-004", "PM2.5 en 210 - zona hospitalaria", Alerta.CRITICA, 3));
        heap.encolar(new Alerta("EST-009", "Bateria baja", Alerta.INFORMATIVA, 4));
        heap.encolar(new Alerta("EST-001", "PM2.5 en 165 - zona residencial", Alerta.CRITICA, 5));

        System.out.println("Llegaron en este orden: 1 critica, 2 advertencia, 3 critica,");
        System.out.println("                        4 informativa, 5 critica.");
        System.out.println();
        System.out.println("El operador las atiende en este orden:");
        Alerta a;
        int turno = 1;
        while ((a = heap.atender()) != null) {
            System.out.println("   " + turno + ". " + a);
            turno++;
        }
        System.out.println();
        System.out.println("Las criticas salen primero. Pero mira EN QUE ORDEN salen entre ellas.");
        System.out.println();
    }

    // ---------- utilidades ----------

    private static Alerta[] generarAlertas(int n) {
        java.util.Random azar = new java.util.Random(4242L);
        Alerta[] alertas = new Alerta[n];
        for (int i = 0; i < n; i++) {
            int sev = azar.nextInt(3) + 1;
            alertas[i] = new Alerta(String.format("EST-%03d", azar.nextInt(9) + 1),
                                    "evento " + i, sev, i);
        }
        return alertas;
    }
}
