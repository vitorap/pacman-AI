package pacvitor;

import java.awt.*;
import java.awt.event.*;

// Mostrar informa√ß√£o no sobre
class cabout extends Window
implements MouseListener
{
	final String[] sobre = {
			"",
			"Jogo do Pacman",
			"",
			"Apresentado na Disciplina de ProgramaÁ„o Orientada a Objetos",
			"Use as setas e WASD para se mover. Espa√ßo Pausa"
	};

	cabout(Frame parent)
	{
		super(parent);

		setSize(420, 280);
		setLocation(100, 100);
		show();

		addMouseListener(this);
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		for (int i=0; i<sobre.length; i++)
			g.drawString(sobre[i], 6, (i+1)*18);
	}

	public void mouseClicked(MouseEvent e)
	{
		dispose();
	}

	public void mousePressed(MouseEvent e) 
	{}

	public void mouseReleased(MouseEvent e) 
	{}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}



