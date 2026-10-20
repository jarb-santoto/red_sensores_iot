/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   HistorialNavegable - VERSION CORREGIDA

   Implementación de una lista doblemente enlazada para el panel de
   control. Se completaron los enlaces faltantes y se implementó la
   navegación hacia atrás.
   ============================================================ */

package control;

import model.LecturaSensor;

public class HistorialNavegable {

    /** Nodo doble: dato, siguiente y anterior. */
    public static class NodoDoble {
        private LecturaSensor dato;
        private NodoDoble siguiente;
        private NodoDoble anterior;

        public NodoDoble(LecturaSensor dato) {
            this.dato = dato;
        }

        public LecturaSensor getDato() { return dato; }
        public NodoDoble getSiguiente() { return siguiente; }
        public NodoDoble getAnterior() { return anterior; }
        public void setSiguiente(NodoDoble siguiente) { this.siguiente = siguiente; }
        public void setAnterior(NodoDoble anterior) { this.anterior = anterior; }
    }

    private NodoDoble primero;
    private NodoDoble ultimo;
    private NodoDoble cursor;
    private int cantidad;

    public int tamano() { return cantidad; }

    /**
     * Agrega una lectura al final del historial.
     */
    public void agregar(LecturaSensor lectura) {
        NodoDoble nuevo = new NodoDoble(lectura);
        if (primero == null) {
            // primer elemento de la lista
            primero = nuevo;
            ultimo = nuevo;
            cursor = nuevo;
        } else {
            // enlazamos hacia adelante
            ultimo.setSiguiente(nuevo);
            // ENLACE HASTA ENTRADA TRASERA (TODO 1)
            nuevo.setAnterior(ultimo);
            // avanzamos el puntero de último
            ultimo = nuevo;
        }
        cantidad++;
    }

    /** Recorre desde el primero hasta el ultimo y devuelve cuantos visito. */
    public int recorrerHaciaAdelante() {
        int visitados = 0;
        NodoDoble actual = primero;
        while (actual != null) {
            visitados++;
            actual = actual.getSiguiente();
        }
        return visitados;
    }

    /** Recorre desde el ultimo hasta el primero y devuelve cuantos visito. */
    public int recorrerHaciaAtras() {
        int visitados = 0;
        NodoDoble actual = ultimo;
        while (actual != null) {
            visitados++;
            actual = actual.getAnterior();
        }
        return visitados;
    }

    // =========================================================
    //  NAVEGACION DEL PANEL DE CONTROL
    // =========================================================

    /** Lectura donde esta parado el operador ahora mismo. */
    public LecturaSensor actual() {
        return cursor == null ? null : cursor.getDato();
    }

    /** Avanza el cursor una lectura hacia el futuro. */
    public boolean siguiente() {
        if (cursor == null || cursor.getSiguiente() == null) return false;
        cursor = cursor.getSiguiente();
        return true;
    }

    /** Retrocede el cursor una lectura hacia el pasado. */
    public boolean anterior() {
        if (cursor == null || cursor.getAnterior() == null) return false;
        cursor = cursor.getAnterior();
        return true;
    }

    /** Ubica el cursor al final del historial. */
    public void irAlFinal() {
        cursor = ultimo;
    }
}
