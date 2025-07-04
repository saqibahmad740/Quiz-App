
import java.awt.*;
import javax.swing.*;

public class QuizApp extends JFrame {

    
    private String[] questions = {
        "What is the capital of France?",
        "Which language runs in a web browser?",
        "What is 2 + 2?",
        "Who wrote 'Romeo and Juliet'?",
        "What is the largest planet in our Solar System?",
        "Which element has the chemical symbol 'O'?",
        "What year did World War II end?",
        "Which is the fastest land animal?",
        "What is the boiling point of water (°C)?",
        "Who painted the Mona Lisa?",
        "What is the speed of light (in km/s)?"
    };

    private String[][] options = {
        {"Paris", "London", "Berlin", "Madrid"},
        {"Java", "C", "Python", "JavaScript"},
        {"3", "4", "5", "6"},
        {"William Wordsworth", "William Shakespeare", "Charles Dickens", "Jane Austen"},
        {"Earth", "Mars", "Jupiter", "Saturn"},
        {"Gold", "Oxygen", "Hydrogen", "Iron"},
        {"1945", "1939", "1918", "1950"},
        {"Cheetah", "Lion", "Tiger", "Leopard"},
        {"90", "80", "100", "120"},
        {"Leonardo da Vinci", "Pablo Picasso", "Vincent Van Gogh", "Michelangelo"},
        {"300,000", "150,000", "1,080", "3,000"}
    };

    private int[] answers = {0, 3, 1, 1, 2, 1, 0, 0, 2, 0, 0};
    private int current = 0;
    private int score = 0;

    
    private JLabel questionLabel;
    private JRadioButton[] radioButtons;
    private ButtonGroup group;
    private JButton nextButton;
    private JLabel titleLabel;
    private JPanel quizPanel;
    private JLabel timerLabel;

    private JPanel loginPanel;
    private JTextField nameField;
    private JTextField regIdField;
    private JButton loginButton;

    private JPanel welcomePanel;
    private JLabel welcomeLabel;
    private JLabel subtitleLabel;

    private Timer quizTimer;
    private int timeLeft = 120;
    private static final long serialVersionUID = 1L;

    
    public QuizApp() {
        setTitle("Java Quiz Application");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        setupLoginPanel();
        setupWelcomePanel();
        setupQuizPanel();

        loginButton.addActionListener(e -> handleLogin());
        nextButton.addActionListener(e -> checkAnswer());

        showLogin();
    }

    private void setupLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);
        JLabel regIdLabel = new JLabel("Registration ID:");
        regIdField = new JTextField(15);
        loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(regIdLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(regIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        add(loginPanel, "Login");
    }

    private void setupWelcomePanel() {
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(new Color(70, 130, 180));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));

        welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitleLabel = new JLabel("Get ready for the quiz!", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        welcomePanel.add(subtitleLabel);

        add(welcomePanel, "Welcome");
    }

    private void setupQuizPanel() {
        quizPanel = new JPanel(new BorderLayout(20, 20));
        quizPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel topPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("General Knowledge Quiz", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        timerLabel = new JLabel("Time Left: 120s", SwingConstants.RIGHT);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timerLabel.setForeground(Color.RED);
        topPanel.add(timerLabel, BorderLayout.EAST);

        quizPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        centerPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        radioButtons = new JRadioButton[4];
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            radioButtons[i] = new JRadioButton();
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            group.add(radioButtons[i]);
            optionsPanel.add(radioButtons[i]);
        }
        centerPanel.add(optionsPanel, BorderLayout.CENTER);

        quizPanel.add(centerPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        quizPanel.add(nextButton, BorderLayout.SOUTH);

        add(quizPanel, "Quiz");
    }

    private void showLogin() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Login");
        nameField.setText("");
        regIdField.setText("");
    }

    private void showWelcome(String name) {
        welcomeLabel.setText("Welcome, " + name + "!");
        subtitleLabel.setText("Get ready for the quiz!");
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Welcome");
        Timer timer = new Timer(2000, e -> showQuiz());
        timer.setRepeats(false);
        timer.start();
    }

    private void showQuiz() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "Quiz");
        current = 0;
        score = 0;
        timeLeft = 120;
        timerLabel.setText("Time Left: " + timeLeft + "s");
        startQuizTimer();
        loadQuestion();
    }

    private void handleLogin() {
        String name = nameField.getText().trim();
        String regId = regIdField.getText().trim();
        if (name.isEmpty() || regId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and registration ID.");
            return;
        }
        showWelcome(name);
    }

    private void loadQuestion() {
        if (current < questions.length) {
            questionLabel.setText("Q" + (current + 1) + ": " + questions[current]);
            group.clearSelection();
            for (int i = 0; i < 4; i++) {
                radioButtons[i].setText(options[current][i]);
            }
        } else {
            stopQuizTimer();
            showResult();
        }
    }

    private void checkAnswer() {
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (radioButtons[i].isSelected()) {
                selected = i;
                break;
            }
        }
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Please select an answer.");
            return;
        }
        if (selected == answers[current]) {
            score++;
        }
        current++;
        loadQuestion();
    }

    private void showResult() {
        stopQuizTimer();
        int choice = JOptionPane.showOptionDialog(this,
                "Quiz finished! Your score: " + score + "/" + questions.length + "\nWhat do you want to do next?",
                "Quiz Completed",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Restart Quiz", "Exit"},
                "Restart Quiz");

        if (choice == JOptionPane.YES_OPTION) {
            showLogin();
        } else {
            System.exit(0);
        }
    }

    private void startQuizTimer() {
        quizTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft + "s");
            if (timeLeft <= 0) {
                stopQuizTimer();
                JOptionPane.showMessageDialog(this, "Time's up!");
                showResult();
            }
        });
        quizTimer.start();
    }

    private void stopQuizTimer() {
        if (quizTimer != null && quizTimer.isRunning()) {
            quizTimer.stop();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizApp app = new QuizApp();
            app.setVisible(true);
        });
    }
}
