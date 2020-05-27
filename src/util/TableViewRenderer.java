package util;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

//这个类的作用是使表格中的单元框可以伸缩
public class TableViewRenderer extends JTextArea implements TableCellRenderer {
	
	public TableViewRenderer() {
		setLineWrap(true);
		setWrapStyleWord(true);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
			int row, int column) {
		 int maxPreferredHeight = 0;
	        for (int i = 0; i < table.getColumnCount(); i++) {
	            setText("" + table.getValueAt(row, i));
	            setSize(table.getColumnModel().getColumn(column).getWidth(), 0);
	            maxPreferredHeight = Math.max(maxPreferredHeight,
	                    getPreferredSize().height);
	        }

	        if (table.getRowHeight(row) != maxPreferredHeight) // 少了这行则处理器瞎忙
	            table.setRowHeight(row, maxPreferredHeight);

	        setText(obj == null ? "" : obj.toString());
	        return this;
	    }
}
