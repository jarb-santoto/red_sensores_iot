/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   ColaDeIngesta - VERSION CORREGIDA

   Una COLA es una lista con la otra mitad prohibida: se entra
   por atras y se sale por adelante. El primero que llega es el
   primero que se atiende.

   Caso de uso: las nueve estaciones envian lecturas al mismo
   tiempo y el procesador no alcanza a atenderlas al instante.
   Las lecturas esperan en un buffer, en el orden en que
   llegaron. Ninguna se cuela.
   ============================================================ */

package ingest;

import model.LecturaSensor;

public class ColaDeIngesta {

    private static final int CAPACIDAD = 1000;

    private LecturaSensor[] buffer;
    private int frente;
    private int fin;
    private int cantidad;

    /** Lecturas que se perdieron por no caber. */
    private int descartadas;

    public ColaDeIngesta() {
        this.buffer = new LecturaSensor[CAPACIDAD];
        this.frente = 0;
        this.fin = 0;
        this.cantidad = 0;
        this.descartadas = 0;
    }

    public int tamano() { return cantidad; }
    public int getDescartadas() { return descartadas; }
    public boolean estaVacia() { return cantidad == 0; }
    public int capacidad() { return CAPACIDAD; }

    /**
     * Encola una lectura que acaba de llegar de la red.
     * Implementacion circular para reciclar espacio.
     *
     * @return true si se encolo, false si se descarto
     */
    public boolean encolar(LecturaSensor lectura) {
        if (cantidad == CAPACIDAD) {
            descartadas++;
            return false;
        }
        buffer[fin] = lectura;
        fin = (fin + 1) % CAPACIDAD;
        cantidad++;
        return true;
    }

    /**
     * Saca la lectura que lleva mas tiempo esperando.
     *
     * @return la lectura mas antigua, o null si la cola esta vacia
     */
    public LecturaSensor desencolar() {
        if (estaVacia()) {
            return null;
        }
        LecturaSensor lectura = buffer[frente];
        buffer[frente] = null;
        frente = (frente + 1) % CAPACIDAD;
        cantidad--;
        return lectura;
    }

    /**
     * Consulta la lectura del frente sin sacarla.
     */
    public LecturaSensor frente() {
        if (estaVacia()) {
            return null;
        }
        return buffer[frente];
    }

    /** Diagnostico: cuantas casillas del arreglo estan realmente ocupadas. */
    public int casillasOcupadas() {
        int ocupadas = 0;
        for (int i = 0; i < CAPACIDAD; i++) {
            if (buffer[i] != null) ocupadas++;
        }
        return ocupadas;
    }

    /** Diagnostico: posicion actual de los indices. */
    public String estadoIndices() {
        return "frente=" + frente + "  fin=" + fin + "  cantidad=" + cantidad;
    }
}
