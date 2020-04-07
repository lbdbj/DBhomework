package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.ArticleInfo;
import gui.panel.BasicSearchPanel;
import service.BasicSearchService;

public class BasicSearchListener implements ActionListener {
	public StringBuilder sb = new StringBuilder();
	public List<ArticleInfo>list = new ArrayList<ArticleInfo>();
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		BasicSearchPanel instance = BasicSearchPanel.instance;
		String option = (String) instance.cb.getSelectedItem();
		String input = instance.input.getText();
//		去除输入内容中的换行
		char[]arr = input.toCharArray();
		for(char c : arr) {
			if(c!='\r' && c!='\n')
				sb.append(c);
		}
		input = sb.toString();
		if(option.equals("作者名")) {
			try {
				instance.itm.is = new BasicSearchService().getAllInfoByAuthor(input);
				instance.t.updateUI();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else {
			try {
				instance.itm.is = new BasicSearchService().getAllInfoByTitle(input);
				instance.t.updateUI();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
