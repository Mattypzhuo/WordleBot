import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WordleBot {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("allowed_words.txt"));
        ArrayList<String> words = new ArrayList<String>();
        String[] word = new String[5];
        String[] greyLetters = new String[10];
        String[] yellowLetters = new String[10];
        String[] greenLetters = new String[10];
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        scanner.close();
        int size = words.size();
        System.out.println(size);
        for (int i = 0; i < 7; i++) {

            // user enters grey letters
            Scanner letterCount = new Scanner(System.in);
            System.out.println("how many grey letters?");
            String userGreyLetters = "";
            int greyPos = 0;
            String userGreenLetters = "";
            int greenPos = 0;
            int totalGrey = letterCount.nextInt();
            for (int count = 0; count < totalGrey; count++) {
                System.out.println("what is the grey letter?");
                Scanner greyLetter = new Scanner(System.in);
                userGreyLetters = greyLetter.nextLine();
                System.out.println("What is the letter's position?");
                Scanner greyPosition = new Scanner(System.in);
                greyPos = greyPosition.nextInt();
                word[greyPos] = userGreyLetters;
            }

            // user enters yellow letters
            String userYellowLetters = "";
            int yellowPos = 0;
            if (yellowExists()) {
                Scanner yellowCount = new Scanner(System.in);
                System.out.println("how many yellow letters?");
                int yellowTotal = yellowCount.nextInt();
                for (int count = 0; count < yellowTotal; count++) {
                    System.out.println("what are the yellow letters?");
                    Scanner yellowLetter = new Scanner(System.in);
                    userYellowLetters = yellowLetter.nextLine();
                    System.out.println("What is the letter's position?");
                    Scanner yellowPosition = new Scanner(System.in);
                    yellowPos = yellowPosition.nextInt();
                    word[yellowPos] = userYellowLetters;
                }
            }

            // user enters green letters
            if (greenExists()) {
                Scanner greenCount = new Scanner(System.in);
                System.out.println("how many green letters?");
                int greenTotal = greenCount.nextInt();
                for (int count = 0; count < greenTotal; count++) {
                    System.out.println("what are the green letters?");
                    Scanner greenLetter = new Scanner(System.in);
                    userGreenLetters = greenLetter.nextLine();
                    System.out.println("What is the letter's position?");
                    Scanner greenPosition = new Scanner(System.in);
                    greenPos = greenPosition.nextInt();
                    word[greenPos] = userGreenLetters;
                }
            }

            yellowLetters[0] = userYellowLetters;
            greyLetters[1] = userGreyLetters;
            greenLetters[2] = userGreenLetters;
            System.out.println("yellow letters: " + yellowLetters);
            System.out.println("grey letters: " + greyLetters);
            ArrayList<String> filteredWords = filter(words, userYellowLetters, yellowPos,
                    userGreyLetters, greyPos, userGreenLetters, greenPos);

            // l:0,m:3,o:4
            if (args.length >= 3) {
                String greenPattern = args[2];
                System.out.println("green pattern: " + greenPattern);
                filteredWords = filterGreen(filteredWords, greenPattern);
            }

            System.out.println(Arrays.toString(filteredWords.toArray()));
            words = filteredWords;
        }
    }

    /**
     * Returns a list of words each containing all of the yellow letters.
     * 
     * Note: yellow letters are not sorted
     * 
     * @param possibleWords
     * @param yellowLetters
     * @return
     */
    private static ArrayList<String> filter(ArrayList<String> possibleWords, String userYellowLetters, int yellowPos,
            String userGreyLetters, int greyPos, String userGreenLetters, int greenPos) {
        ArrayList<String> filteredWords = new ArrayList<String>();
        // 1. loops through all the possible words
        for (int i = 0; i < possibleWords.size(); i++) {
            String word = possibleWords.get(i);
            // 2. checks if the word has all the yellow letters
            boolean found = true;
            for (int l = 0; l < userYellowLetters.length(); l++) {
                if (!word.contains(userYellowLetters.substring(l, l + 1)) || l == yellowPos) {
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
            for (int l = 0; l < userGreyLetters.length(); l++) {
                if (word.contains(userGreyLetters.substring(l, l + 1))) {
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

    private static boolean greenExists() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are there green letters?");
        String userAnswer = scanner.nextLine();
        if (userAnswer.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    private static boolean yellowExists() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are there yellow letters?");
        String userAnswer = scanner.nextLine();
        if (userAnswer.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }
}