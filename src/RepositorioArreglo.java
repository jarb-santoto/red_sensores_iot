/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   RepositorioArreglo - VERSION COMPLETA

   Esta es la solucion de referencia de la semana 2, ajustada
   para cumplir la interfaz Repositorio. La usamos como punto
   de comparacion: NO la modifiques.

   Lleva un contador de movimientos de elementos, para que
   puedas comparar el costo con el de la lista enlazada.
   ============================================================ */

public class RepositorioArreglo implements Repositorio {

    private static final int CAPACIDAD_INICIAL = 10;

    private LecturaSensor[] lecturas;
    private int cantidad;

    private static long movimientos = 0;
    private static long copiasPorRedimension = 0;

    public static long getMovimientos() { return movimientos; }
    public static long getCopiasPorRedimension() { return copiasPorRedimension; }
    public static void reiniciarContadores() { movimientos = 0; copiasPorRedimension = 0; }

    public RepositorioArreglo() {
        this.lecturas = new LecturaSensor[CAPACIDAD_INICIAL];
        this.cantidad = 0;
    }

    @Override
    public String nombre() { return "Arreglo"; }

    @Override
    public int tamano() { return cantidad; }

    private void redimensionar() {
        LecturaSensor[] nuevo = new LecturaSensor[lecturas.length * 2];
        for (int i = 0; i < cantidad; i++) {
            nuevo[i] = lecturas[i];
            copiasPorRedimension++;
        }
        lecturas = nuevo;
    }

    @Override
    public void agregarAlFinal(LecturaSensor lectura) {
        if (cantidad == lecturas.length) redimensionar();
        lecturas[cantidad] = lectura;
        cantidad++;
    }

    @Override
    public void agregarAlInicio(LecturaSensor lectura) {
        if (cantidad == lecturas.length) redimensionar();
        for (int i = cantidad; i > 0; i--) {
            lecturas[i] = lecturas[i - 1];
            movimientos++;
        }
        lecturas[0] = lectura;
        cantidad++;
    }

    @Override
    public LecturaSensor obtener(int posicion) {
        if (posicion < 0 || posicion >= cantidad) return null;
        return lecturas[posicion];
    }

    @Override
    public boolean eliminar(int posicion) {
        if (posicion < 0 || posicion >= cantidad) return false;
        for (int i = posicion; i < cantidad - 1; i++) {
            lecturas[i] = lecturas[i + 1];
            movimientos++;
        }
        lecturas[cantidad - 1] = null;
        cantidad--;
        return true;
    }
}
