package othello;
/*
 * Othello.java
 *
 * Created on March 24, 2005, 11:37 AM
 */

/**
 *
 * @author Menachem & Shira
 */
public class Othello {
    
    /** Creates a new instance of Othello */
    public Othello(Display display) {
        this.display = display;
    }
    public Othello(Othello o) {
        turn = o.turn;
        blocks = new int[bWidth][bHeight];
        
        for (int i = 0; i < bWidth; i++) {
            for (int j = 0; j < bHeight; j++) {
                blocks[i][j] = o.blocks[i][j];
            }
        }

        score = new int[3];
        score[0] = 0;
        score[1] = o.score[1];
        score[2] = o.score[2];
    }
    
    public void newGame() {
        blocks = new int[bWidth][bHeight];
        
        blocks[bWidth / 2 - 1][bHeight / 2 - 1] = WHITE;
        blocks[bWidth / 2][bHeight / 2] = WHITE;
        blocks[bWidth / 2 - 1][bHeight / 2] = BLACK;
        blocks[bWidth / 2][bHeight / 2 - 1] = BLACK;
        
        turn = BLACK;
        
        score[1] = score[2] = 2;
        
    }
    public boolean addPiece(int x, int y) {

        boolean allowed = false;
    
        if (blocks[x][y] != NONE)
            return false;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int n = 1;
                while (x + i * n >= 0 && x + i * n < bWidth &&
                       y + j * n >= 0 && y + j * n < bHeight) {
                    if (blocks[x + i * n][y + j * n] == NONE)
                        break;
                    if (blocks[x + i * n][y + j * n] == turn && n == 1)
                        break;
                    if (blocks[x + i * n][y + j * n] == turn) {
                        for (int m = 1; m < n; m++) {
                            blocks[x + i * m][y + j * m] = turn;
                        }
                        score[turn] += n;
                        score[toggle(turn)] -= n;        
                        allowed = true;
                        break;
                    }
                    n++;
                }
            }
        }

        if (allowed) {
            blocks[x][y] = turn;
            turn = toggle(turn);
            score[turn]++;
            
            if (!isAvaliableMove()) {
                turn = toggle(turn);
                if (!isAvaliableMove())
                    gameOver();
            }
        }
        
        return allowed;
    }
    public boolean isAllowed(int x, int y) {
        
        if (blocks[x][y] != NONE)
            return false;
        
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int n = 1;
                while (x + i * n >= 0 && x + i * n < bWidth &&
                       y + j * n >= 0 && y + j * n < bHeight) {
                    if (blocks[x + i * n][y + j * n] == NONE)
                        break;
                    if (blocks[x + i * n][y + j * n] == turn && n == 1)
                        break;
                    if (blocks[x + i * n][y + j * n] == turn)
                        return true;
                    n++;
                }
            }
        }
        return false;
        
    }
    public boolean isAvaliableMove() {
        for (int i = 0; i < bWidth; i++) {
            for (int j = 0; j < bHeight; j++) {
                if (isAllowed(i, j))
                    return true;
            }
        }
        return false;
    }
    public void gameOver() {
        if (display == null)
            return;
        
        turn = NONE;
        
        String message;
        if (score[WHITE] > score[BLACK])
            message = "White wins!";
        else if (score[WHITE] < score[BLACK])
            message = "Black wins!";
        else
            message = "Tie!";
        display.gameOver(message);
    }
    
    public static int toggle(int color) {
        if (color == WHITE)
            return BLACK;
        if (color == BLACK)
            return WHITE;
        
        throw new RuntimeException();
    }
    
    public Othello Clone() {
        return new Othello(this);
        
    }
    // Variables
    int[][] blocks;
    int turn;
    Display display;
    int[] score = new int[3];

    public static final int bWidth = 8;
    public static final int bHeight = 8;

    public static final int NONE = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    
}
