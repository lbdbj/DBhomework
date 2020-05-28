package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import entity.ArticleInfo;
import entity.Judege;

public class SrcFileUtil {

//	给文章补充空格到指定字节数
	public static String formatStr(StringBuilder sb, int num) {
		int len = sb.length();
		for(int i=len; i<num; i++)
			sb.append(' ');
		return sb.toString();
	}
	
//	获取指定地址的文章的题目
	public static String getTitleFromSrc(int pos, int readLength, RandomAccessFile raf) throws IOException {
		StringBuilder sb = new StringBuilder();
		try {
			int flag = 0;
//			文件指针跳转到文章所在的地址
			raf.seek((pos-1)*readLength);
			byte[] content = new byte[readLength];
//			读取指定地址的文章并存入byte数组
			int result = raf.read(content);
			if(result == readLength) {
//				通过标题标识符*来获取文章的标题
				for(byte b : content) {
					if((char)b == '\r' && flag == 1)
						break;
					if((char)b == '*')
						flag = 1;
					if(flag == 1) {
						sb.append((char)b);
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		将获取的标题转换为字符串
		String str = sb.toString();
//		代表这篇文章没有标题
		if (str.length()==0) {
			return null;
		}
//		去掉标题结尾的.
		if(str.length()>0 &&str.charAt(str.length()-1)=='.')
			str = str.substring(0, str.length()-1);
		return str.substring(1);
	}
//	获取指定地址的文章信息
	public static String getAllInfo(int pos, int readLength, RandomAccessFile raf) throws IOException {
		byte[] content =new byte[readLength];
		try {
//			文件指针跳转到指定地址
			raf.seek((long)(pos-1)*(long)readLength);	
//			读取内容
			raf.read(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(content);
	}
	
//	判断输入的作者名在指定地址的文章中是否存在
	public static Judege judgeAuthor(int pos, String author, int readLength, RandomAccessFile raf) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[]content = new byte[readLength];
		try {
			int flag = 0;
			long moveLength = (long)(pos-1)*(long)readLength;
//			文件指针调转到指定位置
			raf.seek(moveLength);
//			读取文章内容
			int result = raf.read(content);
			if(result == readLength) {
//				根据作者标识符！来获取文章中的作者
				for(byte b : content) {
					if((char)b == '\r'&&flag == 1) {
//						如果与输入的作者相同就返回true和文章的信息
						if(author.equals(sb.toString()))
							return new Judege(true, new String(content));
						sb.setLength(0);
						flag = 0;
					}
					if(flag == 1) {
						sb.append((char)b);
					}
					if((char)b == '!') {
						flag = 1;
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		如果不包含输入的作者就返回false和文章信息
		return new Judege(false, new String(content));
	}
	
//	判断输入的关键词在指定位置的文章信息中是否存在
	public static Judege judgeSubtitles(int pos, String []keywords, int readLength, RandomAccessFile raf) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[]content = new byte[readLength];
		try {
			int flag = 0;
			long moveLength = (long)(pos-1)*(long)readLength;
//			文件指针调转到指定位置
			raf.seek(moveLength);
//			读取文章信息
			int result = raf.read(content);
			if(result == readLength) {
				for(byte b : content) {
//					通过标题标识符*来获取文章的标题
						if((char)b == '\r' && flag == 1)
							break;
						if((char)b == '*')
							flag = 1;
						if(flag == 1) {
							sb.append((char)b);
						}
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = sb.toString();
//		标题不存在的话，直接返回false和文章信息
		if(str.length() == 0)
			return new Judege(false, new String(content));
//		切分标题
		String[] subtitles = PartSearchUtil.subTitle(str);
		int flag = 0;
//		判断文章标题是否包含所有输入的关键词
		for(String keyword : keywords) {
			for(String subtitle : subtitles) {
				if(subtitle.equals(keyword.toLowerCase()))
					flag = 1;
			}
//			只要有一个关键词没有出现就返回false
			if(flag == 0) {
				return new Judege(false, new String(content));
			}
			flag = 0;
		}
//		关键词全部出现返回true和文章信息
		return new Judege(true, new String(content));
	}
	
//	格式化文章信息返回一个ArticleInfo对象
	public static ArticleInfo formatArticle(String content) {
		ArticleInfo article = new ArticleInfo();
		StringBuilder sb = new StringBuilder();
		char[]arr = content.toCharArray();
		int flag = 0;
		for(char c : arr) {
			if(flag != 0)
				sb.append(c);
			if(c=='\r') {
				if(flag == 1)
					article.addAuthor(sb);
				if(flag == 2)
					article.setTitle(sb.toString());
				if(flag == 3)
					article.setJournal(sb.toString());
				if(flag == 4)
					article.setVolume(sb.toString());
				if(flag == 5)
					article.setYear(sb.toString());
				if(flag == 6)
					article.setPages(sb.toString());
				if(flag == 7)
					article.setEe(sb.toString());
				flag = 0;
			}
//			根据文章各个属性的标识符切分文章信息
			if(c == '!' && flag ==0) {
				flag = 1;
				sb.setLength(0);
			}
			if(c == '*'&& flag ==0) {
				flag = 2;
				sb.setLength(0);
			}
			if(c == '@'&& flag ==0) {
				flag = 3;
				sb.setLength(0);
			}
			if(c == '#'&& flag ==0) {
				flag = 4;
				sb.setLength(0);
			}
			if(c == '$'&& flag ==0) {
				flag = 5;
				sb.setLength(0);
			}
			if(c == '%'&& flag ==0) {
				flag = 6;
				sb.setLength(0);
			}
			if(c == '^'&& flag ==0) {
				flag = 7;
				sb.setLength(0);
			}
		}
		return article;
	}
}
