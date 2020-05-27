package util;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIUtil {
	private static String imageFolder = "D:/hutubill_image/img";
//	��������ѡ������
	public static void setImageIcon(JButton b, String fileName, String tip) {
        ImageIcon i = new ImageIcon(new File(imageFolder, fileName).getAbsolutePath());
        b.setIcon(i);
        //���ð�ť��ͼ�꣬Ĭ�Ͼ���
        b.setPreferredSize(new Dimension(100, 100));
        b.setToolTipText(tip);
        //�������ͣ��ʱ����ʾ��Ϣ
        b.setVerticalTextPosition(JButton.BOTTOM);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setText(tip);
    }
// �����������ɫ
	public static void setColor(Color color,JComponent...cs) {
		for(JComponent c : cs) {
			c.setForeground(color);
		}
	}
//	������������ʾ���
public static void showPanel(JPanel p, double stretchRate) {
	JFrame f = new JFrame();
//	���ô��ڴ�С
	f.setSize(600,600);
	f.setLocationRelativeTo(null);
	CenterPanel cp = new CenterPanel(stretchRate);
	f.setContentPane(cp);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
    cp.show(p);
	}
}
