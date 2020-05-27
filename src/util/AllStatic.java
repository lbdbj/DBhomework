package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllStatic {
//	建立索引文件时暂存信息的哈希表
	public static Map<Integer, List<Integer>>map = new HashMap<Integer, List<Integer>>();
//	定义一些全局变量
	
//	定义位置数组，用来根据标题获得文章信息的位置
	public static int titlePos[][] = new int[8388608][5];
	public static int titlePos2[][] = new int[1000][6000];
	//authorindex文件一共有2097152页每页最多存40个数据
	public static int authorPos[][] = new int[2097152][40];
	//authorindex2文件一共有8192页每页最多存2000个数据
	public static int authorPos2[][] = new int[8192][2000];
	public static int a = 0;
}
