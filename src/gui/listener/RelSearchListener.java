package gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entity.ArticleInfo;
import gui.panel.RelSearchPanel;
import service.RelSearchService;

public class RelSearchListener implements ActionListener {

    public StringBuilder sb = new StringBuilder();
    public List<ArticleInfo> list = new ArrayList<ArticleInfo>();

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        RelSearchPanel instance = RelSearchPanel.instance;
        final String input = instance.input.getText();
        try {
            List<String> lists = new ArrayList<String>();
            new RelSearchService().getAllInfoByAuthor(input).stream().forEach(
            item -> {
                String[] strings = item.getAuthors().split("\\r");
                  for (String temp : strings) {
                    if (input.equals(temp)) continue;
                    lists.add(temp);
                }});//获取getAllInfoByAuthor流里面的list 并切割全部存进lists中
            instance.rtm.rs = lists.stream().distinct().collect(Collectors.toList());//输出不重复的
            instance.t.updateUI();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        

    }

}
