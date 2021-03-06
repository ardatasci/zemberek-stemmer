package KeywordExtraction.NamedEntityTurkish;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import KeywordExtraction.NamedEntityTurkish.enums.WordType;
import KeywordExtraction.NamedEntityTurkish.util.UtilFunctions;

import tr.edu.hacettepe.cs.minio.MinioReader;

public class ModifiedListCreator {

	ArrayList<Word> wordsList = new ArrayList<Word>();
	ArrayList<Word> modifiedWordsList = new ArrayList<Word>();
	ArrayList<Word> namedEntityWordList = new ArrayList<Word>();
	ArrayList<Word> allWordList = new ArrayList<Word>();
	UtilFunctions utilFunctions = new UtilFunctions();

	int[] wantedEntities;
	String outputType = "";
	int sentenceNumber = 0;
	long totalWordCount = 0;

	public ModifiedListCreator() {

	}

	public void createModifiedLitst(int[] wantedEntities, String outputType)
			throws IOException {
		this.wantedEntities = wantedEntities;
		this.outputType = outputType;
		MinioReader in = MinioReader.getFileReader("sentenceBySentence.txt");
		EntityFinder entityFinder = new EntityFinder();
		WriteToOutputFile outputWriter = new WriteToOutputFile();
		String line = "";
		Word word;
		// wordsList.clear();

		outputWriter.deleteContent(outputType);

		while (in.inputAvailable()) {
			sentenceNumber++;
			line = in.readLine();
			StringTokenizer token = new StringTokenizer(line);
			while (token.hasMoreTokens()) {
				word = new Word(token.nextToken() + " ");
				// System.out.println(word.getContent());
				// System.out.println(word.getClearedContent());
				// System.out.println(word.getType());
				wordsList.add(word);
				totalWordCount++;

			}
			modifiedWordsList = entityFinder.findEntities(wantedEntities,
					wordsList, sentenceNumber);
			allWordList.addAll(modifiedWordsList);
			//outputWriter.writeToFile(modifiedWordsList, outputType);
			outputWriter.writeToFile(AnnotatedWordListCreator.getInstance().getAnnotatedWordList(), outputType);
			wordsList.clear();
		}
		in.close();
		AnnotatedWordListCreator.getInstance().setTotalWordCount(totalWordCount);
		extractNamedEntityWords(allWordList);
		try {
			utilFunctions.writeWordsToFile("AnnotatedWords.txt", namedEntityWordList);
			utilFunctions.writeWordsToFile("AllWords.txt", allWordList);
			//utilFunctions.writeMyModifiedOutputToFile("MyModifiedOutput.txt", AnnotatedWordListCreator.getInstance().getAnnotatedWordList());
			utilFunctions.writeWordsToFile("MyAnnotatedWords.txt", AnnotatedWordListCreator.getInstance().getAnnotatedWordList());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void extractNamedEntityWords(ArrayList<Word> wordList){
		for (Word modifiedWord : wordList) {
			if(modifiedWord.getType() != null && modifiedWord.getType().equals(WordType.POSSIBLE)){
				namedEntityWordList.add(modifiedWord);
			}
		}
		
	}

	public void printWordListToFile() {
		for (Word namedEntity : namedEntityWordList) {
			System.out.println(namedEntity.getClearedContent() + "-"
					+ namedEntity.getType() + "-" + namedEntity.getSubType());
		}
	}

}
