import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BancoDeListasTest {

    @Test
    public void testExperimentoUnoRunsWithoutException() {
        // This will execute experimentoUno which prints output.
        // Just ensure it does not throw any exception.
        BancoDeListas.main(new String[]{});
    }
}
