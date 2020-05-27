package util;

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
	

}

