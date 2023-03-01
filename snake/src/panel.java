import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

//The Java ActionListener is notified whenever you click on the button or menu item.
public class panel extends JPanel implements ActionListener
{

	static int width=1200;
	static int height =600;
	static int unit =50;

	// score of game
	int score;

	// x, y coordinated of food
	// fx = 0-1200
	// fy = 0-600
	int fx,fy;

	// initial snake length
	int length=3;

	// initial direction of snake : Right
	char dir='R';

	//initial condition of game :false
	boolean flag=false; // means snake has stuck to wall or any false condition


	// rand will make food spawn randomly
	// its class in java (give random values)
	Random random ;

	// timer to check on game regularly , snake and foods gets updated
	Timer timer;
	// running a block of code after some regular instant of time.

	static int delay=160;

	int totunit = width*height/unit;
	// size 12*24 =288 max size of snake
	int xsnake[]=new int[totunit];

	int ysnake[]=new int[totunit];


	panel()
	{
		//setSize will resize the component to the specified size.
		//
		//setPreferredSize sets the preferred size. The component may not actually
		// be this size depending on the size of the container it's in, or if the user re-sized the component manually.
		this.setPreferredSize(new Dimension(width,height));

		this.setBackground(Color.BLACK);

		//The Java KeyListener is notified whenever you change the state of key. It is notified against KeyEvent.
		this.addKeyListener(new MyKey());

		// ? in order to take input from keyboard this focusable should be true
		this.setFocusable(true);

		random = new Random();

		gamestart();

	}

	public void gamestart()
	{

		flag=true;
		//spwan food function
		spawnfood();

		// timer to check on gamestate in each 160 miliseocnd
		timer = new Timer(delay,this);
		timer.start();


	}

	public void spawnfood()
	{
		// we typecast as we do not want fraction
		// nextInt we give range (0 - 24)*50
		fx = random.nextInt((int)width/unit)  * unit;

		fy = random.nextInt((int)height/unit) * unit;

	}

	//After constructor this function will get called first so we are getting some o/p
	// Graphic is class, which can draw circles and other shapes and text
	// graphics is just name of variable(instance variable)
	public void paintComponent(Graphics graphic)
	{
		// super calling JPanel class method of paintComponent
		super.paintComponent(graphic);
		draw(graphic);
	}

	private void draw(Graphics graphic)
	{
		// if game continue flag=true
		if(flag)
		{
			// to spawn food particle
			graphic.setColor(Color.orange);
			                // x, y  , width , ht
			graphic.fillOval(fx,fy,unit,unit);


			// to spawn snake body
			for(int  i = 0 ; i < length ; i++)
			{
				if(i==0)
				{
					// head of snake
					graphic.setColor(Color.red);
					graphic.fillOval(xsnake[0],ysnake[0] ,unit , unit );
				}
				else
				{
					// body of snake
					graphic.setColor(Color.green);
					graphic.fillRect(xsnake[i],ysnake[i] ,unit , unit );
				}
			}
		}
		else
		{
			gameover(graphic);
		}

		// For SCORE display
		graphic.setColor(Color.cyan);
		graphic.setFont(new Font("Times New Roman" , Font.BOLD,40));
		FontMetrics fme = getFontMetrics(graphic.getFont());

		// here i/p : String , x , y cordinates
		// height i.e. y : graphic.getFont().getSize()
		// x : middle of width : (width-fme.stringWidth("Score :"+score))/2
		graphic.drawString("Score : "+score,(width-fme.stringWidth("Score : "+ score))/2,graphic.getFont().getSize());
	}

	public void gameover(Graphics graphic)
	{
//		//the score display
//		graphic.setColor(Color.CYAN);
//		graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
//		FontMetrics fme = getFontMetrics(graphic.getFont());
//		graphic.drawString("Score: "+score, (width - fme.stringWidth("Score: "+score))/2, graphic.getFont().getSize());

		//gameover text
		graphic.setColor(Color.RED);
		graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
		FontMetrics fme1 = getFontMetrics(graphic.getFont());
		graphic.drawString("Game Over", (width - fme1.stringWidth("Game Over"))/2, height/2);

		//replay prompt display
		graphic.setColor(Color.GREEN);
		graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
		FontMetrics fme2 = getFontMetrics(graphic.getFont());
		graphic.drawString("Press R to replay", (width - fme2.stringWidth("Press R to replay"))/2, height/2 - 150);
	}

	public void move()
	{
		// for all other part of body of snake
		// here our x,y getting coordinates of x,y which are front to them
		for(int  i = length ; i>0 ;i--)
		{
			xsnake[i]=xsnake[i-1];
			ysnake[i]=ysnake[i-1];
		}

		// for updating head
		switch(dir)
		{
			case 'U' :  // [0] for head
				// going up on y coordinates thus minus
				ysnake[0] = ysnake[0]-unit;
				break;
			case 'D' :  // [0] for head
				ysnake[0] = ysnake[0]+unit;
				break;
			case 'L' :  // left me jana hai to minus for x coordinates
				xsnake[0] = xsnake[0]-unit;
				break;
			case 'R' :  // [0] for head
				xsnake[0] = xsnake[0]+unit;
				break;
		}
	}


	// check fn use to check if snake collide with body or wall
	void check()
	{
		// check if head hit the body of snake
		for(int  i = length ; i> 0 ; i--)
		{
			if(xsnake[0]==xsnake[i] && ysnake[0]==ysnake[i])
			{
				flag=false;
			}
		}


		// cheking hit with wall
		if(xsnake[0]<0)
		{
			// leftside out bounds
			//xsnake[0]=width;
			flag=false;
		}
		if(xsnake[0]>width)
		{
			// rightside out bounds
			//xsnake[0]=0;
			flag=false;
		}

		if(ysnake[0]<0)
		{
			// up out of bounds
			//ysnake[0]=height;
			flag=false;
		}

		if(ysnake[0]>height)
		{
			//ysnake[0]=0;
			// bottom out of bounds
			flag=false;
		}

		if(flag==false)
		{
			timer.stop();
		}
	}

	public void foodeaten()
	{
		if(xsnake[0]==fx && ysnake[0]==fy)
		{
			length++;
			score++;
			spawnfood();
		}
	}

	//KeyAdapter : An abstract adapter class for receiving keyboard events. The methods in this class are empty.
	// This class exists as convenience for creating listener objects.
	// Extend this class to create a KeyEvent listener and override the methods for the events of interest.
	public class MyKey extends KeyAdapter
	{
		public void keyPressed(KeyEvent k)
		{
			switch(k.getKeyCode())
			{
				case VK_UP :
					// means if you want to go up , snake should not be going down
					if(dir!='D')
						dir='U';
					break;
				case VK_DOWN :

					if(dir!='U')
						dir='D';
					break;
				case VK_RIGHT :
					if(dir!='L')
						dir='R';
					break;
				case VK_LEFT :
					if(dir!='R')
						dir='L';
					break;
				case VK_R : // press R to replay
					// when flag is false meand only when gameover
					if(flag==false)
					{
						score=0;
						length=3;
						dir='R';
						Arrays.fill(xsnake,0);
						Arrays.fill(ysnake,0);
						gamestart();
					}

					break;
			}
		}
	}

	//The actionPerformed() method is invoked automatically whenever you click on the registered component.
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(flag)
		{
			move();
			foodeaten();
			check();
		}

		//explicitly calls paintComponent function
		//repaint : to refresh the display by calling repaint().
		// The repaint() call eventually leads to paintComponent() being called.

		// for every keypressed it will repaint our panel
		repaint();

	}
}
