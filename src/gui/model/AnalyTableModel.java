package gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class AnalyTableModel implements TableModel {

	String columnNames[] = new String[] {"�ؼ���","���ִ���"};
	public List<String> as = new ArrayList<String>();
	public AnalyTableModel() {
		// TODO Auto-generated constructor stub
//		as.add("����ΰ");
//		as.add("������");
	} 
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return as.size()/2;
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
		if(0 == columnIndex) {
			System.out.println("colunmn="+columnIndex+" row="+rowIndex);
			return as.get(2*rowIndex);
		}
		if(1 == columnIndex) {
			System.out.println("colunmn="+columnIndex+" row="+rowIndex);
			return as.get(2*rowIndex+1);
		}
			
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
