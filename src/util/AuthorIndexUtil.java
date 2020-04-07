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
import entity.JudegeAuthor;

//建立文章作者索引文件的工具类
public class AuthorIndexUtil {
	public static AuthorIndexUtil instance = new AuthorIndexUtil();
	
	public FileOutputStream out = null;
	public BufferedOutputStream bout = null;
//	记录发生哈希冲突的次数
	private static int conflict = 0;
//	记录泄露的记录个数
	private static int leftCount = 0;
//	给高精度作者索引数组赋0值
	public static void setZero() {
		for(int i1=0; i1<AllStatic.authorPos.length; i1++)
			for(int i2=0; i2<AllStatic.authorPos[0].length; i2++)
				AllStatic.authorPos[i1][i2]=0;
		System.out.println("置零完成");
	}
//	因为有很多垃圾的author值，即没有空格的author值，所以将没有空格的author值去掉
	public static boolean hasBlank(String str) {
		str = str.trim();
		char[]arr = str.toCharArray();
		for(int i=0;i<arr.length;i++) {
			if(arr[i] == ' ')
				return true;
		}
		return false;
	}
//给低精度作者索引数组赋0值
		public static void setZero2() {
			for(int i1=0; i1<AllStatic.authorPos2.length; i1++)
				for(int i2=0; i2<AllStatic.authorPos2[0].length; i2++)
					AllStatic.authorPos2[i1][i2]=0;
			System.out.println("置零完成");
		}
			
//		获取字符串哈希值的方法,此时获取的哈希值就可以作为存放在数组的位置
		public static int getAuthorPos(String str, int len) {
//			注意先把头部和尾部的空格去掉
			str = str.trim();
//			将字符串转为字符数组
			char[]vals = str.toCharArray();
//			记录字符数组的长度
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
		
		public static int getAnotherPos(int hashcode, int pos) {
			return (hashcode+pos)&8388607;
		}
//		此方法用来根据作者名获取哈希值，并将作者所在文章的位置存到高精度哈希数组中
		public static boolean assignValue(String str, int pos) {
			int index = getAuthorPos(str, AllStatic.authorPos.length);
			for(int i=0; i<AllStatic.authorPos[0].length; i++) {
					if(AllStatic.authorPos[index][i] == 0) {
						AllStatic.authorPos[index][i] = pos;
						System.out.println("位置哈希值是"+index);
						return true;
					}
				}	
				conflict++;
				System.out.println("发生了冲突，冲突次数"+conflict);
				return false;
		}
//		此方法用来根据作者名获取哈希值，并将作者所在文章的位置存到低精度哈希数组中
		public static boolean assignValue2(String str, int pos) {
			System.out.println("泄露数据的个数----"+leftCount);
			int index = getAuthorPos(str, AllStatic.authorPos2.length);
			for(int i=0; i<AllStatic.authorPos2[0].length; i++) {
					if(AllStatic.authorPos2[index][i] == 0) {
						AllStatic.authorPos2[index][i] = pos;
						System.out.println("位置为"+pos+"的数据哈希值为"+index);
						return true;
					}
				}
			leftCount++;
			return false;
		}
//	设置高精度作者索引文件每页的内容
	public String setPageContent(int index) {
		StringBuilder sb = new StringBuilder();
		int a[]= AllStatic.authorPos[index];
		for(int i=0; i<AllStatic.authorPos[0].length; i++) {
			if(a[i] == 0)
				break;
			else {
				sb.append(a[i]);
				sb.append('$');
			}
		}
		int len = sb.length();
//		每页的存储空间为10*AllStatic.authorPos[0].length字节，如果没有填满就用!将空间填满
		for(int i=len; i<10*AllStatic.authorPos[0].length; i++)
			sb.append('!');
		return sb.toString();
	}
	
//	设置低精度作者索引文件每页的内容
	public String setPageContent2(int index) {
		 StringBuilder sb = new StringBuilder();
		int a[]= AllStatic.authorPos2[index];
		for(int i=0; i<AllStatic.authorPos2[0].length; i++) {
			if(a[i] == 0)
				break;
			else {
				sb.append(a[i]);
				sb.append('$');
			}
		}
		int len = sb.length();
//		每页的存储空间为AllStatic.authorPos2[0].length*10字节，如果没有填满就用!将空间填满
		for(int i=len; i<AllStatic.authorPos2[0].length*10; i++)
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
			File authorFile = new File(dir,"authorIndex.txt");
//			文件不存在就创建新文件
			if(!authorFile.exists())
					authorFile.createNewFile();
			
//			创建文件输出流对象
			out = new FileOutputStream(authorFile);
			bout = new BufferedOutputStream(out, 5242880);
			
			String str = null;
			for(int i=0; i<AllStatic.authorPos.length; i++){
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
			File authorFile2 = new File(dir,"authorIndex2.txt");
//			文件不存在就创建新文件
			if(!authorFile2.exists())
				authorFile2.createNewFile();
			
//			创建文件输出流对象
			out = new FileOutputStream(authorFile2);
			bout = new BufferedOutputStream(out, 5242880);
			
			String str = null;
			for(int i=0; i<AllStatic.authorPos2.length; i++)
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
	
	public static List<ArticleInfo> getPosByFile(String author, RandomAccessFile rafAuthor1, RandomAccessFile rafSrc1,
			RandomAccessFile rafAuthor2,RandomAccessFile rafSrc2) {
		int pos = getAuthorPos(author, AllStatic.authorPos.length);
		List<ArticleInfo>list = new ArrayList<ArticleInfo>();
		String getAuthor = null;
		JudegeAuthor ja = new JudegeAuthor();
		int count = 0;
		try {
			rafAuthor1.seek(pos*400);
			byte bs[] = new byte[400];
			rafAuthor1.read(bs);
			System.out.println("获取到的作者索引文件的内容为---"+ new String(bs));
//			获取分割后的结果,要用正则表达式解析
			String results[] = new String(bs).split("\\$");
//			获得数据的个数
			int len = results.length-1;
//			遍历每一个位置判断是否和指定的author相同
			for(int i=0; i<len; i++) {
				int val = Integer.parseInt(results[i]);
				if(val<10000000) {
					ja = SrcFileUtil.judgeAuthor(val, author, 500, rafSrc1);
					if(ja.isSame) {
						count++;
//						System.out.println("第"+count+"个数据");
						list.add(SrcFileUtil.formatArticle(ja.content));
					}
				}
				else {
					val = val-10000000;
					ja = SrcFileUtil.judgeAuthor(val, author, 5000, rafSrc2);
					if(ja.isSame) {
						count++;
						System.out.println("第"+count+"个数据");
						list.add(SrcFileUtil.formatArticle(ja.content));
					}
				}
			}
//			通过一次索引便找到的所有符合要求的文章信息就返回
			if(len<40)
				return list;
//				第一次次索引还没有找到或者找全40个就要到第二个标题索引文件中去找
				else {
					pos = getAuthorPos(author, AllStatic.authorPos2.length);
					rafAuthor2.seek(pos*20000);
					byte bs2[] = new byte[20000];
					rafAuthor2.read(bs2);
					System.out.println("获取到的标题索引文件的内容为---"+ new String(bs2));
//					获取分割后的结果,要用正则表达式解析
					String results2[] = new String(bs2).split("\\$");
//					获得数据的个数
					int len2 = results2.length-1;
//					遍历每一个位置判断是否和指定的author相同
					for(int i=0; i<len2; i++) {
						int val = Integer.parseInt(results2[i]);
						if(val<10000000) {
							ja = SrcFileUtil.judgeAuthor(val, author, 500, rafSrc1);
							if(ja.isSame) {
								count++;
								System.out.println("第"+count+"个数据");
								list.add(SrcFileUtil.formatArticle(ja.content));
							}
						}
						else {
							val = val-10000000;
							ja = SrcFileUtil.judgeAuthor(val, author, 5000, rafSrc2);
							if(ja.isSame) {
								count++;
								System.out.println("第"+count+"个数据");
								list.add(SrcFileUtil.formatArticle(ja.content));
							}
						}
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
		AuthorIndexUtil instance = AuthorIndexUtil.instance;
		System.out.println("索引数组创建完成");
		System.out.println("冲突次数为----"+conflict+"泄露数据个数----"+leftCount);
//		instance.setFile();
//		instance.setFile2();
		System.out.println("索引文件创建完成");
		
	}
}
