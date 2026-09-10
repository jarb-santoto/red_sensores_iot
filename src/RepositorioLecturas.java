/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   TAD RepositorioLecturas - VERSION 0.1 INCOMPLETA

   Este es el Tipo Abstracto de Dato del semestre: quien lo usa
   solo conoce las operaciones publicas de abajo. NO deberia
   saber que por dentro hay un arreglo.

   Ese es el contrato. Respetalo mientras lo completas.

   ADVERTENCIA: esta version corre sin caerse y entrega
   resultados incorrectos. Tu trabajo de hoy es descubrir en
   que miente antes de arreglarla.
   ============================================================ */

public class RepositorioLecturas {

    private static final int CAPACIDAD_INICIAL = 10;

    private LecturaSensor[] lecturas;
    private int cantidad;

    public RepositorioLecturas() {
        this.lecturas = new LecturaSensor[CAPACIDAD_INICIAL];
        this.cantidad = 0;
    }

    // ---------- OPERACIONES DEL CONTRATO ----------

    /**
     * Agrega una lectura al final del repositorio.
     * @return true si se agrego, false si no habia espacio
     */
    public boolean agregar(LecturaSensor lectura) {
        if (cantidad == lecturas.length) {
            return false;
        }
        lecturas[cantidad] = lectura;
        cantidad++;
        return true;
    }

    /**
     * Devuelve la lectura que esta en la posicion indicada.
     */
    public LecturaSensor obtener(int posicion) {
        return lecturas[posicion];
    }

    /**
     * Cantidad de lecturas almacenadas actualmente.
     */
    public int tamano() {
        return cantidad;
    }

    /**
     * Elimina la lectura de la posicion indicada.
     *
     * VERSION INGENUA: revisala con cuidado antes de confiar en ella.
     */
    public void eliminar(int posicion) {
        lecturas[posicion] = null;
    }

    /**
     * Busca la primera lectura de una estacion.
     * TODO 1: implementar. Devolver null si no existe.
     */
    public LecturaSensor buscarPorEstacion(String idSensor) {
        return null;
    }

    /**
     * Reemplaza la lectura de una posicion por otra.
     * TODO 2: implementar, verificando que la posicion sea valida.
     */
    public void actualizar(int posicion, LecturaSensor nueva) {
    }

    /**
     * Duplica la capacidad interna del arreglo conservando el contenido.
     *
     * TODO 3: implementar. Despues llamalo desde agregar() cuando
     * el arreglo se llene, para que el repositorio deje de tener techo.
     *
     * Pista: no puedes "estirar" un arreglo en Java. Tienes que crear
     * uno nuevo mas grande y copiar. Piensa cuantas copias implica eso.
     */
    private void redimensionar() {
    }

    /**
     * Promedio de PM2.5 de todas las lecturas almacenadas.
     *
     * TODO 4: revisar. Este metodo asume algo que puede no ser cierto
     * despues de que alguien llame a eliminar().
     */
    public double promedioPm25() {
        double suma = 0;
        for (int i = 0; i < cantidad; i++) {
            suma = suma + lecturas[i].getPm25();
        }
        return suma / cantidad;
    }
}
