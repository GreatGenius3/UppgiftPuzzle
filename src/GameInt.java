// Game interface
public interface GameInt
{
    enum GameStatus { PAUSED, PLAYING, SOLVED }
    enum BrickDirection { MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT }
    void newGame(int row, int cells);
    void reset();
}
