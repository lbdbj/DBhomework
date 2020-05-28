package gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class RelTableModel implements TableModel {
	String columnNames[] = new String[] {"合作作者名"};
	public List<String> rs = new ArrayList<String>();
	public RelTableModel() {
		// TODO Auto-generated constructor stub
		rs.add("刘世伟");
		rs.add("冯兆鹏");
		rs.add("刘付磊");
		rs.add("张培浩");
	} 
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rs.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return columnNames[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(0 == columnIndex)
			return rs.get(rowIndex);
		else
			return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

}
