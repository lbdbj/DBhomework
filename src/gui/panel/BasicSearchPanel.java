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
import util.GUIUtil;
import util.TableViewRenderer;

public class BasicSearchPanel extends JPanel {
	public static BasicSearchPanel instance = new BasicSearchPanel();
	
//	设置下拉框
	String items[] = new String[] {"作者名","论文题目"};
	public JComboBox<String>cb = new JComboBox<String>(items);
	
//	设置搜索按钮
	public JButton bSearch = new JButton("搜索");
	
//	设置信息输入框
	public JTextArea input = new JTextArea();
	
//	设置显示信息的表格
	String columnNames[] = new String[] {"作者","题目","发表杂志","卷号","出版时间","页数","链接"};
	public InfTableModel itm = new InfTableModel();
	public JTable t = new JTable(itm);
	
	public BasicSearchPanel () {
		t.setRowHeight(40);
		t.setDefaultRenderer(Object.class,new TableViewRenderer());
		this.setLayout(new BorderLayout());
		this.add(north(),BorderLayout.NORTH);
		this.add(center(),BorderLayout.CENTER);
		bSearch.addActionListener(new BasicSearchListener());
	}
	
	//设置界面的上半部分
	private JPanel north() {
		JPanel p = new JPanel();
		input.setPreferredSize(new Dimension(300,50));
		p.add(cb);
		p.add(input);
		p.add(bSearch);
		return p;
	}
	
//	设置界面的下半部分
	private JScrollPane center() {
		JScrollPane s = new JScrollPane(t);
		return s;
	}
	public static void main(String[] args) {
		GUIUtil.showPanel(BasicSearchPanel.instance,1);
	}
}
