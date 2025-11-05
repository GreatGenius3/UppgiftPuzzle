import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// Klassen GameLogic implementerar GameInt
public class GameLogic implements GameInt
{
    // Variabler
    private List<Integer> puzzel;
    private int puzzleRow;
    private int puzzleCells;
    private int emptyIndex;
    private int moves;

    private GameStatus gameStatus;

    private BrickDirection brickLastMoveDir;

    // Konstruktor
    GameLogic ()
    {
        // Vi börjar att skapa ett klassisk 4 * 4 brick spel
        // med 15 brickor
        newGame(4, 4);

        // Återställ spelet.
        reset();
    }
    // --------------------------------------------------------------
    // Geters och setters
    // --------------------------------------------------------------
    public int getPuzzleRow()
    {
        return puzzleRow;
    }
    public int getPuzzleCells() { return puzzleCells; }
    public int getAmountOfPuzzles() {return puzzleRow * puzzleCells;}
    public int getEmptyIndex()
    {
        return emptyIndex;
    }
    public int getMoves()
    {
        return moves;
    }
    public GameStatus getGameStatus()
    {
        return gameStatus;
    }
    public List<Integer> getPuzzel()
    {
        return puzzel;
    }
    public int getPuzzel(int index)
    {
        return puzzel.get(index);
    }

    public void setGameStatus(GameStatus newstatus) { gameStatus = newstatus; }

    // --------------------------------------------------------------
    // Metoder
    // --------------------------------------------------------------
    // Sätt spelet till playning
    public void startGame() { gameStatus = GameStatus.PLAYING; }
    // Sätt spelet till pausad
    public void pauseGame() { gameStatus = GameStatus.PAUSED; }
    // Sätt spelet till pausad
    public void finishGame() {gameStatus = GameStatus.PLAYING;}

    // Funktionen isAdjacent kollar om en bricka på vald index
    // är en bricka vi kan flytta på
    public boolean isAdjacent(int index)
    {
        int row1 = index / getPuzzleCells();
        int col1 = index % getPuzzleCells();
        int row2 = emptyIndex / getPuzzleCells();
        int col2 = emptyIndex % getPuzzleCells();

        // Sätt nu variablen movingDir till
        // den rikning som brickan ska flyttas till
        if (row1 < row2)
            brickLastMoveDir = GameInt.BrickDirection.MOVE_DOWN;
        else if (row1 > row2)
            brickLastMoveDir = GameInt.BrickDirection.MOVE_UP;
        else if (col1 < col2)
            brickLastMoveDir = GameInt.BrickDirection.MOVE_RIGHT;
        else
            brickLastMoveDir = GameInt.BrickDirection.MOVE_LEFT;

        return (Math.abs(row1 - row2) == 1 && col1 == col2) ||
                (Math.abs(col1 - col2) == 1 && row1 == row2);
    }

    // Funktionen findFromDirection tar fram ett index
    // beroende på vilken rikning vi har valt
    public int findFromDirection(int index)
    {
        // Hämta ett index beroende på vilken rikning
        // vi har valt
        // Ta fram den raden och kolumnen som indexet
        // ligger på
        int row = index / puzzleCells;
        int col = index % puzzleCells;
        int resultNextIndex = 0;

        // Beräknar indexet beroende på rikning
        switch (brickLastMoveDir)
        {
                // Upp?
            case MOVE_UP:
                row -= 1;
                break;
                // Ner?
            case MOVE_DOWN:
                row += 1;
                break;
                // Vänster
            case MOVE_LEFT:
                col -= 1;
                break;
                // Höger
            case MOVE_RIGHT:
                col += 1;
                break;
            default:
                break;

        }

        // Sen räknar vi ut den nya indexen
        resultNextIndex = row * puzzleCells + col;

        return resultNextIndex;

    }

    // Byt plats på brickor
    public void switchBricks(int index1, int index2)
    {
        int temp = puzzel.get(index1);
        puzzel.set(index1, puzzel.get(index2));
        puzzel.set(index2, temp);
        if (puzzel.get(index1) == 0)
            emptyIndex = index1;
    }

    // Skapar ett nytt spel med antal rader och celler
    public void newGame(int row, int cells)
    {
        brickLastMoveDir = BrickDirection.MOVE_UP;
        puzzleRow = row;
        puzzleCells = cells;
    }

    void incrementMoves()
    {
        moves++;
    }

    // En metod som löser spelet
    // Kanske finns en algorithm för detta men
    // jag kikar på det i framtiden
    public void solve(int moves)
    {
        // IO.println("Tji fick ni! Inget quicklösning på detta! ");
    }

