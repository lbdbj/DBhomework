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
import entity.AuthorCount;

import entity.AnalysisClass.YearList;


public class ParseUtil{
	public static List<ArticleInfo>list;
	public static void myParse(String pathName) throws ParserConfigurationException, SAXException, IOException {
//		创建SAX解析器的工厂类
		SAXParserFactory factory = SAXParserFactory.newInstance();
//		获得SAX解析器

		SAXParser parser = factory.newSAXParser();
//		获取xml文件解析器
		XMLReader xmlReader = parser.getXMLReader();
//		忽略dtd文件
		xmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
//		新建自定义的解析器对象
		ArticleHandler handler = new ArticleHandler();
//		指定解析器
		xmlReader.setContentHandler(handler);
//		开始解析指定文件
		xmlReader.parse(new InputSource(pathName));
		
	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
//		AllStatic.setFileFlag = 1;
//		ParseUtil.myParse("D:\\DBhomework\\dblp.xml");
	}
}
class ArticleHandler extends DefaultHandler{
		private List<String>authorList;
		private StringBuilder sb = new StringBuilder();
		private StringBuilder contentBuilder = new StringBuilder();
//		用于判断一个article块是否结束
		private int flag = 0;
		private String tag;
//		记录article块在srcfile1中的位置
		private int articlePos1=0;
//		记录article块在srcfile2中的位置
		private int articlePos2=0;

//		记录文章标题

		private static String title = new String();

//		文章的年份
		private static short year = 0;
//		年份索引标识
		private static int indexYear;
//		每年的热词列表
		private static String[] words;
		//计数器
//		private static int count = 0;

		private FileOutputStream out1 = null;
		private FileOutputStream out2 = null;
		private BufferedOutputStream bOut1 = null;
		private BufferedOutputStream bOut2 = null;
		
		private String str = null;
//		记录切割标题后的关键词
		private String[] subTitles = null;
		
	@Override
//	开始解析xml文件
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("开始解析---");
		if(AllStatic.setFileFlag == 1) {
		try {
			File dir = new File("d:/DBhomework");
//			如果文件夹不存在就创建
			if(!dir.exists()) {
				dir.mkdir();
			}
			File srcFile = new File(dir,"srcfile1.txt");
//			文件不存在就创建新文件
			if(!srcFile.exists())
					srcFile.createNewFile();
			File dir2 = new File("d:/DBhomework");
//			如果文件夹不存在就创建
			if(!dir2.exists()) {
				dir2.mkdir();
			}
			File titleFile2 = new File(dir,"srcfile2.txt");
//			文件不存在就创建新文件
			if(!titleFile2.exists())
					titleFile2.createNewFile();
			out1 = new FileOutputStream("d:/DBhomework/srcfile1.txt");
			out2 = new FileOutputStream("d:/DBhomework/srcfile2.txt");
//		                           创建缓冲流对象
			bOut1 = new BufferedOutputStream(out1,10000000);
			bOut2 = new BufferedOutputStream(out2,5000000);
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}
	}
	
	@Override
//	开始解析一个开始标签
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if(null != qName) {
			tag = qName;
//			如果获取的tag为article或者inproceedings代表一篇文章开始
			if("inproceedings".equals(tag) || "article".equals(tag)) {
//				新建一个暂存作者的list
				authorList = new ArrayList<String>();
//				flag为1代表当前正在解析一篇符合要求的文章
				flag = 1;
				int c = articlePos1+1;
				System.out.println("第"+c+"篇article---");
			}
		}
	}
	
	@Override
