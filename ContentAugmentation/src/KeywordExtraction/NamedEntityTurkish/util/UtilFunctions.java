package KeywordExtraction.NamedEntityTurkish.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import KeywordExtraction.NamedEntityTurkish.AnnotatedWordListCreator;
import KeywordExtraction.NamedEntityTurkish.Word;
import KeywordExtraction.NamedEntityTurkish.enums.WordType;

import tr.edu.hacettepe.cs.minio.MinioReader;

public class UtilFunctions {

	public UtilFunctions() {

	}

	public void writeWordsToFile(String fileName, ArrayList<Word> wordList)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");

		for (Word word : wordList) {
			writer.println(word.getClearedContent() + "--type: "
					+ word.getType() + "--sentenceNumber"
					+ word.getSentenceNumber() + "--indexInSentence: "
					+ word.getIndexInSentence() + "--lastIndexInSentence: "
					+ word.getLastIndexInSentence());
		}
		writer.close();
	}

	public void writeMyModifiedOutputToFile(String fileName,
			ArrayList<Word> wordList) throws FileNotFoundException,
			UnsupportedEncodingException {
		MinioReader in = MinioReader.getFileReader("sentenceBySentence.txt");
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		String sentence = "";
		int sentenceNumber = 0;
		int index;
		int lastIndex;
		int currentLoopIndex = 0;
		HashMap<Integer, Word> markedIndexesWordMap = new HashMap<>();
		ArrayList<Integer> indexesToBeDeleted = new ArrayList<>();

		HashMap<Integer, ArrayList<Word>> sentenceWordListMap = wordListToSentenceMap(wordList);
		while (in.inputAvailable()) {
			sentenceNumber++;
			currentLoopIndex = 0;
			sentence = in.readLine();
			indexesToBeDeleted.clear();
			int foundMarkedIndex = -1;
			String toBeWriteSentence = new String(sentence);
			ArrayList<Word> sentenceWordList = sentenceWordListMap
					.get(sentenceNumber);
			if (sentenceWordList != null) {
				for (Word word : sentenceWordList) {

					index = sentence.indexOf(word.getClearedContent());
					lastIndex = index + word.getClearedContent().length();
					if (index != -1) {
						if (!markedIndexesWordMap.containsKey(index)) {
							word.setIndexInSentence(index);
							word.setLastIndexInSentence(lastIndex);
							markedIndexesWordMap.put(index, word);
						} else {
							foundMarkedIndex = new Integer(index);
							while (markedIndexesWordMap.containsKey(index)) {
								index = sentence.indexOf(
										word.getClearedContent(), index + 1);
							}
							if (index != -1) {
								word.setIndexInSentence(index);
								word.setLastIndexInSentence(index
										+ word.getClearedContent().length());
								markedIndexesWordMap.put(index, word);
							} else {
								//indexesToBeDeleted.add(currentLoopIndex);
								Word alreadyAddedWord = markedIndexesWordMap.get(foundMarkedIndex);
								word.setIndexInSentence(foundMarkedIndex);
								word.setLastIndexInSentence(foundMarkedIndex
										+ word.getClearedContent().length());
								
								if (alreadyAddedWord.getClearedContent().length() > word.getClearedContent().length()) {
									
								} else if (alreadyAddedWord.getClearedContent().length() < word.getClearedContent().length()) {
									markedIndexesWordMap.put(foundMarkedIndex, word);
								} else {
									
								}
								
								
								// word.setIndexInSentence(foundMarkedIndex);
								// Word tempWord =
								// getSameContentWordFromMap(markedIndexesWordMap,
								// word.getClearedContent());
								// if(tempWord != null){
								// tempWord.getOtherTypes().add(word.getType());
								// indexesToBeDeleted.add(currentLoopIndex);
								// //AnnotatedWordListCreator.getInstance().getAnnotatedWordList().remove
								// }
								// else{
								// if(foundMarkedIndex != -1){
								// String oldWordContent =
								// markedIndexesWordMap.get(foundMarkedIndex).getClearedContent();
								// String currentWordContent =
								// word.getClearedContent();
								// if(oldWordContent.length() >
								// currentWordContent.length()){
								// indexesToBeDeleted.add(currentLoopIndex);
								// }
								// else {
								// indexesToBeDeleted.add(currentLoopIndex);
								//
								// }
								// }
								// }

							}

						}
					}
					currentLoopIndex++;
				}

//				for(int k=0; k< indexesToBeDeleted.size(); k++){
//					int deleteIndex = indexesToBeDeleted.get(k);
//					sentenceWordList.remove(deleteIndex);
//				}
				//sentenceWordList = removeSameOccurrencesFromList(sentenceWordList);
				sentenceWordList.clear();
				for (Word value : markedIndexesWordMap.values()) {
					sentenceWordList.add(value);
				}
				Collections.sort(sentenceWordList, new WordComparable());

				int listSize = sentenceWordList.size();
				int beginSentenceIndex = 0;
				int currentSentenceIndex = 0;
				int endSentenceIndex = sentence.length();

				for (int i = 0; i < listSize; i++) {
					if (sentence.indexOf(sentenceWordList.get(i)
							.getClearedContent()) != -1) {
						String replacement = "("
								+ sentenceWordList.get(i).getClearedContent()
								+ " : " + sentenceWordList.get(i).getType()
								+ ")";

						int indexDifference = replacement.length()
								- sentenceWordList.get(i).getClearedContent()
										.length();
						sentence = sentence.subSequence(beginSentenceIndex,
								currentSentenceIndex)
								+ sentence.substring(currentSentenceIndex,
										endSentenceIndex).replaceFirst(
										sentenceWordList.get(i)
												.getClearedContent(),
										replacement);
						for (int j = i + 1; j < listSize; j++) {
							sentenceWordList.get(j).setIndexInSentence(
									sentenceWordList.get(j)
											.getIndexInSentence()
											+ indexDifference);
							sentenceWordList.get(j).setLastIndexInSentence(
									sentenceWordList.get(j)
											.getLastIndexInSentence()
											+ indexDifference);
						}
						currentSentenceIndex = sentenceWordList.get(i).getLastIndexInSentence()
								+ indexDifference;
						endSentenceIndex = endSentenceIndex + indexDifference;
					}
				}

			}
			writer.print(sentence);
			markedIndexesWordMap.clear();
		}
		in.close();
		writer.close();

	}

	private HashMap<Integer, ArrayList<Word>> wordListToSentenceMap(
			ArrayList<Word> wordList) {

		HashMap<Integer, ArrayList<Word>> sentenceWordListMap = new HashMap<>();

		ArrayList<Word> sentenceWordList = new ArrayList<>();
		int sentenceNumber = 0;
		for (Word word : wordList) {
			sentenceWordList = sentenceWordListMap
					.get(word.getSentenceNumber());
			if (sentenceWordList == null) {
				ArrayList<Word> tempWordList = new ArrayList<>();
				tempWordList.add(word);
				sentenceWordListMap.put(word.getSentenceNumber(), tempWordList);
			} else {
				sentenceWordList.add(word);
				sentenceWordListMap.put(word.getSentenceNumber(),
						sentenceWordList);

			}

		}

		return sentenceWordListMap;
	}

	public void removeDuplicatesInResource(String resourceFileName)
			throws FileNotFoundException, UnsupportedEncodingException {
		MinioReader in = MinioReader.getFileReader(resourceFileName);
		PrintWriter writer = new PrintWriter(resourceFileName + "_new", "UTF-8");

		ArrayList<String> resList = new ArrayList<>();
		while (in.inputAvailable()) {
			String res = in.readLine();
			if (!resList.contains(res)) {
				resList.add(res);
				writer.println(res);
			}
		}
		in.close();
		writer.close();

	}

	private Word getSameContentWordFromMap(HashMap<Integer, Word> indexWordMap,
			String content) {

		for (Word word : indexWordMap.values()) {
			if (content.equals(word.getClearedContent())) {
				return word;
			}
		}
		return null;
	}

	private ArrayList<Word> removeSameOccurrencesFromList(
			ArrayList<Word> wordList) {
		for (int i = 0; i < wordList.size(); i++) {
			for (int j = 0; j < wordList.size(); j++) {
				if (i != j) {
					if (wordList.get(i).getIndexInSentence() == wordList.get(j)
							.getIndexInSentence()) {

						if (wordList.get(i).getClearedContent().length() > wordList
								.get(j).getClearedContent().length()) {
							wordList.remove(j);
						} else if (wordList.get(i).getClearedContent().length() < wordList
								.get(j).getClearedContent().length()) {
							wordList.remove(i);
						} else {
							wordList.remove(i);
						}
					}
				}

			}
		}
		return wordList;
	}
}