    // Kollar om vi har löst detta
    public boolean isSolved()
    {
        // Ligger alla siffrorna i rätt ordning?
        int counter = 1;
        for (int i = 0; i < getAmountOfPuzzles() - 1; i++)
        {
            if (puzzel.get(i) != counter)
                return false;
            counter++;
        }
        return true;
    }

    // En metod som återställer spelet och sätter
    // alla nummer i nummerordning
    public void reset()
    {
        puzzel = new ArrayList<>();
        emptyIndex = puzzleRow * puzzleCells - 1;
        for(int i = 0; i < (puzzleRow * puzzleCells); i++)
            puzzel.add(i + 1);
        puzzel.set(emptyIndex, 0);
        moves = 0;
        gameStatus = GameStatus.PAUSED;
    }

    // Blanda alla siffror i arrayen
    // Varning! Siffor blandas inte nödvändigtvis
    // till ett lösbart pussel
    public void shuffle()
    {
        // Används Collections shuffle för att blanda ihop
        // våran List array
        Collections.shuffle(puzzel);

        // Sen måste vi ange den tomma platsen
        for (int i = 0; i < getAmountOfPuzzles(); i++)
        {
            if (puzzel.get(i) == 0)
                emptyIndex = i;
        }
    }

    // Denna är en förbättrad funktion som blandar
    // siffror och ger ett lösbart pussel
    public void shuffleSolveAble(int shuffMoves)
    {
        // Se till först att vi har en lösning
        reset();

        // Gör 500 slumpade drag
        // Med hjälp av Random
        // int shuffleMoves = 500;
        Random rand = new Random();

        // Gå igenom alla drag
        for (int i = 0; i < shuffMoves; i++)
        {
            // Här tar vi fram alla tänkbara drag vi kan göra
            List<BrickDirection> possibleMoves = getPossibleMoves();

            // Ta nu fram det möjliga draget
            if (!possibleMoves.isEmpty())
            {
                // Välj ett slumpat drag
                BrickDirection move = possibleMoves.get(rand.nextInt(possibleMoves.size()));
                // Flytta nu brickan
                moveTile(move);
            }
        }
        // Återställ antal moves
        moves = 0;
    }

    // PRIVAT FUNKTION!
    // Funktion som ger dig alla tänkbara drag från den
    // tomma rutan
    private List<BrickDirection> getPossibleMoves()
    {
        List<BrickDirection> moves = new ArrayList<>();
        int emptyRow = emptyIndex / puzzleRow;
        int emptyCol = emptyIndex % puzzleRow;

        // Check if we can move up (empty space moves down)
        if (emptyRow > 0)
            moves.add(BrickDirection.MOVE_UP);
        // Check if we can move down (empty space moves up)
        if (emptyRow < puzzleRow - 1)
            moves.add(BrickDirection.MOVE_DOWN);
        // Check if we can move left (empty space moves right)
        if (emptyCol > 0)
            moves.add(BrickDirection.MOVE_LEFT);
        // Check if we can move right (empty space moves left)
        if (emptyCol < puzzleRow - 1)
            moves.add(BrickDirection.MOVE_RIGHT);

        return moves;
    }

    // PRIVAT FUNKTION!
    // Denna funktion flyttar brickorna i vald riktning
    private void moveTile(BrickDirection direction)
    {
        int newEmptyIndex = emptyIndex;
        int row = emptyIndex / puzzleRow;
        int col = emptyIndex % puzzleRow;

        switch (direction)
        {
            case MOVE_UP:
                if (row > 0)
                    newEmptyIndex = emptyIndex - puzzleRow;
                break;
            case MOVE_DOWN:
                if (row < puzzleRow - 1)
                    newEmptyIndex = emptyIndex + puzzleRow;
                break;
            case MOVE_LEFT:
                if (col > 0)
                    newEmptyIndex = emptyIndex - 1;
                break;
            case MOVE_RIGHT:
                if (col < puzzleRow - 1)
                    newEmptyIndex = emptyIndex + 1;
                break;
        }

        // If the move is valid, swap the tiles
        if (newEmptyIndex != emptyIndex)
        {
            // Swap the empty tile with the adjacent tile
            Collections.swap(puzzel, emptyIndex, newEmptyIndex);
            emptyIndex = newEmptyIndex;
            moves++;

            // Check if the puzzle is solved after the move
            if (isSolved())
                gameStatus = GameStatus.SOLVED;
        }
    }
}
