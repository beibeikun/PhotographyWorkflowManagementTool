package test;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static test.FileChecker.findFileNamesByKeyword;
import static test.FileChecker.isSystemOrHiddenFile;
import static test.SystemPrintOut.systemPrintOut;

public class FileUtil {
    /**
     * 将源文件或文件夹拷贝或剪切到目标位置。
     * 如果源路径为文件，且目标路径为文件夹，则将源文件拷贝或剪切到目标文件夹中。
     * 如果源路径和目标路径都为文件夹，则将源文件夹内的所有内容（排除特定文件）拷贝或剪切到目标文件夹中。
     * 如果目标路径为文件，则弹出错误警告框。
     *
     * @param sourcePath            源文件或文件夹的路径
     * @param targetPath            目标位置的路径
     * @param cut                   是否剪切，true表示剪切，false表示复制
     * @param optionPaneOrSystemOut 输出方式，true表示使用JOptionPane，false表示使用systemPrintOut
     */
    public static void copyOrCutFiles(String sourcePath, String targetPath, boolean cut, boolean optionPaneOrSystemOut) {
        File source = new File(sourcePath);
        File target = new File(targetPath);

        if (!source.exists() || !target.exists()) {
            if (optionPaneOrSystemOut) {
                JOptionPane.showMessageDialog(null,"路径1或路径2不存在","文件夹操作错误",JOptionPane.WARNING_MESSAGE);
            } else {
                systemPrintOut("路径1或路径2不存在",false);
            }
            return;
        }

        if (source.isFile() && target.isDirectory()) {
            // 源为文件，目标为文件夹，拷贝或剪切源文件到目标文件夹
            try {
                File destination = new File(target, source.getName());
                if (cut) {
                    Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    if (optionPaneOrSystemOut) {
                        JOptionPane.showMessageDialog(null,"文件已成功剪切至"+targetPath);
                    } else {
                        systemPrintOut("文件剪切至"+targetPath,true);
                    }
                } else {
                    Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    if (optionPaneOrSystemOut) {
                        JOptionPane.showMessageDialog(null,"文件已成功复制至"+targetPath);
                    } else {
                        systemPrintOut("文件复制至"+targetPath,true);
                    }
                }
            } catch (IOException e) {
                if (optionPaneOrSystemOut) {
                    JOptionPane.showMessageDialog(null,"文件操作失败: " + e.getMessage(),"文件夹操作错误",JOptionPane.WARNING_MESSAGE);
                } else {
                    systemPrintOut("文件操作失败: " + e.getMessage(),false);
                }
            }
        }
        else if (source.isDirectory() && target.isDirectory()) {
            // 源和目标均为文件夹，拷贝或剪切源文件夹内的内容到目标文件夹
            File[] files = source.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (isSystemOrHiddenFile(file)) {
                        continue; // 排除特定文件
                    }
                    try {
                        File destination = new File(target, file.getName());
                        if (cut) {
                            Files.move(file.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } else {
                            Files.copy(file.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        if (optionPaneOrSystemOut) {
                            JOptionPane.showMessageDialog(null,"文件操作失败: " + e.getMessage(),"文件夹操作错误",JOptionPane.WARNING_MESSAGE);
                        } else {
                            systemPrintOut("文件操作失败: " + e.getMessage(),false);
                        }
                    }
                }
                if (cut) {
                    if (optionPaneOrSystemOut) {
                        JOptionPane.showMessageDialog(null,"文件夹内容已成功剪切至"+targetPath);
                    } else {
                        systemPrintOut("文件夹内容剪切至"+targetPath,true);
                    }
                } else {
                    if (optionPaneOrSystemOut) {
                        JOptionPane.showMessageDialog(null,"文件夹内容已成功复制至"+targetPath);
                    } else {
                        systemPrintOut("文件夹内容复制至"+targetPath,true);
                    }
                }
            }
        }
        else if (target.isFile()) {
            if (optionPaneOrSystemOut) {
                JOptionPane.showMessageDialog(null,"错误：路径2为文件","路径错误",JOptionPane.WARNING_MESSAGE);
            } else {
                systemPrintOut("路径2为文件",false);
            }
        }
        else {
            if (optionPaneOrSystemOut) {
                JOptionPane.showMessageDialog(null,"无效的操作","错误",JOptionPane.WARNING_MESSAGE);
            } else {
                systemPrintOut("无效的操作",false);
            }
        }
    }

    public static void MoveOrganizedFiles(String sourcePath, String targetPath) {
        String[] targetFileNames = findFileNamesByKeyword(sourcePath,"JB");
        for (String targetFileName : targetFileNames) {
            copyOrCutFiles(sourcePath + File.separator + targetFileName, targetPath, true, false);
        }

    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        String sourcePath = "/Users/bbk/Documents/test";
        String targetPath = "/Users/bbk/Documents/test3";
        MoveOrganizedFiles(sourcePath,targetPath);
        boolean cut = false; // true表示剪切，false表示复制
        //copyOrCutFiles(sourcePath, targetPath, cut);
        //String[] testout = findFileNamesByKeyword(sourcePath,"jpg");
        //System.out.println(Arrays.toString(testout));
    }
}