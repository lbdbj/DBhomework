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
//		获取程序开始的时间
		long startTime =  System.currentTimeMillis();
//		获得基本搜索界面的实例
		BasicSearchPanel instance = BasicSearchPanel.instance;
//		获取下拉框
		String option = (String) instance.cb.getSelectedItem();
//		获取文本输入框输入的信息
		String input = instance.input.getText();
//		如果未输入信息就点击搜索给出提示
		if(input.length()==0) {
			JOptionPane.showMessageDialog(instance, "请输入信息后查询", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
//		如果选择的是作者搜索，调用作者搜索的函数
		if(option.equals("作者名")) {
			try {
				instance.itm.is = new BasicSearchService().getAllInfoByAuthor(input);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			instance.t.updateUI();
			
		}else {
//			选择的是标题搜索
			try {
				instance.itm.is = new BasicSearchService().getAllInfoByTitle(input);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			instance.t.updateUI();
		}
//		获取程序结束的时间
		long endTime =  System.currentTimeMillis();
//		获取搜索所用时间
		long usedTime = (endTime-startTime);
		System.out.println("搜索用时为"+usedTime+"毫秒");
	}
}
