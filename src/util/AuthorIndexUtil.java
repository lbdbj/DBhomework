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
import entity.AuthorCount;
import entity.JudegeAuthor;

//�����������������ļ��Ĺ�����
public class AuthorIndexUtil {
	public static AuthorIndexUtil instance = new AuthorIndexUtil();
	
	public FileOutputStream out = null;
	public BufferedOutputStream bout = null;
	
	public static FileOutputStream outCount = null;
	public static BufferedOutputStream boutCount = null;
//	��¼������ϣ��ͻ�Ĵ���
	private static int conflict = 0;
//	��¼й¶�ļ�¼����
	private static int leftCount = 0;
//	���߾��������������鸳0ֵ
	public static void setZero() {
		for(int i1=0; i1<AllStatic.authorPos.length; i1++)
			for(int i2=0; i2<AllStatic.authorPos[0].length; i2++)
				AllStatic.authorPos[i1][i2]=0;
		System.out.println("�������");
	}
	
//���;��������������鸳0ֵ
		public static void setZero2() {
			for(int i1=0; i1<AllStatic.authorPos2.length; i1++)
				for(int i2=0; i2<AllStatic.authorPos2[0].length; i2++)
					AllStatic.authorPos2[i1][i2]=0;
			System.out.println("�������");
		}
			
//		��ȡ�ַ�����ϣֵ�ķ���,��ʱ��ȡ�Ĺ�ϣֵ�Ϳ�����Ϊ����������λ��
		public static int getAuthorPos(String str, int len) {
//			ע���Ȱ�ͷ����β���Ŀո�ȥ��
			str = str.trim();
//			���ַ���תΪ�ַ�����
			char[]vals = str.toCharArray();
//			��¼�ַ�����ĳ���
			int len2 = vals.length;
			int hashcode = 0;
			for(int i=0; i<len2; i++) {
				int val = (int)vals[i];
				hashcode = (hashcode<<5)+hashcode+val;
			}
			hashcode = (hashcode^(hashcode >>> 16)) & (len-1);
			if(hashcode<0)
				hashcode = -hashcode;
			return hashcode;
		}
		
//		�˷�������������������ȡ��ϣֵ�����������������µ�λ�ô浽�߾��ȹ�ϣ������
		public static boolean assignValue(String str, int pos) {
			int index = getAuthorPos(str, AllStatic.authorPos.length);
			for(int i=0; i<AllStatic.authorPos[0].length; i++) {
					if(AllStatic.authorPos[index][i] == 0) {
						AllStatic.authorPos[index][i] = pos;
						System.out.println("λ�ù�ϣֵ��"+index);
						return true;
					}
				}	
				conflict++;
				System.out.println("�����˳�ͻ����ͻ����"+conflict);
				return false;
		}
//		�˷�������������������ȡ��ϣֵ�����������������µ�λ�ô浽�;��ȹ�ϣ������
		public static boolean assignValue2(String str, int pos) {
			System.out.println("й¶���ݵĸ���----"+leftCount);
			int index = getAuthorPos(str, AllStatic.authorPos2.length);
			for(int i=0; i<AllStatic.authorPos2[0].length; i++) {
					if(AllStatic.authorPos2[index][i] == 0) {
						AllStatic.authorPos2[index][i] = pos;
						System.out.println("λ��Ϊ"+pos+"�����ݹ�ϣֵΪ"+index);
						return true;
					}
				}
			leftCount++;
			return false;
		}
		
		
		//�ñչ�ϣ�ķ�������������
		public static boolean recordAuthor(String str, int conflict) {

			//��getAuthorPos���������Ϸ�����ͻʱ������̽�⣬ͨ���������ֻ�ȡ�±�index
			//��ͻʱ���ö��γ�ͻ����̽��
			int index = (getAuthorPos(str, AllStatic.authorArray.length)+conflict*conflict) % AllStatic.authorArray.length;
			//�����鳤��ȡ�࣬��ֹ���
			
			//���authorArray[hashcode]�ǿյģ�û�з�����ͻ���Ͳ���
			if(AllStatic.authorArray[index] == null) {
				AllStatic.authorArray[index] = new AuthorCount(str);
				//System.out.println("Ϊ�գ�����"+str);
				return true;
			}
			
			//���authorArray[hashcode]���������ݣ�������ͬ�����ߣ��͸����ߵ�ƪ��+1��
			else if(AllStatic.authorArray[index].getName().equals(str)) {
				AllStatic.authorArray[index].plus();
				//System.out.println("ͬ������"+str);
				return true;
			}
			
			//���������ͻ��������̽��ķ�ʽ����Ѱַ��
			else {
				conflict++;
				//System.out.println("������ͻ�����¼���");
				return recordAuthor(str, conflict);
			}
		}
		
		
//	���ø߾������������ļ�ÿҳ������
	public String setPageContent(int index) {
		StringBuilder sb = new StringBuilder();
		int a[]= AllStatic.authorPos[index];
		for(int i=0; i<AllStatic.authorPos[0].length; i++) {
			if(a[i] == 0)
				break;
			else {
				sb.append(a[i]);
				sb.append('$');
			}
		}
		int len = sb.length();
//		ÿҳ�Ĵ洢�ռ�Ϊ10*AllStatic.authorPos[0].length�ֽڣ����û����������!���ռ�����
		for(int i=len; i<10*AllStatic.authorPos[0].length; i++)
			sb.append('!');
		return sb.toString();
	}
	
//	���õ;������������ļ�ÿҳ������
	public String setPageContent2(int index) {
		 StringBuilder sb = new StringBuilder();
		int a[]= AllStatic.authorPos2[index];
		for(int i=0; i<AllStatic.authorPos2[0].length; i++) {
			if(a[i] == 0)
				break;
			else {
				sb.append(a[i]);
				sb.append('$');
			}
		}
		int len = sb.length();
//		ÿҳ�Ĵ洢�ռ�ΪAllStatic.authorPos2[0].length*10�ֽڣ����û����������!���ռ�����
		for(int i=len; i<AllStatic.authorPos2[0].length*10; i++)
			sb.append('!');
		return sb.toString();
	}
	
