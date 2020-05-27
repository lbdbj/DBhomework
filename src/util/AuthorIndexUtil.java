package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import entity.ArticleInfo;
import entity.Judege;


//建立文章作者索引文件的工具类
public class AuthorIndexUtil {
	public static AuthorIndexUtil instance = new AuthorIndexUtil();
	
	public FileOutputStream out = null;
	public BufferedOutputStream bout = null;

			
//		获取字符串哈希值的方法,此时获取的哈希值就可以作为存放在数组的位置（也就是作者名的哈希值）
		public static int getAuthorPos(String str, int len) {
//			注意先把头部和尾部的空格去掉
			str = str.trim();
//			将字符串转为字符数组
			char[]vals = str.toCharArray();
//			记录字符数组的长度
			int len2 = vals.length;
			int hashcode = 0;
//			遍历字符数组
			for(int i=0; i<len2; i++) {
				int val = (int)vals[i];
				hashcode = (hashcode<<5)+hashcode+val;
			}
			hashcode = (hashcode^(hashcode >>> 16)) & (len-1);
			if(hashcode<0)
				hashcode = -hashcode;
			return hashcode;
		}

//		用来建立作者索引文件时向内存中的哈希表赋值
		public static void assignValue(String str, int len, int pos) {
//			获取给定作者字符串的哈希值当做哈希表中的一个键
			int hashcode = IndexFileUtil.getPos(str, len);
//			如果哈希表中包含这个键
			if(AllStatic.map.containsKey(hashcode))
//				向这个键对应的值中添加这个哈希值
				AllStatic.map.get(hashcode).add(pos);

			else {
//				否则新建一个键值对
				List<Integer>list = new ArrayList<Integer>();
				list.add(pos);
				AllStatic.map.put(hashcode, list);
			}
			
		}

//	根据给定的作者名从srcfile中获取所有包含这个作者的文章信息

	public static List<ArticleInfo> getArticleByFile(String author, RandomAccessFile rafAuthor1, RandomAccessFile rafSrc1,
			RandomAccessFile rafAuthor2,RandomAccessFile rafSrc2) {
		List<ArticleInfo>list = new ArrayList<ArticleInfo>();
		Judege ja = new Judege();
		try {
//			先获取文章地址数组
			int allPos[] = IndexFileUtil.getAllPos(author, rafAuthor1, rafAuthor2, 4194304);
//			遍历每一个位置判断是否和指定的author相同
			for(int i=0; i<allPos.length; i++) {
				int val = allPos[i];
//				如果地址值小于10000000就到srcfile1文件中查找
				if(val<10000000) {
					ja = SrcFileUtil.judgeAuthor(val, author, 500, rafSrc1);
//					如果作者名符合，就将文章信息格式化成ArticleInfo对象并存入List中
					if(ja.isSame) {
						list.add(SrcFileUtil.formatArticle(ja.content));
					}
				}
				else {
//					如果地址值大于10000000就到srcfile2文件中查找
					val = val-10000000;
					ja = SrcFileUtil.judgeAuthor(val, author, 5000, rafSrc2);
					if(ja.isSame) {
						list.add(SrcFileUtil.formatArticle(ja.content));
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ParseUtil.myParse("D:\\DBhomework\\dblp.xml");
		IndexFileUtil instace = new IndexFileUtil();
		instace.setFile1(4194304, "authorIndex1.txt");
		instace.setFile2(4194304, "authorIndex2.txt");
	}
}
