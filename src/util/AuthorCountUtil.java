package util;

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
	

}

