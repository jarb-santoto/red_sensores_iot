import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioEnlazadoTest {

    @Test
    public void testAgregarAlFinalUsesUltimo() {
        RepositorioEnlazado lista = new RepositorioEnlazado();
        // agregar varios elementos al final
        for (int i = 0; i < 5; i++) {
            LecturaSensor l = new LecturaSensor("EST-001", String.format("2026-03-02 %02d:00", i), 20.0, 50.0, i);
            lista.agregarAlFinal(l);
        }
        assertEquals(5, lista.tamano());
        // el último elemento debería ser el último insertado
        LecturaSensor ultimo = lista.obtener(4);
        assertEquals(4.0, ultimo.getPm25());
        // no debería haber saltos al agregar al final
        RepositorioEnlazado.reiniciarSaltos();
        assertEquals(0, RepositorioEnlazado.getSaltos());
    }

    @Test
    public void testEliminarFirstNode() {
        RepositorioEnlazado lista = new RepositorioEnlazado();
        LecturaSensor a = new LecturaSensor("EST-001", "t0", 20, 50, 0);
        LecturaSensor b = new LecturaSensor("EST-001", "t1", 21, 51, 1);
        LecturaSensor c = new LecturaSensor("EST-001", "t2", 22, 52, 2);
        lista.agregarAlFinal(a);
        lista.agregarAlFinal(b);
        lista.agregarAlFinal(c);
        assertTrue(lista.eliminar(0));
        assertEquals(2, lista.tamano());
        // el nuevo primero debe ser b
        LecturaSensor primero = lista.obtener(0);
        assertEquals(1.0, primero.getPm25());
    }

    @Test
    public void testEliminarMiddleNodeKeepsChain() {
        RepositorioEnlazado lista = new RepositorioEnlazado();
        for (int i = 0; i < 5; i++) {
            lista.agregarAlFinal(new LecturaSensor("EST-001", "t"+i, 20+i, 50+i, i));
        }
        assertTrue(lista.eliminar(2)); // elimina el elemento con pm25=2
        assertEquals(4, lista.tamano());
        // los elementos restantes deben estar en orden 0,1,3,4
        assertEquals(0.0, lista.obtener(0).getPm25());
        assertEquals(1.0, lista.obtener(1).getPm25());
        assertEquals(3.0, lista.obtener(2).getPm25());
        assertEquals(4.0, lista.obtener(3).getPm25());
    }

    @Test
    public void testEliminarLastNodeUpdatesUltimo() {
        RepositorioEnlazado lista = new RepositorioEnlazado();
        for (int i = 0; i < 3; i++) {
            lista.agregarAlFinal(new LecturaSensor("EST-001", "t"+i, 20+i, 50+i, i));
        }
        assertTrue(lista.eliminar(2)); // elimina el último
        assertEquals(2, lista.tamano());
        // el último elemento ahora debe ser el de índice 1
        assertEquals(1.0, lista.obtener(1).getPm25());
    }

    @Test
    public void testContarRecorriendoMatchesCantidad() {
        RepositorioEnlazado lista = new RepositorioEnlazado();
        for (int i = 0; i < 7; i++) {
            lista.agregarAlFinal(new LecturaSensor("EST-001", "t"+i, 20+i, 50+i, i));
        }
        assertEquals(7, lista.tamano());
        assertEquals(7, lista.contarRecorriendo());
    }
}