//	获取开始标签和结束标签中间的内容
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
//		flag为1代表当前正在解析一个符合要求的article
		if(null != tag && flag == 1) {
//			根据解析到的tag的内容，分别在内容前面添加不同的标识符
			if(tag.equals("author")) {
					authorList.add(content);
					sb.append('!');
					sb.append(content);
					sb.append("\r\n");
			}
			else if(tag.equals("title")) {
//				获取文章标题
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
//				获取文章年份，格式为short
				year = Short.parseShort(content);
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

//------------------------------------------------------------------------------------------------
//			向热词年份表插入数据
			if(AllStatic.setFileFlag==5) {
//			获取索引
			indexYear=AnalysisUtil.yearlist.getIndexOf(year);
//			插入新年份
			if(indexYear==-1) {
				indexYear=AnalysisUtil.yearlist.addYear(year);
			}
			
			AnalysisUtil.yearlist.addWords(indexYear, title);
		}
//------------------------------------------------------------------------------------------------
//			System.out.println("count:"+(++count));
			
//			System.out.println("作者个数为"+authorCount);
//			解析一篇文章结束令flag为0
			flag = 0;
			
			int len = sb.length();
//			如果文章中包含的字节数小于500
			if(len<=500) {
				if(AllStatic.setFileFlag==1) {
//				向srcfile1写入文章信息
				str = SrcFileUtil.formatStr(sb,500);
				try {
					byte[] bs = str.getBytes();
					int lenb = bs.length;
					if(lenb > 500) {
						byte bs2[] = new byte[500];
						for(int i=0; i<500; i++)
							bs2[i] = bs[i];
						bOut1.write(bs2);
					}else if (lenb<500) {
						byte bs2[] = new byte[500];
						for(int i=0;i<500;i++) {
							if(i<lenb)
								bs2[i] = bs[i];
							else {
								bs2[i] =' ';
							}
						}
						bOut1.write(bs2);
					}
					else {
						bOut1.write(bs);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
//				文章地址自增
				articlePos1++;
//				向标题哈希表中赋值
				if(AllStatic.setFileFlag==2) {
					TitleIndexUtil.assignValue(title, 8388608, articlePos1);
				}
//				向作者哈希表中赋值
				if(AllStatic.setFileFlag==3) {
				for(String author : authorList) {
						AuthorIndexUtil.assignValue(author, 4194304, articlePos1);
						if(AllStatic.setFileFlag==6) {
					//用哈希建立索引
					AuthorCountUtil.recordAuthor(author, 0);
						}
					}
				}
				if(AllStatic.setFileFlag==4) {
//				切分标题
				subTitles = PartSearchUtil.subTitle(title);
//				向部分匹配搜索的关键词哈希表赋值
				PartSearchUtil.assignValue(subTitles, 2097152, articlePos1);
				}
			}else {
////				向srcfile2写入文章信息
				if(AllStatic.setFileFlag==1) {
				str = SrcFileUtil.formatStr(sb,5000);
				try {
					byte[] bs = str.getBytes();
					int lenb = bs.length;
					if(lenb > 5000) {
						byte bs2[] = new byte[5000];
						for(int i=0; i<5000; i++)
							bs2[i] = bs[i];
						bOut2.write(bs2);
					}else if (lenb<5000) {
						byte bs2[] = new byte[5000];
						for(int i=0;i<5000;i++) {
							if(i<lenb)
								bs2[i] = bs[i];
							else {
								bs2[i] =' ';
							}
						}
						bOut2.write(bs2);
					}
					else {
						bOut2.write(bs);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
				}
				articlePos2++;
//				向标题哈希表中赋值
				if(AllStatic.setFileFlag==2) {
					TitleIndexUtil.assignValue(title, 8388608, articlePos2+10000000);
				}
//				向作者哈希表中赋值
				if(AllStatic.setFileFlag==3) {
				for(String author : authorList) {
					if(AllStatic.setFileFlag==6) {
						//用哈希建立索引
						AuthorCountUtil.recordAuthor(author, 0);
							}
					AuthorIndexUtil.assignValue(author, 4194304, articlePos2+10000000);
					}
				}
//				切分标题
				if(AllStatic.setFileFlag==4) {
				subTitles = PartSearchUtil.subTitle(title);
//				向部分匹配搜索的关键词哈希表赋值
				PartSearchUtil.assignValue(subTitles, 2097152, articlePos2+10000000);
				}


			}

			sb.setLength(0);
			authorList = null;  

		}

	}
//	解析xml文件结束
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		if(AllStatic.setFileFlag==1) {
//		刷新缓存流将缓存中的数据输出到文件
		try {
			bOut1.flush();
			bOut2.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				bOut1.close();
				bOut2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		System.out.println("解析完成---"+"大文章"+articlePos2+"小文章"+articlePos1);
		System.out.println("解析完成---------------------------------");

////		创建热词分析的索引文件
//		AnalysisUtil.setFile();
//		
//
//		
//		//将所有数据从哈希表中取出，通过维护一个100数据大小的最小堆，得到最大的100个数据
//		AuthorCount[] aTry = AuthorCountUtil.topK(AllStatic.authorArray, 100);
//		
//		//将一百个数据写进txt文件
//		AuthorCountUtil.setFileCount(aTry);
		

	}
}

