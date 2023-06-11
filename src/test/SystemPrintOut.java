package test;

import static test.GetCorrectTime.getCorrectTime;

/**
 * 输出工具类，用于根据类型打印不同格式的信息。
 */
public class SystemPrintOut {

    /**
     * 打印信息到控制台。
     * @param printoutmessage 要输出的信息
     * @param type 类型，true表示成功，false表示错误
     */
    public static void systemPrintOut(String printoutmessage, boolean type) {
        String info = "[" + getCorrectTime() + "]";
        String success = "   Succeeded: ";
        String error = "   ERROR: ";

        if (type) {
            System.out.println(info + success + printoutmessage);
        } else {
            System.out.println(info + error + printoutmessage);
        }
    }
}
