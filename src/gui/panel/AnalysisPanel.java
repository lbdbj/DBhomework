package gui.panel;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gui.listener.AnalysisListener;
import gui.model.AnalyTableModel;
import service.AnalysisService;
import util.GUIUtil;
import util.TableViewRenderer;

public class AnalysisPanel extends JPanel{
	public static AnalysisPanel instance = new AnalysisPanel();
//	����������
	public String items[] = null;
	public JComboBox<String> YearItem = null;
	
//	����������ť
	public JButton aSearch = new JButton("��ȡǰʮ����");
	
//	���ñ����ʾͳ����Ϣ
	//String columnNames[] = new String[] {"�ؼ���","���ִ���"};
	public AnalyTableModel atm = new AnalyTableModel();
	public JTable t = new JTable(atm);
	
	public AnalysisPanel() {
		// TODO Auto-generated constructor stub
		t.setRowHeight(40);
		t.setDefaultRenderer(Object.class,new TableViewRenderer());
		this.setLayout(new BorderLayout());
		this.add(north(),BorderLayout.NORTH);
		this.add(center(),BorderLayout.CENTER);
		aSearch.addActionListener(new AnalysisListener());
	}
	
	private JPanel north(){
		try {
			items = AnalysisService.getYearItems();
			YearItem = new JComboBox<String>(items);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		JPanel p = new JPanel();
		p.add(new JLabel("ѡ����ݣ�"));
		p.add(YearItem);
		p.add(new JLabel("      "));
		p.add(aSearch);
		return p;
		
	}
	
	private JScrollPane center() {
		JScrollPane p = new JScrollPane(t);
		return p;
	}
	public static void main(String[] args) {
		GUIUtil.showPanel(AnalysisPanel.instance,1);
	}
}
