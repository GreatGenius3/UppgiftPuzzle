import javax.swing.*;
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
    private int[] puzzel;
    private int puzzleRow;
    private int puzzleCells;
    private int moves;
    private int seconds;
    private int emptyIndex;

    private JButton newGameButton;
    private JButton losningButton;
    private JButton aterstallButton;
    private JButton installningButton;

    private JLabel labelOne;
    private JLabel labelTwo;
    private JLabel movesLabel;
    private JLabel secondsLabel;

    // Konstruktor
    PuzzleDialog ()
    {
        puzzleRow = 4;
        puzzleCells = 4;
        puzzel = new int[puzzleRow * puzzleCells];
        moves = 0;
        seconds = 0;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 3));
        this.add(mainPanel);

        setTitle("Game Dialog");
        setSize(1600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Funktion som tar emot en ActionEvent
    // Som tex knapptryck
    public void actionPerformed(ActionEvent e)
    {

    }
}
