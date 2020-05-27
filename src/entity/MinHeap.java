package entity;

public class MinHeap {
	// �ѵĴ洢�ṹ - ����
	private AuthorCount[] data;
	
	// ��һ�����鴫�빹�췽������ת����һ��С����
	public MinHeap(AuthorCount[] data)
	{
		this.data = data;
		buildHeap();

	}
	
	// ������ת������С�ѣ��÷������ڲ���ķ�����ʱ�临�Ӷ���n��
	private void buildHeap()
	{
		// ��ÿһ���м��㿪ʼ��������Ҫ����parentһֱ���ײ�
		// ��ȫ������ֻ�������±�С�ڻ���� (data.length) / 2 - 1 ��Ԫ���к��ӽ�㣬������Щ��㡣
		// ���׽����󷨾��Ǻ���/2-1
        for (int i = (data.length) / 2 - 1; i >= 0; i--) 
        {
        	// ���к��ӽ���Ԫ��heapify
            heapify(i);
        }
    }
	
	private void heapify(int i)
	{
		// ��ȡ���ҽ��������±꣬left��right�����ں���
        int l = left(i);  
        int r = right(i);
        
        // ����һ����ʱ��������ʾ ����㡢���㡢�ҽ������С��ֵ�Ľ����±�
        int smallest = i;
        
        // �������㣬�������ֵС�ڸ�����ֵ
        if (l < data.length && data[l].getCount() < data[i].getCount())  
        	smallest = l;  
        
        // �����ҽ�㣬���ҽ���ֵС�����ϱȽϵĽ�Сֵ
        if (r < data.length && data[r].getCount() < data[smallest].getCount())  
        	smallest = r;  
        
        // ���ҽ���ֵ�����ڸ��ڵ㣬ֱ��return�������κβ��������Ƕ����㷨�ĸĽ�������Ҫһֱ��Ҷ�ӽ��
        if (i == smallest)  
            return;  
        
        // �������ڵ�����ҽ������С���Ǹ�ֵ���Ѹ��ڵ��ֵ�滻��ȥ
        swap(i, smallest);
        
        // �����滻�����������ᱻӰ�죬����Ҫ����Ӱ��������ٽ���heapify
        heapify(smallest);
    }
	
	public void heapSort(int i, int times) {
		//System.out.println(data.length-times-1);
		
		// ��ȡ���ҽ��������±꣬left��right�����ں���
        int l = left(i);  
        int r = right(i);
        
        // ����һ����ʱ��������ʾ ����㡢���㡢�ҽ������С��ֵ�Ľ����±�
        int smallest = i;
        
        // �������㣬�������ֵС�ڸ�����ֵ
        if (l < data.length-times-1 && data[l].getCount() < data[i].getCount())  
        	smallest = l;  
        
        // �����ҽ�㣬���ҽ���ֵС�����ϱȽϵĽ�Сֵ
        if (r < data.length-times-1 && data[r].getCount() < data[smallest].getCount())  
        	smallest = r;  
        
        // ���ҽ���ֵ�����ڸ��ڵ㣬ֱ��return�������κβ��������Ƕ����㷨�ĸĽ�������Ҫһֱ��Ҷ�ӽ��
        if (i == smallest)  
            return;  
        
        // �������ڵ�����ҽ������С���Ǹ�ֵ���Ѹ��ڵ��ֵ�滻��ȥ
        swap(i, smallest);
        
        // �����滻�����������ᱻӰ�죬����Ҫ����Ӱ��������ٽ���heapify
        
        heapSort(smallest, times);
	}
	
//	public void heapSort(int times) {
//		System.out.println(data[0].getName()+"ƪ��Ϊ"+data[0].getCount());
//		
//	}
	
	// ��ȡ�ҽ��������±�
	private int right(int i)
	{  
        return (i + 1) << 1;  
    }   
 
	// ��ȡ����������±�
    private int left(int i) 
    {  
        return ((i + 1) << 1) - 1;  
    }
    
    // ����Ԫ��λ��
    public void swap(int i, int j) 
    {  
        AuthorCount tmp = data[i];  
        data[i] = data[j];  
        data[j] = tmp;  
    }
    
    // ��ȡ���е���С��Ԫ�أ���Ԫ��
    public AuthorCount getRoot()
    {
    	    return data[0];
    }
 
    // �滻��Ԫ�أ�������heapify
	public void setRoot(AuthorCount root)
	{
		data[0] = root;
		heapify(0);
	}
	

}
