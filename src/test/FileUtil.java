package test;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static test.FileChecker.*;
import static test.FileNameProcessor.processFileNames;
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
    /**
     * 将源文件夹中文件名带有关键字的文件移动至目标文件夹。
     *
     * @param sourcePath  源文件夹的路径
     * @param targetPath  目标文件夹的路径
     * @param keyword     关键字
     */
    public static void MoveOrganizedFiles(String sourcePath, String targetPath, String keyword) {
        //根据关键字提取所有符合条件的文件的文件名
        String[] targetFileNames = findFileNamesByKeyword(sourcePath,keyword);
        //使用一个数组循环完成移动操作
        for (String targetFileName : targetFileNames) {
            copyOrCutFiles(sourcePath + File.separator + targetFileName, targetPath, true, false);
        }

    }


    /**
     * 这是一个特殊方法，应该只有在整理已后期图片时有用。
     * 已后期图片的主图后缀为小写jpg，而未后期图片的主图后缀为大写JPG，根据这个来判断将已后期图片移动至目标文件夹。
     *
     * @param sourcePath  源文件夹的路径
     * @param targetPath  目标文件夹的路径
     */
    public static void MoveOrganizedFilesSpecial(String sourcePath, String targetPath) {
        //根据关键字提取所有符合条件的文件的文件名然后使用processFileNames来去除后缀
        String[] targetFileNames = processFileNames(findFileNamesByKeyword(sourcePath,"jpg"));//根据关键字提取所有符合条件的文件的文件名然后使用去除后缀
        for (String targetFileName : targetFileNames) {
            MoveOrganizedFiles(sourcePath,targetPath,targetFileName);
            System.out.println(targetFileName);
        }

    }

    public static void MoveOrganizedFilesSpecialListening(String sourcePath, String targetPath1, String targetPath2) {
        //根据关键字提取所有符合条件的文件的文件名然后使用processFileNames来去除后缀
        int filen = countFiles(sourcePath);
        if (filen == 0)
        {
            systemPrintOut("已完成任务，终止程序",true);
            System.exit(0);
        } else {
            String[] targetFileNames = findFileNamesByKeyword(sourcePath,"jpg");//根据关键字提取所有符合条件的文件的文件名然后使用去除后缀
            String[] targetFileName1 = processFileNames(findFileNamesByKeyword(sourcePath,"jpg"));
            if (targetFileNames.length==2)
            {
                systemPrintOut(targetFileName1[0]+"已经后期完成",true);
                File file = new File(sourcePath+ File.separator +targetFileName1[0]+" (6).JPG");
                if (file.exists()) {
                    MoveOrganizedFiles(sourcePath,targetPath2,targetFileName1[0]+" (6).JPG");
                }
                for (String targetFileName : targetFileName1) {
                    MoveOrganizedFiles(sourcePath,targetPath1,targetFileName);
                }
                int x = countFiles(targetPath1)/6;
                int y1 = countFiles(sourcePath)/6;
                int y2 = countFiles(sourcePath)/5;
                systemPrintOut("当前已完成 "+x+" 件",true);
                systemPrintOut("当前还有约 "+y1+"~"+y2+" 件未完成",true);
            }
        }
        //System.out.println(Arrays.toString(targetFileNames));
/*
        for (String targetFileName : targetFileNames) {
            MoveOrganizedFiles(sourcePath,targetPath,targetFileName);
            System.out.println(targetFileName);
        }

 */

    }

    public static void main(String[] args) throws InterruptedException {
        FlatDarkLaf.setup();
        String sourcePath = "/Users/bbk/Documents/S831_202306/camera/待修/所有待修";
        String targetPath1 = "/Users/bbk/Documents/S831_202306/已修";
        String targetPath2 = "/Users/bbk/Documents/S831_202306/camera/zhou";
        //int fileCount = countFiles(targetPath1);
        //System.out.println("文件夹及其子文件夹中的文件数量为: " + fileCount/6);

        systemPrintOut("开始任务，每十秒秒巡查一次",true);
        while (true) {
            systemPrintOut("执行巡查",true);
            MoveOrganizedFilesSpecialListening(sourcePath,targetPath1,targetPath2);
            Thread.sleep(10000);
            // 循环体代码
        }




        //boolean cut = false; // true表示剪切，false表示复制
        //copyOrCutFiles(sourcePath, targetPath, cut);
        //String[] testout = findFileNamesByKeyword(sourcePath,"jpg");
        //System.out.println(Arrays.toString(testout));
    }
}