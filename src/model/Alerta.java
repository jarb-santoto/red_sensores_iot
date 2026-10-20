/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   Alerta - VERSION COMPLETA

   Cuando una estacion reporta un valor fuera de umbral, la
   plataforma genera una alerta. No todas valen lo mismo:
   una fuga de material particulado en una zona escolar no
   puede esperar detras de un aviso de mantenimiento.

   severidad: 1 = INFORMATIVA
              2 = ADVERTENCIA
              3 = CRITICA
   ============================================================ */

package model;

public class Alerta {

    public static final int INFORMATIVA = 1;
    public static final int ADVERTENCIA = 2;
    public static final int CRITICA = 3;

    private final String idSensor;
    private final String mensaje;
    private final int severidad;
    private final long ordenDeLlegada;

    public Alerta(String idSensor, String mensaje, int severidad, long ordenDeLlegada) {
        this.idSensor = idSensor;
        this.mensaje = mensaje;
        this.severidad = severidad;
        this.ordenDeLlegada = ordenDeLlegada;
    }

    public String getIdSensor() { return idSensor; }
    public String getMensaje() { return mensaje; }
    public int getSeveridad() { return severidad; }
    public long getOrdenDeLlegada() { return ordenDeLlegada; }

    public String nombreSeveridad() {
        if (severidad == CRITICA) return "CRITICA";
        if (severidad == ADVERTENCIA) return "ADVERTENCIA";
        return "INFORMATIVA";
    }

    @Override
    public String toString() {
        return "[" + nombreSeveridad() + "] " + idSensor + " - " + mensaje
             + " (llego #" + ordenDeLlegada + ")";
    }
}
