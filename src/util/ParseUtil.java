package util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.spi.XmlReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

import entity.ArticleInfo;

public class ParseUtil{
	public static List<ArticleInfo>list;
	public static void myParse(String pathName) throws ParserConfigurationException, SAXException, IOException {
//		����SAX�������Ĺ�����
		SAXParserFactory factory = SAXParserFactory.newInstance();
//		���SAX������
		SAXParser parser = factory.newSAXParser();
//		��ȡxml�ļ�������
		XMLReader xmlReader = parser.getXMLReader();
//		����dtd�ļ�
		xmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
//		�½��Զ���Ľ���������
		ArticleHandler handler = new ArticleHandler();
//		ָ��������
		xmlReader.setContentHandler(handler);
//		��ʼ����ָ���ļ�
		xmlReader.parse(new InputSource(pathName));
		
	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ParseUtil.myParse("D:\\DBhomework\\dblp.xml");
}
}
class ArticleHandler extends DefaultHandler{
		private List<String>authorList;
		private StringBuilder sb = new StringBuilder();
		private StringBuilder contentBuilder = new StringBuilder();
//		�����ж�һ��article���Ƿ����
		private static int flag = 0;
		private String tag;
//		��¼article����srcfile1�е�λ��
		private static int articlePos1=0;
//		��¼article����srcfile2�е�λ��
		private static int articlePos2=0;
//		��¼���±���
		private static String title = new String();

//		private static FileOutputStream out1 = null;
//		private static FileOutputStream out2 = null;
//		private static BufferedOutputStream bOut1 = null;
//		private static BufferedOutputStream bOut2 = null;
//		��¼�и�����Ĺؼ���
		private String[] subTitles = null;
		
	@Override
//	��ʼ����xml�ļ�
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("��ʼ����---");
//		try {
//			File dir = new File("d:/DBhomework");
////			����ļ��в����ھʹ���
//			if(!dir.exists()) {
//				dir.mkdir();
//			}
//			File titleFile = new File(dir,"srcfile1.txt");
////			�ļ������ھʹ������ļ�
//			if(!titleFile.exists())
//					titleFile.createNewFile();
//			File dir2 = new File("d:/DBhomework");
////			����ļ��в����ھʹ���
//			if(!dir2.exists()) {
//				dir2.mkdir();
//			}
//			File titleFile2 = new File(dir,"srcfile2.txt");
////			�ļ������ھʹ������ļ�
//			if(!titleFile2.exists())
//					titleFile2.createNewFile();
//			out1 = new FileOutputStream("d:/DBhomework/srcfile1.txt");
//			out2 = new FileOutputStream("d:/DBhomework/srcfile2.txt");
//		                           ��������������
//			bOut1 = new BufferedOutputStream(out1,10000000);
//			bOut2 = new BufferedOutputStream(out2,5000000);
//			
//		} catch (IOException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}
	
	@Override
//	��ʼ����һ����ʼ��ǩ
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if(null != qName) {
			tag = qName;
//			�����ȡ��tagΪarticle����inproceedings����һƪ���¿�ʼ
			if("inproceedings".equals(tag) || "article".equals(tag)) {
//				�½�һ���ݴ����ߵ�list
				authorList = new ArrayList<String>();
//				flagΪ1����ǰ���ڽ���һƪ����Ҫ�������
				flag = 1;
				int c = articlePos1+1;
				System.out.println("��"+c+"ƪarticle---");
			}
		}
	}
	
	@Override
//	��ȡ��ʼ��ǩ�ͽ�����ǩ�м������
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if(null != tag && flag == 1)
			contentBuilder.append(ch,start,length);
		
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		String content = contentBuilder.toString();
		content = content.trim();
		contentBuilder.setLength(0);
