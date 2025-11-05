public interface GameInt
{
    enum GameStatus { PAUSED, IN_PROGRESS, SOLVED }
    enum BrickDirection { MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT }
    void newGame(int row, int cells);
    void solve();
    void reset();
}
