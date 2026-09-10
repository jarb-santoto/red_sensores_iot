/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   Clase LecturaSensor - VERSION COMPLETA

   Esta clase SI esta terminada. Es el resultado de lo que
   discutimos en la Semana 1: en vez de arrastrar cinco
   variables sueltas por todos los metodos, una lectura viaja
   completa dentro de un objeto.

   Estudiala antes de usarla. La vas a ver las doce semanas.
   ============================================================ */

public class LecturaSensor {

    // Rangos fisicos aceptables. Constantes, no numeros magicos.
    public static final double TEMP_MIN = -40.0;
    public static final double TEMP_MAX = 60.0;
    public static final double HUMEDAD_MIN = 0.0;
    public static final double HUMEDAD_MAX = 100.0;
    public static final double PM25_MIN = 0.0;

    private final String idSensor;
    private final String timestamp;
    private final double temperatura;
    private final double humedad;
    private final double pm25;

    public LecturaSensor(String idSensor, String timestamp,
                         double temperatura, double humedad, double pm25) {
        this.idSensor = idSensor;
        this.timestamp = timestamp;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.pm25 = pm25;
    }

    public String getIdSensor() { return idSensor; }
    public String getTimestamp() { return timestamp; }
    public double getTemperatura() { return temperatura; }
    public double getHumedad() { return humedad; }
    public double getPm25() { return pm25; }

    /**
     * Extrae la hora del timestamp con formato "yyyy-MM-dd HH:mm".
     * @return hora entre 0 y 23
     */
    public int getHora() {
        String parteHora = timestamp.substring(11, 13);
        return Integer.parseInt(parteHora);
    }

    /**
     * Verifica que los tres valores esten dentro de rangos fisicamente posibles.
     * @return true si la lectura es utilizable para calculos
     */
    public boolean esValida() {
        if (temperatura < TEMP_MIN || temperatura > TEMP_MAX) return false;
        if (humedad < HUMEDAD_MIN || humedad > HUMEDAD_MAX) return false;
        if (pm25 < PM25_MIN) return false;
        return true;
    }

    @Override
    public String toString() {
        return idSensor + " | " + timestamp
             + " | T=" + temperatura + " | H=" + humedad + " | PM=" + pm25;
    }
}
