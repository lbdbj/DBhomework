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
import util.IndexFileUtil;

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
//		如果输入关键词不符合要求给出提示
		if(keywords.length == 0) {
			JOptionPane.showMessageDialog(instance, "您输入的关键词格式不对，请不要输入冠词，介词等无意义的单词", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
			return articles;
		}
//		如果关键词只有一个，直接获得文章地址数组
		else if (keywords.length == 1) {
			allPos = IndexFileUtil.getAllPos(keywords[0], raf1, raf2, 2097152);
		}else {
//			如果关键词多于1个就获取若干个文章地址数组，并求出这些数组的交集
			allPos =  IndexFileUtil.getAllPos(keywords[0], raf1, raf2, 2097152);
			for(int i=1; i<keywords.length; i++) {
				int temp[] = IndexFileUtil.getAllPos(keywords[i], raf1, raf2, 2097152);
				allPos = PartSearchUtil.getIntersection(allPos, temp);
			}
		}
		if(allPos.length != 0) {
//			遍历文章地址数组
			for(int pos : allPos) {
				Judege j = new Judege();
//				如果地址值小于10000000就到srcfile1文件中查找
				if (pos<10000000) {
					j = SrcFileUtil.judgeSubtitles(pos, keywords, 500, raf3);
//					如果文章中包含所有给定的关键词就把文章对象存入list
					if(j.isSame)
						articles.add(SrcFileUtil.formatArticle(j.content));
				}else {
//					如果地址值大于10000000就到srcfile2文件中查找
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
//		如果没有查到符合要求的文章给出提示信息
		if(articles.size() == 0) {
			JOptionPane.showMessageDialog(instance, "没有查询到文章信息，请修改后重新查询", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
		}
		System.out.println("文章数量"+articles.size());
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
