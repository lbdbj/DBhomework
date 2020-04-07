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

//建立文章标题索引文件的工具类
public class TitleIndexUtil {
	
//	单例模式
	public static TitleIndexUtil instance = new TitleIndexUtil();
	
	public FileOutputStream out = null;
	public BufferedOutputStream bout = null;
	private static int conflict = 0;
	private static int leftCount = 0;
//	给高精度索引数组赋0值
	public static void setZero() {
		for(int i1=0; i1<AllStatic.titlePos.length; i1++)
			for(int i2=0; i2<5; i2++)
				AllStatic.titlePos[i1][i2]=0;
		System.out.println("置零完成");
	}
//给低精度索引数组赋0值
		public static void setZero2() {
			for(int i1=0; i1<1000; i1++)
				for(int i2=0; i2<6000; i2++)
					AllStatic.titlePos2[i1][i2]=0;
			System.out.println("置零完成");
		}
	
//		获取字符串哈希值的方法,此时获取的哈希值就可以作为存放在数组的位置
		public static int getTitlePos(String str, int len) {
//			注意先把头部和尾部的空格去掉
			str = str.trim();
//			把标题的最后的点去掉
			if(str.length()>0 && str.charAt(str.length()-1)=='.')
				str = str.substring(0, str.length()-1);
			char[]vals = str.toCharArray();
			int len2 = vals.length;
			int hashcode = 0;
			for(int i=0; i<len2; i++) {
				if(i == len2)
					break;
				int val = (int)vals[i];
				hashcode = (hashcode << 5)-hashcode+val;
			}
			hashcode = (hashcode^(hashcode >>> 16)) & (len-1);
			if(hashcode<0)
				hashcode = -hashcode;
			return hashcode;
		}
		
		public static int getTitlePos2(String str, int len) {
			str = str.trim();
			if(str.length()>0 &&str.charAt(str.length()-1)=='.')
				str = str.substring(0, str.length()-1);
			char[]vals = str.toCharArray();
			int len2 = vals.length;
			int hashcode = 1;
			for(int i=0; i<=5; i++) {
				if(i == len2)
					break;
				char c = vals[i];
				int val;
				if(c>='a'&& c<='z')
					val = c-96;
				else if(c>='A'&& c<='Z') {
					val = c-64;
				}else {
					val = c;
				}
				hashcode *= c;
			}

			return hashcode & len-1;
		}
		
		public static boolean assignValue(String str, int pos) {
			int index = getTitlePos(str, 8388608);
			System.out.println("位置哈希值是=============="+index);
			for(int i=0; i<5; i++) {
					if(AllStatic.titlePos[index][i] == 0) {
						AllStatic.titlePos[index][i] = pos;
						return true;
					}
				}		
				conflict++;
				System.out.println("发生了冲突，冲突次数------"+conflict);
				return false;
		}
		public static boolean assignValue2(String str, int pos) {
			System.out.println("泄露数据的个数----"+leftCount);
			int index = getTitlePos2(str, 1000);
			System.out.println("位置为"+pos+"的数据哈希值为"+index);
			for(int i=0; i<6000; i++) {
					if(AllStatic.titlePos2[index][i] == 0) {
						AllStatic.titlePos2[index][i] = pos;
						return true;
					}
				}
			leftCount++;
			return false;
		}
	
//	设置高精度标题索引文件每页的内容
	public String setPageContent(int index) {
		StringBuilder sb = new StringBuilder();
		int a[]= AllStatic.titlePos[index];
		for(int i=0; i<5; i++) {
			if(a[i] == 0)
				break;
			else {
				sb.append(a[i]);
				sb.append('$');
			}
		}
		int len = sb.length();
//		每页的存储空间为50字节，如果没有填满就用!将空间填满
		for(int i=len; i<50; i++)
			sb.append('!');
		return sb.toString();
	}
	
//	设置低精度标题索引文件每页的内容
	public String setPageContent2(int index) {
		 StringBuilder sb = new StringBuilder();
		int a[]= AllStatic.titlePos2[index];
		for(int i=0; i<6000; i++) {
			if(a[i] == 0)
				break;
			else {
				sb.append(a[i]);
				sb.append('$');
			}
		}
		int len = sb.length();
//		每页的存储空间为54000字节，如果没有填满就用!将空间填满
		for(int i=len; i<54000; i++)
			sb.append('!');
		return sb.toString();
	}
	
