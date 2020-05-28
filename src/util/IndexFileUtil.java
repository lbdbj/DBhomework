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
//	根据某个字符串获取hashcode,哈希表的大小为len
	public static int getPos(String str, int len) {
//		将字符串转为字符数组
		char[]vals = str.toCharArray();
//		记录字符数组的长度
		int len2 = vals.length;
		int hashcode = 0;
//		遍历字符串数组
		for(int i=0; i<len2; i++) {
//			获取字符的ASCII码值
			int val = (int)vals[i];
//			新的哈希值是（原哈希值*31+ASCII码值）
			hashcode = (hashcode<<5)-hashcode+val;
		}
//		将哈希值右移16位并和原哈希值做异或，最后再对len取余
		hashcode = (hashcode^(hashcode >>> 16)) & (len-1);
//		保证哈希值是正数
		if(hashcode<0)
			hashcode = -hashcode;
		return hashcode;
	}
	
//	设置索引文件1每页的内容
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
	
//	设置索引文件2每页的内容
	public String setPageContent2(int i) {
		StringBuilder sb = new StringBuilder();
		sb.append(i);
		for(int a=sb.length();a<10;a++)
			sb.append("#");
		return sb.toString();
	}
//	向索引文件1填写内容
	public void setFile1(int len, String path) {
		try {
			File dir = new File("d:/DBhomework");
//			如果文件夹不存在就创建
			if(!dir.exists()) {
				dir.mkdir();
			}
			File file = new File(dir,path);
//			文件不存在就创建新文件
			if(file.exists())
					file.createNewFile();
			
//			创建文件输出流对象
			out1 = new FileOutputStream(file);
//			创建缓冲流
			bout1 = new BufferedOutputStream(out1, 5242880);
			String str = null;
//			遍历哈希表
			for(int i=0; i<len; i++) {
				if(AllStatic.map.containsKey(i)) {
//					把哈希表中的数字转换为字符串
					str = setPageContent1(AllStatic.map.get(i));
//					将哈希表中内容写入索引文件1
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
	
//	向索引文件2写入内容
	public void setFile2(int len, String path) {
		try {
			File dir = new File("d:/DBhomework");
//			如果文件夹不存在就创建
			if(!dir.exists()) {
				dir.mkdir();
			}
			
			File file = new File(dir,path);
//			文件不存在就创建新文件
			if(file.exists())
					file.createNewFile();
//			创建文件输出流对象
			out2 = new FileOutputStream(file);
//			建立缓冲流对象
			bout2 = new BufferedOutputStream(out2);
			String str = null;
			int pos = 1;
//			遍历哈希表
			for(int i=0; i<len; i++) {
//				将pos转换为字符串
				str = setPageContent2(pos);
//				向索引文件2写入起始位置
				bout2.write(str.getBytes("UTF-8"));
				if(AllStatic.map.containsKey(i)) {
//					获取i+1在索引文件1对应区域的起始位置
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
//	获取可能包含指定字符串的所有文章的地址
	public static int[] getAllPos (String word, RandomAccessFile raf1, RandomAccessFile raf2, int len) throws IOException {
		int hashcode = getPos(word, len);
		byte[] begByte = new byte[10];
		byte[] endByte = new byte[10];
		raf2.seek(hashcode*10);
//		将hashcode对应的在index1文件中的起始位置读入
		raf2.read(begByte);
//		将hashcode对应的在index1文件中的结束位置读入
		raf2.read(endByte);
//		获取hashcode对应的在index1文件中的起始位置
		int beg = parseIndexFile2(begByte);
//		获取hashcode对应的在index1文件中的结束位置
		int end = parseIndexFile2(endByte);
		raf1.seek((beg-1)*10);
		
		byte[] allPosByte = new byte[(end-beg)*10];
//		将index1对应位置的数据全部获取
		raf1.read(allPosByte);
//		获取到所有文章的位置
		int[] allPos = parseIndexFile1(allPosByte);
		return allPos;
	}
	
//	解析indexFile1文件中每页的数据
	public static int[] parseIndexFile1(byte[]bs) {
//		按$切割每个文章地址
		String allPos[] = new String(bs).split("\\$");
		int arr[] = new int[allPos.length-1];
		for(int i=0;i<arr.length;i++)
			arr[i] = Integer.parseInt(allPos[i]);
		return arr;
	}
	
//	解析indexFile2文件中每页的数据
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
