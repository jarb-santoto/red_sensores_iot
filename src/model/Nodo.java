/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   Nodo - VERSION COMPLETA

   Un nodo es una caja con dos cosas adentro: un dato y la
   direccion de la siguiente caja.
   ============================================================ */

package model;

public class Nodo {

    private LecturaSensor dato;
    private Nodo siguiente;

    public Nodo(LecturaSensor dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public LecturaSensor getDato() { return dato; }
    public Nodo getSiguiente() { return siguiente; }
    public void setSiguiente(Nodo siguiente) { this.siguiente = siguiente; }
}
