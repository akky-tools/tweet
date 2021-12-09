package jobs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Job {
    final static Logger logger = Logger.getLogger("JobLogging");
    final static String asterisk = "****************************************************";

    /**
     * Twitter接続処理
     * 
     * @param mode        1：タイムラインを取得、2：ユーザーIDを取得
     * @param displayName ID検索用のTwitter画面表示名（@以降の値）
     */
    public static void connection(int mode, String displayName) {
        try {
            // 設定値を取得
            PrintWriter printWriter = null;
            ResourceBundle rb = ResourceBundle.getBundle("application");

            String consumerKey = rb.getString("consumerKey");
            String consumerSecret = rb.getString("consumerSecret");
            String accessToken = rb.getString("accessToken");
            String accessTokenSecret = rb.getString("accessTokenSecret");
            String outputPath = rb.getString("outputPath");

            int userIdCount = Integer.parseInt(rb.getString("userIdCount"));
            List<Long> userIdList = new ArrayList<Long>();
            for (int i = 1; i <= userIdCount; i++) {
                userIdList.add(Long.parseLong(rb.getString("id_" + i)));
            }

            // Twitter API認証処理
            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

            switch (mode) {
            case 1: // タイムライン取得
                FileWriter file = new FileWriter(outputPath, false);
                printWriter = new PrintWriter(new BufferedWriter(file));

                System.out.println("\nタイムライン取得中。。。");
                for (Long userId : userIdList) {
                    getTimelines(printWriter, twitter, userId);
                }

                System.out.println("\nファイルを出力しました。\nパス：" + outputPath + "\n");
                printWriter.close();
                break;
            case 2: // ユーザーID取得
                getUserId(twitter, displayName);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "*****IOException*****\n" + e.getMessage());
        }
    }

    /**
     * ユーザーのタイムラインを取得
     * 
     * @param printWriter ファイル出力用の変数
     * @param twitter     接続済みのTwitterオブジェクト
     * @param userId      TwitterのユーザーID
     */
    public static void getTimelines(PrintWriter printWriter, Twitter twitter, Long userId) {
        try {
            ResponseList<Status> list = twitter.getUserTimeline(userId);
            printWriter.println(asterisk);
            printWriter.println("*");
            String name = list.get(0).getUser().getName().toString();
            String displayName = list.get(0).getUser().getScreenName().toString();
            printWriter.println("*\tname : " + name);
            printWriter.println("*\tlink : https://twitter.com/" + displayName);
            printWriter.println("*");
            printWriter.println(asterisk + "\n");

            int showCount = 5;
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss");
            for (Status status : list) {
                printWriter.println(df.format(status.getCreatedAt()));
                printWriter.println(status.getText());
                printWriter.println("\n" + "----------------------------------------------------" + "\n");
                if (showCount-- == 1) {
                    break;
                }
            }
        } catch (TwitterException e) {
            logger.log(Level.SEVERE, "*****TwitterException*****\n" + e.getMessage());
        }
    }

    /**
     * ユーザーIDを取得
     * 
     * @param twitter     接続済みのTwitterオブジェクト
     * @param displayName Twitter画面表示名（@以降の値）
     */
    public static void getUserId(Twitter twitter, String displayName) {
        try {
            System.out.println("\nユーザーIDを出力します。\n");
            ResponseList<Status> list = twitter.getUserTimeline(displayName);
            System.out.println(asterisk);
            System.out.println("*");
            String name = list.get(0).getUser().getName().toString();
            String userId = String.valueOf(list.get(0).getUser().getId());
            System.out.println("*\tname : " + name);
            System.out.println("*\ti  d : " + userId);
            System.out.println("*");
            System.out.println(asterisk + "\n");
        } catch (TwitterException e) {
            logger.log(Level.SEVERE, "*****TwitterException*****\n" + e.getMessage());
        }
    }
}