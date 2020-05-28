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
//	用来绘制选项侧边栏
	public static void setImageIcon(JButton b, String tip) {
        //设置按钮的图标，默认居中
        b.setPreferredSize(new Dimension(100, 100));
        b.setToolTipText(tip);
        //设置鼠标停留时的显示信息
        b.setVerticalTextPosition(JButton.BOTTOM);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setText(tip);
    }
// 设置组件的颜色
	public static void setColor(Color color,JComponent...cs) {
		for(JComponent c : cs) {
			c.setForeground(color);
		}
	}
//	用来按比例显示面板
public static void showPanel(JPanel p, double stretchRate) {
	JFrame f = new JFrame();
//	设置窗口大小
	f.setSize(600,600);
	f.setLocationRelativeTo(null);
	CenterPanel cp = new CenterPanel(stretchRate);
	f.setContentPane(cp);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
    cp.show(p);
	}
}
