package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class wordleFrame extends JFrame {

	JLabel Label, bestGuessLabel;
	JButton Tile1 = new JButton(""), Tile2 = new JButton(""), Tile3 = new JButton(""), Tile4 = new JButton(""),
			Tile5 = new JButton(""), enterButton, resetButton;
	JTextArea textArea;
	JTextField BestGuessField;
	String[] possibleGuesses;
	String[] possibleAnswers;
	wordleSolver wordleObject;

	public wordleFrame(String[] possibleAnswers, String[] possibleGuesses) {
		this.possibleGuesses = possibleGuesses;
		this.possibleAnswers = possibleAnswers;
		Label = new JLabel("Type the Word Guessed and Click on Each Box to Change Colors");
		Label.setBounds(50, 0, 600, 60);
		Label.setFont(new Font("Arial", Font.BOLD, 18));
		add(Label);

		buttonSetUp(Tile1);
		buttonSetUp(Tile2);
		buttonSetUp(Tile3);
		buttonSetUp(Tile4);
		buttonSetUp(Tile5);
		Tile1.setBounds(100, 75, 100, 100);
		Tile2.setBounds(200, 75, 100, 100);
		Tile3.setBounds(300, 75, 100, 100);
		Tile4.setBounds(400, 75, 100, 100);
		Tile5.setBounds(500, 75, 100, 100);

		bestGuessLabel = new JLabel("The Best Guess is: ", SwingConstants.TRAILING);
		bestGuessLabel.setBounds(240, 550, 200, 50);
		add(bestGuessLabel);
		bestGuessLabel.setFont(new Font("Arial", Font.BOLD, 18));
		bestGuessLabel.setFocusable(false);

		BestGuessField = new JTextField();
		BestGuessField.setEditable(false);
		BestGuessField.setBounds(150, 600, 400, 100);
		BestGuessField.setFont(new Font("Arial", Font.BOLD, 100));
		add(BestGuessField);
		BestGuessField.setHorizontalAlignment(JTextField.CENTER);
		BestGuessField.setBorder(new LineBorder(getBackground()));
		BestGuessField.setFocusable(false);

		JLabel wordsLeft = new JLabel("The following words are the possible solutions left: ");
		wordsLeft.setBounds(290, 75, 300, 50);
		add(wordsLeft);

		textArea = new JTextArea(5, 30);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		scrollPane.setBounds(150, 200, 400, 300);
		add(scrollPane);

		for (int i = 0; i < possibleAnswers.length; i++) {
			textArea.append(possibleAnswers[i] + "\n");
		}

		enterButton = new JButton("Enter");
		enterButton.setBounds(150, 500, 200, 50);
		enterButton.setFocusable(false);
		add(enterButton);
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enter();
			}
		});

		resetButton = new JButton("Reset");
		resetButton.setBounds(350, 500, 200, 50);
		resetButton.setFocusable(false);
		add(resetButton);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				BestGuessField.setText("");
				for (int i = 0; i < possibleAnswers.length; i++) {
					textArea.append(possibleAnswers[i] + "\n");
				}
				resetTiles();
				wordleObject = new wordleSolver(possibleAnswers, possibleGuesses);
			}
		});

		addKeyListener(new TAdapter());
		setFocusable(true);
		setPreferredSize(new Dimension(700, 750));
		setLayout(null);
		wordleObject = new wordleSolver(this.possibleAnswers, this.possibleGuesses);
	}

	public void enter() {
		String[] answersLeft;
		textArea.setText("");
		String wordGuessed = Tile1.getText() + Tile2.getText() + Tile3.getText() + Tile4.getText() + Tile5.getText();
		wordGuessed = wordGuessed.toLowerCase();
		String colors = "";
		colors += returnColorChar(Tile1.getBackground());
		colors += returnColorChar(Tile2.getBackground());
		colors += returnColorChar(Tile3.getBackground());
		colors += returnColorChar(Tile4.getBackground());
		colors += returnColorChar(Tile5.getBackground());
		wordleObject.setList(wordleObject.wordsLeft(wordGuessed, colors));
		answersLeft = wordleObject.returnPossibleAnswers();

		BestGuessField.setText(wordleObject.bestWord().toUpperCase());
		resetTiles();
		for (int i = 0; i < answersLeft.length; i++) {
			textArea.append(answersLeft[i] + "\n");
		}
	}

	public char returnColorChar(Color color) {

		if (color == Color.yellow)
			return 'y';
		if (color == Color.green)
			return 'g';
		else
			return 'b';
	}

	public void resetTiles() {
		textArea.setText("");
		Tile1.setBackground(Color.gray);
		Tile1.setText("");
		Tile2.setBackground(Color.gray);
		Tile2.setText("");
		Tile3.setBackground(Color.gray);
		Tile3.setText("");
		Tile4.setBackground(Color.gray);
		Tile4.setText("");
		Tile5.setBackground(Color.gray);
		Tile5.setText("");
	}

	public void buttonSetUp(JButton Tile) {
		add(Tile);
		Tile.setBorder(new LineBorder(Color.BLACK));
		Tile.setFont(new Font("Arial", Font.BOLD, 75));
		Tile.setBackground(Color.gray);
		Tile.setFocusable(false);
		Tile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Tile.getBackground() == Color.gray) {
					Tile.setBackground(Color.yellow);
				} else if (Tile.getBackground() == Color.yellow) {
					Tile.setBackground(Color.green);
				} else if (Tile.getBackground() == Color.green) {
					Tile.setBackground(Color.gray);
				}
			}
		});
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();
			char converted = (char) key;
			if (key == 8) {
				if (Tile5.getText() != "")
					Tile5.setText("");
				else if (Tile4.getText() != "")
					Tile4.setText("");
				else if (Tile3.getText() != "")
					Tile3.setText("");
				else if (Tile2.getText() != "")
					Tile2.setText("");
				else if (Tile1.getText() != "")
					Tile1.setText("");
			} else if (key == 10) {
				enter();
			} else if (key >= 65 && key <= 90) {
				if (Tile1.getText() == "")
					Tile1.setText(Character.toString(converted));
				else if (Tile2.getText() == "")
					Tile2.setText(Character.toString(converted));
				else if (Tile3.getText() == "")
					Tile3.setText(Character.toString(converted));
				else if (Tile4.getText() == "")
					Tile4.setText(Character.toString(converted));
				else if (Tile5.getText() == "")
					Tile5.setText(Character.toString(converted));

			}
		}
	}

}
