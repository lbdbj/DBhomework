package util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import entity.ArticleInfo;
import entity.JudegeAuthor;
import util.AnalysisClass.WordList;
import util.AnalysisClass.YearList;

//用于热点分析的工具类
public class AnalysisUtil {
	public static AnalysisUtil instance = new AnalysisUtil();
	
	//文件输出类
	public static FileOutputStream out = null;
	public static BufferedOutputStream bout = null;
	/**热词记录文件单个记录的总长
	 * 
	 */
	public static int LENGTH = 300;
	/**用于记录热词数量的类
	 * YearList contains Year, [Year]->WordList
	 * WordList contains Wore, [Word]->{word,sum}
	 */
	public static YearList yearlist = new AnalysisClass().getYearList();


	/**供内部写文件用：获取某年的前十单词排名到字符串中
	 * @param index -对应年份的索引值
	 * @return String：“词!数!词!数!...    ”,以“ ”补齐至总长为LENGTH的字符串
	 */
	private static String getWordsOfYear(int index) {
		int i = 0;
		StringBuilder sb = new StringBuilder();
		//获取前十的排名
		WordList wl = yearlist.getWordsOfIndex(index, 10);
		int size = wl.size;
//		System.out.println("wl.size:"+wl.size);
//		System.out.println("wl.wordList.size():"+wl.wordList.size());
//		词!数!词!数...词!(空格填满至LENGTH个字节)
//		sb.append(yearlist.list.get(index).year);
//		sb.append("!");
		for(i = 0; i<size; i++) {
			sb.append(wl.wordList.get(i).word);
			sb.append("!");
			sb.append(wl.wordList.get(i).sum);
			sb.append("!");
		}
//		System.out.println("sb.length():"+sb.length());
		String blank = "                    ";
		int len = (LENGTH-sb.length()) % blank.length();
		int times = (LENGTH-sb.length()) / blank.length();
//		每年份的内容取定长500
		for(i = 0; i<len; i++) sb.append(" ");
		for(i = 0; i<times; i++) sb.append(blank);
		return sb.toString();
	}
	

	/**供内部写文件用：获取年份的顺序字符串，用作读文件的索引
	 * @return String ：“年份!年份!...”，以"!"分割的年份字符串
	 */
	private static String getIndexOfYear() {
		int i = 0;
		StringBuilder sb = new StringBuilder();
		for(i=0;i<yearlist.size;i++) {
			sb.append(yearlist.list.get(i).year);
			sb.append("!");
		}
		return sb.toString();
	}
	
	/**将结果写入到文件
	 * 
	 */
	public static void setFile() {
		try {
			File dir = new File("d:/DBhomework");
//			如果文件夹不存在就创建
			if(!dir.exists()) {
				dir.mkdir();
			}
			File YearsFile = new File(dir,"AnalysisIndex.txt");
			File WordsFile = new File(dir,"AnalysisWords.txt");
//			文件不存在就创建新文件
			if(!YearsFile.exists())
				YearsFile.createNewFile();
			if(!WordsFile.exists())
				WordsFile.createNewFile();

			String str = null;
			//解析WordList并写到文件流
			out = new FileOutputStream(WordsFile);
			bout = new BufferedOutputStream(out, 5242880);
			for(int i=0; i<yearlist.size; i++){
				str=getWordsOfYear(i);
				bout.write(str.getBytes());
			}
			bout.close();
			
			//解析yearlist的年份索引并写到文件中
			out = new FileOutputStream(YearsFile);
			bout = new BufferedOutputStream(out, 5242880);
			str=getIndexOfYear();
			bout.write(str.getBytes());
			
			
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				bout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**读文件用：从文件中获得库中存在的年份
	 * @param raf --读文件的文件流
	 * @return String[]:字符串形式的年份数组
	 */
	public static String[] getYearItem(RandomAccessFile raf) {
		String[] yearArray = null;
		try {
			yearArray = raf.readLine().split("!");
		}catch(IOException e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
		return yearArray;
	}

	/**读文件用：从文件中获得对应年份的热词存放位置
	 * @param year -字符串形式的年份
	 * @param raf -读文件的文件流
	 * @return int: 对应年份的热词存放位置
	 * @throws IOException
	 */
	public static int getPosOfWords(String year, RandomAccessFile raf) throws IOException{
		int pos = -1;
		try {
			String[] yearArray = raf.readLine().split("!");
			List<String> yearList = Arrays.asList(yearArray);
			pos = yearList.indexOf(year);
			System.out.println(pos);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pos + 1;
	}
	
	/**读文件用：从指定位置获取对应热词列表
	 * @param pos -指定的位置
	 * @param readLength -每个位置记录的长度
	 * @param raf -读文件的文件流
	 * @return String[20]: {词，数，词，数...}
	 * @throws IOException
	 */
	public static String[] getWordsOfPos(int pos, int readLength, RandomAccessFile raf) throws IOException{
		StringBuilder sb = new StringBuilder();
		try {
			//寻址
			raf.seek(pos*readLength);
			byte[] content = new byte[readLength];
			int result = raf.read(content);
			if(result == readLength) {
				for(byte b : content) {
					sb.append((char)b);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//偶数为单词，奇数为数量
		String[] words = sb.toString().replace(" ", "").split("!");
		return words;
	}
	
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ParseUtil.myParse("src\\dblp.xml");
		AnalysisUtil instance = AnalysisUtil.instance;
		System.out.println("热点词列表创建完成");
		instance.setFile();
		System.out.println("热点词文件创建完成");
		
	}
}