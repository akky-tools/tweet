package jobs;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String asterisk = "**************************";

    /**
     * 
     * 任意のユーザーの以下情報をTwitterから取得<br>
     * ・タイムライン<br>
     * ・ユーザーID
     * 
     * @param args
     */
    public static void main(String[] args) {
        final Logger logger = Logger.getLogger("MainLogging");

        int mode, tmpMode = 0;
        while (true) {
            tmpMode = inputMode();
            if (tmpMode != 1 && tmpMode != 2) {
                System.out.println("\n選択されたモード番号は存在しません。\n");
            } else {
                mode = tmpMode;
                break;
            }
        }

        String displayName = "";
        if (mode == 2) {
            String tmpDisplayName = "";
            while (true) {
                tmpDisplayName = inputDisplayName();
                if (tmpDisplayName.length() == 0) {
                    System.out.println("\n画面表示名が入力されていません。\n");
                } else {
                    displayName = tmpDisplayName;
                    break;
                }
            }
        }

        Job.connection(mode, displayName);

        scanner.close();
        System.out.println("処理を終了します。。。");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "*****InterruptedException*****\n" + e.getMessage());
        }
    }

    /**
     * 入力されたモード番号を返却
     * 
     * @return モード番号<br>
     *         1：タイムライン取得<br>
     *         2：ユーザーID取得
     */
    public static int inputMode() {
        System.out.println("モード番号を入力してください");
        System.out.println(asterisk);
        System.out.println("1：タイムライン取得");
        System.out.println("2：ユーザーID取得");
        System.out.print("\nモード番号：");
        String mode = scanner.next();

        return Integer.parseInt(mode);
    }

    /**
     * 入力された画面表示名を返却
     * 
     * @return ユーザーID検索対象の画面表示名
     */
    public static String inputDisplayName() {
        System.out.println("\nユーザーID検索対象の\n画面表示名を入力してください。");
        System.out.println(asterisk);
        System.out.print("\n画面表示名：");
        String displayName = scanner.next();

        return displayName;
    }
}