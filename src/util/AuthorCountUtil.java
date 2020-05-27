package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import entity.AuthorCount;
import entity.MinHeap;


//��ά��һ��100�����ݵ�С���ѣ���������100����
public class AuthorCountUtil
{


	private AuthorCount[] data;
	int num;
	
	public AuthorCountUtil(AuthorCount[] data, int k){
		this.data = data;
		this.num = k;
	}

	
	// ��data�����л�ȡ����k����
	public static AuthorCount[] topK(AuthorCount[] data,int k)
	{
		int i = 0;
		
		
		// ��ȡ100�����ݽ�һ��100�����ݵ�С����
		//��ȡK��Ԫ�ط���һ������topk��
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
		
		// ת������С��
		MinHeap heap = new MinHeap(topk);
		
		// ��k��ʼ������data���ҵ�����100������
		for(;i<data.length;i++)
		{
			if(data[i]!=null) {
				short root = heap.getRoot().getCount();
				
				// �����ݴ��ڶ�����С���������ڵ㣩ʱ���滻���еĸ��ڵ㣬��ת���ɶ�
				if(data[i].getCount() > root)
				{
					heap.setRoot(data[i]);
				}
			}
		}
		
		//Ϊ���С����ʵ�ֶ��ţ�ʵ��ǰһ������
		for(int times = 0; times < 99;times++) {
			heap.swap(0,99-times);
			heap.heapSort(0,times);
		}
		
		
		return topk;
	}
	
	public static int getAuthorPos(String str, int len) {
//		ע���Ȱ�ͷ����β���Ŀո�ȥ��
		str = str.trim();
//		���ַ���תΪ�ַ�����
		char[]vals = str.toCharArray();
//		��¼�ַ�����ĳ���
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
//			����ļ��в����ھʹ���
			if(!dir.exists()) {
				dir.mkdir();
			}
			File authorFile = new File(dir,"authorCount.txt");
//			�ļ������ھʹ������ļ�
			if(!authorFile.exists())
					authorFile.createNewFile();
			
//			�����ļ����������
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
	//�ñչ�ϣ�ķ�������������
	public static boolean recordAuthor(String str, int conflict) {

		//��getAuthorPos���������Ϸ�����ͻʱ������̽�⣬ͨ���������ֻ�ȡ�±�index
		//��ͻʱ���ö��γ�ͻ����̽��
		int index = (getAuthorPos(str, AllStatic.authorArray.length)+conflict*conflict) % AllStatic.authorArray.length;
		//�����鳤��ȡ�࣬��ֹ���
		
		//���authorArray[hashcode]�ǿյģ�û�з�����ͻ���Ͳ���
		if(AllStatic.authorArray[index] == null) {
			AllStatic.authorArray[index] = new AuthorCount(str);
			//System.out.println("Ϊ�գ�����"+str);
			return true;
		}
		
		//���authorArray[hashcode]���������ݣ�������ͬ�����ߣ��͸����ߵ�ƪ��+1��
		else if(AllStatic.authorArray[index].getName().equals(str)) {
			AllStatic.authorArray[index].plus();
			//System.out.println("ͬ������"+str);
			return true;
		}
		
		//���������ͻ��������̽��ķ�ʽ����Ѱַ��
		else {
			conflict++;
			//System.out.println("������ͻ�����¼���");
			return recordAuthor(str, conflict);
		}
	}
	
	
}
