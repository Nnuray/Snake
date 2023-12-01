public class Main extends MathQuizGame {
    private static final int MAX_QUESTIONS = 5;

    @Override
    protected void initializeGame() {
        score = 0;
        questionNumber = 1;
        System.out.println("Welcome to the Addition Quiz Game!");
    }

    @Override
    protected boolean isGameOver() {
        return questionNumber > MAX_QUESTIONS;
    }

    @Override
    protected void endGame() {
        System.out.println("Addition Quiz Game Over. Final Score: " + score);
    }

    public static void main(String[] args) {
        Main additionQuizGame = new Main();
        additionQuizGame.playGame();
    }
}