	public void setFile() {
		try {
			File dir = new File("d:/DBhomework");
//			如果文件夹不存在就创建
			if(!dir.exists()) {
				dir.mkdir();
			}
			File titleFile = new File(dir,"titleIndex.txt");
//			文件不存在就创建新文件
			if(!titleFile.exists())
					titleFile.createNewFile();
			
//			创建文件输出流对象
			out = new FileOutputStream(titleFile);
			bout = new BufferedOutputStream(out, 5242880);
			
			String str = null;
			for(int i=0; i<8388608; i++){
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
//			如果文件夹不存在就创建
			if(!dir.exists()) {
				dir.mkdir();
			}
			File titleFile2 = new File(dir,"titleIndex2.txt");
//			文件不存在就创建新文件
			if(!titleFile2.exists())
					titleFile2.createNewFile();
			
//			创建文件输出流对象
			out = new FileOutputStream(titleFile2);
			bout = new BufferedOutputStream(out, 5242880);
			
			String str = null;
			for(int i=0; i<1000; i++)
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
	
	public static List<ArticleInfo> getPosByFile(String title, RandomAccessFile rafTitle1, RandomAccessFile rafSrc1,
			RandomAccessFile rafTitle2,RandomAccessFile rafSrc2) {
		int pos = getTitlePos(title, 8388608);
//		查找到的符合要求的文章数量，暂定最大为10
		int count = 0;
		List<ArticleInfo>list = new ArrayList<ArticleInfo>();
		String getTitile = null;
		try {
			rafTitle1.seek(pos*50);
			byte bs[] = new byte[50];
			rafTitle1.read(bs);
			System.out.println("获取到的标题索引文件的内容为---"+ new String(bs));
//			获取分割后的结果,要用正则表达式解析
			String results[] = new String(bs).split("\\$");
//			获得数据的个数
			int len = results.length-1;
//			遍历每一个位置判断是否和指定的title相同
			for(int i=0; i<len; i++) {
				int val = Integer.parseInt(results[i]);
				if(val<10000000) {
					getTitile = SrcFileUtil.getTitleFromSrc(val, 500, rafSrc1);
					if(getTitile.equals(title)) {
						list.add(SrcFileUtil.formatArticle(SrcFileUtil.getAllInfo(val, 500, rafSrc1)));
						count++;
					}
				}
				else {
					val = val-10000000;
					getTitile = SrcFileUtil.getTitleFromSrc(val, 5000, rafSrc2);
					if(getTitile.equals(title)) {
						list.add(SrcFileUtil.formatArticle(SrcFileUtil.getAllInfo(val, 5000, rafSrc2)));
						count++;
					}
				}
			}
//			通过一次索引便找到的所有符合要求的文章信息就返回
			if(len<5)
				return list;
//				第一次次索引还没有找到或者找全5个就要到第二个标题索引文件中去找
				else {
					pos = getTitlePos(title, 1000);
					rafTitle1.seek(pos*54000);
					byte bs2[] = new byte[54000];
					rafTitle2.read(bs2);
					System.out.println("获取到的标题索引文件的内容为---"+ new String(bs2));
//					获取分割后的结果,要用正则表达式解析
					String results2[] = new String(bs2).split("\\$");
//					获得数据的个数
					int len2 = results2.length-1;
//					遍历每一个位置判断是否和指定的title相同
					for(int i=0; i<len2; i++) {
						int val = Integer.parseInt(results2[i]);
						if(val<10000000){
							getTitile = SrcFileUtil.getTitleFromSrc(val, 500, rafSrc1);
							if(getTitile.equals(title)) {
								list.add(SrcFileUtil.formatArticle(SrcFileUtil.getAllInfo(val, 500, rafSrc1)));
								count++;
							}
						}
						else {
							val = val-10000000;
							getTitile = SrcFileUtil.getTitleFromSrc(val, 5000, rafSrc2);
							if(getTitile.equals(title)) {
								list.add(SrcFileUtil.formatArticle(SrcFileUtil.getAllInfo(val, 5000, rafSrc2)));
								count++;
							}
						}
						if(count == 10)
							return list;
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
		System.out.println("索引数组创建完成");
		System.out.println("冲突次数为----"+conflict+"泄露数据个数----"+leftCount);
//		instance.setFile();
//		instance.setFile2();
		System.out.println("索引文件创建完成");
		
	}
}
