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

//�����ȵ�����Ĺ�����
public class AnalysisUtil {
	public static AnalysisUtil instance = new AnalysisUtil();
	
	//�ļ������
	public static FileOutputStream out = null;
	public static BufferedOutputStream bout = null;
	/**�ȴʼ�¼�ļ�������¼���ܳ�
	 * 
	 */
	public static int LENGTH = 300;
	/**���ڼ�¼�ȴ���������
	 * YearList contains Year, [Year]->WordList
	 * WordList contains Wore, [Word]->{word,sum}
	 */
	public static YearList yearlist = new AnalysisClass().getYearList();


	/**���ڲ�д�ļ��ã���ȡĳ���ǰʮ�����������ַ�����
	 * @param index -��Ӧ��ݵ�����ֵ
	 * @return String������!��!��!��!...    ��,�ԡ� ���������ܳ�ΪLENGTH���ַ���
	 */
	private static String getWordsOfYear(int index) {
		int i = 0;
		StringBuilder sb = new StringBuilder();
		//��ȡǰʮ������
		WordList wl = yearlist.getWordsOfIndex(index, 10);
		int size = wl.size;
//		System.out.println("wl.size:"+wl.size);
//		System.out.println("wl.wordList.size():"+wl.wordList.size());
//		��!��!��!��...��!(�ո�������LENGTH���ֽ�)
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
//		ÿ��ݵ�����ȡ����500
		for(i = 0; i<len; i++) sb.append(" ");
		for(i = 0; i<times; i++) sb.append(blank);
		return sb.toString();
	}
	

	/**���ڲ�д�ļ��ã���ȡ��ݵ�˳���ַ������������ļ�������
	 * @return String �������!���!...������"!"�ָ������ַ���
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
	
	/**�����д�뵽�ļ�
	 * 
	 */
	public static void setFile() {
		try {
			File dir = new File("d:/DBhomework");
//			����ļ��в����ھʹ���
			if(!dir.exists()) {
				dir.mkdir();
			}
			File YearsFile = new File(dir,"AnalysisIndex.txt");
			File WordsFile = new File(dir,"AnalysisWords.txt");
//			�ļ������ھʹ������ļ�
			if(!YearsFile.exists())
				YearsFile.createNewFile();
			if(!WordsFile.exists())
				WordsFile.createNewFile();

			String str = null;
			//����WordList��д���ļ���
			out = new FileOutputStream(WordsFile);
			bout = new BufferedOutputStream(out, 5242880);
			for(int i=0; i<yearlist.size; i++){
				str=getWordsOfYear(i);
				bout.write(str.getBytes());
			}
			bout.close();
			
			//����yearlist�����������д���ļ���
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
	
	/**���ļ��ã����ļ��л�ÿ��д��ڵ����
	 * @param raf --���ļ����ļ���
	 * @return String[]:�ַ�����ʽ���������
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

	/**���ļ��ã����ļ��л�ö�Ӧ��ݵ��ȴʴ��λ��
	 * @param year -�ַ�����ʽ�����
	 * @param raf -���ļ����ļ���
	 * @return int: ��Ӧ��ݵ��ȴʴ��λ��
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
	
	/**���ļ��ã���ָ��λ�û�ȡ��Ӧ�ȴ��б�
	 * @param pos -ָ����λ��
	 * @param readLength -ÿ��λ�ü�¼�ĳ���
	 * @param raf -���ļ����ļ���
	 * @return String[20]: {�ʣ������ʣ���...}
	 * @throws IOException
	 */
	public static String[] getWordsOfPos(int pos, int readLength, RandomAccessFile raf) throws IOException{
		StringBuilder sb = new StringBuilder();
		try {
			//Ѱַ
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
		//ż��Ϊ���ʣ�����Ϊ����
		String[] words = sb.toString().replace(" ", "").split("!");
		return words;
	}
	
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ParseUtil.myParse("src\\dblp.xml");
		AnalysisUtil instance = AnalysisUtil.instance;
		System.out.println("�ȵ���б������");
		instance.setFile();
		System.out.println("�ȵ���ļ��������");
		
	}
}