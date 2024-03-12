import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Quiz {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    public Quiz() {
        this.questions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void addQuestion(String question, String[] options, int correctAnswerIndex) {
        questions.add(new Question(question, options, correctAnswerIndex));
    }

    public void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        System.out.println(currentQuestion.getQuestion());
        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        startTimer();
    }

    private void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int timeLeft = 10; // Adjust time limit here

            @Override
            public void run() {
                if (timeLeft > 0) {
                    System.out.println("Time left: " + timeLeft + " seconds");
                    timeLeft--;
                } else {
                    timer.cancel();
                    System.out.println("Time's up!");
                    evaluateAnswer(-1); // -1 indicates time's up
                }
            }
        }, 0, 1000);
    }

    public void evaluateAnswer(int selectedOptionIndex) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int correctAnswerIndex = currentQuestion.getCorrectAnswerIndex();

        if (selectedOptionIndex == -1) {
            System.out.println("You didn't select an option.");
        } else if (selectedOptionIndex == correctAnswerIndex) {
            System.out.println("Correct answer!");
            score++;
        } else {
            System.out.println("Incorrect answer!");
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayQuestion();
        } else {
            displayResult();
        }
    }

    public void displayResult() {
        System.out.println("Quiz completed!\nYour score: " + score + "/" + questions.size());
        System.out.println("Summary of correct/incorrect answers:");
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswer;
            if (i < currentQuestionIndex) {
                userAnswer = (i == question.getCorrectAnswerIndex()) ? "Correct" : "Incorrect";
            } else {
                userAnswer = "Not answered";
            }
            System.out.println("Question " + (i + 1) + ": " + userAnswer);
        }
    }

    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.addQuestion("What is the capital of France?", new String[]{"Paris", "Berlin", "Madrid", "London"}, 0);
        quiz.addQuestion("Who wrote 'Romeo and Juliet'?", new String[]{"William Shakespeare", "Jane Austen", "Charles Dickens", "Leo Tolstoy"}, 0);
        quiz.addQuestion("What is the chemical symbol for water?", new String[]{"H2O", "CO2", "O2", "H2SO4"}, 0);

        quiz.displayQuestion();
        // Simulate user input, assuming user selects option 1 for all questions
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < quiz.questions.size(); i++) {
            int selectedOption = scanner.nextInt() - 1; // Adjusting to 0-based index
            quiz.evaluateAnswer(selectedOption);
        }
        scanner.close();
    }
}

class Question {
    private String question;
    private String[] options;
    private int correctAnswerIndex;

    public Question(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
