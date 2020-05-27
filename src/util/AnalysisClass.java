package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AnalysisClass {
	//�ȵ�ʵİ�װ��
	public class Word{
		//����
		public String word;
		//����
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
	
	//�ȵ���б�İ�װ��
	public class WordList{
		
		//����
		final static int EMPTY = -1;
		final static int EXIST = 1;
		final static int MISSING = 0;
		//����
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
		

		/**�����ֵ�˳������´�
		 * @param word -�´�
		 * @param index -����λ��
		 * @param tag -�����־{EMPTY,EXIST,MISSING}
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
					
					//ȷ��indexΪ����λ��
					if(wordList.get(index).word.compareTo(word)<0)	index+=1;
					wordList.add(wordList.get(size-1));
					size++;
					for(int j = size-2; j>index; j--)	wordList.set(j, wordList.get(j-1));
					wordList.set(index,new Word(word));
					return;
				}
					
			}
		}
		
		/**�ȴ�����������װ�������ȴʴ�������������λ��
		 * @param word -��ӵ��ȴ�
		 */
		public void increase(String word) {
			//��ʼΪ��
			if(size==0) add(word, 0, EMPTY);
			//���ַ��ƽ�����λ��
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
			
			//��ʱl==r, ����Ӧλ�õ��״��ڻ���С�ڻ��ߵ�������δ֪
			temp = wordList.get(l).word;
			if(temp.compareTo(word)==0) {
				add(word, l, EXIST);
			}
			else{
				add(word, l, MISSING);
			}
		}
		

		/**��ȡWordList�а���������ǰN���ȴ�����
		 * @param end -�����⣩����ĩ��
		 * @return WordList: ����������ǰN��Word
		 */
		public WordList getsortedWords(int end) {
			
			if(end>size) {
				selectsort(size);
				return new WordList(wordList.subList(0, size));
			}
			
			//ѡ�����������10n
			if(size>1024) {
				selectsort(end);
				return new WordList(wordList.subList(0, end));
			}
			
			//��ʱnlogn<10n
			else {
				//ģ��ջ����ֹջ���
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
		
		/**���εĿ������򣬹��ڲ�ʹ��
		 * @param l -�������
		 * @param r -�������
		 * @return int�� ֧��λ��
		 */
		private int qsort(int l, int r) {
			Word temp;
			int i = l;
			int j = r;
			
			//��ÿ���Ӷε��м�Ԫ��Ϊ֧��
			int pivot2 = wordList.get((i+j)/2).sum;
			while(i<j) {
				while(wordList.get(i).sum >= pivot2 && i<j) i++;
				while(wordList.get(j).sum <  pivot2 && i<j) j--;
				//����
				temp = wordList.get(i);
				wordList.set(i, wordList.get(j));
				wordList.set(j, temp);
			}
			
			temp = wordList.get(l);
			wordList.set(l, wordList.get(i-1));
			wordList.set(i-1, temp);
			return i-1;
		}
		

		/**ѡ�����򣬹��ڲ�ʹ��
		 * @param N -��Ҫ��������򳤶�
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
	
	//������
	class Year{
		//���
		public short year;
		//�����б�
		WordList words;
		
		public Year(short year) {
			this.year=year;
			words = new WordList();
		}
	}
	
	//�����б�İ�װ��
	class YearList{
		//����
		public List<Year> list;
		public int size;
		
		public YearList() {
			this.list = new ArrayList<>();
			this.size = 0;
		}
		
		//
		/**��������YearList��������,��������ݵ�����
		 * @param year -�µ����
		 * @return int -����ݵ�����
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
		

		/**��ȡĿ�������YearList�е�����
		 * @param year -�����ҵ�Ŀ�����
		 * @return int�� Ŀ����ݵ�����ֵ����Ŀ����ݲ���yearlist���򷵻�-1
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
		

		/**��YearList�ж�Ӧλ�õ�Year��ӱ���
		 * @param index -��ݵ�����
		 * @param words -�´ʵ�����
		 */
		public void addWords(int index, String title) {
//			bad method: �˹�ɸ�鵥��
			String uselessWords = "of for and a in the on with using to an based from by via towards through as over is "+
					 "under at is two how into their it or during are can that use them but";
			String pluralWords = "System Computer Network Algorithm Note";
			
//			�ֽ����,��ȡ�����ĵ�����
			String[] words = title.replaceAll("[[^-]&&\\p{P}\\d]", "").replace(" - ","").split(" ");
			Year temp = list.get(index);
			for(String word:words) {
//				���˿��ַ��Լ�û��ʵ�ʺ���ĵ���
				if(word.equals("")
						||word.charAt(0) >= 'a' && word.charAt(0) <= 'z'
						||uselessWords.contains(word.toLowerCase())) continue;
//				���ڸ����ĵ���һ�ɱ�Ϊ����
				if(pluralWords.contains(word))
					word+="s";
//				System.out.println("word increase:"+word);
				temp.words.increase(word);
			}
			list.set(index, temp);
		}
		
		//
		/**��ȡĳ�����������ǰN���ĵ���
		 * @param index -��ݵ�����
		 * @param end -�����⣩����ĩβ
		 * @return WordList������ǰN�ڵĵ���
		 */
		public WordList getWordsOfIndex(int index, int end) {
			//��ÿ����ݵ�wordList����ǰN
			return list.get(index).words.getsortedWords(end);
		}
	}
	
	
	public YearList getYearList() {
		return new YearList();
	}
	
	//���߿�
	public AnalysisClass() {
		
	}
}
