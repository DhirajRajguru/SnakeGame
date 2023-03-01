

import javax.swing.*;
import java.awt.*;

public class frame extends JFrame
{
	frame()
	{

		// this is helping us to implement functionality of JFrame class
		// here we are making class panel which will will use JPanel
		this.add(new panel());

		this.setTitle("Snake Game");
		// so that user cant resize window
		this.setResizable(false);

		//it sizes the frame so that all its contents are at or above their preferred sizes.
		/*pack leaves the frame layout manager in charge of
		the frame size and layout managers are good at adjusting to platform
		dependencies and other factors that affect the component size.*/
		this.pack();  // we can use setSize also but


		this.setVisible(true);
	}

}
