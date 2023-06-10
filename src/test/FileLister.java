package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileLister {
    /**
     * 获取文件夹内所有文件名的数组，排除了 macOS 系统的 .DS_Store 文件和 Windows 系统的隐藏文件和缩略图文件
     *
     * @param folderPath 文件夹路径
     * @return 包含文件夹内所有文件名的数组
     */
    public static String[] getFileNames(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            return new String[0]; // 如果文件夹不存在或不是文件夹，则返回空数组
        }

        List<String> fileNameList = new ArrayList<>();

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!isSystemFile(file)) {
                    fileNameList.add(file.getName());
                }
            }
        }

        return fileNameList.toArray(new String[0]);
    }

    /**
     * 检查文件是否是系统文件（.DS_Store 或 Windows 隐藏文件和缩略图文件）
     *
     * @param file 文件对象
     * @return 如果是系统文件，则返回 true，否则返回 false
     */
    private static boolean isSystemFile(File file) {
        String name = file.getName();

        // 排除 macOS 系统的 .DS_Store 文件
        if (name.equals(".DS_Store")) {
            return true;
        }

        // 排除 Windows 隐藏文件和缩略图文件
        if (name.startsWith("~$") || name.startsWith("Thumbs.db")) {
            return true;
        }

        return false;
    }
}
