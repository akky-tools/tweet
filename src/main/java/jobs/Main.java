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
        int mode = 0;
        int tmpMode = 0;
        int missingCount = 0;

        while (true) {
            tmpMode = args.length == 0 ? inputMode() : Integer.parseInt(args[0]);
            if (tmpMode != 1 && tmpMode != 2) {
                System.out.println("\n選択されたモード番号は存在しません。\n");
            } else {
                mode = tmpMode;
                break;
            }

            if (++missingCount == 5) {
                System.out.println("正しいモード番号が選択されないため、処理を終了します。\n");
                break;
            }
        }

        String displayName = "";
        if (mode == 2) {
            missingCount = 0;
            String tmpDisplayName = "";
            while (true) {
                tmpDisplayName = args.length == 0 ? inputDisplayName() : args[1];
                if (tmpDisplayName.length() == 0) {
                    System.out.println("\n画面表示名が入力されていません。\n");
                } else {
                    displayName = tmpDisplayName;
                    break;
                }

                if (++missingCount == 5) {
                    System.out.println("画面表示名が入力されないため、処理を終了します。\n");
                    break;
                }
            }
        }

        if (missingCount < 5) {
            Job.connection(mode, displayName);
        }

        try {
            scanner.close();
            System.out.println("処理を終了します。。。");
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