package startup;

import javax.swing.SwingUtilities;

import gui.frame.MainFrame;
import gui.panel.BasicSearchPanel;
import gui.panel.MainPanel;


public class BoostStrap {
	public static void main(String []args)throws Exception {
		SwingUtilities.invokeAndWait(new Runnable(){
			 public void run() {
//				 ���Ե������ڴ�С
	                MainFrame.instance.setVisible(true);
//	                Ĭ������������������
	                MainPanel.instance.workingPanel.show(BasicSearchPanel.instance);
	            }
		});
	
     }
}
