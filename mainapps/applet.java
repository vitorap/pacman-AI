/**
 *Menuzinho de Play
 */

package mainapps;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

import pacvitor.cpcman;


public class applet extends Applet
implements ActionListener
{
	

	static cpcman pacMan=null;

	public void init()
	{
		setSize(50,50);
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		Button play=new Button("PLAY");
		add(play);

		play.addActionListener(this);

	}

	void newGame()
	{
		pacMan=new cpcman();
	}

	
	public void actionPerformed(ActionEvent e)
	{
		if ( pacMan != null && ! pacMan.isFinalized() )
				return;
		newGame();
	}

}
