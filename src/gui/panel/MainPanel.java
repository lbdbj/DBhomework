package gui.panel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import gui.listener.ToolbarListener;
import util.CenterPanel;
import util.GUIUtil;

public class MainPanel extends JPanel {
//	����ģʽ
	public static MainPanel instance = new MainPanel();
	
//	���ù�������������ʾ���ֹ���ѡ�ť
	public JToolBar tb = new JToolBar();
	
//	���ø��ֹ��ܰ�ť
	public JButton bBasicSearch = new JButton();
	public JButton bRelSearch = new JButton();
	public JButton bStatistics = new JButton();
	public JButton bImportFile = new JButton();
	public JButton bAnalysis = new JButton();
	public JButton bPartSearch = new JButton();
	
//	���ù������
	public CenterPanel workingPanel;
	
//	���������Ĺ��캯��
	private MainPanel() {
//		���ð�ť�ĸ�������
		GUIUtil.setImageIcon(bBasicSearch, "home.png", "��������");
		GUIUtil.setImageIcon(bRelSearch, "record.png", "�������");
		GUIUtil.setImageIcon(bStatistics, "category2.png", "����ͳ��");
		GUIUtil.setImageIcon(bImportFile, "report.png", "�����ļ�");
		GUIUtil.setImageIcon(bAnalysis, "config.png", "�ȵ����");
		GUIUtil.setImageIcon(bPartSearch, "backup.png", "����ƥ������");
		
//		����ť��ӵ���������
		tb.add(bBasicSearch);
		tb.add(bRelSearch);
		tb.add(bStatistics);
		tb.add(bImportFile);
		tb.add(bAnalysis);
		tb.add(bPartSearch);
		
//		���ù����������ƶ�
		tb.setFloatable(false);
		
//		���ò���
		workingPanel = new CenterPanel(0.98);
		setLayout(new BorderLayout());
		add(tb,BorderLayout.NORTH);
		add(workingPanel,BorderLayout.CENTER);
		
//		���ð�ť��������
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
