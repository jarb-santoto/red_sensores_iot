import experiment.BancoDeOrdenamiento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BancoDeOrdenamientoTest {

    @Test
    public void testExperimentoUnoRunsWithoutException() {
        // Executes the main method which runs experimentoUno by default.
        BancoDeOrdenamiento.main(new String[]{});
    }
}
