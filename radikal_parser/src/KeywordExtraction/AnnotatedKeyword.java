package KeywordExtraction;

import java.util.Vector;

public class AnnotatedKeyword {
	String keyword;
	Vector<String> keywordTypes;
	
	public AnnotatedKeyword(String keyword, Vector<String> keywordTypes)
	{
		this.keyword = keyword;
		this.keywordTypes = keywordTypes;
	}
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Vector<String> getKeywordTypes() {
		return keywordTypes;
	}
	public void setKeywordTypes(Vector<String> keywordTypes) {
		this.keywordTypes = keywordTypes;
	}
	
	public void printAnnotatedKeyword()
	{
		System.out.print(keyword);
		System.out.print(": ");
		System.out.print(keywordTypes);
		System.out.println("");
	}



}
