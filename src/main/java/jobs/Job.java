package jobs;

import java.io.IOException;
import java.util.List;

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
            // 前回のツイート格納用変数
            String latestTweet = "";

            // ツイートの内要をS3バケットから取得
            List<String> msgList = s3.getTweetList();

            // 前回のツイート内容を取得
            ResponseList<Status> latestTweets = twitter.getHomeTimeline();
            latestTweet = latestTweets.get(0).getText();

            // 前回のツイート内容と重複していないものを選択
            String tweet = decideTweetContent(msgList, latestTweet);

            // ツイート内容を投稿
            // twitter.updateStatus(tweet);
            // s3.putLog("send", tweet);
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
        int listSize = msgList.size();
        int idx = 0;

        if (listSize == 0) {
            return null;
        } else if (listSize == 1 && msgList.get(0).equals(latestTweet)) {
            return null;
        } else {
            for (String msg : msgList) {
                if (msg.equals(latestTweet)) {
                    idx++;
                    continue;
                } else {
                    return msgList.get(idx);
                }
            }
        }

        return null;
    }

}