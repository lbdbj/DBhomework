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
//	��������һ�����԰������Ŵ���С�ľ������
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
//	�������ĺ���
	public void repaint() {
		if(c != null) {
//			��ȡ����ԭʼ�ߴ�
			Dimension containerSize = this.getSize();
			Dimension componentSize = c.getPreferredSize();
//			���㰴�������к����ĳߴ�
			if(strech)
			c.setSize((int)(containerSize.width*rate),(int)(containerSize.height*rate));
			else
				c.setSize(componentSize);
			c.setLocation(containerSize.width/2-c.getSize().width/2,containerSize.height/2
					-c.getSize().height/2);
		}
		super.repaint();
	}
//	��ʾһ���µĹ��ܽ���
	public void show(JComponent p) {
		this.c = p;
		Component []cs = getComponents();
		for(Component c: cs)
			remove(c);
		//��ԭ�����ȫ���Ƴ�
		add(p);
		//��Ҫ��ʾ�������ӽ�ȥ
		//���p��ָ�����;�ִ�и������ݵķ���
		this.updateUI();
	}
}
