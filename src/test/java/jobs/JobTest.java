package jobs;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class JobTest {

    @Test
    public void postTweet() {
        try {
            Job.postTweet();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test_decideTweetContent_listSize_zero() {
        String[] ary = {};
        List<String> msgList = Arrays.asList(ary);
        String latestTweet = "おはよう";

        String returnStr = Job.decideTweetContent(msgList, latestTweet);

        assertNull(returnStr);
    }

    @Test
    public void test_decideTweetContent_listSize_one() {
        String[] ary = { "おはよう" };
        List<String> msgList = Arrays.asList(ary);
        String latestTweet = "おはよう";

        String returnStr = Job.decideTweetContent(msgList, latestTweet);

        assertNull(returnStr);
    }

    @Test
    public void test_decideTweetContent_decide() {
        String[] ary = { "おはよう", "こんにちは", "こんばんは" };
        List<String> msgList = Arrays.asList(ary);
        String latestTweet = "おはよう";

        String returnStr = Job.decideTweetContent(msgList, latestTweet);

        assertNotEquals(returnStr, latestTweet);
    }

    @Test
    public void test_decideTweetContent_notDecide() {
        String[] ary = { "おはよう", "おはよう", "おはよう" };
        List<String> msgList = Arrays.asList(ary);
        String latestTweet = "おはよう";

        String returnStr = Job.decideTweetContent(msgList, latestTweet);

        assertNull(returnStr);
    }

}
