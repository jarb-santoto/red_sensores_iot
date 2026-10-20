/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   ColaDeAlertas - VERSION CORREGIDA

   Una COLA DE PRIORIDAD no atiende por orden de llegada sino
   por importancia. Una alerta CRITICA que acaba de llegar pasa
   delante de veinte informativas que llevaban horas esperando.

   Aqui hay DOS implementaciones del mismo contrato:
     - porListaOrdenada: sencilla de entender, cara de mantener
     - el MONTICULO (heap): el arreglo que finge ser un arbol

   El monticulo es la estructura que HeapSort usaba como caja
   negra en la semana 4. Hoy la abres.
   ============================================================ */

package repository;

import model.Alerta;

public class ColaDeAlertas {

    private static long comparaciones = 0;
    public static long getComparaciones() { return comparaciones; }
    public static void reiniciarContador() { comparaciones = 0; }

    // =========================================================
    //  IMPLEMENTACION A: LISTA ORDENADA
    // =========================================================

    /**
     * Version ingenua: mantiene un arreglo siempre ordenado por
     * severidad. Insertar obliga a buscar el sitio y correr todo
     * lo demas, igual que en la semana 2.
     */
    public static class PorListaOrdenada {
        private Alerta[] datos = new Alerta[200_000];
        private int cantidad = 0;

        public int tamano() { return cantidad; }

        public void encolar(Alerta alerta) {
            int i = cantidad - 1;
            while (i >= 0) {
                comparaciones++;
                if (datos[i].getSeveridad() < alerta.getSeveridad()) {
                    datos[i + 1] = datos[i];
                    i--;
                } else {
                    break;
                }
            }
            datos[i + 1] = alerta;
            cantidad++;
        }

        public Alerta atender() {
            if (cantidad == 0) return null;
            Alerta mayor = datos[0];
            for (int i = 0; i < cantidad - 1; i++) datos[i] = datos[i + 1];
            cantidad--;
            return mayor;
        }
    }

    // =========================================================
    //  IMPLEMENTACION B: MONTICULO (HEAP)
    // =========================================================

    /**
     * El monticulo es un ARREGLO que se comporta como un arbol,
     * sin nodos ni enlaces. La relacion padre-hijo es pura aritmetica:
     *
     *    hijo izquierdo de i  ->  2*i + 1
     *    hijo derecho de i    ->  2*i + 2
     *    padre de i           ->  (i - 1) / 2
     *
     * Regla unica: todo padre es MAS importante que sus hijos.
     * La alerta mas urgente siempre queda en la posicion 0.
     */
    public static class PorMonticulo {
        private Alerta[] datos = new Alerta[200_000];
        private int cantidad = 0;

        public int tamano() { return cantidad; }

        private void intercambiar(int i, int j) {
            Alerta t = datos[i]; datos[i] = datos[j]; datos[j] = t;
        }

        /** Devuelve true si a es mas urgente que b.
         * Incluye desempate por orden de llegada.
         */
        private boolean esMasUrgente(Alerta a, Alerta b) {
            comparaciones++;
            if (a.getSeveridad() != b.getSeveridad()) {
                return a.getSeveridad() > b.getSeveridad();
            }
            // Desempate: gana el que llego primero (menor ordenDeLlegada)
            return a.getOrdenDeLlegada() < b.getOrdenDeLlegada();
        }

        /**
         * Encola una alerta: la pone al final y la hace SUBIR hasta
         * su lugar, intercambiandola con su padre mientras sea mas
         * urgente que el.
         */
        public void encolar(Alerta alerta) {
            datos[cantidad] = alerta;
            cantidad++;
            flotar(cantidad - 1);
        }

        private void flotar(int posicion) {
            while (posicion > 0) {
                int padre = (posicion - 1) / 2;
                if (esMasUrgente(datos[posicion], datos[padre])) {
                    intercambiar(posicion, padre);
                    posicion = padre;
                } else {
                    break;
                }
            }
        }

        /**
         * Atiende la alerta mas urgente: saca la raiz, sube la ultima
         * al tope y la deja HUNDIRSE hasta su lugar.
         */
        public Alerta atender() {
            if (cantidad == 0) return null;
            Alerta masUrgente = datos[0];
            datos[0] = datos[cantidad - 1];
            datos[cantidad - 1] = null;
            cantidad--;
            hundir(0);
            return masUrgente;
        }

        private void hundir(int posicion) {
            while (true) {
                int izq = 2 * posicion + 1;
                int der = 2 * posicion + 2;
                int mayor = posicion;
                if (izq < cantidad && esMasUrgente(datos[izq], datos[mayor])) mayor = izq;
                if (der < cantidad && esMasUrgente(datos[der], datos[mayor])) mayor = der;
                if (mayor == posicion) return;
                intercambiar(posicion, mayor);
                posicion = mayor;
            }
        }
    }
}
