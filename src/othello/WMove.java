/*
 * WMove.java
 *
 * Created on March 28, 2005, 1:57 PM
 */

package othello;

/**
 *
 * @author Menachem & Shira
 */

public class WMove extends java.awt.Point{
    
    WMove(int x, int y, int weight) {
        super(x, y);
        this.weight = weight;
    }
    int weight;
    
}
