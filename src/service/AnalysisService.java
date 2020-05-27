package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import util.AnalysisUtil;

public class AnalysisService {
	/**与前端交互，返回库中存在的热词年份字符串数组
	 * @return String[]：{年份，年份，...}
	 * @throws FileNotFoundException 
	 */
	public static String[] getYearItems() throws IOException {
		File IndexFile = new File(AnalysisUtil.DIR_PATH+"\\"+AnalysisUtil.YEARS_PATH);
		RandomAccessFile raf = new RandomAccessFile(IndexFile, "r");
		return AnalysisUtil.getYearItem(raf);
	}
	
	/**与前端交互，返回对应年份的热词列表所在位置
	 * @param year -年份
	 * @return int -热词列表位置
	 * @throws IOException
	 */
	public static int getPosByYear(String year) throws IOException {
		File IndexFile = new File(AnalysisUtil.DIR_PATH+"\\"+AnalysisUtil.YEARS_PATH);
		RandomAccessFile raf = new RandomAccessFile(IndexFile, "r");
		return AnalysisUtil.getPosOfWords(year, raf);
	}
	/**与前端交互，返回指定位置的热词列表
	 * @param pos -从getPosByYear获取的指定位置
	 * @return String[20]:{词，数，词，数...}
	 * @throws IOException
	 */
	public static String[] getWordsByPos(int pos) throws IOException {
		File WordsFile = new File(AnalysisUtil.DIR_PATH+"\\"+AnalysisUtil.WORDS_PATH);
		RandomAccessFile raf = new RandomAccessFile(WordsFile, "r");
		return AnalysisUtil.getWordsOfPos(pos, AnalysisUtil.LENGTH, raf);
	}
	
	
}