	public void setFile() {
		try {
			File dir = new File("d:/DBhomework");
//			����ļ��в����ھʹ���
			if(!dir.exists()) {
				dir.mkdir();
			}
			File authorFile = new File(dir,"authorIndex.txt");
//			�ļ������ھʹ������ļ�
			if(!authorFile.exists())
					authorFile.createNewFile();
			
//			�����ļ����������
			out = new FileOutputStream(authorFile);
			bout = new BufferedOutputStream(out, 5242880);
			
			String str = null;
			for(int i=0; i<AllStatic.authorPos.length; i++){
					str = setPageContent(i);
					bout.write(str.getBytes());
				}
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
	
	public void setFile2() {
		try {
			File dir = new File("d:/DBhomework");
//			����ļ��в����ھʹ���
			if(!dir.exists()) {
				dir.mkdir();
			}
			File authorFile2 = new File(dir,"authorIndex2.txt");
//			�ļ������ھʹ������ļ�
			if(!authorFile2.exists())
				authorFile2.createNewFile();
			
//			�����ļ����������
			out = new FileOutputStream(authorFile2);
			bout = new BufferedOutputStream(out, 5242880);
			
			String str = null;
			for(int i=0; i<AllStatic.authorPos2.length; i++)
						 {
								str = setPageContent2(i);
								bout.write(str.getBytes());
							}
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
	
	//��100������������������д���ļ���
	public static void setFileCount(AuthorCount[] aTry) {
		try {
			File dir = new File("d:/DBhomework");
//			����ļ��в����ھʹ���
			if(!dir.exists()) {
				dir.mkdir();
			}
			File authorFile = new File(dir,"authorCount.txt");
//			�ļ������ھʹ������ļ�
			if(!authorFile.exists())
					authorFile.createNewFile();
			
//			�����ļ����������
			outCount = new FileOutputStream(authorFile);
			boutCount = new BufferedOutputStream(outCount, 5242880);
			
			String str = null;
			for(int i=0; i<aTry.length; i++){
					str = aTry[i].getName()+"|"+aTry[i].getCount()+"\r\n";
					boutCount.write(str.getBytes());
				}
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				boutCount.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static List<ArticleInfo> getArticleByFile(String author, RandomAccessFile rafAuthor1, RandomAccessFile rafSrc1,
			RandomAccessFile rafAuthor2,RandomAccessFile rafSrc2) {
		int pos = getAuthorPos(author, AllStatic.authorPos.length);
		List<ArticleInfo>list = new ArrayList<ArticleInfo>();
		String getAuthor = null;
		JudegeAuthor ja = new JudegeAuthor();
		int count = 0;
		try {
			rafAuthor1.seek(pos*400);
			byte bs[] = new byte[400];
			rafAuthor1.read(bs);
			System.out.println("��ȡ�������������ļ�������Ϊ---"+ new String(bs));
//			��ȡ�ָ��Ľ��,Ҫ��������ʽ����
			String results[] = new String(bs).split("\\$");
//			������ݵĸ���
			int len = results.length-1;
//			����ÿһ��λ���ж��Ƿ��ָ����author��ͬ
			for(int i=0; i<len; i++) {
				int val = Integer.parseInt(results[i]);
				if(val<10000000) {
					ja = SrcFileUtil.judgeAuthor(val, author, 500, rafSrc1);
					if(ja.isSame) {
						count++;
//						System.out.println("��"+count+"������");
						list.add(SrcFileUtil.formatArticle(ja.content));
					}
				}
				else {
					val = val-10000000;
					ja = SrcFileUtil.judgeAuthor(val, author, 5000, rafSrc2);
					if(ja.isSame) {
						count++;
						System.out.println("��"+count+"������");
						list.add(SrcFileUtil.formatArticle(ja.content));
					}
				}
			}
//			ͨ��һ���������ҵ������з���Ҫ���������Ϣ�ͷ���
			if(len<40)
				return list;
//				��һ�δ�������û���ҵ�������ȫ40����Ҫ���ڶ������������ļ���ȥ��
				else {
					pos = getAuthorPos(author, AllStatic.authorPos2.length);
					rafAuthor2.seek(pos*20000);
					byte bs2[] = new byte[20000];
					rafAuthor2.read(bs2);
					System.out.println("��ȡ���ı��������ļ�������Ϊ---"+ new String(bs2));
//					��ȡ�ָ��Ľ��,Ҫ��������ʽ����
					String results2[] = new String(bs2).split("\\$");
//					������ݵĸ���
					int len2 = results2.length-1;
//					����ÿһ��λ���ж��Ƿ��ָ����author��ͬ
					for(int i=0; i<len2; i++) {
						int val = Integer.parseInt(results2[i]);
						if(val<10000000) {
							ja = SrcFileUtil.judgeAuthor(val, author, 500, rafSrc1);
							if(ja.isSame) {
								count++;
								System.out.println("��"+count+"������");
								list.add(SrcFileUtil.formatArticle(ja.content));
							}
						}
						else {
							val = val-10000000;
							ja = SrcFileUtil.judgeAuthor(val, author, 5000, rafSrc2);
							if(ja.isSame) {
								count++;
								System.out.println("��"+count+"������");
								list.add(SrcFileUtil.formatArticle(ja.content));
							}
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
		ParseUtil.myParse("src\\dblp.xml");
		AuthorIndexUtil instance = AuthorIndexUtil.instance;
		System.out.println("�������鴴�����");
		System.out.println("��ͻ����Ϊ----"+conflict+"й¶���ݸ���----"+leftCount);
//		instance.setFile();
//		instance.setFile2();
		System.out.println("�����ļ��������");
		
	}
}
