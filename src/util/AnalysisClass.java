package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AnalysisClass {
	//热点词的包装类
	public class Word{
		//单词
		public String word;
		//数量
		public int sum;
		
		public Word(String word) {
			this.word = word;
			this.sum = 1;
		}
		
		public Word(String word, int sum) {
			this.word = word;
			this.sum = sum;
		}
	}
	
	//热点词列表的包装类
	public class WordList{
		
		//常量
		final static int EMPTY = -1;
		final static int EXIST = 1;
		final static int MISSING = 0;
		//变量
		public List<Word> wordList;
		public int size;
		
		public WordList() {
			this.wordList = new ArrayList<>();
			this.size = 0;
		}
		
		public WordList(List<Word> list) {
			this.wordList = list;
			this.size = list.size();
		}
		

		/**保持字典顺序添加新词
		 * @param word -新词
		 * @param index -插入位置
		 * @param tag -插入标志{EMPTY,EXIST,MISSING}
		 */
		private void add(String word, int index, int tag) {
			switch(tag) {
				case EMPTY:{
					wordList.add(new Word(word));
					size++;
					return;
				}
				case EXIST:{
					wordList.get(index).sum+=1;
					return;
				}
				case MISSING:{
					
					//确保index为插入位置
					if(wordList.get(index).word.compareTo(word)<0)	index+=1;
					wordList.add(wordList.get(size-1));
					size++;
					for(int j = size-2; j>index; j--)	wordList.set(j, wordList.get(j-1));
					wordList.set(index,new Word(word));
					return;
				}
					
			}
		}
		
		/**热词增长的外层包装，分析热词存在与否及其待插入位置
		 * @param word -添加的热词
		 */
		public void increase(String word) {
			//初始为空
			if(size==0) add(word, 0, EMPTY);
			//二分法逼近插入位置
			int l = 0; int r = size-1; int mid=(l+r)/2; String temp;
			while(l<r) {
				temp = wordList.get(mid).word;
				if(temp.compareTo(word)==0) {
					add(word, mid, EXIST);
					return;
				}else if(temp.compareTo(word)>0){
					r=mid-1;
				}else {
					l=mid+1;
				}
				mid=(l+r)/2;
			}
			
			//此时l==r, 但对应位置到底大于还是小于或者等于尚且未知
			temp = wordList.get(l).word;
			if(temp.compareTo(word)==0) {
				add(word, l, EXIST);
			}
			else{
				add(word, l, MISSING);
			}
		}
		

		/**获取WordList中按数量排名前N的热词排名
		 * @param end -（除外）区域末端
		 * @return WordList: 含数量排行前N的Word
		 */
		public WordList getsortedWords(int end) {
			
			if(end>size) {
				selectsort(size);
				return new WordList(wordList.subList(0, size));
			}
			
			//选择排序控制在10n
			if(size>1024) {
				selectsort(end);
				return new WordList(wordList.subList(0, end));
			}
			
			//此时nlogn<10n
			else {
				//模拟栈，防止栈溢出
				Stack<Integer> s = new Stack<>();
				s.push(0);
				s.push(size-1);
				while(s.empty()==false) {
					int j = s.pop();
					int i = s.pop();
					int mid = qsort(i, j);
					
					if(mid-1 > i) {
						s.push(i);
						s.push(mid-1);
					}
					if(j > mid+1) {
						s.push(mid+1);
						s.push(j);
					}
				}
				return new WordList(wordList.subList(0, end));
			}
			
//			System.out.println("wordList.size:"+wordList.size());
		}
		
		/**单次的快速排序，供内部使用
		 * @param l -区域左端
		 * @param r -区域左端
		 * @return int： 支点位置
		 */
		private int qsort(int l, int r) {
			Word temp;
			int i = l;
			int j = r;
			
			//以每个子段的中间元素为支点
			int pivot2 = wordList.get((i+j)/2).sum;
			while(i<j) {
				while(wordList.get(i).sum >= pivot2 && i<j) i++;
				while(wordList.get(j).sum <  pivot2 && i<j) j--;
				//互换
				temp = wordList.get(i);
				wordList.set(i, wordList.get(j));
				wordList.set(j, temp);
			}
			
			temp = wordList.get(l);
			wordList.set(l, wordList.get(i-1));
			wordList.set(i-1, temp);
			return i-1;
		}
		

		/**选择排序，供内部使用
		 * @param N -需要排序的区域长度
		 */
		private void selectsort(int N) {
			int posmark;
			int summark;
			Word temp;
			for(int i = 0; i<N; i++) {
				posmark = i;
				summark = wordList.get(i).sum;
				for(int j = i+1; j < size; j++) {
					if(summark<wordList.get(j).sum) {
						posmark=j;
						summark=wordList.get(j).sum;
					}
				}
				temp = wordList.get(i);
				wordList.set(i, wordList.get(posmark));
				wordList.set(posmark, temp);
			}
		}
		
	}
	
	//纪年类
	class Year{
		//年份
		public short year;
		//单词列表
		WordList words;
		
		public Year(short year) {
			this.year=year;
			words = new WordList();
		}
	}
	
	//纪年列表的包装类
	class YearList{
		//变量
		public List<Year> list;
		public int size;
		
		public YearList() {
			this.list = new ArrayList<>();
			this.size = 0;
		}
		
		//
		/**按升序向YearList添加新年份,返回新年份的索引
		 * @param year -新的年份
		 * @return int -新年份的索引
		 */
		public int addYear(short year) {
			if(size==0) {
				list.add(new Year(year));
				size++;
				return 0;
			}
			int i;
			for(i = 0; i<size; i++) {
				if(list.get(i).year > year) {
					list.add(list.get((size-1)));
					size++;
					for(int j = size-2; j>i; j--)	list.set(j, list.get(j-1));
					list.set(i, new Year(year));
					return i;
				}
			}
			if(i==size) {
				list.add(new Year(year));
				size++;
			}
			return i;
		}
		

		/**获取目标年份在YearList中的索引
		 * @param year -待查找的目标年份
		 * @return int： 目标年份的索引值，若目标年份不在yearlist中则返回-1
		 */
		public int getIndexOf(short year) {
			if(size==0) return -1;
			
			int l = 0; int r = size-1;
			while(l<r) {
				if(list.get((l+r)/2).year == year) {
					return (l+r)/2;
				}else if(list.get((l+r)/2).year > year){
					r=(l+r)/2-1;
				}else {
					l=(l+r)/2+1;
				}
			}
			
			if(list.get(l).year==year) return l;
			return -1;
		}
		

		/**向YearList中对应位置的Year添加标题
		 * @param index -年份的索引
		 * @param words -新词的数组
		 */
		public void addWords(int index, String title) {
//			bad method: 人工筛查单词
			String uselessWords = "of for and a in the on with using to an based from by via towards through as over is "+
					 "under at is two how into their it or during are can that use them but";
			String pluralWords = "System Computer Network Algorithm Note";
			
//			分解标题,获取纯净的单词流
			String[] words = title.replaceAll("[[^-]&&\\p{P}\\d]", "").replace(" - ","").split(" ");
			Year temp = list.get(index);
			for(String word:words) {
//				过滤空字符以及没有实质含义的单词
				if(word.equals("")
						||word.charAt(0) >= 'a' && word.charAt(0) <= 'z'
						||uselessWords.contains(word.toLowerCase())) continue;
//				存在复数的单词一律变为复数
				if(pluralWords.contains(word))
					word+="s";
//				System.out.println("word increase:"+word);
				temp.words.increase(word);
			}
			list.set(index, temp);
		}
		
		//
		/**获取某年的数量排行前N名的单词
		 * @param index -年份的索引
		 * @param end -（除外）区域末尾
		 * @return WordList：排名前N内的单词
		 */
		public WordList getWordsOfIndex(int index, int end) {
			//对每个年份的wordList保留前N
			return list.get(index).words.getsortedWords(end);
		}
	}
	
	
	public YearList getYearList() {
		return new YearList();
	}
	
	//工具壳
	public AnalysisClass() {
		
	}
}
