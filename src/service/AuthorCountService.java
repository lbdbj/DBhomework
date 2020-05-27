package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import entity.AuthorCount;

public class AuthorCountService {
	//ͨ���ļ��õ�ǰһ�ٵ�����
	public static AuthorCount[] getCountByTitle()  {
		//�ȿ���һ��100������
		AuthorCount[] topAu = new AuthorCount[100];
		
		try {
			//�ļ�λ��
	    	String pathname = "D:\\DBhomework\\authorCount.txt"; 
	    	 // Ҫ��ȡ����·����output.txt�ļ�  
	        File filename = new File(pathname);
	        // ����һ������������reader  
	        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); 
	        
	        // ����һ�����������ļ�����ת��
	        BufferedReader br = new BufferedReader(reader); 
	        
	        //һ��һ�еض�ȡ
	        String line = null;
	        line = br.readLine();  
	        for(int i = 0; i < 100; i++) {  

	        	//�ļ���ÿһ�и�ʽΪ������|ƪ����������split�и�string
	        	String[] strArr = line.split("\\|");
	        	
	        	//�����ֺ�ƪ���Ž�����
	        	AuthorCount topAuthor = new AuthorCount(strArr[0],Short.parseShort(strArr[1]));
	        	topAu[i] = topAuthor;
	        	
	        	
	        	// һ�ζ���һ������  
	            line = br.readLine(); 
	        }
		}catch (Exception e) {  

            e.printStackTrace();  

        }
		return topAu;

	}
}
