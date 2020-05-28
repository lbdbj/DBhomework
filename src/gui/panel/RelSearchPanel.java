package gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import gui.listener.RelSearchListener;
import gui.model.RelTableModel;
import util.GUIUtil;
import util.TableViewRenderer;

public class RelSearchPanel extends JPanel {

    public static RelSearchPanel instance = new RelSearchPanel();

    // ����������ť
    JButton bSearch = new JButton("����");

    // ������Ϣ�����
    public JTextField input = new JTextField();

    JLabel label = new JLabel("������");

    // ������ʾ��Ϣ�ı��
    String columnNames[] = new String[] {"����������"};
    public RelTableModel rtm = new RelTableModel();
    public JTable t = new JTable(rtm);

    public RelSearchPanel() {
        t.setRowHeight(40);
        t.setDefaultRenderer(Object.class, new TableViewRenderer());
        this.setLayout(new BorderLayout());
        this.add(north(), BorderLayout.NORTH);
        this.add(center(), BorderLayout.CENTER);
        bSearch.addActionListener(new RelSearchListener());
    }

    // ���ý�����ϰ벿��
    private JPanel north() {
        JPanel p = new JPanel();
        input.setPreferredSize(new Dimension(300, 25));
        p.add(label);
        p.add(input);
        p.add(bSearch);
        return p;
    }

    // ���ý�����°벿��
    private JScrollPane center() {
        JScrollPane s = new JScrollPane(t);
        return s;
    }

    public static void main(String[] args) {
        GUIUtil.showPanel(RelSearchPanel.instance, 1);
    }
}
