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
	
//	部分匹配搜索时会用到的，切割标题的工具函数
	public static String[] subTitle(String str) {
		StringBuilder sb = new StringBuilder();
//		按空格来进行切分获得字符串数组
		String[]subTitles = str.split("\\s+");
		List<String>list = new ArrayList<String>();
//		遍历字符串数组
		for(int i=0; i<subTitles.length; i++) {
//			将字符串转换为小写
			subTitles[i] = subTitles[i].toLowerCase();
//			如果字符串不符合要求就继续判断下一个字符串
			if(!judgeWord(subTitles[i]))
				continue;
			char[]arrs = subTitles[i].toCharArray();
//			只保留字符串中的字母 - + 
			for(char c : arrs) {
				if((c>=97 && c<=122)||(c==45)||(c==43))
					sb.append(c);
			}
			list.add(sb.toString());
			sb.setLength(0);
		}
//		将符合要求的字符串重新组成一个数组返回
		String strs[] = new String[list.size()];
		for(int i=0;i<strs.length;i++)
			strs[i] = list.get(i);
		return strs;
		
	}
	
//	在分割单词后，判断单词是否可以使用，比如冠词、介词、连词、感叹词这类单词不再添加进去。
	public static boolean judgeWord(String word) {
		char[]arr = word.toCharArray();
//		无用的单词集合
		String uselessWords = "of for and a in the on with using to an based from by via towards through as over is "
				+ "under at is two how into their it or during are can that use them but";
		String uselessArr[] = uselessWords.split("\\s+");
		int len=0;
		for(char c : arr)
			if((c>=97 && c<=122)||c==43)
				len++;
//		单词长度过短，不符合要求
		if(len<2)
			return false;
//		如果和无用单词集合中的单词相同，不符合
		for(String str : uselessArr)
			if(word.equals(str))
				return false;
		return true;
	}
//	用来建立部分匹配搜索索引文件时向内存中的哈希表赋值
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

//	求两个文章地址数组的交集
	public static int[] getIntersection(int arr1[], int arr2[]) {
//		初始化哈希表，键值对为（文章地址：出现次数）
		Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
//		遍历两个数组，存入信息到哈希表
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
//	    遍历哈希表
	    for(Map.Entry<Integer, Integer>mapping : indexMap.entrySet())
//	    	如果文章地址出现两次就存入list
	    	if(mapping.getValue() == 2)
	    		list.add(mapping.getKey());
	    int[] arr = new int[list.size()];
//	    将list转换为数组
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
