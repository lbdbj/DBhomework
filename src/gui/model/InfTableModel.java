package gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import entity.ArticleInfo;

public class InfTableModel implements TableModel {
	String columnNames[] = new String[] {"作者","题目","发表杂志","卷号","出版时间","页数","链接"};
	public List<ArticleInfo> is = new ArrayList<ArticleInfo>();
	
	public InfTableModel() {
		// TODO Auto-generated constructor stub
	} 
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return is.size();
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
		ArticleInfo info = is.get(rowIndex);
		if(0 == columnIndex)
			return info.getAuthors().toString();
		if(1 == columnIndex)
			return info.getTitle();
		if(2 == columnIndex)
			return info.getJournal();
		if(3 == columnIndex)
			return info.getVolume();
		if(4 == columnIndex)
			return info.getYear();
		if(5 == columnIndex)
			return info.getPages();
		if(6 == columnIndex)
			return info.getEe();
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
