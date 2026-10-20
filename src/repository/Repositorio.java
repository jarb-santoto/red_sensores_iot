/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   Interfaz Repositorio - VERSION COMPLETA

   Esto es el CONTRATO del que hablamos en la semana 2, ahora
   escrito en codigo. Quien use un Repositorio no sabe -ni
   necesita saber- si por dentro hay un arreglo o una cadena
   de nodos.
   ============================================================ */

package repository;

import model.LecturaSensor;

public interface Repositorio {

    /** Agrega una lectura al final. */
    void agregarAlFinal(LecturaSensor lectura);

    /** Agrega una lectura al inicio. */
    void agregarAlInicio(LecturaSensor lectura);

    /** Devuelve la lectura de una posicion, o null si no existe. */
    LecturaSensor obtener(int posicion);

    /** Elimina la lectura de una posicion. Devuelve true si la elimino. */
    boolean eliminar(int posicion);

    /** Cantidad de lecturas almacenadas. */
    int tamano();

    /** Nombre de la implementacion, para los reportes. */
    String nombre();
}
