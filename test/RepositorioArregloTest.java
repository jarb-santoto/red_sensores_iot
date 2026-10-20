import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioArregloTest {

    @Test
    public void testAgregarAlFinalAndObtener() {
        RepositorioArreglo repo = new RepositorioArreglo();
        LecturaSensor a = new LecturaSensor("EST-001", "2026-03-02 00:00", 20.0, 50.0, 10.0);
        LecturaSensor b = new LecturaSensor("EST-002", "2026-03-02 01:00", 21.0, 51.0, 20.0);
        repo.agregarAlFinal(a);
        repo.agregarAlFinal(b);
        assertEquals(2, repo.tamano());
        assertSame(a, repo.obtener(0));
        assertSame(b, repo.obtener(1));
    }

    @Test
    public void testAgregarAlInicioShiftsElements() {
        RepositorioArreglo repo = new RepositorioArreglo();
        // Add initial element at end
        repo.agregarAlFinal(new LecturaSensor("EST-001", "t0", 20, 50, 0));
        // Now add at the beginning
        LecturaSensor nuevo = new LecturaSensor("EST-002", "t1", 21, 51, 1);
        repo.agregarAlInicio(nuevo);
        assertEquals(2, repo.tamano());
        // The new element should be at position 0
        assertSame(nuevo, repo.obtener(0));
        // The previous element should have moved to position 1
        assertEquals("t0", repo.obtener(1).getTimestamp());
    }

    @Test
    public void testEliminarShiftsElementsAndUpdatesSize() {
        RepositorioArreglo repo = new RepositorioArreglo();
        for (int i = 0; i < 5; i++) {
            repo.agregarAlFinal(new LecturaSensor("EST-001", "t"+i, 20+i, 50+i, i));
        }
        assertTrue(repo.eliminar(2)); // remove third element
        assertEquals(4, repo.tamano());
        // After deletion, element previously at index 3 should now be at index 2
        assertEquals(3.0, repo.obtener(2).getPm25());
    }

    @Test
    public void testRedimensionAndCounters() {
        RepositorioArreglo repo = new RepositorioArreglo();
        // Force redimension by adding more than initial capacity (10)
        for (int i = 0; i < 12; i++) {
            repo.agregarAlFinal(new LecturaSensor("EST-001", "t"+i, 20+i, 50+i, i));
        }
        assertEquals(12, repo.tamano());
        // There should have been at least one copy due to redimension
        assertTrue(RepositorioArreglo.getCopiasPorRedimension() > 0);
    }
}
