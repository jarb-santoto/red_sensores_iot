public class LecturaSensor {
    private String idEstacion;
    private String fechaHora;
    private double temperatura;
    private double humedad;
    private double pm25;

    public LecturaSensor(
            String idEstacion,
            String fechaHora,
            double temperatura,
            double humedad,
            double pm25) {

        this.idEstacion = idEstacion;
        this.fechaHora = fechaHora;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.pm25 = pm25;
    }

    public String getIdEstacion() {
        return idEstacion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public double getPm25() {
        return pm25;
    }
}
