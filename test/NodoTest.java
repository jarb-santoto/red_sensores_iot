import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodoTest {
    @Test
    public void testLinkingNodes() {
        LecturaSensor dato1 = new LecturaSensor("EST-001", "t0", 20, 50, 0);
        LecturaSensor dato2 = new LecturaSensor("EST-002", "t1", 21, 51, 1);
        Nodo n1 = new Nodo(dato1);
        Nodo n2 = new Nodo(dato2);
        n1.setSiguiente(n2);
        assertSame(dato2, n1.getSiguiente().getDato());
        assertSame(dato1, n1.getDato());
    }
}
