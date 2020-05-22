package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import gui.panel.AnalysisPanel;
import service.AnalysisService;

public class AnalysisListener implements ActionListener{
	public StringBuilder sb = new StringBuilder();
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		AnalysisPanel instance = AnalysisPanel.instance;
		//String year = Integer.toString(instance.YearItem.getSelectedIndex());
		int pos;
		try {
			pos = instance.YearItem.getSelectedIndex();
			instance.atm.as=Arrays.asList(AnalysisService.getWordsByPos(pos));
			instance.t.updateUI();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		
	}
	
}
