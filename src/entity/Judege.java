package entity;

//辅助类，用来返回判断文章中是否包含输入的标题/作者/关键词的结果
public class Judege {
	public boolean isSame;
	public String content;
	public Judege() {
		// TODO Auto-generated constructor stub
		isSame = false;
		content = null;
	}
	public Judege(boolean isSame, String content) {
		// TODO Auto-generated constructor stub
		this.isSame = isSame;
		this.content = content;
	}
}
