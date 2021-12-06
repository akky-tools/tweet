package jobs;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    /**
     * 
     * Twitterより任意のユーザーの以下情報を取得<br>
     * タイムライン<br>
     * ユーザーID
     * 
     * @param args <br>
     *             第一引数 timeline：タイムラインを取得、id：ユーザーIDを取得<br>
     *             第二引数 ID検索対象のユーザーの画面表示名
     */
    public static void main(String[] args) {
        final Logger logger = Logger.getLogger("TweetLogging");
        try {
            String mode = args[0];
            String displayName = "";
            Job.connection(mode, displayName);
            System.out.println("処理を終了します。。。");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "*****InterruptedException*****");
            logger.log(Level.SEVERE, e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.log(Level.SEVERE, "*****ArrayIndexOutOfBoundsException*****");
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, "コマンドライン引数が誤っています。");
        }
    }
}