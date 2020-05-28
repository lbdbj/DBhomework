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
//		获取程序开始的时间
		long startTime =  System.currentTimeMillis();
//		获取部分匹配搜索界面的实例
		PartSearchPanel instance = PartSearchPanel.instance;
//		获取文本输入框输入的内容
		String input = instance.input.getText();
//		如果未输入信息就点击搜索给出提示
		if(input.length()==0) {
			JOptionPane.showMessageDialog(instance, "请输入信息后查询", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
//			获取所有搜索到的文章对象
			instance.itm.is = new PartSearchService().getAllInfoByKeywords(input);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		instance.t.updateUI();
//		获取程序结束的时间
		long endTime =  System.currentTimeMillis();
//		获取搜索所用时间
		long usedTime = (endTime-startTime);
		System.out.println("搜索用时为"+usedTime+"毫秒");
	}

}
