package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import gui.panel.AnalysisPanel;
import gui.panel.BasicSearchPanel;
import gui.panel.ImportFilePanel;
import gui.panel.MainPanel;
import gui.panel.RelSearchPanel;
import gui.panel.StatisticsPanel;
import sun.nio.cs.ext.MacArabic;

public class ToolbarListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		MainPanel p =  MainPanel.instance;
		
		JButton b = (JButton)e.getSource();
		
		if(b == p.bBasicSearch)
			p.workingPanel.show(BasicSearchPanel.instance);
		if(b == p.bRelSearch)
			p.workingPanel.show(RelSearchPanel.instance);
		if(b == p.bStatistics)
			p.workingPanel.show(StatisticsPanel.instance);
		if(b == p.bAnalysis)
			p.workingPanel.show(AnalysisPanel.instance);
		if(b == p.bImportFile)
			p.workingPanel.show(ImportFilePanel.instance);
	}

}
