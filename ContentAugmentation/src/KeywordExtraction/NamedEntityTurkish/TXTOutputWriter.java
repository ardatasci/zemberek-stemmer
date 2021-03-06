package KeywordExtraction.NamedEntityTurkish;

import java.io.IOException;
import java.util.ArrayList;

import KeywordExtraction.NamedEntityTurkish.enums.WordType;

import tr.edu.hacettepe.cs.minio.MinioReader;
import tr.edu.hacettepe.cs.minio.MinioWriter;

public class TXTOutputWriter {

	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();
	public TXTOutputWriter()
	{
		
	}
	
	public void writeToFile(ArrayList modifiedWordsList) throws IOException
	{
		String line = "";
		String fullText = "";
		WordType previousType = null;
		String stringToWrite = null;
		WordType type = null;
		
		int position = 0;
		MinioReader in;
		try {
			in = MinioReader.getFileReader("modifiedText.txt");
			while ( in.inputAvailable() )
			{
				line = in.readLine();
				fullText += line + "\n";
			}
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	
		MinioWriter out = MinioWriter.getFileWriter("modifiedText.txt");
		
		this.modifiedWordsList = modifiedWordsList;
		
		out.print(fullText);
		
		for (int i = 0; i < modifiedWordsList.size(); i++)
		{
			
			if ( (((Word)modifiedWordsList.get(i)).type) != null )
			{
				type = ((Word)modifiedWordsList.get(i)).type;
				if (type.equals(WordType.POSSIBLE))
				{
					
					try
					{
						if ( previousType == null || previousType.equals(WordType.POSSIBLE) )
						{
							out.print( ((Word)modifiedWordsList.get(i)).content );
							previousType = WordType.POSSIBLE;
						}
						else
						{
							out.print( "(" + stringToWrite + " : " + previousType + ")" );
							out.print( ((Word)modifiedWordsList.get(i)).content );
							previousType = WordType.POSSIBLE;
							stringToWrite = null;
						}
						
					}
					catch (Exception e)
					{
						// TODO: handle exception
						out.print( ((Word)modifiedWordsList.get(i)).content );
						previousType = type;
					}
					
					//out.write(((Word)modifiedWordsList.get(i)).content );
					//previousType = type;					
				}
				else
				{
					//out.write( "<" + type + ">" + ((Word)modifiedWordsList.get(i)).content + "</" + type + ">" );
					try
					{
						if ( previousType == null || previousType.equals(WordType.POSSIBLE) )
						{
							if ( type.equals(WordType.ABBREVIATION) || type.equals(WordType.CITY) || type.equals(WordType.COUNTRY) || type.equals(WordType.CONTINENT) )
							{
								out.print( "(" + ((Word)modifiedWordsList.get(i)).content + " : " + type + ")" );
								stringToWrite = null;
								previousType = type;
							}
							else
							{
								stringToWrite = ((Word)modifiedWordsList.get(i)).content;
								previousType = type;
							}
							
						}
						else
						{
							try
							{
								if ( previousType.equals(type) )
								{
									
									if ( type.equals(WordType.ABBREVIATION) || type.equals(WordType.CITY) || type.equals(WordType.COUNTRY) || type.equals(WordType.CONTINENT) )
									{
										out.print( "(" + ((Word)modifiedWordsList.get(i)).content + " : " + type + ")" );
										stringToWrite = null;
										previousType = type;
									}
									else
									{
										stringToWrite += ((Word)modifiedWordsList.get(i)).content;
										previousType = type;
									}
								}
								else
								{
									if ( stringToWrite != null )
									{
										out.print( "(" + stringToWrite + " : " + previousType + ")" );	
									}
									stringToWrite = ((Word)modifiedWordsList.get(i)).content;
									previousType = type;
								}
							}
							catch (Exception e)
							{
								// TODO: handle exception
								stringToWrite += ((Word)modifiedWordsList.get(i)).content;
								previousType = type;
							}
						}
						
						
					}
					catch (Exception e)
					{
						// TODO: handle exception
						stringToWrite = ((Word)modifiedWordsList.get(i)).content;
						previousType = type;
					}
				}
			}
			else
			{
				try
				{
					if ( previousType == null || previousType.equals(WordType.POSSIBLE) )
					{
						out.print( ((Word)modifiedWordsList.get(i)).content );
						previousType = null;
						
					}
					else
					{
						if ( stringToWrite != null)
						{
							out.print( "(" + stringToWrite + " : " + previousType + ")" );
						}
						out.print( ((Word)modifiedWordsList.get(i)).content );
						previousType = null;
						stringToWrite = null;
						
						
					}
				}
				catch (Exception e)
				{
					// TODO: handle exception
					out.print( ((Word)modifiedWordsList.get(i)).content );
					previousType = null;
				}

			}
			
			//System.out.print( ((Word)modifiedWordsList.get(i)).content );
			
		}
		
		if (stringToWrite != null )
		{
			out.print( "(" + stringToWrite + " : " + previousType + ")" );
			previousType = null;
		}
		out.println();
		//System.out.print("\n");
		
		out.close();
	}

	public void deleteContent() throws IOException {
		// TODO Auto-generated method stub
		MinioWriter out = MinioWriter.getFileWriter("modifiedText.txt");
		out.close();
	}
}
