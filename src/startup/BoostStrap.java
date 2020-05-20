package startup;

import javax.swing.SwingUtilities;

import gui.frame.MainFrame;
import gui.panel.BasicSearchPanel;
import gui.panel.MainPanel;


public class BoostStrap {
	public static void main(String []args)throws Exception {
		SwingUtilities.invokeAndWait(new Runnable(){
			 public void run() {
	                MainFrame.instance.setVisible(true);
	                MainPanel.instance.workingPanel.show(BasicSearchPanel.instance);
	            }
		});
	
     }
}
