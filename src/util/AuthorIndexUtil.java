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
import entity.Judege;


//�����������������ļ��Ĺ�����
public class AuthorIndexUtil {
	public static AuthorIndexUtil instance = new AuthorIndexUtil();
	
	public FileOutputStream out = null;
	public BufferedOutputStream bout = null;

			
//		��ȡ�ַ�����ϣֵ�ķ���,��ʱ��ȡ�Ĺ�ϣֵ�Ϳ�����Ϊ����������λ�ã�Ҳ�����������Ĺ�ϣֵ��
		public static int getAuthorPos(String str, int len) {
//			ע���Ȱ�ͷ����β���Ŀո�ȥ��
			str = str.trim();
//			���ַ���תΪ�ַ�����
			char[]vals = str.toCharArray();
//			��¼�ַ�����ĳ���
			int len2 = vals.length;
			int hashcode = 0;
//			�����ַ�����
			for(int i=0; i<len2; i++) {
				int val = (int)vals[i];
				hashcode = (hashcode<<5)+hashcode+val;
			}
			hashcode = (hashcode^(hashcode >>> 16)) & (len-1);
			if(hashcode<0)
				hashcode = -hashcode;
			return hashcode;
		}

//		�����������������ļ�ʱ���ڴ��еĹ�ϣ��ֵ
		public static void assignValue(String str, int len, int pos) {
//			��ȡ���������ַ����Ĺ�ϣֵ������ϣ���е�һ����
			int hashcode = IndexFileUtil.getPos(str, len);
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

//	���ݸ�������������srcfile�л�ȡ���а���������ߵ�������Ϣ

	public static List<ArticleInfo> getArticleByFile(String author, RandomAccessFile rafAuthor1, RandomAccessFile rafSrc1,
			RandomAccessFile rafAuthor2,RandomAccessFile rafSrc2) {
		List<ArticleInfo>list = new ArrayList<ArticleInfo>();
		Judege ja = new Judege();
		try {
//			�Ȼ�ȡ���µ�ַ����
			int allPos[] = IndexFileUtil.getAllPos(author, rafAuthor1, rafAuthor2, 4194304);
//			����ÿһ��λ���ж��Ƿ��ָ����author��ͬ
			for(int i=0; i<allPos.length; i++) {
				int val = allPos[i];
//				�����ֵַС��10000000�͵�srcfile1�ļ��в���
				if(val<10000000) {
					ja = SrcFileUtil.judgeAuthor(val, author, 500, rafSrc1);
//					������������ϣ��ͽ�������Ϣ��ʽ����ArticleInfo���󲢴���List��
					if(ja.isSame) {
						list.add(SrcFileUtil.formatArticle(ja.content));
					}
				}
				else {
//					�����ֵַ����10000000�͵�srcfile2�ļ��в���
					val = val-10000000;
					ja = SrcFileUtil.judgeAuthor(val, author, 5000, rafSrc2);
					if(ja.isSame) {
						list.add(SrcFileUtil.formatArticle(ja.content));
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
		instace.setFile1(4194304, "authorIndex1.txt");
		instace.setFile2(4194304, "authorIndex2.txt");
	}
}
