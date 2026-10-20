/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   PilaDeAcciones - VERSION CORREGIDA

   Una PILA es una lista con casi todo prohibido: solo se puede
   meter y sacar por UN extremo, el de arriba. El ultimo que
   entra es el primero que sale.

   Suena a limitacion. Es exactamente lo que necesita un
   "deshacer": la ultima accion del operador es la primera que
   hay que revertir.

   Caso de uso: el operador de la sala de control marca lecturas
   como sospechosas, reasigna estaciones, corrige umbrales. Si se
   equivoca, oprime Ctrl+Z.
   ============================================================ */

package control;

public class PilaDeAcciones {

    private static final int CAPACIDAD = 100;

    private String[] acciones;
    private int tope;

    public PilaDeAcciones() {
        this.acciones = new String[CAPACIDAD];
        this.tope = 0;
    }

    /** Cantidad de acciones apiladas. */
    public int tamano() { return tope; }

    public boolean estaVacia() { return tope == 0; }

    /**
     * Apila una accion del operador.
     */
    public void apilar(String accion) {
        if (tope == CAPACIDAD) {
            System.out.println("   (historial lleno, se descarta la accion mas antigua)");
            return;
        }
        acciones[tope] = accion;
        tope++;
    }

    /**
     * Devuelve la ultima accion para deshacerla y la saca de la pila.
     *
     * @return la accion a deshacer, o null si no hay nada
     */
    public String desapilar() {
        if (estaVacia()) {
            return null;
        }
        String accion = acciones[tope - 1];
        acciones[tope - 1] = null; // Evitar fuga por referencia obsoleta
        tope--;
        return accion;
    }

    /**
     * Consulta la ultima accion SIN sacarla de la pila.
     */
    public String cima() {
        if (estaVacia()) {
            return null;
        }
        return acciones[tope - 1];
    }
}
