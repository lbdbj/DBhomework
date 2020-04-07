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
	public static void setImageIcon(JButton b, String fileName, String tip) {
        ImageIcon i = new ImageIcon(new File(imageFolder, fileName).getAbsolutePath());
        b.setIcon(i);
        //设置按钮的图标，默认居中
        b.setPreferredSize(new Dimension(100, 100));
        b.setToolTipText(tip);
        //设置鼠标停留时的显示信息
        b.setVerticalTextPosition(JButton.BOTTOM);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setText(tip);
    }
 
	public static void setColor(Color color,JComponent...cs) {
		for(JComponent c : cs) {
			c.setForeground(color);
		}
	}
public static void showPanel(JPanel p, double stretchRate) {
	JFrame f = new JFrame();
	f.setSize(600,600);
	f.setLocationRelativeTo(null);
	CenterPanel cp = new CenterPanel(stretchRate);
	f.setContentPane(cp);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
    cp.show(p);
}
public static void showPanel(JPanel p) {
	showPanel(p,0.85);
}
public static boolean checkEmpty(JTextField tf,String input) {
	String text = tf.getText().trim();
	if(text.length() == 0) {
		JOptionPane.showMessageDialog(null, "输入不能为空");
		return false;
	}
	return true;
}
public static boolean checkNumber(JTextField tf,String input) {
	if(!checkEmpty(tf,input))
		return false;
	else {
		String text = tf.getText().trim();
		try {
			Integer.parseInt(text);
			return true;
		}catch(NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "输入必须为整数");
			 tf.grabFocus();
	         return false;
		}
	}
}
public static boolean checkZero(JTextField tf,String input) {
	if(!checkNumber(tf,input))
		return false;
	else {
		String text = tf.getText().trim();
			if(0 == Integer.parseInt(text)) {
			JOptionPane.showMessageDialog(null, "输入必须为整数");
			 tf.grabFocus();
			 return false;
			}
	         return true;
	}
}
public static int getInt(JTextField tf) {
	return Integer.parseInt(tf.getText());
}
}
