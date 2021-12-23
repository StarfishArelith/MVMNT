import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class mvmnt extends JFrame implements KeyListener
{
	private static Tile[] terArr;
	private static String wall = "#";
	private static String floor = ".";
	private static String character = "@";
	private static String target = "X";
	private static int width;
	private static int height;
	private static int counter = 0;
	private static int tpos;
	private static int cpos;
	private static String map = "";
	private static JTextArea txa;
	private static int score = 0;
	
	public mvmnt(int height,int width)
	{
		super("MVMNT");
		this.width = width;
		this.height = height;
		terArr = new Tile[width*height];
		
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
		txa.setBackground(Color.BLACK);
		txa.setForeground(Color.WHITE);
		lPanel.add(txa);
		
		txa.addKeyListener(this);
	}
	
	public static void main(String[] args)
	{
		mvmnt mv = new mvmnt(12,12); 	//height,width
										//why is it back to front?
		mv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mv.setSize(600,700);
		mv.setLocationRelativeTo(null);
		mv.setVisible(true);
		
		
		for(int i = 0;i < width;i++)
		{
			for(int j = 0;j < height;j++)
			{
				if(i != 0 && j != 0 && i != width - 1 && j != height - 1)
				{
					terArr[counter] = new Tile(floor);
				}
				else
				{
					terArr[counter] = new Tile(wall);
				}
				counter++;
			}
		}
		
		cpos = (terArr.length / 2) - (height / 2);
		while(terArr[cpos].getTerrain().equals(floor) == false)//place player at a valid location
		{
			cpos++;
		}
		terArr[cpos].setEntity(character);
		counter = 0;
		respawnTarget();
		redraw();
	}
	
	public static void redraw()
	{
		for(int k = 0;k < width;k++)
		{
			for(int l = 0;l < height;l++)
			{
				map = map + terArr[counter].getDisplayed();
				counter++;
			}
			map = map + "\r\n";
		}
		map = map + "\r\n\r\nScore: " + score;
		counter = 0;
		txa.setText(map);
		map = "";
	}
	
	public static void respawnTarget()
	{
		tpos = ThreadLocalRandom.current().nextInt(width + 1, terArr.length - width);
		while(terArr[tpos].getTerrain().equals(floor) == false)
		{
			tpos = ThreadLocalRandom.current().nextInt(width + 1, terArr.length - width);
		}
		terArr[tpos].setEntity(target);
	}
	
	public void directAction(String direction)
	{
		if(terArr[cpos - height].getTerrain().equals(wall) == false && direction.equals("up") == true)
		{
			if(terArr[cpos - height].getEntity().equals(target) == true)
			{
				terArr[cpos - height].setEntity("");
				score++;
				respawnTarget();
				redraw();
			}
			else
			{
				terArr[cpos].setEntity("");
				terArr[cpos - height].setEntity(character);
				cpos = cpos - height;
				redraw();
			}
			
		}
		else if(terArr[cpos + height].getTerrain().equals(wall) == false && direction.equals("down") == true)
		{
			if(terArr[cpos + height].getEntity().equals(target) == true)
			{
				terArr[cpos + height].setEntity("");
				score++;
				respawnTarget();
				redraw();
			}
			else
			{
				terArr[cpos].setEntity("");
				terArr[cpos + height].setEntity(character);
				cpos = cpos + height;
				redraw();
			}
			
		}
		else if(terArr[cpos - 1].getTerrain().equals(wall) == false && direction.equals("left") == true)
		{
			if(terArr[cpos - 1].getEntity().equals(target) == true)
			{
				terArr[cpos - 1].setEntity("");
				score++;
				respawnTarget();
				redraw();
			}
			else
			{
				terArr[cpos].setEntity("");
				terArr[cpos - 1].setEntity(character);
				cpos--;
				redraw();
			}
			
		}
		else if(terArr[cpos + 1].getTerrain().equals(wall) == false && direction.equals("right") == true)
		{
			if(terArr[cpos + 1].getEntity().equals(target) == true)
			{
				terArr[cpos + 1].setEntity("");
				score++;
				respawnTarget();
				redraw();
			}
			else
			{
				terArr[cpos].setEntity("");
				terArr[cpos + 1].setEntity(character);
				cpos++;
				redraw();
			}
			
		}
	}
	
	public void keyPressed(KeyEvent event)
	{
		
	}
	
	public void keyTyped(KeyEvent event)
	{
		
	}
	
	public void keyReleased(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_UP)
		{
			directAction("up");
		}
		else if(event.getKeyCode() == KeyEvent.VK_DOWN)
		{
			directAction("down");
		}
		else if(event.getKeyCode() == KeyEvent.VK_LEFT)
		{
			directAction("left");
		}
		else if(event.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			directAction("right");
		}
	}
}