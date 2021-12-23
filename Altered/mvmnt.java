import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

    public class mvmnt extends JFrame implements KeyListener
    {
        private static String[][] strArr2;
        private static String wall = "#";
        private static String floor = ".";
        private static String character = "@";
        private static String target = "X";
        private static int width;
        private static int height;
        private static String map = "";
        private static JTextArea txa;
        private static Pos playerPos;

        public static class Pos
        {
            public int x;
            public int y;
            public Pos(int x, int y)
            {
                this.x = x;
                this.y = y;
            }
        }

        public mvmnt(int height,int width)
        {
            super("MVMNT");
            this.width = width;
            this.height = height;
            strArr2 = new String[width][height];

            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JPanel lPanel = new JPanel();
            lPanel.setLayout(new GridLayout(1,0));
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1;
            c.weighty = 1;
            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.CENTER;
            add(lPanel,c);

            txa = new JTextArea(height,width);
            txa.setFont(new Font("Courier New", Font.PLAIN, 12));
            txa.setEditable(false);
            lPanel.add(txa);

            txa.addKeyListener(this);
        }

        public static void main(String[] args)
        {
            mvmnt mv = new mvmnt(20,10); 	//height,width
            //why is it back to front?
            mv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mv.setSize(600,700);
            mv.setLocationRelativeTo(null);
            mv.setVisible(true);

            int maxWidth = width - 1;
            int maxHeight = height -1;

            //Efficient population (NO IFs) - fill floor first
            for(int height = 0; height < width; height++)
            {
                for(int width = 0; width < mvmnt.height; width++)
                {
                    strArr2[height][width] = floor;
                }
            }
            //make 2 smaller passes to fill the top bot walls
            for(int height = 0; height < mvmnt.height; height++)
            {
                strArr2[0][height] = wall; // top wall
                strArr2[maxWidth][height] = wall;
            }
            //make last 2 small passes to fill side walls
            for(int width = 0; width < mvmnt.width; width++)
            {
                strArr2[width][0] = wall;
                strArr2[width][maxHeight] = wall;
            }
            //set player
            playerPos = new Pos(maxWidth/2, maxHeight/2);
            strArr2[playerPos.x][playerPos.y] = character;
            //set target
            strArr2[maxWidth-1][maxHeight-1] = target;
            //draw start map
            redraw();
        }

        public static void redraw()
        {
            map = ""; // clear before draw
            for(String[] row: strArr2)
            {
                for(String column: row)
                {
                    map += column;
                }
                map += "\r\n";
            }
            txa.setText(map);
        }

        public void keyPressed(KeyEvent event){}

        public void keyTyped(KeyEvent event){}

        private void movePlayer(int x, int y)
        {
            if(strArr2[x][y].equalsIgnoreCase(wall) == false)
            {
                strArr2[playerPos.x][playerPos.y] = floor; // Set floor where player is
                playerPos.x = x;//move x
                playerPos.y = y;//move y
                strArr2[playerPos.x][playerPos.y] = character; // Set player to new position

                redraw();
            }
        }

        public void keyReleased(KeyEvent event)
        {
            if(event.getKeyCode() == KeyEvent.VK_UP)
            {
                movePlayer(playerPos.x-1, playerPos.y);
            }
            else if(event.getKeyCode() == KeyEvent.VK_DOWN)
            {
                movePlayer(playerPos.x+1, playerPos.y);
            }
            else if(event.getKeyCode() == KeyEvent.VK_LEFT)
            {
                movePlayer(playerPos.x, playerPos.y-1);
            }
            else if(event.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                movePlayer(playerPos.x, playerPos.y+1);
            }
        }
    }

