package jobs;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class MainTest {
    final static Logger logger = Logger.getLogger("MainTestLogging");

    /**
     * モード1 正常系
     */
    @Test
    public void test_main_mode1_ok() {
        try {
            logger.log(Level.INFO, "test_main_mode1_ok　開始");
            String[] args = { "1" };
            Main.main(args);
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_main_mode1_ok　終了\n");
        }
    }

    /**
     * モード2 正常系
     */
    @Test
    public void test_main_mode2_ok() {
        try {
            logger.log(Level.INFO, "test_main_mode2_ok　開始");
            String[] args = { "2", "akky_work" };
            Main.main(args);
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_main_mode2_ok　終了\n");
        }
    }

    /**
     * モード2 画面表示名の入力値が空
     */
    @Test
    public void test_main_mode2_no_data() {
        try {
            logger.log(Level.INFO, "test_main_mode2_no_data　開始");
            String[] args = { "2", "" };
            Main.main(args);
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_main_mode2_no_data　終了\n");
        }
    }

    /**
     * モード不明
     */
    @Test
    public void test_main_mode_missing() {
        try {
            logger.log(Level.INFO, "test_main_mode_missing　開始");
            String[] args = { "0" };
            Main.main(args);
        } catch (Exception e) {
            fail();
        } finally {
            logger.log(Level.INFO, "test_main_mode_missing　終了\n");
        }
    }

    /**
     * モード番号入力 正常系
     */
    @Test
    public void test_main_inputMode_ok() {
        try {
            logger.log(Level.INFO, "test_main_inputMode_ok　開始");
            Main.inputMode();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "モード番号入力エラー発生!!\nエラーメッセージ：" + e.getMessage());
        } finally {
            logger.log(Level.INFO, "test_main_inputMode_ok　終了\n");
        }
    }

    /**
     * 画面表示名入力 正常系
     */
    @Test
    public void test_main_inputDisplayName_ok() {
        try {
            logger.log(Level.INFO, "test_main_inputDisplayName_ok　開始");
            Main.inputDisplayName();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "画面表示名入力エラー発生!!\nエラーメッセージ" + e.getMessage());
        } finally {
            logger.log(Level.INFO, "test_main_inputDisplayName_ok　終了\n");
        }
    }
}