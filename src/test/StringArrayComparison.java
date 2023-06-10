package test;

import java.util.*;
import static test.TwoDimensionalArrayConverter.convertToSingleArray;

public class StringArrayComparison {
    /**
     * 获取字符串数组2中存在但字符串数组1中不存在的元素
     * @param array1 字符串数组1
     * @param array2 字符串数组2
     * @return {array3∣array3 ∈ array2,且 array3 ∉ array1}
     */
    public static String[] getStringArrayDifference(String[] array1, String[] array2) {
        // 使用 HashSet 存储数组1中的元素
        HashSet<String> set1 = new HashSet<>(Arrays.asList(array1));

        // 使用 List 存储数组2中在数组1中不存在的元素
        List<String> diffList = new ArrayList<>();
        for (String element : array2) {
            if (!set1.contains(element)) {
                diffList.add(element);
            }
        }

        // 将 List 转换为数组并返回
        return diffList.toArray(new String[0]);
    }

    /**
     * 获取两个字符串数组中相同的部分
     * @param array1 字符串数组1
     * @param array2 字符串数组2
     * @return array1 ∩ array2
     */
    public static String[] getStringArrayIntersection(String[] array1, String[] array2) {
        // 使用 Set 存储数组 1 中的元素
        Set<String> set = new HashSet<>(Arrays.asList(array1));

        // 使用 List 存储相同的元素
        List<String> resultList = new ArrayList<>();
        for (String str : array2) {
            if (set.contains(str)) {
                resultList.add(str);
            }
        }

        // 将 List 转换为数组
        String[] resultArray = new String[resultList.size()];
        resultArray = resultList.toArray(resultArray);

        return resultArray;
    }
    public static void main(String[] args) {

        String folderPath = "/Users/bbk/Documents/test1";
        String[] fileNames = FileLister.getFileNames(folderPath);
        String[] array1 = FileNameProcessor.processFileNames(fileNames);

        String[][] dataArray = ReadCsvFile.csvToArray("/Users/bbk/Documents/test.csv");
        String[] array2 = convertToSingleArray(dataArray);
        //System.out.println(Arrays.toString(array2));
        String[] diffArray = StringArrayComparison.getStringArrayDifference(array1, array2);
        System.out.println(Arrays.toString(diffArray));
        // 输出: [d, e]


        String[] result = getStringArrayIntersection(array1, array2);

        System.out.print("相同部分：");
        for (String str : result) {
            System.out.print(str + " ");
        }
    }
}
