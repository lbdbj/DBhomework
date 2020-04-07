package gui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gui.model.AnalyTableModel;
import util.GUIUtil;
import util.TableViewRenderer;

public class AnalysisPanel extends JPanel {
public static AnalysisPanel instance = new AnalysisPanel();
	
//	设置表格，显示统计信息
	String columnNames[] = new String[] {"关键词","出现次数"};
	public AnalyTableModel atm = new AnalyTableModel();
	public JTable t = new JTable(atm);
	
	public AnalysisPanel() {
		// TODO Auto-generated constructor stub
		t.setRowHeight(40);
		t.setDefaultRenderer(Object.class,new TableViewRenderer());
		JScrollPane p = new JScrollPane(t);
		this.setLayout(new BorderLayout());
		this.add(p,BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		GUIUtil.showPanel(AnalysisPanel.instance,1);
	}
}
