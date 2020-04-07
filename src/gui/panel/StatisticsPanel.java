package gui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gui.model.StaTableModel;
import util.GUIUtil;
import util.TableViewRenderer;

public class StatisticsPanel extends JPanel {
	public static StatisticsPanel instance = new StatisticsPanel();
	
//	设置表格，显示统计信息
	String columnNames[] = new String[] {"作者名","论文数量"};
	public StaTableModel stm = new StaTableModel();
	public JTable t = new JTable(stm);
	
	public StatisticsPanel () {
		t.setRowHeight(40);
		t.setDefaultRenderer(Object.class,new TableViewRenderer());
		JScrollPane p = new JScrollPane(t);
		this.setLayout(new BorderLayout());
		this.add(p,BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		GUIUtil.showPanel(StatisticsPanel.instance,1);
	}
}
