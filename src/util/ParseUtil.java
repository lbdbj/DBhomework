package util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
	
	public static List<ArticleInfo> myParse(String pathName) throws ParserConfigurationException, SAXException, IOException {
SAXParserFactory factory = SAXParserFactory.newInstance();
		
		SAXParser parser = factory.newSAXParser();
		XMLReader xmlReader = parser.getXMLReader();
		xmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		ArticleHandler handler = new ArticleHandler();
		xmlReader.setContentHandler(handler);
		
		xmlReader.parse(new InputSource(pathName));
		list = handler.getArticles();
		
		return list;
	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ParseUtil.myParse("src\\dblp.xml");
}
}
class ArticleHandler extends DefaultHandler{
		private List<ArticleInfo> articles;
		private List<String>authorList;
		private StringBuilder sb = new StringBuilder();
		private StringBuilder contentBuilder = new StringBuilder();
		private static String str = new String();
//		用于判断一个article块是否结束
		private static int flag = 0;
		private String tag;
//		记录article块在源文件1中的位置
		private static int articlePos1=0;
//		记录article块在源文件2中的位置
		private static int articlePos2=0;
		
		private static String title = new String();

//		private static FileOutputStream out1 = null;
//		private static FileOutputStream out2 = null;
//		private static BufferedOutputStream bOut1 = null;
//		private static BufferedOutputStream bOut2 = null;
		private static int authorCount = 0;
		private static List<String> list = new ArrayList<String>();
		
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		AuthorIndexUtil.setZero();
		AuthorIndexUtil.setZero2();
		System.out.println("开始解析---");
//		try {
//			File dir = new File("d:/DBhomework");
////			如果文件夹不存在就创建
//			if(!dir.exists()) {
//				dir.mkdir();
//			}
//			File titleFile = new File(dir,"srcfile1.txt");
////			文件不存在就创建新文件
//			if(!titleFile.exists())
//					titleFile.createNewFile();
//			File dir2 = new File("d:/DBhomework");
////			如果文件夹不存在就创建
//			if(!dir2.exists()) {
//				dir2.mkdir();
//			}
//			File titleFile2 = new File(dir,"srcfile2.txt");
////			文件不存在就创建新文件
//			if(!titleFile2.exists())
//					titleFile2.createNewFile();
//			out1 = new FileOutputStream("d:/DBhomework/srcfile1.txt");
//			out2 = new FileOutputStream("d:/DBhomework/srcfile2.txt");
//			bOut1 = new BufferedOutputStream(out1,10000000);
//			bOut2 = new BufferedOutputStream(out2,5000000);
//			
//		} catch (IOException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
//		if(articlePos1 == 10)
//			System.exit(0);
		if(null != qName) {
			tag = qName;
			if("inproceedings".equals(tag) || "article".equals(tag)) {
				authorList = new ArrayList<String>();
				flag = 1;
				int c = articlePos1+1;
				System.out.println("第"+c+"篇article-------------------------------------------------");
			}
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	// TODO Auto-generated method stub
		super.characters(ch, start, length);
		if(null != tag && flag == 1)
			contentBuilder.append(ch,start,length);
		
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
	// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		String content = contentBuilder.toString();
		content = content.trim();
		contentBuilder.setLength(0);
		if(null != tag && flag == 1) {
			if(tag.equals("author")) {

					authorCount++;
					authorList.add(content);
					sb.append('!');
					sb.append(content);
					sb.append("\r\n");
					System.out.println(tag+":"+content);

			}
			else if(tag.equals("title")) {
				title = content;
				sb.append('*');
				sb.append(content);
				sb.append("\r\n");
				System.out.println(tag+":"+content);
				}
			else if(tag.equals("booktitle") || tag.equals("journal")) {
				sb.append('@');
				sb.append(content);
				sb.append("\r\n");
				System.out.println(tag+":"+content);
			}
			else if(tag.equals("volume")) {
				sb.append('#');
				sb.append(content);
				sb.append("\r\n");
				System.out.println(tag+":"+content);
			}
			else if(tag.equals("year")) {
				sb.append('$');
				sb.append(content);
				sb.append("\r\n");
				System.out.println(tag+":"+content);
			}
			else if(tag.equals("pages")) {
				sb.append('%');
				sb.append(content);
				sb.append("\r\n");
				System.out.println(tag+":"+content);
			}
			else if(tag.equals("ee")) {
				sb.append('^');
				sb.append(content);
				sb.append("\r\n");
				System.out.println(tag+":"+content);
			}
		}
		tag = null;
		if("inproceedings".equals(qName) || "article".equals(qName)) {
			System.out.println("作者个数为"+authorCount);
			flag = 0;
			int len = sb.length();
			if(len<=500) {
////				向源文件1写入数据
//				str = SrcFileUtil.formatStr(sb,500);
//				try {
//					byte[] bs = str.getBytes();
//					int lenb = bs.length;
//					System.out.println(lenb);
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
				articlePos1++;
				for(String author : authorList) {
					if(!AuthorIndexUtil.assignValue(author,articlePos1)) {
						boolean b = AuthorIndexUtil.assignValue2(author, articlePos1);
						if(b == false)
							list.add(author);
					}
				}
//				if(!TitleIndexUtil.assignValue(title, articlePos1)) {
//					TitleIndexUtil.assignValue2(title,articlePos1);
//					}
			}else {
//				向源文件2写入数据
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
				for(String author : authorList) {
					if(!AuthorIndexUtil.assignValue(author,articlePos2+10000000)) {
						AuthorIndexUtil.assignValue2(author, articlePos2+10000000);			
					}
				}
//				if(!TitleIndexUtil.assignValue(title, articlePos2+10000000)) {
//					TitleIndexUtil.assignValue2(title,articlePos2+10000000);
//					}
			}
			sb.setLength(0);
			authorList = null;
		}
	}
	
	@Override
	public void endDocument() throws SAXException {
	// TODO Auto-generated method stub
		super.endDocument();
//		刷新缓存流将缓存中的数据输出到文件
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
		System.out.println("解析完成---------------------------------");
//		for(String s : list)
//			System.out.println("泄露作者名为"+s);
	}

	public List<ArticleInfo> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleInfo> articles) {
		this.articles = articles;
	}
}

