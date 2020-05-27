package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
//		���ո��������зֻ���ַ�������
		String[]subTitles = str.split("\\s+");
		List<String>list = new ArrayList<String>();
//		�����ַ�������
		for(int i=0; i<subTitles.length; i++) {
//			���ַ���ת��ΪСд
			subTitles[i] = subTitles[i].toLowerCase();
//			����ַ���������Ҫ��ͼ����ж���һ���ַ���
			if(!judgeWord(subTitles[i]))
				continue;
			char[]arrs = subTitles[i].toCharArray();
//			ֻ�����ַ����е���ĸ - + 
			for(char c : arrs) {
				if((c>=97 && c<=122)||(c==45)||(c==43))
					sb.append(c);
			}
			list.add(sb.toString());
			sb.setLength(0);
		}
//		������Ҫ����ַ����������һ�����鷵��
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
//		���ʳ��ȹ��̣�������Ҫ��
		if(len<2)
			return false;
//		��������õ��ʼ����еĵ�����ͬ��������
		for(String str : uselessArr)
			if(word.equals(str))
				return false;
		return true;
	}
//	������������ƥ�����������ļ�ʱ���ڴ��еĹ�ϣ��ֵ
	public static void assignValue(String subTitles[],int len, int pos) {
		for(String str : subTitles) {
			if(str.length()!=0) {
			int hashcode = IndexFileUtil.getPos(str, len);
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

//	���������µ�ַ����Ľ���
	public static int[] getIntersection(int arr1[], int arr2[]) {
//		��ʼ����ϣ����ֵ��Ϊ�����µ�ַ�����ִ�����
		Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
//		�����������飬������Ϣ����ϣ��
		for(int a : arr1) {
			if(indexMap.containsKey(a))
				indexMap.put(a, indexMap.get(a)+1);
			else {
				indexMap.put(a, 1);
			}
		}
		for(int a : arr2) {
			if(indexMap.containsKey(a))
				indexMap.put(a, indexMap.get(a)+1);
			else {
				indexMap.put(a, 1);
			}
		}
	    List<Integer>list = new ArrayList<Integer>();
//	    ������ϣ��
	    for(Map.Entry<Integer, Integer>mapping : indexMap.entrySet())
//	    	������µ�ַ�������ξʹ���list
	    	if(mapping.getValue() == 2)
	    		list.add(mapping.getKey());
	    int[] arr = new int[list.size()];
//	    ��listת��Ϊ����
	    for(int i=0;i<arr.length;i++)
	    	arr[i] = list.get(i);
	    return arr;
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ParseUtil.myParse("D:\\DBhomework\\dblp.xml");
		IndexFileUtil instance = new IndexFileUtil();
		instance.setFile1(2097152,"partindex1.txt");
		instance.setFile2(2097152,"partindex2.txt");
		}
}