//		flagΪ1����ǰ���ڽ���һ������Ҫ���article
		if(null != tag && flag == 1) {
//			���ݽ�������tag�����ݣ��ֱ�������ǰ����Ӳ�ͬ�ı�ʶ��
			if(tag.equals("author")) {
					authorList.add(content);
					sb.append('!');
					sb.append(content);
					sb.append("\r\n");
			}
			else if(tag.equals("title")) {
				title = content;
				sb.append('*');
				sb.append(content);
				sb.append("\r\n");
				}
			else if(tag.equals("booktitle") || tag.equals("journal")) {
				sb.append('@');
				sb.append(content);
				sb.append("\r\n");
			}
			else if(tag.equals("volume")) {
				sb.append('#');
				sb.append(content);
				sb.append("\r\n");
			}
			else if(tag.equals("year")) {
				sb.append('$');
				sb.append(content);
				sb.append("\r\n");
			}
			else if(tag.equals("pages")) {
				sb.append('%');
				sb.append(content);
				sb.append("\r\n");
			}
			else if(tag.equals("ee")) {
				sb.append('^');
				sb.append(content);
				sb.append("\r\n");
			}
		}
		tag = null;
		if("inproceedings".equals(qName) || "article".equals(qName)) {
//			����һƪ���½�����flagΪ0
			flag = 0;
			int len = sb.length();
//			��������а������ֽ���С��500
			if(len<=500) {
////				��srcfile1д��������Ϣ
//				str = SrcFileUtil.formatStr(sb,500);
//				try {
//					byte[] bs = str.getBytes();
//					int lenb = bs.length;
//					if(lenb > 500) {
//						byte bs2[] = new byte[500];
//						for(int i=0; i<500; i++)
//							bs2[i] = bs[i];
//						bOut1.write(bs2);
//					}else if (lenb<500) {
//						byte bs2[] = new byte[500];
//						for(int i=0;i<500;i++) {
//							if(i<lenb)
//								bs2[i] = bs[i];
//							else {
//								bs2[i] =' ';
//							}
//						}
//						bOut1.write(bs2);
//					}
//					else {
//						bOut1.write(bs);
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				���µ�ַ����
				articlePos1++;
//				������ϣ���и�ֵ
				TitleIndexUtil.assignValue(title, 8388608, articlePos1);
//				�����߹�ϣ���и�ֵ
				for(String author : authorList) {
						AuthorIndexUtil.assignValue(author, 4194304, articlePos1);
				}
//				�зֱ���
				subTitles = PartSearchUtil.subTitle(title);
//				�򲿷�ƥ�������Ĺؼ��ʹ�ϣ��ֵ
				PartSearchUtil.assignValue(subTitles, 2097152, articlePos1);
			}else {
////				��srcfile2д��������Ϣ
//				str = SrcFileUtil.formatStr(sb,5000);
//				try {
//					byte[] bs = str.getBytes();
//					int lenb = bs.length;
//					if(lenb > 5000) {
//						byte bs2[] = new byte[5000];
//						for(int i=0; i<5000; i++)
//							bs2[i] = bs[i];
//						bOut2.write(bs2);
//					}else if (lenb<5000) {
//						byte bs2[] = new byte[5000];
//						for(int i=0;i<5000;i++) {
//							if(i<lenb)
//								bs2[i] = bs[i];
//							else {
//								bs2[i] =' ';
//							}
//						}
//						bOut2.write(bs2);
//					}
//					else {
//						bOut2.write(bs);
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				articlePos2++;
//				������ϣ���и�ֵ
				TitleIndexUtil.assignValue(title, 8388608, articlePos2+10000000);
//				�����߹�ϣ���и�ֵ
				for(String author : authorList) {
					AuthorIndexUtil.assignValue(author, 4194304, articlePos2+10000000);
			}
//				�зֱ���
				subTitles = PartSearchUtil.subTitle(title);
//				�򲿷�ƥ�������Ĺؼ��ʹ�ϣ��ֵ
				PartSearchUtil.assignValue(subTitles, 2097152, articlePos2+10000000);
			}
			sb.setLength(0);
			authorList = null;
		}
	}
//	����xml�ļ�����
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
////		ˢ�»������������е�����������ļ�
//		try {
//			bOut1.flush();
//			bOut2.flush();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			try {
//				bOut1.close();
//				bOut2.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		System.out.println("�������---"+"������"+articlePos2+"С����"+articlePos1);
	}
}

