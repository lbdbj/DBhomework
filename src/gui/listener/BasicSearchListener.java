package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entity.ArticleInfo;
import gui.panel.BasicSearchPanel;
import service.BasicSearchService;

public class BasicSearchListener implements ActionListener {
	public StringBuilder sb = new StringBuilder();
	public List<ArticleInfo>list = new ArrayList<ArticleInfo>();
	@Override
	public void actionPerformed(ActionEvent e) {
//		��ȡ����ʼ��ʱ��
		long startTime =  System.currentTimeMillis();
//		��û������������ʵ��
		BasicSearchPanel instance = BasicSearchPanel.instance;
//		��ȡ������
		String option = (String) instance.cb.getSelectedItem();
//		��ȡ�ı�������������Ϣ
		String input = instance.input.getText();
//		���δ������Ϣ�͵������������ʾ
		if(input.length()==0) {
			JOptionPane.showMessageDialog(instance, "��������Ϣ���ѯ", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
//		���ѡ����������������������������ĺ���
		if(option.equals("������")) {
			try {
				instance.itm.is = new BasicSearchService().getAllInfoByAuthor(input);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			instance.t.updateUI();
			
		}else {
//			ѡ����Ǳ�������
			try {
				instance.itm.is = new BasicSearchService().getAllInfoByTitle(input);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			instance.t.updateUI();
		}
//		��ȡ���������ʱ��
		long endTime =  System.currentTimeMillis();
//		��ȡ��������ʱ��
		long usedTime = (endTime-startTime);
		System.out.println("������ʱΪ"+usedTime+"����");
	}
}
