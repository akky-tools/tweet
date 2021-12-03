package jobs;

import java.io.IOException;

import common.PropertiesValue;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Job {

    /**
     * ツイート取得
     */
    public static void getTweet() {
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
            System.out.println(e.getMessage());
        }

        // Twitter API認証処理
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

        // 自分のツイート内容を取得
        try {
            ResponseList<Status> list = twitter.getUserTimeline();
            for (Status status : list) {
                System.out.println(status.getText());
            }
        } catch (TwitterException e) {
            System.out.println(e.getMessage());
        }
    }

}