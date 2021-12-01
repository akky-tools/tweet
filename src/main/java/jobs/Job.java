package jobs;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import common.PropertiesValue;
import common.S3;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Job {

    /**
     * ツイート送信
     */
    public static void postTweet() {
        // ツイートの内要をS3バケットから取得
        S3 s3 = new S3();

        // Twitter APIの認証用変数
        Twitter twitter = new TwitterFactory().getInstance();
        PropertiesValue propertiesValue = new PropertiesValue();
        String consumerKey = "";
        String consumerSecret = "";
        String accessToken = "";
        String accessTokenSecret = "";

        // Twitter API認証処理
        try {
            consumerKey = propertiesValue.getPropatiesValue("consumerKey");
            consumerSecret = propertiesValue.getPropatiesValue("consumerSecret");
            accessToken = propertiesValue.getPropatiesValue("accessToken");
            accessTokenSecret = propertiesValue.getPropatiesValue("accessTokenSecret");
        } catch (IOException e) {
            s3.putLog("IOException", e.getMessage());
        } finally {
            // 認証処理
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
        }

        // ツイート処理
        try {
            // 前回のツイート内容を取得
            ResponseList<Status> latestTweets = twitter.getHomeTimeline();

        } catch (TwitterException e) {
            s3.putLog("TwitterException", e.getMessage());
        }
    }

    /**
     * ツイート内容を確定させる
     * 
     * @param msgList     ツイート候補の内容リスト
     * @param latestTweet 前回のツイート内容
     * @return ツイート予定内容
     */
    public static String decideTweetContent(List<String> msgList, String latestTweet) {
        Random rnd = new Random();
        int listSize = msgList.size();
        int idx = rnd.nextInt(listSize);

        if (listSize == 0) {
            return null;
        } else if (listSize == 1 && msgList.get(0).equals(latestTweet)) {
            return null;
        } else {
            for (String msg : msgList) {
                if (msg.equals(latestTweet)) {
                    continue;
                }
                return null;
            }
        }

        if (msgList.get(idx).equals(latestTweet)) {
            decideTweetContent(msgList, latestTweet);
        }

        return msgList.get(idx);
    }

}