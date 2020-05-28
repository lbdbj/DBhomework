package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import entity.ArticleInfo;
import entity.Judege;

public class SrcFileUtil {

//	�����²���ո�ָ���ֽ���
	public static String formatStr(StringBuilder sb, int num) {
		int len = sb.length();
		for(int i=len; i<num; i++)
			sb.append(' ');
		return sb.toString();
	}
	
//	��ȡָ����ַ�����µ���Ŀ
	public static String getTitleFromSrc(int pos, int readLength, RandomAccessFile raf) throws IOException {
		StringBuilder sb = new StringBuilder();
		try {
			int flag = 0;
//			�ļ�ָ����ת���������ڵĵ�ַ
			raf.seek((pos-1)*readLength);
			byte[] content = new byte[readLength];
//			��ȡָ����ַ�����²�����byte����
			int result = raf.read(content);
			if(result == readLength) {
//				ͨ�������ʶ��*����ȡ���µı���
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
//		����ȡ�ı���ת��Ϊ�ַ���
		String str = sb.toString();
//		������ƪ����û�б���
		if (str.length()==0) {
			return null;
		}
//		ȥ�������β��.
		if(str.length()>0 &&str.charAt(str.length()-1)=='.')
			str = str.substring(0, str.length()-1);
		return str.substring(1);
	}
//	��ȡָ����ַ��������Ϣ
	public static String getAllInfo(int pos, int readLength, RandomAccessFile raf) throws IOException {
		byte[] content =new byte[readLength];
		try {
//			�ļ�ָ����ת��ָ����ַ
			raf.seek((long)(pos-1)*(long)readLength);	
//			��ȡ����
			raf.read(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(content);
	}
	
//	�ж��������������ָ����ַ���������Ƿ����
	public static Judege judgeAuthor(int pos, String author, int readLength, RandomAccessFile raf) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[]content = new byte[readLength];
		try {
			int flag = 0;
			long moveLength = (long)(pos-1)*(long)readLength;
//			�ļ�ָ���ת��ָ��λ��
			raf.seek(moveLength);
//			��ȡ��������
			int result = raf.read(content);
			if(result == readLength) {
//				�������߱�ʶ��������ȡ�����е�����
				for(byte b : content) {
					if((char)b == '\r'&&flag == 1) {
//						����������������ͬ�ͷ���true�����µ���Ϣ
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
//		�����������������߾ͷ���false��������Ϣ
		return new Judege(false, new String(content));
	}
	
//	�ж�����Ĺؼ�����ָ��λ�õ�������Ϣ���Ƿ����
	public static Judege judgeSubtitles(int pos, String []keywords, int readLength, RandomAccessFile raf) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[]content = new byte[readLength];
		try {
			int flag = 0;
			long moveLength = (long)(pos-1)*(long)readLength;
//			�ļ�ָ���ת��ָ��λ��
			raf.seek(moveLength);
//			��ȡ������Ϣ
			int result = raf.read(content);
			if(result == readLength) {
				for(byte b : content) {
//					ͨ�������ʶ��*����ȡ���µı���
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
//		���ⲻ���ڵĻ���ֱ�ӷ���false��������Ϣ
		if(str.length() == 0)
			return new Judege(false, new String(content));
//		�зֱ���
		String[] subtitles = PartSearchUtil.subTitle(str);
		int flag = 0;
//		�ж����±����Ƿ������������Ĺؼ���
		for(String keyword : keywords) {
			for(String subtitle : subtitles) {
				if(subtitle.equals(keyword.toLowerCase()))
					flag = 1;
			}
//			ֻҪ��һ���ؼ���û�г��־ͷ���false
			if(flag == 0) {
				return new Judege(false, new String(content));
			}
			flag = 0;
		}
//		�ؼ���ȫ�����ַ���true��������Ϣ
		return new Judege(true, new String(content));
	}
	
//	��ʽ��������Ϣ����һ��ArticleInfo����
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
//			�������¸������Եı�ʶ���з�������Ϣ
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
