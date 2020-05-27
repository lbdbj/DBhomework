package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import entity.AuthorCount;

public class AuthorCountService {
	//通过文件得到前一百的作者
	public static AuthorCount[] getCountByTitle()  {
		//先开辟一个100的数组
		AuthorCount[] topAu = new AuthorCount[100];
		
		try {
			//文件位置
	    	String pathname = "D:\\DBhomework\\authorCount.txt"; 
	    	 // 要读取以上路径的output.txt文件  
	        File filename = new File(pathname);
	        // 建立一个输入流对象reader  
	        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); 
	        
	        // 建立一个对象，它把文件内容转换
	        BufferedReader br = new BufferedReader(reader); 
	        
	        //一行一行地读取
	        String line = null;
	        line = br.readLine();  
	        for(int i = 0; i < 100; i++) {  

	        	//文件中每一行格式为作者名|篇数，所以用split切割string
	        	String[] strArr = line.split("\\|");
	        	
	        	//将名字和篇数放进数组
	        	AuthorCount topAuthor = new AuthorCount(strArr[0],Short.parseShort(strArr[1]));
	        	topAu[i] = topAuthor;
	        	
	        	
	        	// 一次读入一行数据  
	            line = br.readLine(); 
	        }
		}catch (Exception e) {  

            e.printStackTrace();  

        }
		return topAu;

	}
}
