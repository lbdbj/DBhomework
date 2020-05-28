package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entity.AuthorCount;

public class AllStatic {
//	建立索引文件时暂存信息的哈希表
	public static Map<Integer, List<Integer>>map = new HashMap<Integer, List<Integer>>();
//建立不同文件的标识符
	public static int setFileFlag = 0;
	
	public static AuthorCount authorArray[] = new AuthorCount[15464693];
	
}
