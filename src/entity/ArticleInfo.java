package entity;

import java.util.List;

public class ArticleInfo {
	private StringBuilder authors;
	private String title;
	private String journal;
	private String volume;
	private String year;
	private String pages;
	private String ee;
	public ArticleInfo() {
		// TODO Auto-generated constructor stub
		authors = new StringBuilder();
		title = null;
		journal = null;
		volume = null;
		year = null;
		pages = null;
		ee = null;
	}
	public String getAuthors() {
		return authors.toString();
	}
	public void setAuthors(StringBuilder authors) {
		this.authors = authors;
	}
	public void addAuthor(StringBuilder author) {
		this.authors.append(author);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJournal() {
		return journal;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String p) {
		this.pages = p;
	}
	public String getEe() {
		return ee;
	}
	public void setEe(String ee) {
		this.ee = ee;
	}
	
	
	
	
}
