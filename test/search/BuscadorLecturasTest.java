import model.LecturaSensor;
import org.junit.jupiter.api.Test;
import search.BuscadorLecturas;

import static org.junit.jupiter.api.Assertions.*;

public class BuscadorLecturasTest {

    @Test
    public void testBusquedaLinealPorTimestamp() {
        LecturaSensor[] datos = new LecturaSensor[]{
                new LecturaSensor("EST-001", "2026-01-01 00:00", 20.0, 50.0, 10.0),
                new LecturaSensor("EST-002", "2026-01-01 01:00", 21.0, 51.0, 20.0),
                new LecturaSensor("EST-003", "2026-01-01 02:00", 22.0, 52.0, 30.0)
        };
        int pos = BuscadorLecturas.busquedaLinealPorTimestamp(datos, "2026-01-01 01:00");
        assertEquals(1, pos);
        assertEquals(2, BuscadorLecturas.getComparaciones()); // stopped after finding
    }

    @Test
    public void testBusquedaBinariaPorTimestamp() {
        LecturaSensor[] datos = new LecturaSensor[]{
                new LecturaSensor("EST-001", "2026-01-01 00:00", 20.0, 50.0, 10.0),
                new LecturaSensor("EST-002", "2026-01-01 01:00", 21.0, 51.0, 20.0),
                new LecturaSensor("EST-003", "2026-01-01 02:00", 22.0, 52.0, 30.0)
        };
        int pos = BuscadorLecturas.busquedaBinariaPorTimestamp(datos, "2026-01-01 01:00");
        assertEquals(1, pos);
        // binary search on 3 elements does at most 2 comparisons
        assertTrue(BuscadorLecturas.getComparaciones() <= 2);
    }

    @Test
    public void testBusquedaBinariaPorPm25() {
        // Order the array by pm25 ascending
        LecturaSensor[] datos = new LecturaSensor[]{
                new LecturaSensor("EST-001", "2026-01-01 00:00", 20.0, 50.0, 5.0),
                new LecturaSensor("EST-002", "2026-01-01 01:00", 21.0, 51.0, 10.0),
                new LecturaSensor("EST-003", "2026-01-01 02:00", 22.0, 52.0, 15.0)
        };
        int pos = BuscadorLecturas.busquedaBinariaPorPm25(datos, 10.0);
        assertEquals(1, pos);
    }

    @Test
    public void testBuscarPorEstacion() {
        LecturaSensor[] datos = new LecturaSensor[]{
                new LecturaSensor("EST-001", "2026-01-01 00:00", 20.0, 50.0, 5.0),
                new LecturaSensor("EST-002", "2026-01-01 01:00", 21.0, 51.0, 10.0),
                new LecturaSensor("EST-001", "2026-01-01 02:00", 22.0, 52.0, 15.0)
        };
        int pos = BuscadorLecturas.buscarPorEstacion(datos, "EST-001");
        assertEquals(0, pos); // first occurrence
    }
}
