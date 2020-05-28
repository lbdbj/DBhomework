package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.List;

import javax.swing.JOptionPane;

import entity.ArticleInfo;
import entity.AuthorCount;
import util.AuthorIndexUtil;
import util.TitleIndexUtil;

public class BasicSearchService {
	public List<ArticleInfo> getAllInfoByAuthor(String author) throws IOException {
//		创建相应的索引文件和源文件读写流
		File file1 = new File("D:\\DBhomework\\authorindex1.txt");
		File file2 = new File("D:\\DBhomework\\srcfile1.txt");
		File file3 = new File("D:\\DBhomework\\authorindex2.txt");
		File file4 = new File("D:\\DBhomework\\srcfile2.txt");
		RandomAccessFile raf1 = new RandomAccessFile(file1, "r");
		RandomAccessFile raf2 = new RandomAccessFile(file2, "r");
		RandomAccessFile raf3 = new RandomAccessFile(file3, "r");
		RandomAccessFile raf4 = new RandomAccessFile(file4, "r");
//		获取所有包含给定作者的文章对象
		List<ArticleInfo>articles = AuthorIndexUtil.getArticleByFile(author, raf1, raf2,raf3,raf4);
		raf1.close();
		raf2.close();
		raf3.close();
		raf4.close();
//		如果没有符合要求的文章，给出提示信息
		if(articles.size()==0) {
			JOptionPane.showMessageDialog(null, "没有查询到文章信息，请修改后重新查询", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
		}
		System.out.println("文章数量"+articles.size());
		return articles;
		
	}
	public List<ArticleInfo> getAllInfoByTitle(String title) throws IOException {
//		创建相应的索引文件和源文件读写流
		File file1 = new File("D:\\DBhomework\\titleindex1.txt");
		File file2 = new File("D:\\DBhomework\\srcfile1.txt");
		File file3 = new File("D:\\DBhomework\\titleindex2.txt");
		File file4 = new File("D:\\DBhomework\\srcfile2.txt");
		RandomAccessFile raf1 = new RandomAccessFile(file1, "r");
		RandomAccessFile raf2 = new RandomAccessFile(file2, "r");
		RandomAccessFile raf3 = new RandomAccessFile(file3, "r");
		RandomAccessFile raf4 = new RandomAccessFile(file4, "r");
//		获取所有包含给定标题的文章对象
		List<ArticleInfo>articles = TitleIndexUtil.getArticleByFile(title, raf1, raf2,raf3,raf4);
		raf1.close();
		raf2.close();
		raf3.close();
		raf4.close();
//		如果没有符合要求的文章，给出提示信息
		if(articles.size()==0) {
			JOptionPane.showMessageDialog(null, "没有查询到文章信息，请修改后重新查询", "INFORMATION_MESSAGE",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return articles;
	}

	public static void main(String[] args) throws IOException {
		List<ArticleInfo> list = new BasicSearchService().getAllInfoByAuthor("H. Vincent Poor");
		int i=0;
		for(ArticleInfo a : list)
			i++;
		System.out.println(i);
	}
}
