package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import entity.Judege;

public class PartSearchUtil {
	
	private BufferedOutputStream bout1 = null;
	private BufferedOutputStream bout2 = null;
	private FileOutputStream out1 = null;
	private FileOutputStream out2 = null;
	
//	����ƥ������ʱ���õ��ģ��и����Ĺ��ߺ���
	public static String[] subTitle(String str) {
		StringBuilder sb = new StringBuilder();
		String[]subTitles = str.split("\\s+");
		List<String>list = new ArrayList<String>();
		for(int i=0; i<subTitles.length; i++) {
			subTitles[i] = subTitles[i].toLowerCase();
			if(!judgeWord(subTitles[i]))
				continue;
			char[]arrs = subTitles[i].toCharArray();
			for(char c : arrs) {
				if((c>=97 && c<=122)||(c==45)||(c==43))
					sb.append(c);
			}
			list.add(sb.toString());
			sb.setLength(0);
		}
		String strs[] = new String[list.size()];
		for(int i=0;i<strs.length;i++)
			strs[i] = list.get(i);
		return strs;
		
	}
	
//	�ڷָ�ʺ��жϵ����Ƿ����ʹ�ã�����ڴʡ���ʡ����ʡ���̾�����൥�ʲ�����ӽ�ȥ��
	public static boolean judgeWord(String word) {
		char[]arr = word.toCharArray();
//		���õĵ��ʼ���
		String uselessWords = "of for and a in the on with using to an based from by via towards through as over is "
				+ "under at is two how into their it or during are can that use them but";
		String uselessArr[] = uselessWords.split("\\s+");
		int len=0;
		for(char c : arr)
			if((c>=97 && c<=122)||c==43)
				len++;
		if(len<2)
			return false;
		for(String str : uselessArr)
			if(word.equals(str))
				return false;
		return true;
	}
	public static int getPos(String str, int len) {
//		���ַ���תΪ�ַ�����
		char[]vals = str.toCharArray();
//		��¼�ַ�����ĳ���
		int len2 = vals.length;
		int hashcode = 0;
		for(int i=0; i<len2; i++) {
			int val = (int)vals[i];
			hashcode = (hashcode<<5)-hashcode+val;
		}
		hashcode = (hashcode^(hashcode >>> 16)) & (len-1);
		if(hashcode<0)
			hashcode = -hashcode;
		return hashcode;
	}
	
	public static void assignValue(String subTitles[],int len, int pos) {
		for(String str : subTitles) {
			if(str.length()!=0) {
			int hashcode = getPos(str, len);
			if(AllStatic.map.containsKey(hashcode))
				AllStatic.map.get(hashcode).add(pos);
			else {
				List<Integer>list = new ArrayList<Integer>();
				list.add(pos);
				AllStatic.map.put(hashcode, list);
			}
		}	
	}
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
	public void setFile1(int len) {
		try {
			File dir = new File("d:/DBhomework");
//			����ļ��в����ھʹ���
			if(!dir.exists()) {
				dir.mkdir();
			}
			File partFile1 = new File(dir,"partIndex1.txt");
//			�ļ������ھʹ������ļ�
			if(!partFile1.exists())
					partFile1.createNewFile();
			
//			�����ļ����������
			out1 = new FileOutputStream(partFile1);
			bout1 = new BufferedOutputStream(out1, 5242880);
			String str = null;
			for(int i=0; i<len; i++) {
				if(AllStatic.map.containsKey(i)) {
					str = setPageContent1(AllStatic.map.get(i));
					bout1.write(str.getBytes());
				}
			}
			System.out.println("�ļ�1�������");
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				bout1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void setFile2(int len) {
		try {
			File dir = new File("d:/DBhomework");
//			����ļ��в����ھʹ���
			if(!dir.exists()) {
				dir.mkdir();
			}
			
			File partFile2 = new File(dir,"partIndex2.txt");
//			�ļ������ھʹ������ļ�
			if(!partFile2.exists())
					partFile2.createNewFile();
//			�����ļ����������
			out2 = new FileOutputStream(partFile2);
			bout2 = new BufferedOutputStream(out2);
			String str = null;
			int pos = 1;
			for(int i=0; i<len; i++) {
				str = setPageContent2(pos);
				bout2.write(str.getBytes("UTF-8"));
				if(AllStatic.map.containsKey(i)) {
					pos += AllStatic.map.get(i).size();
				}
			}
			System.out.println("�ļ�2�������");
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
	public static int[] getAllPos (String word, RandomAccessFile raf1, RandomAccessFile raf2) throws IOException {
		int hashcode = getPos(word, 2097152);
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
	
//	����������Ľ���
	public static int[] getIntersection(int arr1[], int arr2[]) {
		List<Integer>list = new ArrayList<Integer>();
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		int len = 0;
	    int p1 = 0;
	    int p2 = 0;
	    while(p1<arr1.length && p2<arr2.length) {
	    	if(arr1[p1]>arr2[p2]) {
	    		p2++;
	    		continue;
	    	}
	    	if(arr1[p1]<arr2[p2]) {
	    		p1++;
	    		continue;
	    	}
	    	if(arr1[p1]==arr2[p2]) {
	    		list.add(arr1[p1]);
	    		p1++;
	    		p2++;
	    		continue;
	    	}
	    }
	    int arr[] = new int[list.size()];
	    for(int i=0; i<arr.length; i++)
	    	arr[i] = list.get(i);
	    return arr;
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
//		ParseUtil.myParse("D:\\DBhomework\\dblp.xml");
		File file1 = new File("D:\\DBhomework\\partindex1.txt");
		File file2 = new File("D:\\DBhomework\\partindex2.txt");
		File file3 = new File("D:\\DBhomework\\srcfile1.txt");
		RandomAccessFile raf1 = new RandomAccessFile(file1, "r");
		RandomAccessFile raf2 = new RandomAccessFile(file2, "r");
		RandomAccessFile raf3 = new RandomAccessFile(file3, "r");
		String s[] = {"Public", "Transportation"};
		Judege j = SrcFileUtil.judgeSubtitles(11111, s, 500, raf3);
		System.out.println(j.isSame);
		}
}
