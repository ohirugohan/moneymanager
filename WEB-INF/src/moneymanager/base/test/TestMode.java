package moneymanager.base.test;

/**
 * 現在がテスト実行中か制御するクラス
 *
 */
public class TestMode {

    private static boolean is_test = false;

    /**
     * 現在ユニットテスト中かどうか判定する
     * @return boolean
     */
    public static boolean isTest() {
        return TestMode.is_test;
    }

    /**
     * ユニットテストモードに移行する
     */
    public static void setTestMode() {
        TestMode.is_test = true;
    }
}
