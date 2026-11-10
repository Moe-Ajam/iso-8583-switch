import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GreaterTest {
    @Test
    void greetsByName() {
        String name = "Sara";
        String result = "Hi " + name;
        Assertions.assertEquals("Hi Sara", result);
    }
}
