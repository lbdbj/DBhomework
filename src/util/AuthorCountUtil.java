package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import entity.AuthorCount;
import entity.MinHeap;


//用维护一个100个数据的小顶堆，来找最大的100个数
public class AuthorCountUtil
{


	private AuthorCount[] data;
	int num;
	
	public AuthorCountUtil(AuthorCount[] data, int k){
		this.data = data;
		this.num = k;
	}

	
	// 从data数组中获取最大的k个数
	public static AuthorCount[] topK(AuthorCount[] data,int k)
	{
		int i = 0;
		
		
		// 先取100个数据建一个100个数据的小顶堆
		//先取K个元素放入一个数组topk中
		AuthorCount[] topk = new AuthorCount[k]; 
		for(int j = 0;j< k ;i++)
		{
			if(data[i]!=null) {
				topk[j] = data[i];
				j++;
			}
			if(i>15000000) {
				//System.out.println("kongde");
				break;
			}
		}
		
		// 转换成最小堆
		MinHeap heap = new MinHeap(topk);
		
		// 从k开始，遍历data，找到最大的100个数据
		for(;i<data.length;i++)
		{
			if(data[i]!=null) {
				short root = heap.getRoot().getCount();
				
				// 当数据大于堆中最小的数（根节点）时，替换堆中的根节点，再转换成堆
				if(data[i].getCount() > root)
				{
					heap.setRoot(data[i]);
				}
			}
		}
		
		//为这个小顶堆实现堆排，实现前一百排序
		for(int times = 0; times < 99;times++) {
			heap.swap(0,99-times);
			heap.heapSort(0,times);
		}
		
		
		return topk;
	}
	
	public static int getAuthorPos(String str, int len) {
//		注意先把头部和尾部的空格去掉
		str = str.trim();
//		将字符串转为字符数组
		char[]vals = str.toCharArray();
//		记录字符数组的长度
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
	public static void setFileCount(AuthorCount[] aTry) {
		BufferedOutputStream boutCount = null;
		try {
			File dir = new File("d:/DBhomework");
//			如果文件夹不存在就创建
			if(!dir.exists()) {
				dir.mkdir();
			}
			File authorFile = new File(dir,"authorCount.txt");
//			文件不存在就创建新文件
			if(!authorFile.exists())
					authorFile.createNewFile();
			
//			创建文件输出流对象
			FileOutputStream outCount = new FileOutputStream(authorFile);
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
	//用闭哈希的方法，建立索引
	public static boolean recordAuthor(String str, int conflict) {

		//用getAuthorPos方法，加上发生冲突时的线性探测，通过作者名字获取下标index
		//冲突时采用二次冲突进行探测
		int index = (getAuthorPos(str, AllStatic.authorArray.length)+conflict*conflict) % AllStatic.authorArray.length;
		//对数组长度取余，防止溢出
		
		//如果authorArray[hashcode]是空的，没有发生冲突，就插入
		if(AllStatic.authorArray[index] == null) {
			AllStatic.authorArray[index] = new AuthorCount(str);
			//System.out.println("为空，插入"+str);
			return true;
		}
		
		//如果authorArray[hashcode]里面有数据，但是是同个作者，就给作者的篇数+1。
		else if(AllStatic.authorArray[index].getName().equals(str)) {
			AllStatic.authorArray[index].plus();
			//System.out.println("同个作者"+str);
			return true;
		}
		
		//如果发生冲突，用线性探测的方式重新寻址。
		else {
			conflict++;
			//System.out.println("发生冲突，重新计算");
			return recordAuthor(str, conflict);
		}
	}
	
	
}
