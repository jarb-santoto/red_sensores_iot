/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   GeneradorDatos - VERSION COMPLETA

   Hasta ahora trabajaste con 211 lecturas: un dia de la red.
   Esta semana la ciudad amplio el despliegue y necesitas saber
   si tu plataforma aguanta.

   Esta clase fabrica lecturas sinteticas por millones sin
   necesidad de un archivo gigante en disco.

   IMPORTANTE: las lecturas se generan EN ORDEN CRONOLOGICO,
   igual que llegan de la red real: minuto a minuto, sin saltos
   hacia atras en el tiempo. Recuerda ese detalle. Va a importar.
   ============================================================ */

import java.util.Random;

public class GeneradorDatos {

    private static final int NUM_ESTACIONES = 9;
    private static final long SEMILLA = 20262L;

    /**
     * Genera n lecturas sinteticas en orden cronologico ascendente.
     * El timestamp se representa como los minutos transcurridos desde
     * el inicio del despliegue, formateado a 10 digitos para que el
     * orden alfabetico coincida con el orden cronologico.
     *
     * @param n cantidad de lecturas a generar
     * @return arreglo de lecturas ordenado por timestamp
     */
    public static LecturaSensor[] generar(int n) {
        Random azar = new Random(SEMILLA);
        LecturaSensor[] datos = new LecturaSensor[n];

        for (int i = 0; i < n; i++) {
            String id = String.format("EST-%03d", (i % NUM_ESTACIONES) + 1);
            String timestamp = String.format("%010d", i);

            double temperatura = 11 + azar.nextDouble() * 18;
            double humedad = 55 + azar.nextDouble() * 35;
            double pm25 = 5 + azar.nextDouble() * 55;

            datos[i] = new LecturaSensor(id, timestamp,
                                         redondear(temperatura),
                                         redondear(humedad),
                                         redondear(pm25));
        }
        return datos;
    }

    private static double redondear(double valor) {
        return Math.round(valor * 10.0) / 10.0;
    }

    /**
     * Devuelve el timestamp de una lectura que SI existe en el arreglo,
     * ubicada en la posicion indicada. Util para probar el peor caso.
     */
    public static String timestampEnPosicion(int posicion) {
        return String.format("%010d", posicion);
    }

    /**
     * Devuelve un timestamp que NO existe en ningun arreglo generado.
     */
    public static String timestampInexistente() {
        return "9999999999";
    }
}
