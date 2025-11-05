import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Klasen PuzzleDialog är en dialogruta som visar ett pusselspel
public class PuzzleDialog extends JFrame implements ActionListener
{
    // Variabler
    private JPanel gamePanel;
    private JPanel progressPanel;
    private JPanel buttonPanel;
    private JButton[] buttons;
    private int seconds;
    private int minutes;
    private int emptyIndex;

    private JButton newGameButton;
    private JButton losningButton;
    private JButton aterstallButton;
    private JButton installningButton;

    private JLabel labelOne;
    private JLabel labelTwo;
    private JLabel movesLabel;
    private JLabel secondsLabel;

    // Skapa spellogiken för fårt pusselspel
    GameLogic gameLogic = new GameLogic();

    Color puzzelColor = new Color(125, 0, 0);
    Color customDarkGray = new Color(80, 80, 80);
    Color customLightGray = new Color(120, 120, 120);
    Color customRed = new Color(255, 0, 0);
    Color customGreen = new Color(0, 255, 0);
    Color customBlue = new Color(0, 0, 255);
    Color customYellow = new Color(255, 255, 0);
    Color customCyan = new Color(0, 255, 255);
    Color customMagenta = new Color(255, 0, 255);
    Color customWhite = new Color(255, 255, 255);
    Color customBlack = new Color(0, 0, 0);

