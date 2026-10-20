import analysis.AnalizadorMatriz;
import model.LecturaSensor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnalizadorMatrizTest {

    @Test
    public void testRegistrarYPromedioDeHora() {
        AnalizadorMatriz am = new AnalizadorMatriz();
        // tres estaciones con diferentes PM para la hora 5
        am.registrar(new LecturaSensor("EST-001", "2026-01-01 05:00", 20.0, 50.0, 10.0));
        am.registrar(new LecturaSensor("EST-002", "2026-01-01 05:00", 21.0, 51.0, 20.0));
        am.registrar(new LecturaSensor("EST-003", "2026-01-01 05:00", 22.0, 52.0, 30.0));
        double avg = am.promedioDeHora(5);
        assertEquals(20.0, avg); // (10+20+30)/3
    }

    @Test
    public void testPromedioDeEstacion() {
        AnalizadorMatriz am = new AnalizadorMatriz();
        // EST-004 corresponds to row index 3
        am.registrar(new LecturaSensor("EST-004", "2026-01-01 01:00", 20.0, 50.0, 10.0));
        am.registrar(new LecturaSensor("EST-004", "2026-01-01 02:00", 21.0, 51.0, 30.0));
        am.registrar(new LecturaSensor("EST-004", "2026-01-01 03:00", 22.0, 52.0, 50.0));
        double avg = am.promedioDeEstacion(3); // row 3 = EST-004
        assertEquals(30.0, avg); // (10+30+50)/3
    }

    @Test
    public void testHoraMasContaminada() {
        AnalizadorMatriz am = new AnalizadorMatriz();
        // Hour 8 gets high values, hour 9 gets low values
        am.registrar(new LecturaSensor("EST-001", "2026-01-01 08:00", 20.0, 50.0, 80.0));
        am.registrar(new LecturaSensor("EST-002", "2026-01-01 08:00", 21.0, 51.0, 90.0));
        am.registrar(new LecturaSensor("EST-001", "2026-01-01 09:00", 22.0, 52.0, 10.0));
        int hora = am.horaMasContaminada();
        assertEquals(8, hora);
    }
}
