package test;

import java.util.ArrayList;
import java.util.List;

public class TwoDimensionalArrayConverter {
    /**
     * 将二维字符串数组转换为一维字符串数组
     * 注意！这是一个暴力转换方式！会破坏二维数组原有结构！谨慎使用！
     * @param array 二维字符串数组
     * @return 转换后的一维字符串数组
     */
    public static String[] convertToSingleArray(String[][] array) {
        List<String> resultList = new ArrayList<>(array.length * array[0].length);

        for (String[] row : array) {
            for (String element : row) {

                //去除所有的null元素和空元素
                if (element != null && !element.isEmpty()) {
                    resultList.add(element);
                }
            }
        }

        return resultList.toArray(new String[0]);  // 将列表转换为一维数组并返回
    }
}
