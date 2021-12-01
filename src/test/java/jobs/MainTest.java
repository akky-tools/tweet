package jobs;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void test_main() {
        try {
            String[] args = null;
            Main.main(args);
        } catch (Exception e) {
            fail();
        }
    }
}