package gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gui.listener.BasicSearchListener;
import gui.model.InfTableModel;
import jdk.internal.util.xml.impl.Input;
import util.GUIUtil;
import util.TableViewRenderer;

public class BasicSearchPanel extends JPanel {
	public static BasicSearchPanel instance = new BasicSearchPanel();
	
//	����������
	String items[] = new String[] {"������","������Ŀ"};
	public JComboBox<String>cb = new JComboBox<String>(items);
	
//	����������ť
	public JButton bSearch = new JButton("����");
	
//	������Ϣ�����
	public JTextArea input = new JTextArea();
	
//	������ʾ��Ϣ�ı��
	String columnNames[] = new String[] {"����","��Ŀ","������־","���","����ʱ��","ҳ��","����"};
	public InfTableModel itm = new InfTableModel();
	public JTable t = new JTable(itm);
	
	public BasicSearchPanel () {
//		�����Ϣʱ����Ϊ�Զ�����
		input.setLineWrap(true);
		t.setRowHeight(40);
		t.setDefaultRenderer(Object.class,new TableViewRenderer());
		this.setLayout(new BorderLayout());
		this.add(north(),BorderLayout.NORTH);
		this.add(center(),BorderLayout.CENTER);
		bSearch.addActionListener(new BasicSearchListener());
	}
	
	//���ý�����ϰ벿��
	private JPanel north() {
		JPanel p = new JPanel();
		input.setPreferredSize(new Dimension(300,50));
		p.add(cb);
		p.add(input);
		p.add(bSearch);
		return p;
	}
	
//	���ý�����°벿��
	private JScrollPane center() {
		JScrollPane s = new JScrollPane(t);
		return s;
	}
	public static void main(String[] args) {
		GUIUtil.showPanel(BasicSearchPanel.instance,1);
	}
}
