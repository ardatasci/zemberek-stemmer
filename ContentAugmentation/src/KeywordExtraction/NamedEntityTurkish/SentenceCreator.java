package KeywordExtraction.NamedEntityTurkish;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import tr.edu.hacettepe.cs.minio.MinioReader;
import tr.edu.hacettepe.cs.minio.MinioWriter;
import zemberek.tokenizer.SimpleSentenceBoundaryDetector;

public class SentenceCreator {
	ArrayList<String> sentence;
	ArrayList<String> temp;
	char[] smallLetters = { 'a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h',
			'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'r', 's', 'ş',
			't', 'u', 'ü', 'v', 'y', 'z' };

	public SentenceCreator() {

	}

	public void createSentenceBySentenceText(String infile, String outfile)
			throws IOException {
		String line = "";
		StringTokenizer token;
		String fullText = "";
		String word = "";
		String sentenceToWrite = "";
		int totalSentenceSize = 0;
		sentence = new ArrayList<String>();
		MinioReader in = MinioReader.getFileReader(infile);
		MinioWriter out = MinioWriter.getFileWriter(outfile);
		boolean smallLetterFound = false;
		boolean containsSentence = false;
		while (in.inputAvailable()) {
			line = in.readLine();
			fullText += line + " ";
		}

		token = new StringTokenizer(fullText);

		while (token.hasMoreTokens()) {
			word = token.nextToken();

			sentence.add(word);

			if (word.substring((word.length() - 1)).equals(".")
					|| word.substring((word.length() - 1)).equals(":")
					|| word.substring((word.length() - 1)).equals("?")
					|| word.substring((word.length() - 1)).equals("!")) {
				if (token.hasMoreTokens())
					word = token.nextToken();
				else
					word = "";

				for (int i = 0; i < smallLetters.length; i++) {
					// if( word.substring(0,1).equals(smallLetters[i]) )
					if (!word.equals("")) {
						if (word.charAt(0) == smallLetters[i]) {
							smallLetterFound = true;
							break;
						}
					}

				}

				if (!smallLetterFound) {
					int sentenceSize = sentence.size();
					for (int i = 0; i < sentenceSize; i++) {
						sentenceToWrite += sentence.remove(0) + " ";
						containsSentence = true;
					}
				} else {
					containsSentence = false;
					smallLetterFound = false;
				}

				if (!sentenceToWrite.equals("")) {
					out.println(sentenceToWrite);
					totalSentenceSize++;
					// System.out.println(sentenceToWrite);
				}
				sentence.add(word);
				sentenceToWrite = "";
			}

		}

		// write remain words
		if (!containsSentence) {
			int sentenceSize = sentence.size();
			for (int i = 0; i < sentenceSize; i++) {
				sentenceToWrite += sentence.remove(0) + " ";
				containsSentence = true;
			}
			if (!sentenceToWrite.equals("")) {
				out.println(sentenceToWrite);
				totalSentenceSize++;
				// System.out.println(sentenceToWrite);
			}
		}

		// if (!containsSentence) {
		// out.println(fullText);
		//
		// }
		AnnotatedWordListCreator.getInstance().setTotalSentenceSize(
				totalSentenceSize);
		in.close();
		out.close();

	}

	public void extractSentencesFromText(String infile, String outfile)
			throws IOException {
		SimpleSentenceBoundaryDetector splitter = new SimpleSentenceBoundaryDetector();
		MinioReader in = MinioReader.getFileReader(infile);
		MinioWriter out = MinioWriter.getFileWriter(outfile);
		File file = new File(infile);
		List<String> list = splitter.getSentences(file);

		int listSize = list.size();
		for (String string : list) {
			out.println(string);
		}
		if (list.get(listSize - 1).length() == 0) {
			AnnotatedWordListCreator.getInstance().setTotalSentenceSize(
					listSize - 1);
		} else {
			AnnotatedWordListCreator.getInstance().setTotalSentenceSize(
					listSize);
		}

		out.close();
	}
}
