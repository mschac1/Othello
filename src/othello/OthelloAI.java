/*
 * OthelloAI.java
 *
 * Created on March 28, 2005, 1:10 PM
 */

package othello;

import java.awt.Point;
import java.util.Random;
import java.util.ArrayList;
/**
 *
 * @author Menachem & Shira
 */
public class OthelloAI {
    
    /** Creates a new instance of OthelloAI */
    public OthelloAI() {
    }
    
    public static Point getBestMove(Othello game, int level) {
        return (Point) getBestWMove(game, level);
    }
    
    private static WMove getBestWMove(Othello game, int level) {
        ArrayList moves = new ArrayList();
        Othello testGame;
        int weight;

        if (level == 0) {
            for (int i = 0; i < Othello.bWidth; i++) {
                for (int j = 0; j < Othello.bHeight; j++) {
                    if (game.isAllowed(i, j))
                        moves.add(new WMove(i, j, 0));
                }
            }
        }

        else if (level == 1) { 
            for (int i = 0; i < Othello.bWidth; i++) {
                for (int j = 0; j < Othello.bHeight; j++) {
                    if (game.isAllowed(i, j))
                    {
                        testGame = game.Clone();
                        testGame.addPiece(i, j);
                        weight = testGame.score[game.turn] - game.score[game.turn];
                        if (moves.isEmpty())
                        {
                            moves.add(new WMove(i, j, weight));
                        }
                        else if (weight > ((WMove) moves.get(0)).weight) {
                            moves.clear();
                            moves.add(new WMove(i, j, weight));
                        }
                        else {
                            moves.add(new WMove(i, j, weight));
                        }
                    }
                }
            }
        }

        else { 
            for (int i = 0; i < Othello.bWidth; i++) {
                for (int j = 0; j < Othello.bHeight; j++) {
                    if (game.isAllowed(i, j))
                    {
                        testGame = game.Clone();
                        testGame.addPiece(i, j);
                        weight = testGame.score[game.turn] - game.score[game.turn];
                        if (testGame.turn == Othello.NONE)
                        {
                            if (testGame.score[game.turn] > testGame.score[Othello.toggle(game.turn)])
                                weight = 100;
                            else if (testGame.score[game.turn] < testGame.score[Othello.toggle(game.turn)])
                                weight = -100;
                        }
                        else if (testGame.turn == game.turn)
                            weight += getBestWMove(testGame, level - 1).weight;
                        else
                            weight -= getBestWMove(testGame, level - 1).weight;
                        
                        if (moves.isEmpty())
                            moves.add(new WMove(i, j, weight));
                        else if ( weight > ((WMove) moves.get(0)).weight) {
                            moves.clear();
                            moves.add(new WMove(i, j, weight));
                        }
                        else {
                            moves.add(new WMove(i, j, weight));
                        }
                    }
                }
            }
        }

        
        if (moves.isEmpty())
            return new WMove(-1, -1, 0);
        return (WMove) moves.get(r.nextInt(moves.size()));
    }
    
    private static Random r = new Random();
}
