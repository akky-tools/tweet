package jobs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import common.PropertiesValue;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Job {
    /**
     * Twitter接続処理
     */
    public static void connection() {
        PropertiesValue propertiesValue = new PropertiesValue();
        String consumerKey = "";
        String consumerSecret = "";
        String accessToken = "";
        String accessTokenSecret = "";
        int userIdCount = 0;
        List<Long> userIdList = new ArrayList<Long>();

        // Twitter API認証処理
        try {
            consumerKey = propertiesValue.getPropatiesValue("consumerKey");
            consumerSecret = propertiesValue.getPropatiesValue("consumerSecret");
            accessToken = propertiesValue.getPropatiesValue("accessToken");
            accessTokenSecret = propertiesValue.getPropatiesValue("accessTokenSecret");
            userIdCount = Integer.parseInt(propertiesValue.getPropatiesValue("userIdCount"));
            for (int i = 1; i <= userIdCount; i++) {
                userIdList.add(Long.parseLong(propertiesValue.getPropatiesValue("id_" + i)));
            }
        } catch (IOException e) {
            System.out.println(("*****IOException*****"));
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(("*****NumberFormatException*****"));
            System.out.println(e.getMessage());
        }

        // Twitter API認証処理
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

        for (Long userId : userIdList) {
            getTimelines(twitter, userId);
        }

        // ユーザーID調査用
        getUserId(twitter, "displayName");

    }

    /**
     * ユーザーのタイムラインを取得
     * 
     * @param twitter 接続済みのTwitterオブジェクト
     * @param userId  TwitterのユーザーID
     */
    public static void getTimelines(Twitter twitter, Long userId) {
        String asterisk = "****************************************************";
        try {
            ResponseList<Status> list = twitter.getUserTimeline(userId);
            System.out.println(asterisk);
            System.out.println("*");
            String name = list.size() > 0 ? list.get(0).getUser().getName().toString() : "データなし";
            String displayName = list.size() > 0 ? list.get(0).getUser().getScreenName().toString() : "データなし";
            System.out.println("*\tname : " + name);
            System.out.println("*\tlink : https://twitter.com/" + displayName);
            System.out.println("*");
            System.out.println(asterisk + "\n");

            int showCount = 5;
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss");
            for (Status status : list) {
                System.out.println(df.format(status.getCreatedAt()));
                System.out.println(status.getText());
                System.out.println("\n" + "----------------------------------------------------" + "\n");
                if (showCount-- == 1) {
                    break;
                }
            }
        } catch (TwitterException e) {
            System.out.println(("*****TwitterException*****"));
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(("*****NullPointerException*****"));
            System.out.println(e.getMessage());
        }
    }

    /**
     * ユーザーIDを取得
     * 
     * @param twitter     接続済みのTwitterオブジェクト
     * @param displayName Twitterの画面表示名（@以降の値）
     */
    public static void getUserId(Twitter twitter, String displayName) {
        String asterisk = "****************************************************";
        try {
            ResponseList<Status> list = twitter.getUserTimeline(displayName);
            System.out.println(asterisk);
            System.out.println("*");
            String name = list.size() > 0 ? list.get(0).getUser().getName().toString() : "データなし";
            String userId = list.size() > 0 ? String.valueOf(list.get(0).getUser().getId()) : "データなし";
            System.out.println("*\tname : " + name);
            System.out.println("*\ti  d : " + userId);
            System.out.println("*");
            System.out.println(asterisk + "\n");
        } catch (TwitterException e) {
            System.out.println(("*****TwitterException*****"));
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(("*****NullPointerException*****"));
            System.out.println(e.getMessage());
        }
    }

}