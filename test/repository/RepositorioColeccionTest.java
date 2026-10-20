import model.LecturaSensor;
import org.junit.jupiter.api.Test;
import repository.RepositorioColeccion;
import repository.Repositorio;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioColeccionTest {

    @Test
    public void testAgregarYTamano() {
        RepositorioColeccion repo = new RepositorioColeccion();
        LecturaSensor ls = new LecturaSensor("EST-001", "2026-03-02 00:00", 20.0, 50.0, 10.0);
        repo.agregarAlFinal(ls);
        assertEquals(1, repo.tamano());
    }

    @Test
    public void testAgregarAlInicio() {
        RepositorioColeccion repo = new RepitorioColeccion();
        LecturaSensor a = new LecturaSensor("EST-001", "t0", 20, 50, 0);
        LecturaSensor b = new LecturaSensor("EST-002", "t1", 21, 51, 1);
        repo.agregarAlFinal(a);
        repo.agregarAlInicio(b);
        assertEquals(2, repo.tamano());
        assertSame(b, repo.obtener(0));
        assertSame(a, repo.obtener(1));
    }

    @Test
    public void testObtenerYEliminar() {
        RepositorioColeccion repo = new RepositorioColeccion();
        LecturaSensor a = new LecturaSensor("EST-001", "t0", 20, 50, 0);
        repo.agregarAlFinal(a);
        assertSame(a, repo.obtener(0));
        assertTrue(repo.eliminar(0));
        assertEquals(0, repo.tamano());
        assertNull(repo.obtener(0));
    }

    @Test
    public void testSumaPm25() {
        RepositorioColeccion repo = new RepositorioColeccion();
        repo.agregarAlFinal(new LecturaSensor("EST-001", "t0", 20, 50, 10.0));
        repo.agregarAlFinal(new LecturaSensor("EST-002", "t1", 21, 51, 20.0));
        assertEquals(30.0, repo.sumaPm25(), 0.001);
    }
}
