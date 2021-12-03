package jobs;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class JobTest {

    @Test
    public void test_getTweet() {
        try {
            Job.getTweet();
        } catch (Exception e) {
            fail();
        }
    }

}