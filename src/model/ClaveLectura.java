package model;

import java.util.Objects;

/**
 * Clave compuesta para identificar una lectura de sensor de forma única.
 * Debe ser INMUTABLE para evitar que el hashCode cambie mientras el objeto
 * está en una tabla hash.
 */
public final class ClaveLectura {
    private final String idSensor;
    private final String timestamp;

    public ClaveLectura(String idSensor, String timestamp) {
        this.idSensor = idSensor;
        this.timestamp = timestamp;
    }

    public String getIdSensor() { return idSensor; }
    public String getTimestamp() { return timestamp; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaveLectura that = (ClaveLectura) o;
        return Objects.equals(idSensor, that.idSensor) &&
               Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        // El contrato fundamental: si equals es true, hashCode debe ser igual.
        return Objects.hash(idSensor, timestamp);
    }

    @Override
    public String toString() {
        return idSensor + "@" + timestamp;
    }
}
