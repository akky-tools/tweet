package jobs;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class JobTest {
    final static Logger logger = Logger.getLogger("JobTestLogging");

    /**
     * タイムライン取得 正常系（データ取得成功）
     */
    @Test
    public void test_connection_mode1_ok() {
        try {
            logger.log(Level.INFO, "test_connection_mode1_ok　開始");
            Job.connection(1, "1350445035892817921");
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_connection_mode1_ok　終了\n");
        }
    }

    /**
     * タイムライン取得 正常系（データ無し）
     */
    @Test
    public void test_connection_mode1_no_data() {
        try {
            logger.log(Level.INFO, "test_connection_mode1_no_data　開始");
            Job.connection(1, "");
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_connection_mode1_no_data　終了\n");
        }
    }

    /**
     * タイムライン取得 異常系
     */
    @Test
    public void test_getTimelines_fali() {
        try {
            logger.log(Level.INFO, "test_getTimelines_fali　開始");

            // 設定値を取得
            PrintWriter printWriter = null;
            String consumerKey = "";
            String consumerSecret = "";
            String accessToken = "";
            String accessTokenSecret = "";

            // Twitter API認証処理
            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

            Job.getTimelines(printWriter, twitter, 1L);
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_getTimelines_fali　終了\n");
        }
    }

    /**
     * ユーザーID取得 正常系（取得成功）
     */
    @Test
    public void test_connection_mode2_ok() {
        try {
            logger.log(Level.INFO, "test_connection_mode2_ok　開始");
            Job.connection(2, "akky_work");
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_connection_mode2_ok　終了\n");
        }
    }

    /**
     * ユーザーID取得 正常系（取得失敗）
     */
    @Test
    public void test_connection_mode2_no_data() {
        try {
            logger.log(Level.INFO, "test_connection_mode2_no_data　開始");
            Job.connection(2, "");
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_connection_mode2_no_data　終了\n");
        }
    }

    /**
     * ユーザーID取得 異常系
     */
    @Test
    public void test_connection_mode2_fail() {
        try {
            logger.log(Level.INFO, "test_connection_mode2_fail　開始");
            Job.connection(2, "*****TwitterException*****");
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_connection_mode2_fail　終了\n");
        }
    }

    /**
     * 該当モード無し
     */
    @Test
    public void test_connection_mode_missing() {
        try {
            logger.log(Level.INFO, "test_connection_mode_missing　開始");
            Job.connection(0, "");
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_connection_mode_missing　終了\n");
        }
    }

}