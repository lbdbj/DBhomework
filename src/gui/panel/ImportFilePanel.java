package gui.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import gui.listener.ImportListener;
import util.AllStatic;
import util.GUIUtil;
import util.IndexFileUtil;
import util.ParseUtil;

public class ImportFilePanel extends JPanel {
	public static ImportFilePanel instance = new ImportFilePanel();
	
	public JTextField filePath = new JTextField();
	
	public JButton imp = new JButton("�����ļ�");
	public JButton determine = new JButton("ȷ��");
	
	public String pathName;
	public ImportFilePanel() {
		JPanel p1 = new JPanel();
		p1.setBounds(50, 50, 300, 60);
		filePath.setPreferredSize(new Dimension(350,20));
		determine.setPreferredSize(new Dimension(60,30));
		p1.add(filePath);
		p1.add(imp);
		p1.add(determine);
		
		
		this.add(p1);
		
//		Ϊ��ť��Ӽ�����
		imp.addActionListener(new ImportListener());
		determine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pathName = filePath.getText().toString();
				if(pathName.equals("")) {
					JOptionPane.showMessageDialog(ImportFilePanel.instance, "��ѡ���ļ�");
					return;
				}
				else if(!pathName.endsWith(".xml")) {
					JOptionPane.showMessageDialog(ImportFilePanel.instance, "ֻ����xml�ļ�");
					return;
				}
				else {
					try {
						JOptionPane.showMessageDialog(ImportFilePanel.instance, "�����ļ�������Ҫ�����ӣ������ĵȴ�");
						AllStatic.setFileFlag = 1;
						ParseUtil.myParse(pathName);
						AllStatic.setFileFlag = 2;
						ParseUtil.myParse(pathName);
						IndexFileUtil instance = new IndexFileUtil();
						instance.setFile1(8388608, "titleIndex1.txt");
						instance.setFile2(8388608, "titleIndex2.txt");
						AllStatic.map.clear();
						AllStatic.setFileFlag = 3;
						ParseUtil.myParse(pathName);
						instance.setFile1(4194304, "authorIndex1.txt");
						instance.setFile2(4194304, "authorIndex2.txt");
						AllStatic.map.clear();
						AllStatic.setFileFlag = 4;
						ParseUtil.myParse(pathName);
						instance.setFile1(2097152,"partindex1.txt");
						instance.setFile2(2097152,"partindex2.txt");
						AllStatic.map.clear();
						JOptionPane.showMessageDialog(ImportFilePanel.instance, "�ļ��������");
						
					} catch (ParserConfigurationException | SAXException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	public static void main(String[] args) {
		GUIUtil.showPanel(ImportFilePanel.instance,1);
	}
}
