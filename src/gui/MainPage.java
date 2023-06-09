package gui;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class MainPage {
    private JPanel panel1;
    static JFrame frame = new JFrame("MainPage");
    private JTabbedPane tabbedPane1;
    private JButton 连接到数据库Button;

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        frame.setContentPane(new MainPage().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("IWMT");
    }
}
