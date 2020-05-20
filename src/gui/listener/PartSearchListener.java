package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import gui.panel.PartSearchPanel;
import service.BasicSearchService;
import service.PartSearchService;

public class PartSearchListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		PartSearchPanel instance = PartSearchPanel.instance;
		String input = instance.input.getText();
		System.out.println(input);
		System.out.println(input);
		try {
			instance.itm.is = new PartSearchService().getAllInfoByKeywords(input);
			instance.input.setText("");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		instance.t.updateUI();
	}

}
