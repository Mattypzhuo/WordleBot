import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WordleBot {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("allowed_words.txt"));
        ArrayList<String> words = new ArrayList<String>();
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        scanner.close();
        int size = words.size();
        System.out.println(size);
        String yellowLetters = args[0];
        String greyLetters = args[1];

        System.out.println("yellow letters: " + yellowLetters);
        System.out.println("grey letters: " + greyLetters);
        ArrayList<String> filteredWords = filter(words, yellowLetters, greyLetters);

        // l:0,m:3,o:4
        if (args.length >= 3) {
            String greenPattern = args[2];
            System.out.println("green pattern: " + greenPattern);
            filteredWords = filterGreen(filteredWords, greenPattern);    
        }

        System.out.println(Arrays.toString(filteredWords.toArray()));
    }

    /**
     * Returns a list of words each containing all of the yellow letters. 
     * 
     * Note: yellow letters are not sorted
     * @param possibleWords
     * @param yellowLetters
     * @return
     */
    private static ArrayList<String> filter(ArrayList<String> possibleWords, String yellowLetters, String greyLetters) {
        ArrayList<String> filteredWords = new ArrayList<String>();
        // 1. loops through all the possible words
        for (int i = 0; i < possibleWords.size(); i++) {
            String word = possibleWords.get(i);
            // 2. checks if the word has all the yellow letters
            boolean found = true;
            for (int l = 0; l < yellowLetters.length(); l++) {
                if (! word.contains(yellowLetters.substring(l, l + 1))) {
                    found = false;
                    break;
                }   
            }

            if (found) {
                filteredWords.add(word);
            }
        }
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < filteredWords.size(); i++) {
            String word = filteredWords.get(i);
            boolean found = false;
            for (int l = 0; l < greyLetters.length(); l++) {
                if (word.contains(greyLetters.substring(l, l + 1))) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                result.add(word);
            }
        }

        return result;

    }

    private static ArrayList<String> filterGreen(ArrayList<String> wordList, String greenPattern) {
        // 1. go through all of the words
        // 2. 

        // l:0,m:3,o:4
        ArrayList<String[]> pairList = new ArrayList<String[]>();
        String[] letterPos = greenPattern.split(",");
        for (String str : letterPos) {
            String[] pair = str.split(":");
            pairList.add(pair);
        }

        ArrayList<String> result = new ArrayList<String>();
        for (String word : wordList) {
            boolean found = true;
            for (String[] pair : pairList) {
                String letter = pair[0];
                int index = Integer.parseInt(pair[1]);
                if (word.charAt(index) != letter.charAt(0)) {
                    found = false;
                    break;
                }
            }

            if (found) {
                result.add(word);
            }
        }

        return result;
    }
}