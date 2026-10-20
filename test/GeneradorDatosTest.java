import generator.GeneradorDatos;
import model.LecturaSensor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeneradorDatosTest {

    @Test
    public void testGenerarTamaño() {
        LecturaSensor[] arr = GeneradorDatos.generar(123);
        assertEquals(123, arr.length);
    }

    @Test
    public void testTimestampOrdenado() {
        LecturaSensor[] arr = GeneradorDatos.generar(1000);
        for (int i = 1; i < arr.length; i++) {
            String prev = arr[i - 1].getTimestamp();
            String cur = arr[i].getTimestamp();
            assertTrue(prev.compareTo(cur) < 0, "Timestamps should be strictly increasing");
        }
    }

    @Test
    public void testIdCicloNueveEstaciones() {
        LecturaSensor[] arr = GeneradorDatos.generar(20);
        // the 10th element (index 9) should wrap back to EST-001
        assertEquals("EST-001", arr[9].getIdSensor());
        // verify first nine are EST-001 .. EST-009
        for (int i = 0; i < 9; i++) {
            String expected = String.format("EST-%03d", i + 1);
            assertEquals(expected, arr[i].getIdSensor());
        }
    }

    @Test
    public void testRangosValidos() {
        LecturaSensor[] arr = GeneradorDatos.generar(100);
        for (LecturaSensor ls : arr) {
            assertTrue(ls.esValida(), "Generated reading should be within valid ranges");
        }
    }
}
