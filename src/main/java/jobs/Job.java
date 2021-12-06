package jobs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
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
    final static Logger logger = Logger.getLogger("TweetLogging");
    final static String asterisk = "****************************************************";

    /**
     * Twitter接続処理
     * 
     * @param mode        timeline：タイムラインを取得、id：ユーザーIDを取得
     * @param displayName ID検索用のTwitter画面表示名（@以降の値）
     */
    public static void connection(String mode, String displayName) {
        System.out.println("処理開始");

        String consumerKey = "";
        String consumerSecret = "";
        String accessToken = "";
        String accessTokenSecret = "";
        String outputPath = "";
        int userIdCount = 0;
        List<Long> userIdList = new ArrayList<Long>();
        PrintWriter printWriter = null;

        try {
            // 設定値を取得
            ResourceBundle rb = ResourceBundle.getBundle("application");
            consumerKey = rb.getString("consumerKey");
            consumerSecret = rb.getString("consumerSecret");
            accessToken = rb.getString("accessToken");
            accessTokenSecret = rb.getString("accessTokenSecret");
            userIdCount = Integer.parseInt(rb.getString("userIdCount"));
            outputPath = rb.getString("outputPath");
            for (int i = 1; i <= userIdCount; i++) {
                userIdList.add(Long.parseLong(rb.getString("id_" + i)));
            }

            // 出力ファイル
            FileWriter file = new FileWriter(outputPath, false);
            printWriter = new PrintWriter(new BufferedWriter(file));

            // Twitter API認証処理
            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

            switch (mode) {
            case "timeline":
                for (Long userId : userIdList) {
                    getTimelines(printWriter, twitter, userId);
                }
                logger.log(Level.INFO, "ファイルを出力しました。\nパス：" + outputPath);
                return;
            case "id":
                getUserId(printWriter, twitter, displayName);
                return;
            default:
                logger.log(Level.INFO, "指定のモードは存在しません。\n【モード】\n①timeline：タイムラインを取得\n②id：ユーザーIDを取得");
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "*****IOException*****");
            logger.log(Level.SEVERE, e.getMessage());
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "*****NumberFormatException*****");
            logger.log(Level.SEVERE, e.getMessage());
        } catch (MissingResourceException e) {
            logger.log(Level.SEVERE, "*****MissingResourceException*****");
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            printWriter.close();
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
            String name = list.size() > 0 ? list.get(0).getUser().getName().toString() : "データなし";
            String displayName = list.size() > 0 ? list.get(0).getUser().getScreenName().toString() : "データなし";
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
            logger.log(Level.SEVERE, "*****TwitterException*****");
            logger.log(Level.SEVERE, e.getMessage());
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "*****NullPointerException*****");
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * ユーザーIDを取得
     * 
     * @param printWriter ファイル出力用の変数
     * @param twitter     接続済みのTwitterオブジェクト
     * @param displayName Twitter画面表示名（@以降の値）
     */
    public static void getUserId(PrintWriter printWriter, Twitter twitter, String displayName) {
        try {
            ResponseList<Status> list = twitter.getUserTimeline(displayName);
            logger.log(Level.INFO, asterisk);
            logger.log(Level.INFO, "*");
            String name = list.size() > 0 ? list.get(0).getUser().getName().toString() : "データなし";
            String userId = list.size() > 0 ? String.valueOf(list.get(0).getUser().getId()) : "データなし";
            logger.log(Level.INFO, "*\tname : " + name);
            logger.log(Level.INFO, "*\ti  d : " + userId);
            logger.log(Level.INFO, "*");
            logger.log(Level.INFO, asterisk + "\n");
        } catch (TwitterException e) {
            logger.log(Level.SEVERE, "*****TwitterException*****");
            logger.log(Level.SEVERE, e.getMessage());
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "*****NullPointerException*****");
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

}