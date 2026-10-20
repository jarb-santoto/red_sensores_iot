import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HistorialNavegableTest {

    @Test
    public void testAgregarAndForwardTraversal() {
        HistorialNavegable historial = new HistorialNavegable();
        for (int i = 0; i < 5; i++) {
            historial.agregar(new LecturaSensor("EST-001", "t"+i, 20+i, 50+i, i));
        }
        assertEquals(5, historial.tamano());
        assertEquals(5, historial.recorrerHaciaAdelante());
    }

    @Test
    public void testBackwardTraversalAfterAdding() {
        HistorialNavegable historial = new HistorialNavegable();
        for (int i = 0; i < 4; i++) {
            historial.agregar(new LecturaSensor("EST-001", "t"+i, 20+i, 50+i, i));
        }
        assertEquals(4, historial.recorrerHaciaAtras()); // should equal size
    }

    @Test
    public void testNavigationAnteriorAndSiguiente() {
        HistorialNavegable historial = new HistorialNavegable();
        LecturaSensor a = new LecturaSensor("EST-001", "t0", 20, 50, 0);
        LecturaSensor b = new LecturaSensor("EST-001", "t1", 21, 51, 1);
        LecturaSensor c = new LecturaSensor("EST-001", "t2", 22, 52, 2);
        historial.agregar(a);
        historial.agregar(b);
        historial.agregar(c);
        // Cursor starts at first element after first insert (as per implementation)
        assertEquals(a, historial.actual());
        // avanzar dos veces
        assertTrue(historial.siguiente()); // ahora b
        assertEquals(b, historial.actual());
        assertTrue(historial.siguiente()); // ahora c
        assertEquals(c, historial.actual());
        // intentar avanzar más debe devolver false
        assertFalse(historial.siguiente());
        // retroceder dos veces
        assertTrue(historial.anterior()); // vuelve a b
        assertEquals(b, historial.actual());
        assertTrue(historial.anterior()); // vuelve a a
        assertEquals(a, historial.actual());
        // no hay más atrás
        assertFalse(historial.anterior());
    }
}
