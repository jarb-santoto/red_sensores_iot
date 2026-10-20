import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrdenadorTest {

    // Helper to shuffle an array deterministically
    private static LecturaSensor[] desordenar(LecturaSensor[] original) {
        LecturaSensor[] copia = original.clone();
        java.util.Random r = new java.util.Random(777L);
        for (int i = copia.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            LecturaSensor tmp = copia[i];
            copia[i] = copia[j];
            copia[j] = tmp;
        }
        return copia;
    }

    @Test
    public void testBurbujaEarlyExitOnSortedData() {
        LecturaSensor[] datos = GeneradorDatos.generar(100);
        LecturaSensor[] copia = datos.clone();
        Ordenador.burbuja(copia);
        assertTrue(Ordenador.estaOrdenadoPorTimestamp(copia));
        // Expected comparisons: n-1 = 99
        assertEquals(99, Ordenador.getComparaciones(), "Comparaciones con corte temprano deberían ser n-1");
    }

    @Test
    public void testBurbujaSortUnsorted() {
        LecturaSensor[] datos = desordenar(GeneradorDatos.generar(50));
        Ordenador.burbuja(datos);
        assertTrue(Ordenador.estaOrdenadoPorTimestamp(datos));
    }

    @Test
    public void testQuickSortRandomPivotSortedNoOverflow() {
        LecturaSensor[] datos = GeneradorDatos.generar(200);
        try {
            Ordenador.quickSortPivotePrimero(datos);
        } catch (StackOverflowError e) {
            fail("QuickSort con pivote aleatorio no debería lanzar StackOverflowError en datos ordenados");
        }
        assertTrue(Ordenador.estaOrdenadoPorTimestamp(datos));
    }

    @Test
    public void testQuickSortRandomPivotUnsorted() {
        LecturaSensor[] datos = desordenar(GeneradorDatos.generar(200));
        Ordenador.quickSortPivotePrimero(datos);
        assertTrue(Ordenador.estaOrdenadoPorTimestamp(datos));
    }

    @Test
    public void testMergeSortSortsCorrectly() {
        LecturaSensor[] datos = desordenar(GeneradorDatos.generar(150));
        Ordenador.mergeSort(datos);
        assertTrue(Ordenador.estaOrdenadoPorTimestamp(datos));
    }

    @Test
    public void testHeapSortSortsCorrectly() {
        LecturaSensor[] datos = desordenar(GeneradorDatos.generar(150));
        Ordenador.heapSort(datos);
        assertTrue(Ordenador.estaOrdenadoPorTimestamp(datos));
    }

    @Test
    public void testOrdenarPorPm25() {
        LecturaSensor[] datos = GeneradorDatos.generar(100);
        Ordenador.ordenarPorPm25(datos);
        for (int i = 1; i < datos.length; i++) {
            assertTrue(datos[i - 1].getPm25() <= datos[i].getPm25(), "Array debe estar ordenado por PM2.5");
        }
    }
}