    // Konstruktor
    PuzzleDialog ()
    {
        seconds = 0;
        minutes = 0;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 3));
        this.add(mainPanel);

        setTitle("Game Dialog");
        setSize(1600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LineBorder border = new LineBorder(new Color(0, 0, 255), 6, false);
        gamePanel = new JPanel();
        gamePanel.setBorder(border);
        gamePanel.setLayout(new GridLayout(gameLogic.getPuzzleRow(), gameLogic.getPuzzleCells()));

        LineBorder border2 = new LineBorder(new Color(0, 255, 0), 6, false);
        progressPanel = new JPanel();
        progressPanel.setBorder(border2);
        progressPanel.setLayout(new GridLayout(4, 1));

        LineBorder border3 = new LineBorder(new Color(255, 255, 0), 6, false);
        buttonPanel = new JPanel();
        buttonPanel.setBorder(border3);
        buttonPanel.setLayout(new GridLayout(5, 1));

        mainPanel.add(gamePanel);
        mainPanel.add(progressPanel);
        mainPanel.add(buttonPanel);

        int fontSize = 26;
        newGameButton = new JButton("Nytt Spel");
        newGameButton.setFont(new Font("Arial", Font.BOLD, fontSize));
        // newGameButton.addActionListener(e -> gameLogic.newGame(gameLogic.getPuzzleRow(), gameLogic.getPuzzleCells()));
        newGameButton.addActionListener(this);
        losningButton = new JButton("Lösning");
        losningButton.setFont(new Font("Arial", Font.BOLD, fontSize));
        // losningButton.addActionListener(e -> gameLogic.solve());
        losningButton.addActionListener(this);
        aterstallButton = new JButton("Återställ");
        aterstallButton.setFont(new Font("Arial", Font.BOLD, fontSize));
        aterstallButton.addActionListener(e -> testRensaKnappar());
        // aterstallButton.addActionListener(this);
        installningButton = new JButton("Inställningar");
        installningButton.setFont(new Font("Arial", Font.BOLD, fontSize));
        // installningButton.addActionListener(this);

        buttonPanel.add(newGameButton);
        buttonPanel.add(losningButton);
        buttonPanel.add(aterstallButton);
        buttonPanel.add(installningButton);

        labelOne = new JLabel("Tid");
        labelOne.setFont(new Font("Arial", Font.BOLD, fontSize));
        labelOne.setHorizontalAlignment(JLabel.CENTER);
        labelOne.setVerticalAlignment(JLabel.CENTER);
        labelTwo = new JLabel("Antal drag");
        labelTwo.setFont(new Font("Arial", Font.BOLD, fontSize));
        labelTwo.setHorizontalAlignment(JLabel.CENTER);
        labelTwo.setVerticalAlignment(JLabel.CENTER);
        movesLabel = new JLabel("0");
        movesLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        movesLabel.setHorizontalAlignment(JLabel.CENTER);
        movesLabel.setVerticalAlignment(JLabel.CENTER);
        secondsLabel = new JLabel("0:00");
        secondsLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        secondsLabel.setHorizontalAlignment(JLabel.CENTER);
        secondsLabel.setVerticalAlignment(JLabel.CENTER);

        progressPanel.add(labelOne);
        progressPanel.add(secondsLabel);
        progressPanel.add(labelTwo);
        progressPanel.add(movesLabel);

        // Sätt minsta storlek på fönstret
        // setMinimumSize(new Dimension(1600, 600));

        // pack();

        gamePanel.setBackground(customWhite);

        // Skapa nytt spel
        nyttSpel();

        setVisible(true);
    }

    // Här är funktionen som anropas när
    // vi trycker på en bricka
    public void brickKnapp(int index)
    {
        // Nu kolla vi om brickan vi trycker på
        // är en bricka vi kan flytta på
        if (gameLogic.isAdjacent(index, emptyIndex))
        {
            // Sätt brickan vi trycker på till tom
            buttons[index].setVisible(false);
            buttons[index].setText("");

            // Ta fram nya indexen från den rikting vi har rört oss på
            int getNextIndex = gameLogic.findFromDirection(index);

            // Sätt tom brickan till den vi tryckte på
            buttons[emptyIndex].setVisible(true);
            buttons[emptyIndex].setText(String.valueOf(gameLogic.getPuzzel(index)));

            // Uppdatera tom index
            emptyIndex = index;

            // Byt plats på brickorna
            gameLogic.switchBricks(index, getNextIndex);

            // Öka dragräknaren
            gameLogic.incrementMoves();
            movesLabel.setText(String.valueOf(gameLogic.getMoves()));

            // Kolla om pusslet är löst
            if (gameLogic.isSolved())
            {
                JOptionPane.showMessageDialog(this, "Grattis! Du löste pusslet på " +
                    gameLogic.getMoves() + " drag!");
            }
        }
    }

    public void nyttSpel()
    {
        // Ornda upp antal knappar till spelfältet
        gamePanel.removeAll();
        buttons = null;
        buttons = new JButton[gameLogic.getAmountOfPuzzles()];

        // Återställ spelet.
        gameLogic.reset();

        // Blanda nu brickorna
        // gameLogic.shuffle();
        gameLogic.shuffleSolveAble();

        for (int i = 0; i < gameLogic.getAmountOfPuzzles(); i++)
        {
            final int index = i;
            buttons[i] = new JButton();
            buttons[i].setText(String.valueOf(gameLogic.getPuzzel(i)));
            buttons[i].setFont(new Font("Arial", Font.BOLD, 48));
            buttons[i].setHorizontalAlignment(JButton.CENTER);
            buttons[i].setVerticalAlignment(JButton.CENTER);

            // LineBorder newBorder = new LineBorder(Color.black, 4);
            // buttons[i].setBorder(newBorder);

            buttons[i].setBackground(puzzelColor);
            buttons[i].setForeground(customWhite);
            buttons[i].setFocusable(false);
            buttons[i].setBorder(new LineBorder(customBlack));

            // buttons[i].setBorder(new EmptyBorder(10, 10, 10, 10));
            // buttons[i].setBounds(10, 10, 100, 100);
            buttons[i].addActionListener(e -> brickKnapp(index));
            gamePanel.add(buttons[i]);
        }
        // Sätt sen sista knappen till tom
        buttons[gameLogic.getEmptyIndex()].setText("");
        buttons[gameLogic.getEmptyIndex()].setVisible(false);
        emptyIndex = gameLogic.getEmptyIndex();

        // Denna bevövs för att signalera gamePanel att den behöver
        // uppdateras (målas om)
        gamePanel.repaint();

        // Uppdatera antal drag
        movesLabel.setText(String.valueOf(gameLogic.getMoves()));
    }

    // Här är funktionen som snabbt löser din lösning
    // samt ger dig det antalet moves som användes
    // för lösningen
    public void solve()
    {
        gameLogic.solve();
    }

    // Funktion som tar emot en ActionEvent
    // Som tex knapptryck
    public void actionPerformed(ActionEvent e)
    {
        // Nytt spel knappen
        if (e.getSource() == newGameButton)
        {
            nyttSpel();
        }
        else if (e.getSource() == losningButton)
        {
            solve();
        }
    }

    public void testRensaKnappar()
    {
        gamePanel.removeAll();
        buttons = null;
        gamePanel.repaint();
    }
}
