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
	public void repaint() {
		if(c != null) {
			Dimension containerSize = this.getSize();
			Dimension componentSize = c.getPreferredSize();
			if(strech)
			c.setSize((int)(containerSize.width*rate),(int)(containerSize.height*rate));
			else
				c.setSize(componentSize);
			c.setLocation(containerSize.width/2-c.getSize().width/2,containerSize.height/2
					-c.getSize().height/2);
		}
		super.repaint();
	}
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
	public static void main(String []args) {
		JFrame f = new JFrame();
		f.setSize(200,200);
		f.setLocationRelativeTo(null);
		CenterPanel cp = new CenterPanel(0.85,true);
		f.setContentPane(cp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        JButton b  =new JButton("abc");
        cp.show(b);
		
	}

}
