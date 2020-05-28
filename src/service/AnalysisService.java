package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import util.AnalysisUtil;

public class AnalysisService {
	/**��ǰ�˽��������ؿ��д��ڵ��ȴ�����ַ�������
	 * @return String[]��{��ݣ���ݣ�...}
	 * @throws FileNotFoundException 
	 */
	public static String[] getYearItems() throws IOException {
		File IndexFile = new File(AnalysisUtil.DIR_PATH+"\\"+AnalysisUtil.YEARS_PATH);
		RandomAccessFile raf = new RandomAccessFile(IndexFile, "r");
		return AnalysisUtil.getYearItem(raf);
	}
	
	/**��ǰ�˽��������ض�Ӧ��ݵ��ȴ��б�����λ��
	 * @param year -���
	 * @return int -�ȴ��б�λ��
	 * @throws IOException
	 */
	public static int getPosByYear(String year) throws IOException {
		File IndexFile = new File(AnalysisUtil.DIR_PATH+"\\"+AnalysisUtil.YEARS_PATH);
		RandomAccessFile raf = new RandomAccessFile(IndexFile, "r");
		return AnalysisUtil.getPosOfWords(year, raf);
	}
	/**��ǰ�˽���������ָ��λ�õ��ȴ��б�
	 * @param pos -��getPosByYear��ȡ��ָ��λ��
	 * @return String[20]:{�ʣ������ʣ���...}
	 * @throws IOException
	 */
	public static String[] getWordsByPos(int pos) throws IOException {
		File WordsFile = new File(AnalysisUtil.DIR_PATH+"\\"+AnalysisUtil.WORDS_PATH);
		RandomAccessFile raf = new RandomAccessFile(WordsFile, "r");
		return AnalysisUtil.getWordsOfPos(pos, AnalysisUtil.LENGTH, raf);
	}
	
	
}
