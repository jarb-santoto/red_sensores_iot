import model.LecturaSensor;
import org.junit.jupiter.api.Test;
import repository.RepositorioLecturas;

import static org.junit.jupiter.api.Assertions.*;

public class RepositorioLecturasTest {

    @Test
    public void testAgregarYTamano() {
        RepositorioLecturas repo = new RepositorioLecturas();
        LecturaSensor ls = new LecturaSensor("EST-001", "2026-03-02 00:00", 20.0, 50.0, 10.0);
        assertTrue(repo.agregar(ls));
        assertEquals(1, repo.tamano());
    }

    @Test
    public void testRedimensionar() {
        RepositorioLecturas repo = new RepositorioLecturas();
        // La capacidad inicial es 10; vamos a agregar 15 lecturas.
        for (int i = 0; i < 15; i++) {
            LecturaSensor ls = new LecturaSensor("EST-001", String.format("2026-03-02 %02d:00", i), 20.0, 50.0, i);
            assertTrue(repo.agregar(ls));
        }
        assertEquals(15, repo.tamano());
    }

    @Test
    public void testObtenerOutOfBounds() {
        RepositorioLecturas repo = new RepositorioLecturas();
        assertNull(repo.obtener(-1));
        assertNull(repo.obtener(0)); // aún vacío
    }

    @Test
    public void testEliminarDesplaza() {
        RepositorioLecturas repo = new RepositorioLecturas();
        LecturaSensor a = new LecturaSensor("EST-001", "2026-03-02 00:00", 20.0, 50.0, 10.0);
        LecturaSensor b = new LecturaSensor("EST-002", "2026-03-02 01:00", 21.0, 51.0, 20.0);
        LecturaSensor c = new LecturaSensor("EST-003", "2026-03-02 02:00", 22.0, 52.0, 30.0);
        repo.agregar(a);
        repo.agregar(b);
        repo.agregar(c);
        repo.eliminar(1); // elimina b
        assertEquals(2, repo.tamano());
        assertEquals(a, repo.obtener(0));
        assertEquals(c, repo.obtener(1));
    }

    @Test
    public void testBuscarPorEstacion() {
        RepositorioLecturas repo = new RepositorioLecturas();
        LecturaSensor target = new LecturaSensor("EST-002", "2026-03-02 01:00", 21.0, 51.0, 20.0);
        repo.agregar(new LecturaSensor("EST-001", "2026-03-02 00:00", 20.0, 50.0, 10.0));
        repo.agregar(target);
        repo.agregar(new LecturaSensor("EST-003", "2026-03-02 02:00", 22.0, 52.0, 30.0));
        assertSame(target, repo.buscarPorEstacion("EST-002"));
        assertNull(repo.buscarPorEstacion("EST-999"));
    }

    @Test
    public void testActualizar() {
        RepositorioLecturas repo = new RepositorioLecturas();
        LecturaSensor original = new LecturaSensor("EST-001", "2026-03-02 00:00", 20.0, 50.0, 10.0);
        repo.agregar(original);
        LecturaSensor nueva = new LecturaSensor("EST-001", "2026-03-02 00:00", 25.0, 55.0, 15.0);
        repo.actualizar(0, nueva);
        assertEquals(nueva, repo.obtener(0));
    }

    @Test
    public void testPromedioPm25() {
        RepositorioLecturas repo = new RepositorioLecturas();
        repo.agregar(new LecturaSensor("EST-001", "2026-03-02 00:00", 20.0, 50.0, 10.0));
        repo.agregar(new LecturaSensor("EST-002", "2026-03-02 01:00", 21.0, 51.0, 30.0));
        assertEquals(20.0, repo.promedioPm25()); // (10+30)/2
    }
}
