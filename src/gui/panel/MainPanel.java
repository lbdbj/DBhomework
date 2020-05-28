package gui.panel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import gui.listener.ToolbarListener;
import util.CenterPanel;
import util.GUIUtil;

public class MainPanel extends JPanel {
//	单例模式
	public static MainPanel instance = new MainPanel();
	
//	设置工具栏，用来显示各种功能选项按钮
	public JToolBar tb = new JToolBar();
	
//	设置各种功能按钮
	public JButton bBasicSearch = new JButton();
	public JButton bRelSearch = new JButton();
	public JButton bStatistics = new JButton();
	public JButton bImportFile = new JButton();
	public JButton bAnalysis = new JButton();
	public JButton bPartSearch = new JButton();
	
//	设置工作面板
	public CenterPanel workingPanel;
	
//	设置主面板的构造函数
	private MainPanel() {
//		设置按钮的各种属性
		GUIUtil.setImageIcon(bBasicSearch, "基本搜索");
		GUIUtil.setImageIcon(bRelSearch, "相关搜索");
		GUIUtil.setImageIcon(bStatistics, "作者统计");
		GUIUtil.setImageIcon(bImportFile, "导入文件");
		GUIUtil.setImageIcon(bAnalysis, "热点分析");
		GUIUtil.setImageIcon(bPartSearch, "部分匹配搜索");
		
//		将按钮添加到工具栏中
		tb.add(bBasicSearch);
		tb.add(bRelSearch);
		tb.add(bStatistics);
		tb.add(bImportFile);
		tb.add(bAnalysis);
		tb.add(bPartSearch);
		
//		设置工具栏不可移动
		tb.setFloatable(false);
		
//		设置布局
		workingPanel = new CenterPanel(0.98);
		setLayout(new BorderLayout());
		add(tb,BorderLayout.NORTH);
		add(workingPanel,BorderLayout.CENTER);
		
//		调用按钮监听函数
		addListener();
		
	}

private void addListener() {
	// TODO Auto-generated method stub
	ToolbarListener listener = new ToolbarListener();
	bAnalysis.addActionListener(listener);
	bBasicSearch.addActionListener(listener);
	bImportFile.addActionListener(listener);
	bRelSearch.addActionListener(listener);
	bStatistics.addActionListener(listener);
	bPartSearch.addActionListener(listener);
}
	public static void main(String[] args) {
		GUIUtil.showPanel(MainPanel.instance, 1);
	}
	
	
}
