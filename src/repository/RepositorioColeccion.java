/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   RepositorioColeccion - VERSION CORREGIDA

   Tercera implementacion del contrato Repositorio de la semana 5.
   La primera guardaba en un arreglo propio, la segunda en una
   cadena de nodos propia. Esta delega en el Java Collections
   Framework.

   Si el contrato quedo bien definido, el resto del sistema no
   deberia notar el cambio. Compruebalo.
   ============================================================ */

package repository;

import java.util.ArrayList;
import java.util.List;
import model.LecturaSensor;

public class RepositorioColeccion implements Repositorio {

    private List<LecturaSensor> lecturas;

    /**
     * Eleccion de implementacion: ArrayList.
     * La plataforma inserta lecturas nuevas todo el tiempo al final
     * y consulta el historico recorriendolo completo (iteracion),
     * lo cual es extremadamente eficiente en un ArrayList.
     */
    public RepositorioColeccion() {
        this.lecturas = new ArrayList<>();
    }

    @Override
    public String nombre() { return "Coleccion JCF"; }

    @Override
    public void agregarAlFinal(LecturaSensor lectura) {
        lecturas.add(lectura);
    }

    @Override
    public void agregarAlInicio(LecturaSensor lectura) {
        lecturas.add(0, lectura);
    }

    @Override
    public LecturaSensor obtener(int posicion) {
        if (posicion < 0 || posicion >= lecturas.size()) {
            return null;
        }
        return lecturas.get(posicion);
    }

    @Override
    public boolean eliminar(int posicion) {
        if (posicion < 0 || posicion >= lecturas.size()) {
            return false;
        }
        lecturas.remove(posicion);
        return true;
    }

    @Override
    public int tamano() {
        return lecturas.size();
    }

    /**
     * Suma el PM2.5 de todas las lecturas.
     * Implementacion optimizada usando for-each (Iterador),
     * evitando el costo cuadratico de get(i) en LinkedLists.
     */
    public double sumaPm25() {
        double suma = 0;
        for (LecturaSensor l : lecturas) {
            suma += l.getPm25();
        }
        return suma;
    }
}
