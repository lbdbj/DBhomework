package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import gui.panel.PartSearchPanel;
import service.BasicSearchService;
import service.PartSearchService;

public class PartSearchListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
//		��ȡ����ƥ�����������ʵ��
		PartSearchPanel instance = PartSearchPanel.instance;
//		��ȡ�ı���������������
		String input = instance.input.getText();
//		���δ������Ϣ�͵������������ʾ
		if(input.length()==0) {
			JOptionPane.showMessageDialog(instance, "��������Ϣ���ѯ", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
//			��ȡ���������������¶���
			instance.itm.is = new PartSearchService().getAllInfoByKeywords(input);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		instance.t.updateUI();
	}

}
