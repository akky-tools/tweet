package jobs;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class JobTest {

    @Test
    public void test_handleRequest_ok() {
        Job.postTweet();
        assertTrue(true);
    }

    @Test
    public void test_decideTweetContent_if_true() {
        String[] ary = { "おはよう", "こんにちは", "こんばんは" };
        List<String> msgList = Arrays.asList(ary);
        String latestTweet = "おはよう";

        String returnStr = Job.decideTweetContent(msgList, latestTweet);

        assertNotEquals(returnStr, latestTweet);
    }

    @Test
    public void test_decideTweetContent_if_false() {
        String[] ary = { "おはよう", "こんにちは", "こんばんは" };
        List<String> msgList = Arrays.asList(ary);
        String latestTweet = "おはよう";

        String returnStr = Job.decideTweetContent(msgList, latestTweet);

        assertNotEquals(returnStr, latestTweet);
    }

}
