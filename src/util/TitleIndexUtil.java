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

//建立文章标题索引文件的工具类
public class TitleIndexUtil {
	
//	单例模式
	public static TitleIndexUtil instance = new TitleIndexUtil();
	
	public FileOutputStream out = null;
	public BufferedOutputStream bout = null;
	
//		获取字符串哈希值的方法,此时获取的哈希值就可以作为存放在数组的位置
		public static int getTitlePos(String str, int len) {
//			注意先把头部和尾部的空格去掉
			str = str.trim();
//			把标题的最后的点去掉
			if(str.length()>0 && str.charAt(str.length()-1)=='.')
				str = str.substring(0, str.length()-1);
			char[]vals = str.toCharArray();
			int len2 = vals.length;
			int hashcode = 0;
			for(int i=0; i<len2; i++) {
				if(i == len2)
					break;
				int val = (int)vals[i];
				hashcode = (hashcode << 5)-hashcode+val;
			}
			hashcode = (hashcode^(hashcode >>> 16)) & (len-1);
			if(hashcode<0)
				hashcode = -hashcode;
			return hashcode;
		}
		
//		用来建立标题索引文件时向内存中的哈希表赋值
		public static void assignValue(String str, int len, int pos) {
//			获取给定作者字符串的哈希值当做哈希表中的一个键
			int hashcode = getTitlePos(str, len);
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
	
//		根据给定的标题从srcfile中获取所有包含这个标题的文章信息
	public static List<ArticleInfo> getArticleByFile(String title, RandomAccessFile rafTitle1, RandomAccessFile rafSrc1,
			RandomAccessFile rafTitle2,RandomAccessFile rafSrc2) {
		List<ArticleInfo>list = new ArrayList<ArticleInfo>();
		String getTitile = null;
		try {
//			获取所有指定标题文章的地址
			int allPos[] = IndexFileUtil.getAllPos(title, rafTitle1, rafTitle2, 8388608);
//			遍历每一个地址判断是否和指定的title相同
			for(int i=0; i<allPos.length; i++) {
				int val = allPos[i];
//				如果地址值小于10000000就到srcfile1文件中查找
				if(val<10000000) {
					getTitile = SrcFileUtil.getTitleFromSrc(val, 500, rafSrc1);
//					如果标题符合，就将文章信息格式化成ArticleInfo对象并存入List中
					if(getTitile.equals(title)) {
						list.add(SrcFileUtil.formatArticle(SrcFileUtil.getAllInfo(val, 500, rafSrc1)));
					}
				}
				else {
//					如果地址值大于10000000就到srcfile2文件中查找
					val = val-10000000;
					getTitile = SrcFileUtil.getTitleFromSrc(val, 5000, rafSrc2);
					if(getTitile.equals(title)) {
						list.add(SrcFileUtil.formatArticle(SrcFileUtil.getAllInfo(val, 5000, rafSrc2)));
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
		instace.setFile1(8388608, "titleIndex1.txt");
		instace.setFile2(8388608, "titleIndex2.txt");
	}
}
