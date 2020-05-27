package entity;

public class AuthorCount {
	private String authorName=null;
	private short authorCount=0;
	//public List<String>authorList;
	

	public AuthorCount(String str){
		this.authorCount = 1;
		this.authorName = str;
	}
	
	public AuthorCount(String str, short count){
		this.authorCount = count;
		this.authorName = str;
	}
	
	public AuthorCount(){
	}
	
	public String getName() {
		return this.authorName;
	}
	
	public short getCount() {
		return this.authorCount;
	}
	
	public void setName(String str) {
		this.authorName = str;
	}
	
	public void plus() {
		this.authorCount++;
	}

}
