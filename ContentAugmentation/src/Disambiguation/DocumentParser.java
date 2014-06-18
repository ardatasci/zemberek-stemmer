/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Disambiguation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to read documents
 *
 * @author Mubin Shrestha
 */
public class DocumentParser {

    //This variable will hold all terms of each document in an array.
    private List<String[]> termsDocsArray = new ArrayList<String[]>(); // first element is my document, others are disambiguation page documents
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();

    /**
     * Method to read files and store in array.
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parseFiles(ArrayList<String> documents) {

    	for (String document : documents) {
    		 String[] tokenizedTerms = document.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
             for (String term : tokenizedTerms) {
                 if (!allTerms.contains(term)) {  //avoid duplicate entry
                     allTerms.add(term);
                 }
             }
             termsDocsArray.add(tokenizedTerms);
         }
    	

    }

    /**
     * Method to create termVector according to its tfidf score.
     */
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency        
        for (String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
    }

    /**
     * Method to calculate cosine similarity between all the documents.
     */
    public void getCosineSimilarity() {
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
            for (int j = 0; j < tfidfDocsVector.size(); j++) {
                System.out.println("between " + i + " and " + j + "  =  "
                                   + new CosineSimilarity().cosineSimilarity
                                       (
                                         tfidfDocsVector.get(i), 
                                         tfidfDocsVector.get(j)
                                       )
                                  );
            }
        }
    }
    
    public int getMostSimilarIndex(){
    	int mostSimilarIndex = 1;
    	double similarity = 0;
    	
        for (int i = 1; i < tfidfDocsVector.size(); i++) {
        	double tempSimilarity = new CosineSimilarity().cosineSimilarity(tfidfDocsVector.get(0), tfidfDocsVector.get(i));
        	System.out.println(tempSimilarity);
        	if(tempSimilarity > similarity){
        		similarity = tempSimilarity;
        		mostSimilarIndex = i;
        	}
        }
        
        return mostSimilarIndex;
    }
}
