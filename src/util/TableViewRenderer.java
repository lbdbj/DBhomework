package util;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class TableViewRenderer extends JTextArea implements TableCellRenderer {
	
	public TableViewRenderer() {
		setLineWrap(true);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		setText(obj == null ? "" : obj.toString());
		 return this;
	}
}
