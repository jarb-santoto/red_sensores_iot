package repository;

import model.ClaveLectura;
import java.util.LinkedList;

/**
 * Implementación de una Tabla Hash con encadenamiento.
 * Diseñada para demostrar el impacto de la función hash y el factor de carga.
 */
public class TablaHash<K, V> {
    private LinkedList<Entrada<K, V>>[] casillas;
    private int cantidad;
    private static final double FACTOR_CARGA_MAX = 0.75;

    private static class Entrada<K, V> {
        K clave;
        V valor;
        Entrada(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    @SuppressWarnings("unchecked")
    public TablaHash(int capacidadInicial) {
        this.casillas = new LinkedList[capacidadInicial];
        this.cantidad = 0;
    }

    /**
     * Función hash la cual el estudiante debe optimizar.
     * Inicialmente se puede implementar una versión "mala" para el experimento.
     */
    public int calcularCasilla(K clave) {
        // Versión optimizada (Polinómica)
        int h = 0;
        String s = clave.toString();
        for (int i = 0; i < s.length(); i++) {
            h = 31 * h + s.charAt(i);
        }
        return Math.abs(h) % casillas.length;
    }

    public void poner(K clave, V valor) {
        if ((double) cantidad / casillas.length > FACTOR_CARGA_MAX) {
            redimensionar();
        }

        int indice = calcularCasilla(clave);
        if (casillas[indice] == null) {
            casillas[indice] = new LinkedList<>();
        }

        for (Entrada<K, V> e : casillas[indice]) {
            if (e.clave.equals(clave)) {
                e.valor = valor;
                return;
            }
        }

        casillas[indice].add(new Entrada<>(clave, valor));
        cantidad++;
    }

    public V obtener(K clave) {
        int indice = calcularCasilla(clave);
        if (casillas[indice] == null) return null;

        for (Entrada<K, V> e : casillas[indice]) {
            if (e.clave.equals(clave)) return e.valor;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void redimensionar() {
        LinkedList<Entrada<K, V>>[] viejasCasillas = casillas;
        int nuevaCapacidad = casillas.length * 2;
        casillas = new LinkedList[nuevaCapacidad];
        cantidad = 0;

        for (LinkedList<Entrada<K, V>> lista : viejasCasillas) {
            if (lista != null) {
                for (Entrada<K, V> e : lista) {
                    // IMPORTANTE: Recalcular casilla con la nueva longitud
                    poner(e.clave, e.valor);
                }
            }
        }
    }

    public int tamano() { return cantidad; }
    public int numCasillas() { return casillas.length; }

    public int obtenerCadenaMasLarga() {
        int max = 0;
        for (LinkedList<Entrada<K, V>> lista : casillas) {
            if (lista != null) max = Math.max(max, lista.size());
        }
        return max;
    }

    public int contarCasillasVacias() {
        int vacias = 0;
        for (LinkedList<Entrada<K, V>> lista : casillas) {
            if (lista == null || lista.isEmpty()) vacias++;
        }
        return vacias;
    }
}
