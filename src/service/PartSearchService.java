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
//		�洢���в�ѯ����������Ϣ
		List<ArticleInfo> articles = new ArrayList<ArticleInfo>();
//		��ȡ����ƥ����������ʵ��
		PartSearchPanel instance = PartSearchPanel.instance;
//		������Ĺؼ����ַ��������з�
		String keywords[] = PartSearchUtil.subTitle(keywordsStr);
//		������Ӧ���ļ���
		File file1 = new File("D:\\DBhomework\\partindex1.txt");
		File file2 = new File("D:\\DBhomework\\partindex2.txt");
		File file3 = new File("D:\\DBhomework\\srcfile1.txt");
		File file4 = new File("D:\\DBhomework\\srcfile2.txt");
		RandomAccessFile raf1 = new RandomAccessFile(file1, "r");
		RandomAccessFile raf2 = new RandomAccessFile(file2, "r");
		RandomAccessFile raf3 = new RandomAccessFile(file3, "r");
		RandomAccessFile raf4 = new RandomAccessFile(file4, "r");
//		�洢���������ؼ��ʵ�����λ��
		int allPos[] = null;	
//		�������ؼ��ʲ�����Ҫ�������ʾ
		if(keywords.length == 0) {
			JOptionPane.showMessageDialog(instance, "������Ĺؼ��ʸ�ʽ���ԣ��벻Ҫ����ڴʣ���ʵ�������ĵ���", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
			return articles;
		}
//		����ؼ���ֻ��һ����ֱ�ӻ�����µ�ַ����
		else if (keywords.length == 1) {
			allPos = IndexFileUtil.getAllPos(keywords[0], raf1, raf2, 2097152);
		}else {
//			����ؼ��ʶ���1���ͻ�ȡ���ɸ����µ�ַ���飬�������Щ����Ľ���
			allPos =  IndexFileUtil.getAllPos(keywords[0], raf1, raf2, 2097152);
			for(int i=1; i<keywords.length; i++) {
				int temp[] = IndexFileUtil.getAllPos(keywords[i], raf1, raf2, 2097152);
				allPos = PartSearchUtil.getIntersection(allPos, temp);
			}
		}
		if(allPos.length != 0) {
//			�������µ�ַ����
			for(int pos : allPos) {
				Judege j = new Judege();
//				�����ֵַС��10000000�͵�srcfile1�ļ��в���
				if (pos<10000000) {
					j = SrcFileUtil.judgeSubtitles(pos, keywords, 500, raf3);
//					��������а������и����Ĺؼ��ʾͰ����¶������list
					if(j.isSame)
						articles.add(SrcFileUtil.formatArticle(j.content));
				}else {
//					�����ֵַ����10000000�͵�srcfile2�ļ��в���
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
//		���û�в鵽����Ҫ������¸�����ʾ��Ϣ
		if(articles.size() == 0) {
			JOptionPane.showMessageDialog(instance, "û�в�ѯ��������Ϣ�����޸ĺ����²�ѯ", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
		}
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
