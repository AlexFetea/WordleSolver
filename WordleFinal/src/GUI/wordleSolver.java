package GUI;

public class wordleSolver {
	int smallest;
	int position;
	int sum;
	int wordsLeftSize;
	int[] sizeList;
	String colors;
	String[] possibleAnswers;
	String[] possibleGuesses;

	public wordleSolver(String[] possibleAnswers, String[] possibleGuesses) {
		smallest = 2315;
		position = 0;
		sum = 0;
		sizeList = new int[12972];
		wordsLeftSize = 2315;
		this.possibleAnswers = possibleAnswers;
		this.possibleGuesses = possibleGuesses;

	}

	public String bestWord() {
		for (int i = 0; i < this.possibleGuesses.length; i++) {
			sum = 0;
			for (int j = 0; j < this.possibleAnswers.length; j++) {
				colors = colorMatch(this.possibleGuesses[i], this.possibleAnswers[j]);
				sum += wordsLeftLength(possibleGuesses[i], colors);
			}
			sizeList[i] = sum;
		}
		for (int x = 0; x < sizeList.length; x++) {
			if (sizeList[x] < smallest && !doubleLetter(possibleGuesses[x])) {
				smallest = sizeList[x];
				position = x;
			}
		}
		if (possibleAnswers.length < 3)
			return possibleAnswers[0];
		else
			return possibleGuesses[position];
	}

	public String colorMatch(String wordOne, String wordTwo) {
		char tempChar;
		String colors = "";
		for (int i = 0; i < 5; i++) {
			if (wordOne.charAt(i) == wordTwo.charAt(i))
				tempChar = 'g';
			else if (wordOne.charAt(i) == wordTwo.charAt(0) || wordOne.charAt(i) == wordTwo.charAt(1)
					|| wordOne.charAt(i) == wordTwo.charAt(2) || wordOne.charAt(i) == wordTwo.charAt(3)
					|| wordOne.charAt(i) == wordTwo.charAt(4))
				tempChar = 'y';
			else
				tempChar = 'b';
			colors += tempChar;
		}
		return colors;
	}

	public void setList(String[] possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}

	public String[] wordsLeft(String wordGuessed, String colors) {
		int count = possibleAnswers.length;
		int tempListLength = 0;
		String[] tempList = this.possibleAnswers.clone();
		for (int pos = 0; pos < 5; pos++) {

			if (colors.charAt(pos) == 'y') {
				for (int i = 0; i < count; i++) {
					if (-1 != (tempList[i]).indexOf(wordGuessed.charAt(pos))
							&& pos != (tempList[i]).indexOf(wordGuessed.charAt(pos))) {
						tempList[tempListLength] = tempList[i];
						tempListLength++;
					}
				}
			}

			if (colors.charAt(pos) == 'b') {
				if (!doubleLetter(wordGuessed)) {
					for (int i = 0; i < count; i++) {
						if (-1 == (tempList[i]).indexOf(wordGuessed.charAt(pos))) {
							tempList[tempListLength] = tempList[i];
							tempListLength++;
						}
					}
				} else
					tempListLength = count;

			}

			if (colors.charAt(pos) == 'g') {
				for (int i = 0; i < count; i++) {
					if (tempList[i].charAt(pos) == wordGuessed.charAt(pos)) {
						tempList[tempListLength] = tempList[i];
						tempListLength++;
					}
				}
			}
			count = tempListLength;
			tempListLength = 0;
		}

		String[] subList = new String[count];
		for (int j = 0; j < count; j++) {
			subList[j] = tempList[j];
		}
		return subList;
	}

	public int wordsLeftLength(String wordGuessed, String colors) {

		return wordsLeft(wordGuessed, colors).length;
	}

	public String[] returnPossibleAnswers() {
		return this.possibleAnswers;
	}

	public boolean doubleLetter(String word) {

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (i != j) {
					if (word.charAt(i) == word.charAt(j)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
