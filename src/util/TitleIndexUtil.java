package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import entity.ArticleInfo;

//�������±��������ļ��Ĺ�����
public class TitleIndexUtil {
	
//	����ģʽ
	public static TitleIndexUtil instance = new TitleIndexUtil();
	
	public FileOutputStream out = null;
	public BufferedOutputStream bout = null;
	
//		��ȡ�ַ�����ϣֵ�ķ���,��ʱ��ȡ�Ĺ�ϣֵ�Ϳ�����Ϊ����������λ��
		public static int getTitlePos(String str, int len) {
//			ע���Ȱ�ͷ����β���Ŀո�ȥ��
			str = str.trim();
//			�ѱ�������ĵ�ȥ��
			if(str.length()>0 && str.charAt(str.length()-1)=='.')
				str = str.substring(0, str.length()-1);
			char[]vals = str.toCharArray();
			int len2 = vals.length;
			int hashcode = 0;
			for(int i=0; i<len2; i++) {
				if(i == len2)
					break;
				int val = (int)vals[i];
				hashcode = (hashcode << 5)-hashcode+val;
			}
			hashcode = (hashcode^(hashcode >>> 16)) & (len-1);
			if(hashcode<0)
				hashcode = -hashcode;
			return hashcode;
		}
		
//		�����������������ļ�ʱ���ڴ��еĹ�ϣ��ֵ
		public static void assignValue(String str, int len, int pos) {
//			��ȡ���������ַ����Ĺ�ϣֵ������ϣ���е�һ����
			int hashcode = getTitlePos(str, len);
//			�����ϣ���а��������
			if(AllStatic.map.containsKey(hashcode))
//				���������Ӧ��ֵ����������ϣֵ
				AllStatic.map.get(hashcode).add(pos);
			else {
//				�����½�һ����ֵ��
				List<Integer>list = new ArrayList<Integer>();
				list.add(pos);
				AllStatic.map.put(hashcode, list);
			}
			
		}
	
//		���ݸ����ı����srcfile�л�ȡ���а�����������������Ϣ
	public static List<ArticleInfo> getArticleByFile(String title, RandomAccessFile rafTitle1, RandomAccessFile rafSrc1,
			RandomAccessFile rafTitle2,RandomAccessFile rafSrc2) {
		List<ArticleInfo>list = new ArrayList<ArticleInfo>();
		String getTitile = null;
		try {
//			��ȡ����ָ���������µĵ�ַ
			int allPos[] = IndexFileUtil.getAllPos(title, rafTitle1, rafTitle2, 8388608);
//			����ÿһ����ַ�ж��Ƿ��ָ����title��ͬ
			for(int i=0; i<allPos.length; i++) {
				int val = allPos[i];
//				�����ֵַС��10000000�͵�srcfile1�ļ��в���
				if(val<10000000) {
					getTitile = SrcFileUtil.getTitleFromSrc(val, 500, rafSrc1);
//					���������ϣ��ͽ�������Ϣ��ʽ����ArticleInfo���󲢴���List��
					if(getTitile.equals(title)) {
						list.add(SrcFileUtil.formatArticle(SrcFileUtil.getAllInfo(val, 500, rafSrc1)));
					}
				}
				else {
//					�����ֵַ����10000000�͵�srcfile2�ļ��в���
					val = val-10000000;
					getTitile = SrcFileUtil.getTitleFromSrc(val, 5000, rafSrc2);
					if(getTitile.equals(title)) {
						list.add(SrcFileUtil.formatArticle(SrcFileUtil.getAllInfo(val, 5000, rafSrc2)));
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ParseUtil.myParse("D:\\DBhomework\\dblp.xml");
		IndexFileUtil instace = new IndexFileUtil();
		instace.setFile1(8388608, "titleIndex1.txt");
		instace.setFile2(8388608, "titleIndex2.txt");
	}
}
