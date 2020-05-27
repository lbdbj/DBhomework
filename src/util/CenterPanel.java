package util;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class CenterPanel  extends JPanel {
	private double rate;
	private boolean strech;
	private JComponent c;
//	用来创建一个可以按比例放大缩小的居中面板
	public CenterPanel(double rate,boolean strech) {
		this.setLayout(null);
		this.rate = rate;
		this.strech = strech;
	}
	public CenterPanel(double rate) {
		this.setLayout(null);
		this.rate = rate;
		this.strech = true;
	}
//	绘制面板的函数
	public void repaint() {
		if(c != null) {
//			获取面板的原始尺寸
			Dimension containerSize = this.getSize();
			Dimension componentSize = c.getPreferredSize();
//			计算按比例居中后面板的尺寸
			if(strech)
			c.setSize((int)(containerSize.width*rate),(int)(containerSize.height*rate));
			else
				c.setSize(componentSize);
			c.setLocation(containerSize.width/2-c.getSize().width/2,containerSize.height/2
					-c.getSize().height/2);
		}
		super.repaint();
	}
//	显示一个新的功能界面
	public void show(JComponent p) {
		this.c = p;
		Component []cs = getComponents();
		for(Component c: cs)
			remove(c);
		//将原有组件全部移除
		add(p);
		//将要显示的组件添加进去
		//如果p是指定类型就执行更新数据的方法
		this.updateUI();
	}
}
