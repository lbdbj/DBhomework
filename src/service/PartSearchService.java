package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entity.ArticleInfo;
import entity.Judege;
import gui.panel.PartSearchPanel;
import util.PartSearchUtil;
import util.SrcFileUtil;

public class PartSearchService {
	public List<ArticleInfo> getAllInfoByKeywords(String keywordsStr) throws IOException {
//		存储所有查询到的文章信息
		List<ArticleInfo> articles = new ArrayList<ArticleInfo>();
//		获取部分匹配搜索面板的实例
		PartSearchPanel instance = PartSearchPanel.instance;
//		将输入的关键词字符串进行切分
		String keywords[] = PartSearchUtil.subTitle(keywordsStr);
//		建立相应的文件流
		File file1 = new File("D:\\DBhomework\\partindex1.txt");
		File file2 = new File("D:\\DBhomework\\partindex2.txt");
		File file3 = new File("D:\\DBhomework\\srcfile1.txt");
		File file4 = new File("D:\\DBhomework\\srcfile2.txt");
		RandomAccessFile raf1 = new RandomAccessFile(file1, "r");
		RandomAccessFile raf2 = new RandomAccessFile(file2, "r");
		RandomAccessFile raf3 = new RandomAccessFile(file3, "r");
		RandomAccessFile raf4 = new RandomAccessFile(file4, "r");
//		存储包含给定关键词的文章位置
		int allPos[] = null;
		
		if(keywords.length == 0)
			JOptionPane.showMessageDialog(instance, "您输入的关键词格式不对", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
		else if (keywords.length == 1) {
			allPos = PartSearchUtil.getAllPos(keywords[0], raf1, raf2);
		}else {
			allPos =  PartSearchUtil.getAllPos(keywords[0], raf1, raf2);
			for(int i=1; i<keywords.length; i++) {
				int temp[] = PartSearchUtil.getAllPos(keywords[i], raf1, raf2);
				allPos = PartSearchUtil.getIntersection(allPos, temp);
			}
		}
		if(allPos.length == 0)
			JOptionPane.showMessageDialog(instance, "没有查询到文章信息，请修改后重新查询", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
		else {
			for(int pos : allPos) {
				Judege j = new Judege();
				if (pos<10000000) {
					j = SrcFileUtil.judgeSubtitles(pos, keywords, 500, raf3);
					if(j.isSame)
						articles.add(SrcFileUtil.formatArticle(j.content));
				}else {
					j = SrcFileUtil.judgeSubtitles(pos-10000000, keywords, 5000, raf4);
					if(j.isSame)
						articles.add(SrcFileUtil.formatArticle(j.content));
				}
			}
		}
		raf4.close();
		raf3.close();
		raf2.close();
		raf1.close();
		if(articles.size() == 0)
			JOptionPane.showMessageDialog(instance, "没有查询到文章信息，请修改后重新查询", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
		return articles;
	}
	public static void main(String[] args) throws IOException {
		PartSearchService service = new PartSearchService();
		List<ArticleInfo>list = new ArrayList<ArticleInfo>();
		list = service.getAllInfoByKeywords("Recommendations from a Scientific");
		for(ArticleInfo a : list)
			System.out.println(a.getTitle());
	}
}
