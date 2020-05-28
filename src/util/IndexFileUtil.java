package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class IndexFileUtil {
	private BufferedOutputStream bout1 = null;
	private BufferedOutputStream bout2 = null;
	private FileOutputStream out1 = null;
	private FileOutputStream out2 = null;
//	����ĳ���ַ�����ȡhashcode,��ϣ��Ĵ�СΪlen
	public static int getPos(String str, int len) {
//		���ַ���תΪ�ַ�����
		char[]vals = str.toCharArray();
//		��¼�ַ�����ĳ���
		int len2 = vals.length;
		int hashcode = 0;
//		�����ַ�������
		for(int i=0; i<len2; i++) {
//			��ȡ�ַ���ASCII��ֵ
			int val = (int)vals[i];
//			�µĹ�ϣֵ�ǣ�ԭ��ϣֵ*31+ASCII��ֵ��
			hashcode = (hashcode<<5)-hashcode+val;
		}
//		����ϣֵ����16λ����ԭ��ϣֵ���������ٶ�lenȡ��
		hashcode = (hashcode^(hashcode >>> 16)) & (len-1);
//		��֤��ϣֵ������
		if(hashcode<0)
			hashcode = -hashcode;
		return hashcode;
	}
	
//	���������ļ�1ÿҳ������
	public String setPageContent1(List<Integer>list) {
		StringBuilder sb = new StringBuilder();
			for(Integer i : list) {
				sb.append(i);
				sb.append("$");
			}
		for(int i=sb.length();i<list.size()*10;i++)
			sb.append('!');
		return sb.toString();
	}
	
//	���������ļ�2ÿҳ������
	public String setPageContent2(int i) {
		StringBuilder sb = new StringBuilder();
		sb.append(i);
		for(int a=sb.length();a<10;a++)
			sb.append("#");
		return sb.toString();
	}
//	�������ļ�1��д����
	public void setFile1(int len, String path) {
		try {
			File dir = new File("d:/DBhomework");
//			����ļ��в����ھʹ���
			if(!dir.exists()) {
				dir.mkdir();
			}
			File file = new File(dir,path);
//			�ļ������ھʹ������ļ�
			if(file.exists())
					file.createNewFile();
			
//			�����ļ����������
			out1 = new FileOutputStream(file);
//			����������
			bout1 = new BufferedOutputStream(out1, 5242880);
			String str = null;
//			������ϣ��
			for(int i=0; i<len; i++) {
				if(AllStatic.map.containsKey(i)) {
//					�ѹ�ϣ���е�����ת��Ϊ�ַ���
					str = setPageContent1(AllStatic.map.get(i));
//					����ϣ��������д�������ļ�1
					bout1.write(str.getBytes());
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bout1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
//	�������ļ�2д������
	public void setFile2(int len, String path) {
		try {
			File dir = new File("d:/DBhomework");
//			����ļ��в����ھʹ���
			if(!dir.exists()) {
				dir.mkdir();
			}
			
			File file = new File(dir,path);
//			�ļ������ھʹ������ļ�
			if(file.exists())
					file.createNewFile();
//			�����ļ����������
			out2 = new FileOutputStream(file);
//			��������������
			bout2 = new BufferedOutputStream(out2);
			String str = null;
			int pos = 1;
//			������ϣ��
			for(int i=0; i<len; i++) {
//				��posת��Ϊ�ַ���
				str = setPageContent2(pos);
//				�������ļ�2д����ʼλ��
				bout2.write(str.getBytes("UTF-8"));
				if(AllStatic.map.containsKey(i)) {
//					��ȡi+1�������ļ�1��Ӧ�������ʼλ��
					pos += AllStatic.map.get(i).size();
				}
			}
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				bout2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
//	��ȡ���ܰ���ָ���ַ������������µĵ�ַ
	public static int[] getAllPos (String word, RandomAccessFile raf1, RandomAccessFile raf2, int len) throws IOException {
		int hashcode = getPos(word, len);
		byte[] begByte = new byte[10];
		byte[] endByte = new byte[10];
		raf2.seek(hashcode*10);
//		��hashcode��Ӧ����index1�ļ��е���ʼλ�ö���
		raf2.read(begByte);
//		��hashcode��Ӧ����index1�ļ��еĽ���λ�ö���
		raf2.read(endByte);
//		��ȡhashcode��Ӧ����index1�ļ��е���ʼλ��
		int beg = parseIndexFile2(begByte);
//		��ȡhashcode��Ӧ����index1�ļ��еĽ���λ��
		int end = parseIndexFile2(endByte);
		raf1.seek((beg-1)*10);
		
		byte[] allPosByte = new byte[(end-beg)*10];
//		��index1��Ӧλ�õ�����ȫ����ȡ
		raf1.read(allPosByte);
//		��ȡ���������µ�λ��
		int[] allPos = parseIndexFile1(allPosByte);
		return allPos;
	}
	
//	����indexFile1�ļ���ÿҳ������
	public static int[] parseIndexFile1(byte[]bs) {
//		��$�и�ÿ�����µ�ַ
		String allPos[] = new String(bs).split("\\$");
		int arr[] = new int[allPos.length-1];
		for(int i=0;i<arr.length;i++)
			arr[i] = Integer.parseInt(allPos[i]);
		return arr;
	}
	
//	����indexFile2�ļ���ÿҳ������
	public static int parseIndexFile2(byte[]bs) {
		StringBuilder sb = new StringBuilder();
		char[] cs = new String(bs).toCharArray();
		for(char c : cs) {
			if(c != '#')
				sb.append(c);
		}
		return Integer.parseInt(sb.toString());
	}
	
}
