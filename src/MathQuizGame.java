import java.util.Random;
import java.util.Scanner;

public abstract class MathQuizGame {
    protected int score;
    protected int questionNumber;

    public void playGame() {
        initializeGame();
        while (!isGameOver()) {
            takeTurn();
            displayScore();
        }
        endGame();
    }

    protected abstract void initializeGame();

    protected void takeTurn() {
        System.out.println("Question " + questionNumber + ":");
        int num1 = generateRandomNumber();
        int num2 = generateRandomNumber();
        char operator = generateRandomOperator();

        System.out.println("What is " + num1 + " " + operator + " " + num2 + "?");

        int userAnswer = getUserAnswer();
        if (checkAnswer(num1, num2, operator, userAnswer)) {
            System.out.println("Correct!");
            score += 1;
        } else {
            System.out.println("Incorrect");
            System.out.println("Correct answer is " + calculateAnswer(num1, num2, operator));
        }
        questionNumber++;
    }

    protected abstract boolean isGameOver();

    protected void displayScore() {
        System.out.println("Current Score: " + score);
    }

    protected abstract void endGame();

    private int generateRandomNumber() {
        return new Random().nextInt(10) + 1;
    }

    private char generateRandomOperator() {
        char[] operators = {'+', '-'};
        return operators[new Random().nextInt(operators.length)];
    }

    private int getUserAnswer() {
        System.out.print("Your answer: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private boolean checkAnswer(int num1, int num2, char operator, int userAnswer) {
        return userAnswer == calculateAnswer(num1, num2, operator);
    }

    private int calculateAnswer(int num1, int num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }
}
