package gui.frame;

import javax.swing.JFrame;

import gui.panel.MainPanel;

public class MainFrame extends JFrame {
	public static MainFrame instance = new MainFrame();
	private MainFrame() {
		this.setSize(600,600);
		this.setTitle("���׹���ϵͳ");
		this.setContentPane(MainPanel.instance);
		 this.setLocationRelativeTo(null);
	     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	  public static void main(String[] args) {
	        instance.setVisible(true);
	    }
}
