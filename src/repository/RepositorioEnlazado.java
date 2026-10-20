/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   RepositorioEnlazado - VERSION CORREGIDA

   Implementa la interfaz Repositorio usando una lista enlazada.
   Se corrigieron los defectos señalados en la guía:
   * agregarAlFinal usa el atributo 'ultimo' para evitar recorrer toda la lista.
   * eliminar maneja correctamente los bordes (posicion 0, último) y
     mantiene la cadena sin perder nodos.
   ============================================================ */

package repository;

import model.LecturaSensor;
import model.Nodo;

public class RepositorioEnlazado implements Repositorio {

    private Nodo primero;
    private Nodo ultimo;
    private int cantidad;

    /** Cuenta cuantos nodos se recorrieron en la ultima operacion. */
    private static long saltos = 0;
    public static long getSaltos() { return saltos; }
    public static void reiniciarSaltos() { saltos = 0; }

    public RepositorioEnlazado() {
        this.primero = null;
        this.ultimo = null;
        this.cantidad = 0;
    }

    @Override
    public String nombre() { return "Lista enlazada"; }

    @Override
    public int tamano() { return cantidad; }

    // =========================================================
    //  INSERCION
    // =========================================================

    @Override
    public void agregarAlInicio(LecturaSensor lectura) {
        Nodo nuevo = new Nodo(lectura);
        nuevo.setSiguiente(primero);
        primero = nuevo;
        if (ultimo == null) {
            ultimo = nuevo;
        }
        cantidad++;
    }

    @Override
    public void agregarAlFinal(LecturaSensor lectura) {
        Nodo nuevo = new Nodo(lectura);
        if (primero == null) {
            // lista vacía
            primero = nuevo;
            ultimo = nuevo;
        } else {
            // usar el atributo ultimo directamente
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }
        // no hay saltos añadiendo al final
        cantidad++;
    }

    // =========================================================
    //  ACCESO
    // =========================================================

    @Override
    public LecturaSensor obtener(int posicion) {
        if (posicion < 0 || posicion >= cantidad) {
            return null;
        }
        Nodo actual = primero;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
            saltos++;
        }
        return actual.getDato();
    }

    // =========================================================
    //  ELIMINACION
    // =========================================================

    @Override
    public boolean eliminar(int posicion) {
        if (posicion < 0 || posicion >= cantidad) {
            return false;
        }
        // Caso eliminar el primer nodo
        if (posicion == 0) {
            Nodo aEliminar = primero;
            primero = aEliminar.getSiguiente();
            if (primero == null) {
                // la lista quedó vacía
                ultimo = null;
            }
            // No es necesario actualizar saltos aquí porque no recorrimos la lista
            cantidad--;
            return true;
        }
        // Recorrer hasta el nodo anterior al que queremos eliminar
        Nodo anterior = primero;
        for (int i = 0; i < posicion - 1; i++) {
            anterior = anterior.getSiguiente();
            saltos++;
        }
        Nodo aEliminar = anterior.getSiguiente();
        Nodo siguiente = aEliminar.getSiguiente();
        anterior.setSiguiente(siguiente);
        // Si eliminamos el último nodo, actualizar referencia ultimo
        if (siguiente == null) {
            ultimo = anterior;
        }
        cantidad--;
        return true;
    }

    // =========================================================
    //  RECORRIDO
    // =========================================================

    /** Cuenta los nodos recorriendo la cadena de verdad, eslabon a eslabon.
        Sirve para verificar si el atributo 'cantidad' está diciendo la verdad. */
    public int contarRecorriendo() {
        int contador = 0;
        Nodo actual = primero;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }

    /** Búsqueda lineal por timestamp, recorriendo la cadena. */
    public int buscarPorTimestamp(String timestamp) {
        reiniciarSaltos();
        Nodo actual = primero;
        int posicion = 0;
        while (actual != null) {
            saltos++;
            if (actual.getDato().getTimestamp().equals(timestamp)) {
                return posicion;
            }
            actual = actual.getSiguiente();
            posicion++;
        }
        return -1;
    }
}
