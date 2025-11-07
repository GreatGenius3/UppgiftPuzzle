import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.Timer;


// Klasen PuzzleDialog är en dialogruta som visar ett pusselspel
public class PuzzleDialog extends JFrame implements ActionListener
{
    // Variabler
    private JPanel gamePanel;
    private JPanel progressPanel;
    private JPanel buttonPanel;
    private JButton[] buttons;
    private int emptyIndex;

    private GameTimer gameTimer;
    private boolean firstMove = true;

    private JButton newGameButton;
    private JButton losningButton;
    private JButton pausaButton;
    private JButton avslutaButton;
    private JButton solveButton;

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
        // Först förbered alla paneler
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 3));
        this.add(mainPanel);

        setTitle("Puzzle Game");
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
        newGameButton.addActionListener(this);

        losningButton = new JButton("Lösning");
        losningButton.setFont(new Font("Arial", Font.BOLD, fontSize));
        losningButton.addActionListener(this);

        solveButton = new JButton("Solve");
        solveButton.setFont(new Font("Arial", Font.BOLD, fontSize));
        solveButton.addActionListener(this);

        pausaButton = new JButton("Pausa");
        pausaButton.setFont(new Font("Arial", Font.BOLD, fontSize));
        pausaButton.addActionListener(this);

        avslutaButton = new JButton("Avsluta");
        avslutaButton.setFont(new Font("Arial", Font.BOLD, fontSize));
        avslutaButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(newGameButton);
        buttonPanel.add(losningButton);
        buttonPanel.add(solveButton);
        buttonPanel.add(pausaButton);
        buttonPanel.add(avslutaButton);

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

        gamePanel.setBackground(customWhite);

        // Skapa en timer
        gameTimer = new GameTimer();
        // Sen sätter vi en listener på timern
        // idet här fallet har vi en eget interface
        gameTimer.setTimerListener(new GameTimer.TimerListener()
        {
            @Override
            public void onTimeUpdated(String time)
            {
                secondsLabel.setText(time);
            }

            @Override
            public void onTimerStarted()
            {
                // Inget att lägga in men måste finnas
            }

            @Override
            public void onTimerStopped()
            {
                // Inget att lägga in men måste finnas
            }
        });

        // Skapa nytt spel
        // med 50 blandningar
        nyttSpel(100);

        // Sätter detta fönster till fokus
        setFocusable(true);
        // Därefter gör vi iordning tangentbordsbindningen
        ordnaTangentbordKnappar();

        // Visa fönstret
        setVisible(true);
    }

    // Funktionen för att skapa nytt spel
    // och blandar brickorna med antalet drag
    public void nyttSpel(int shuffleMoves)
    {
        // Ornda upp antal knappar till spelfältet
        gamePanel.removeAll();
        buttons = null;
        buttons = new JButton[gameLogic.getAmountOfPuzzles()];

        // Återställ timern
        // och sätt första draget till sant
        gameTimer.reset();
        firstMove = true;

        // Återställ spelet.
        gameLogic.reset();

        // Blanda med den lösbara blandningsfunktion
        gameLogic.shuffleSolveAble(shuffleMoves);

        // Gör iordning alla knappar
        for (int i = 0; i < gameLogic.getAmountOfPuzzles(); i++)
        {
            final int index = i;
            buttons[i] = new JButton();
            buttons[i].setText(String.valueOf(gameLogic.getPuzzel(i)));
            buttons[i].setFont(new Font("Arial", Font.BOLD, 48));
            buttons[i].setHorizontalAlignment(JButton.CENTER);
            buttons[i].setVerticalAlignment(JButton.CENTER);

            buttons[i].setBackground(puzzelColor);
            buttons[i].setForeground(customWhite);
            buttons[i].setFocusable(false);
            buttons[i].setBorder(new LineBorder(customBlack));

            // Vi sätter en ActionListener på knappen
            // som skickar indexen till brickKnapp
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

    // Här är funktionen som anropas när
    // vi trycker på en bricka
    public void brickKnapp(int index)
    {
        // Nu kolla vi om brickan vi trycker på
        // är en bricka vi kan flytta på
        if (gameLogic.isNarliggande(index))
        {
            // Starta timer om det är första draget
            if (firstMove)
            {
                gameTimer.start(); // Starta timern
                gameLogic.startGame(); // Sätt spelet till PLAYING
                firstMove = false; // Sätt variabeln till false
            }

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
                // Sätt spelet till klart
                gameLogic.finishGame();
                // Stoppa timern
                gameTimer.stop();
                JOptionPane.showMessageDialog(this, "Grattis! Du löste pusslet på " +
                        gameLogic.getMoves() + " drag och " +
                        gameTimer.getTimeString() + "!");
            }
        }
    }

    // Här är funktionen som snabbt löser ditt pussel
    // Den använder en A* algoritm och beräknar
    // ut en lösning och meddelar hur många
    // steg det tar att lösa pusslet
    public void solve()
    {
        // Använd vår nya lösningsmetod
        solvePuzzle();
    }

    // Här pausar vi spelet
    public void pauseGame()
    {
        // TODO
    }

    // Metod för att lösa pusslet med algoritmen A (A-star)
    // Detta kan ta lit tid beroende på hur
    // komplex det är
    private void solvePuzzle()
    {
        // Visa en dialogruta medan lösningen beräknas
        JOptionPane.showMessageDialog(this,
                "Lösning påbörjad. Detta kan ta en stund...",
                "Löser pusslet",
                JOptionPane.INFORMATION_MESSAGE);

        // Skapa en SwingWorker för att köra lösningen i bakgrunden
        SwingWorker<Void, Void> worker = new SwingWorker<>()
        {
            private List<PuzzleSolver.BrickDir> solution;

            @Override
            protected Void doInBackground() throws Exception {
                // Hämta nuvarande tillstånd och tomma rutan
                List<Integer> currentPuzzle = gameLogic.getPuzzel();
                int emptyIndex = gameLogic.getEmptyIndex();
                int puzzleSize = gameLogic.getPuzzleRow();

                // Hitta lösningen i bakgrundstråden
                solution = PuzzleSolver.solvePuzzle(currentPuzzle, emptyIndex, puzzleSize);
                return null;
            }

            @Override
            protected void done()
            {
                try
                {
                    // Detta körs på EDT när doInBackground är klar
                    if (solution == null || solution.isEmpty())
                    {
                        JOptionPane.showMessageDialog(PuzzleDialog.this,
                                "Kunde inte hitta en lösning på pusslet.",
                                "Ingen lösning hittad",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(PuzzleDialog.this,
                            "Lösning hittad! Antal drag: " + solution.size(),
                            "Lösning hittad",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Använd en Timer för att köra dragen med fördröjning
                    Timer timer = new Timer(500, new ActionListener()
                    {
                        private int moveIndex = 0;

                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            if (moveIndex < solution.size())
                            {
                                PuzzleSolver.BrickDir direction = solution.get(moveIndex);
                                // Hitta indexet för brickan som ska flyttas
                                int emptyInd = gameLogic.getEmptyIndex();
                                int[] emptyPos = PuzzleSolver.indexToPosition(emptyInd, gameLogic.getPuzzleRow());
                                int row = emptyPos[0];
                                int col = emptyPos[1];

                                // Beräkna positionen för brickan som ska flyttas
                                switch (direction)
                                {
                                    case MOVE_UP: row--; break;
                                    case MOVE_DOWN: row++; break;
                                    case MOVE_LEFT: col--; break;
                                    case MOVE_RIGHT: col++; break;
                                }

                                // Hitta indexet för brickan
                                int brickIndex = row * gameLogic.getPuzzleRow() + col;

                                // Flytta brickan
                                if (gameLogic.isNarliggande(brickIndex))
                                {
                                    gameLogic.switchBricks(brickIndex, emptyInd);
                                    updateBoard();
                                }

                                moveIndex++;
                            }
                            else
                            {
                                // Stoppa timern när alla drag är klara
                                ((Timer) e.getSource()).stop();
                            }
                        }
                    });

                    timer.setInitialDelay(0); // Starta direkt
                    timer.start();

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PuzzleDialog.this,
                            "Ett fel uppstod: " + ex.getMessage(),
                            "Fel",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        // Starta arbetstråden
        worker.execute();
    }

    // Uppdatera spelplanen
    private void updateBoard()
    {
        for (int i = 0; i < gameLogic.getAmountOfPuzzles(); i++)
        {
            int value = gameLogic.getPuzzel(i);
            if (value == 0)
            {
                buttons[i].setText("");
                buttons[i].setVisible(false);
                emptyIndex = i;
            }
            else
            {
                buttons[i].setText(String.valueOf(value));
                buttons[i].setVisible(true);
            }
        }
        movesLabel.setText(String.valueOf(gameLogic.getMoves()));
        gamePanel.repaint();
    }

    // Funktion som tar emot en ActionEvent
    // Som tex knapptryck
    public void actionPerformed(ActionEvent e)
    {
        // Nytt spel knappen
        if (e.getSource() == newGameButton)
        {
            nyttSpel(500);
        }
        // Gör en enkel lösning för testandet skull
        else if (e.getSource() == losningButton)
        {
            nyttSpel(1);
        }

        // Testa solve button
        else if (e.getSource() == solveButton)
        {
            solvePuzzle();
        }
        // Pausa knappen
        else if (e.getSource() == pausaButton)
        {
            pauseGame();
        }
    }

    // Tangentbordsbindning
    // Denna lade jag till för att vi ska kunna avända piltangenterna
    // för att flytta brickorna
    private void ordnaTangentbordKnappar()
    {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        // Koppla piltangenter till motsvarande rörelser
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");

        actionMap.put("moveUp", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                flyttaTill(0, -1); // Upp
            }
        });

        actionMap.put("moveDown", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                flyttaTill(0, 1); // Ner
            }
        });

        actionMap.put("moveLeft", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                flyttaTill(-1, 0); // Vänster
            }
        });

        actionMap.put("moveRight", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                flyttaTill(1, 0); // Höger
            }
        });
    }

    // En funktion som flyttar knappen i vald position
    // Denna är en förbättrad version av moveTile
    // för att funka med tangentbords tryckning
    private void flyttaTill(int dx, int dy)
    {
        int emptyIndex = gameLogic.getEmptyIndex(); // Ta fram den tomma rutan
        int emptyRow = emptyIndex / gameLogic.getPuzzleCells(); // Ta fram aktuella raden där indexet är
        int emptyCol = emptyIndex % gameLogic.getPuzzleCells(); // Ta fram aktuella columnen där indexen är

        int targetRow = emptyRow - dy; // Notera minus för att kompensera för koordinatsystemet
        int targetCol = emptyCol - dx;

        // Kontrollera om draget är giltigt
        if (targetRow >= 0 && targetRow < gameLogic.getPuzzleRow() &&
                targetCol >= 0 && targetCol < gameLogic.getPuzzleCells())
        {
            // Ta fram nuvarande indexet
            int targetIndex = targetRow * gameLogic.getPuzzleCells() + targetCol;
            // Kör brickKnapp funktionen med vald index
            brickKnapp(targetIndex);
        }
    }
}
