package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import entity.ArticleInfo;
import entity.JudegeAuthor;

public class SrcFileUtil {
	public static String formatStr(StringBuilder sb, int num) {
		int len = sb.length();
		for(int i=len; i<num; i++)
			sb.append(' ');
		System.out.println("这部分的长度是"+sb.length());
		return sb.toString();
	}
	
//	获取源文件指定位置文章的题目
	public static String getTitleFromSrc(int pos, int readLength, RandomAccessFile raf) throws IOException {
		StringBuilder sb = new StringBuilder();
		try {
			int flag = 0;
			raf.seek((pos-1)*readLength);
			byte[] content = new byte[readLength];
			int result = raf.read(content);
			if(result == readLength) {
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
		String str = sb.toString();
		if(str.length()>0 &&str.charAt(str.length()-1)=='.')
			str = str.substring(0, str.length()-1);
		return str.substring(1);
	}
	
	public static String getAllInfo(int pos, int readLength, RandomAccessFile raf) throws IOException {
		byte[] content =new byte[readLength];
		try {
			raf.seek((long)(pos-1)*(long)readLength);	
			raf.read(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(content);
	}
//	判断输入的作者名在指定位置的文章信息中是否存在
	public static JudegeAuthor judgeAuthor(int pos, String author, int readLength, RandomAccessFile raf) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[]content = new byte[readLength];
		try {
			int flag = 0;
			long moveLength = (long)(pos-1)*(long)readLength;
			raf.seek(moveLength);
			int result = raf.read(content);
			if(result == readLength) {
				for(byte b : content) {
					if((char)b == '\r'&&flag == 1) {
						if(author.equals(sb.toString()))
							return new JudegeAuthor(true, new String(content));
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
		return new JudegeAuthor(false, new String(content));
	}
//	格式化文章信息
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
	public static void main(String[] args) {
		String str = "zdfdgdssfs";
		StringBuilder sb = new StringBuilder(str);
		str = SrcFileUtil.formatStr(sb, 50);
		System.out.println(str+str.length());
	}
}
