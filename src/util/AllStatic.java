package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllStatic {
//	定义一些全局变量
	
//	定义位置数组，用来根据标题获得文章信息的位置
	public static int titlePos[][] = new int[8388608][5];
	public static int titlePos2[][] = new int[1000][6000];
	public static int authorPos[][] = new int[2097152][40];
	public static int authorPos2[][] = new int[8192][2000];
//	用于建立部分匹配搜索索引文件时的hashmap
	public static Map<Integer, List<Integer>>map = new HashMap<Integer, List<Integer>>();
	public static int a = 0;
	
	public static void mySort(Map<Integer, List<Integer>>map) {
		List<Map.Entry<Integer, List<Integer>>>list = new ArrayList<Map.Entry<Integer, List<Integer>>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, List<Integer>>>() {

			@Override
			public int compare(Map.Entry<Integer, List<Integer>> o1, Map.Entry<Integer, List<Integer>> o2) {
				return o1.getKey()-o2.getKey();
			}
			
		});
		for(Map.Entry<Integer, List<Integer>> m : map.entrySet())
			System.out.println("key为"+m.getKey()+"value为"+m.getValue());
		System.out.println("排序完成");
	}
}
