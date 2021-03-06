package othello;
/*
 * VOthello.java
 *
 * Created on March 24, 2005, 11:37 AM
 */

/**
 *
 * @author  Menachem & Shira
 */

import java.awt.*;
import java.awt.geom.*;
public class VOthello extends javax.swing.JFrame 
                      implements Display {
    
    /** Creates new form VOthello */
    public VOthello() {
        initComponents();
        initMyComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        newGameMenu = new javax.swing.JMenuItem();
        exitMenu = new javax.swing.JMenuItem();

        getContentPane().setLayout(new java.awt.FlowLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        FileMenu.setText("File");
        newGameMenu.setText("New Game");
        newGameMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGame(evt);
            }
        });

        FileMenu.add(newGameMenu);

        exitMenu.setText("Exit");
        exitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitGame(evt);
            }
        });

        FileMenu.add(exitMenu);

        jMenuBar1.add(FileMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }//GEN-END:initComponents

    private void NewGame(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGame
        game.newGame();
        repaint();
    }//GEN-LAST:event_NewGame

    private void ExitGame(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitGame
        System.exit(0);
    }//GEN-LAST:event_ExitGame

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        int x;
        int y;
        
        if (!(evt.getX() > bLeft && evt.getY() > bTop)) return;
        x = (evt.getX() - bLeft) / blockSize;
        y = (evt.getY() - bTop) / blockSize;
        
        if (game.addPiece(x, y)) {
            update();
        }
    }//GEN-LAST:event_formMousePressed
    
    private void initMyComponents() {
        game = new Othello((Display) this);
        this.setSize(bLeft + blockSize * bWidth, bTop + blockSize * bHeight);
        
        g = (Graphics2D) this.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        game.newGame();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VOthello().setVisible(true);
            }
        });
    }
    
    public void paint(Graphics g) {
        super.paint(g);

        //Color darkGreen = new Color(0, 200, 0);
        Paint darkGreen = new GradientPaint(0, 0, Color.GREEN, 100, 100, new Color(0, 200, 0), true);
        this.g.setPaint(darkGreen);
        this.g.fillRect(bLeft, bTop, bWidth * blockSize, bHeight * blockSize);
        
        this.g.setColor(Color.BLACK);
        for (int i = 0; i < bWidth; i++) {
            this.g.drawLine(bLeft + i * blockSize, bTop, bLeft + i * blockSize, bTop + bHeight * blockSize); 
        }
        for (int i = 0; i < bHeight; i++) {
            this.g.drawLine(bLeft, bTop + i * blockSize, bLeft + blockSize * bWidth, bTop + i * blockSize);
        }
        update();
    }
            
    public void update() {
        for (int i = 0; i < bWidth; i++) {
            for (int j = 0; j < bHeight; j++) {
                if (game.blocks[i][j] == Othello.WHITE) {
                    g.setColor(Color.WHITE);
                }
                else if (game.blocks[i][j] == Othello.BLACK) {
                    g.setColor(Color.BLACK);
                }
                
                if (game.blocks[i][j] != Othello.NONE) {
                    g.fillOval(bLeft + delta + i * blockSize, bTop + delta + j * blockSize,
                            blockSize - 2 * delta, blockSize - 2 * delta);
                }
            }
        }
        
        // AI
        if (game.turn == Othello.WHITE && WhiteAI != -1) {
            Point move = OthelloAI.getBestMove(game, WhiteAI);
            game.addPiece(move.x, move.y);
            try {Thread.sleep(50);} catch (Exception e) {}
            update();

        }
        else if (game.turn == Othello.BLACK && BlackAI != -1) {
            Point move = OthelloAI.getBestMove(game, BlackAI);
            game.addPiece(move.x, move.y);
            try {Thread.sleep(50);} catch (Exception e) {}
            update();
        }
        /*
        Cursor
        java.awt.image.BufferedImage image = (java.awt.image.BufferedImage) this.createImage(32, 32);
        Graphics2D g2 = image.createGraphics();
        g.setColor((game.turn == Othello.WHITE) ? Color.WHITE : Color.BLACK);
        g.fillOval(0, 0, 32, 32);
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setCursor(tk.createCustomCursor(image, new Point(16, 16), "Turn"));
        */
    }
    
    public void gameOver(String message) {
        update();
        javax.swing.JOptionPane.showMessageDialog(this, message);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem newGameMenu;
    // End of variables declaration//GEN-END:variables

    // My variabes
    Othello game;
    Graphics2D g;
    //TODO Incorporate AI settings into Menu
    int WhiteAI = -1;
    int BlackAI = -1;
    
    static final int bLeft = 0;
    static final int bTop = 51;
    static final int blockSize = 80;
    static final int delta = blockSize / 8; // Distance from edge of square to piece;
    static final int bWidth = Othello.bWidth;
    static final int bHeight = Othello.bHeight;
}
