package startup;

import javax.swing.SwingUtilities;

import gui.frame.MainFrame;
import gui.panel.BasicSearchPanel;
import gui.panel.MainPanel;


public class BoostStrap {
	public static void main(String []args)throws Exception {
		SwingUtilities.invokeAndWait(new Runnable(){
			 public void run() {
//				 可以调整窗口大小
	                MainFrame.instance.setVisible(true);
//	                默认启动基本搜索界面
	                MainPanel.instance.workingPanel.show(BasicSearchPanel.instance);
	            }
		});
	
     }
}
