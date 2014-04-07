package KeywordExtraction.NamedEntityTurkish;

public class Word {
	
	String content = "";
	String clearedContent = null;
	
	String affix = "";
	String type = null;
	String subType = "";
	
	String[] punctuation = {"\"", ":", ".", ",", "’"};
	String[] reagent = {"\'", "’", "’", "(", ")"};
	boolean punctiationFound = false;
	public Word(String content)
	{
		this.content = content;
		this.clearedContent = content.substring(0,content.length()-1);
		this.affix = null;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
	
	public String getClearedContent() {
		return clearedContent;
	}

	public void setClearedContent(String clearedContent) {
		this.clearedContent = clearedContent;
	}
	
	public String getAffix() {
		return affix;
	}

	public void setAffix(String affix) {
		this.affix = affix;
	}
	
	public void cleareContent()
	{
		for (int i = 0; i < reagent.length; i++)
		{
			//System.out.println("cleared: " + clearedContent + i);
			for (int j=0; j < clearedContent.length(); j++)
			{
				if ( (clearedContent.substring(j, j+1)).equals(reagent[i]) )
				{
					//System.out.println("cleared-reagent: " + clearedContent + j);
					if(j == 0)
					{
						clearedContent = clearedContent.substring(j+1);
						//System.out.println("sub cleared: " + clearedContent + j );
					}
					else
					{
						//System.out.println("sub cleared else: " + clearedContent + j );
						try
						{
							affix = clearedContent.substring(j, clearedContent.length());
							clearedContent = clearedContent.substring(0,j);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
					}
				}
			}
		}
		
		for (int i = 0; i < punctuation.length; i++)
		{
			try {
				if ( (clearedContent.substring(0, 1)).equals(punctuation[i]) )
				{
					clearedContent = clearedContent.substring(1);
					//System.out.println(clearedContent);
				}
				else if ( (clearedContent.substring(clearedContent.length()-1, clearedContent.length()) ).equals(punctuation[i]) )
				{
					clearedContent = clearedContent.substring(0,clearedContent.length()-1);
					//System.out.println(clearedContent);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		
	}

}
