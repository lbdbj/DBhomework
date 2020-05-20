package gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import gui.listener.BasicSearchListener;
import gui.listener.PartSearchListener;
import gui.model.InfTableModel;
import util.GUIUtil;
import util.TableViewRenderer;

public class PartSearchPanel extends JPanel {
	public static PartSearchPanel instance = new PartSearchPanel();
	
//	����������ť
	public JButton bSearch = new JButton("����");
	
//	������Ϣ�����
	public JTextArea input = new JTextArea();
	
	JLabel label = new JLabel("����");
//	������ʾ��Ϣ�ı��
	String columnNames[] = new String[] {"����","��Ŀ","������־","���","����ʱ��","ҳ��","����"};
	public InfTableModel itm = new InfTableModel();
	public JTable t = new JTable(itm);
	
	public PartSearchPanel () {
		t.setRowHeight(40);
		t.setDefaultRenderer(Object.class,new TableViewRenderer());
		this.setLayout(new BorderLayout());
		this.add(north(),BorderLayout.NORTH);
		this.add(center(),BorderLayout.CENTER);
		bSearch.addActionListener(new PartSearchListener());
	}
	
	//���ý�����ϰ벿��
	private JPanel north() {
		JPanel p = new JPanel();
		input.setPreferredSize(new Dimension(300,50));
		p.add(label);
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
		
	}
}
