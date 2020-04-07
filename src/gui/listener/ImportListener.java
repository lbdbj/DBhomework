package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JFileChooser;

import gui.panel.ImportFilePanel;

public class ImportListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ImportFilePanel p = ImportFilePanel.instance; 
		 JFileChooser fc = new JFileChooser();
	        fc.setSelectedFile(new File(".xml"));
	        fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
				
				@Override
				public String getDescription() {
					// TODO Auto-generated method stub
					return ".xml";
				}
				
				@Override
				public boolean accept(File f) {
					// TODO Auto-generated method stub
					return true;
				}
			});
	        int returnVal =  fc.showOpenDialog(p);
	         File file = fc.getSelectedFile();
	         
//	         当选择好文件，点击确定后的响应事件
	         if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	 p.filePath.setText(file.getAbsolutePath());
	         }
	}

}
