package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import GUI.wordleFrame;

public class Main {

	public Main(String[] possibleAnswers, String[] possibleGuesses) {

	}

	public static wordleFrame f;

	public static void main(String[] args) throws IOException {
		File file = new File("wordleWords.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));

		String[] possibleAnswers = new String[2315];
		String[] possibleGuesses = new String[12972];

		int x = 0;
		String myString;
		while ((myString = br.readLine()) != null) {
			if (x < 2315)
				possibleAnswers[x] = myString;
			possibleGuesses[x] = myString;
			x++;
		}
		f = new wordleFrame(possibleAnswers, possibleGuesses);
		f.setResizable(false);
		f.pack();
		f.setTitle("Wordle Solver");
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setVisible(true);

	}

}
