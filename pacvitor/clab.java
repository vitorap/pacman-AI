package pacvitor;

import java.awt.*;

/* ajeita o labirinto */
public class clab
{
	static final int VAZIO=0;
	static final int PAREDE=1;
	static final int PORTA=2;
	static final int DOT=4;
	static final int POWER_PELLET=8;

	static final int HEIGHT=16;
	static final int WIDTH=21;

	static final int iHeight=HEIGHT*16;
	static final int iWidth=WIDTH*16;

	
	Window applet;
	Graphics graphics;
	Image imageLab;
	Image imageDot;
	int iTotalDotcont; //dots sobrando
	int[][] iLab;

	
	clab(Window a, Graphics g) //inicia labirinto
	{
		// construtor
		applet=a;
		graphics=g;

		imageLab=applet.createImage(iWidth, iHeight);
		imageDot=applet.createImage(2,2);

		iLab=new int[HEIGHT][WIDTH];
	}

	public void start()
	{
		int i,j,k;
		iTotalDotcont=0;
		for (i=0; i<HEIGHT; i++)
			for (j=0; j<WIDTH; j++)
			{
				switch (ctables.LabDefine[i].charAt(j))
				{
				case ' ':
					k=VAZIO;
					break;
				case 'X':
					k=PAREDE;
					break;
				case '.':
					k=DOT;
					iTotalDotcont++;
					break;
				case 'O':
					k=POWER_PELLET;
					break;
				case '-':
					k=PORTA;
					break;
				default:
					k=DOT;
					iTotalDotcont++;
					break;
				}
				iLab[i][j]=k;
			}
		// create initial lab image
		createImage();	
	}

	public void draw()
	{
		graphics.drawImage(imageLab,0,0,applet);
		drawDots();
	}

	void drawDots()	// on the offscreen
	{
		int i,j;

		for (i=0; i<HEIGHT; i++)
			for (j=0; j<WIDTH; j++)
			{
				if (iLab[i][j]==DOT)
					graphics.drawImage(imageDot, j*16+7,i*16+7,applet);
			}
	}

	void createImage()
	{
		cimage.drawDot(imageDot);
	Graphics glab=imageLab.getGraphics();
		glab.setColor(Color.black);
		glab.fillRect(0,0,iWidth,iHeight);

		DrawParede(glab);
	}

	public void DrawDot(int icol, int iLinha)
	{
		if (iLab[iLinha][icol]==DOT)
			graphics.drawImage(imageDot, icol*16+7,iLinha*16+7,applet);
	}	

	void DrawParede(Graphics g)
	{
		int i,j;
		int iDir;

		g.setColor(Color.green);

		for (i=0; i<HEIGHT; i++)
		{
			for (j=0; j<WIDTH; j++)
			{
				for (iDir=ctables.RIGHT; iDir<=ctables.DOWN; iDir++)
				{
					if (iLab[i][j]==PORTA)
					{
						g.drawLine(j*16,i*16+8,j*16+16,i*16+8);
						continue;
					}
					if (iLab[i][j]!=PAREDE)	continue;
					switch (iDir)
					{
					case ctables.UP:
						if (i==0)	break;
						if (iLab[i-1][j]==PAREDE)
							break;
						DrawBoundary(g, j, i-1, ctables.DOWN);
						break;
					case ctables.RIGHT:
						if (j==WIDTH-1)	break;
						if (iLab[i][j+1]==PAREDE)
							break;
						DrawBoundary(g, j+1,i, ctables.LEFT);
						break;
					case ctables.DOWN:
						if (i==HEIGHT-1)	break;
						if (iLab[i+1][j]==PAREDE)
							break;
						DrawBoundary(g, j,i+1, ctables.UP);
						break;
					case ctables.LEFT:
						if (j==0)	break;
						if (iLab[i][j-1]==PAREDE)
							break;
						DrawBoundary(g, j-1,i, ctables.RIGHT);
						break;
					default:	
					}
				}
			}
		}
	}

	void DrawBoundary(Graphics g, int col, int linha, int iDir)
	{
		int x,y;

		x=col*16;	y=linha*16;

		switch (iDir)
		{
		case ctables.LEFT:
			if (iLab[linha+1][col]!=PAREDE)
				if (iLab[linha+1][col-1]!=PAREDE)
				{
					g.drawArc(x-8-6, y+8-6, 12, 12, 270, 100);
				}
				else
				{
					g.drawLine(x-2,y+8,x-2,y+16);
				}
			else
			{
				g.drawLine(x-2,y+8,x-2,y+17);
				g.drawLine(x-2,y+17,x+7,y+17);
			}

			if (iLab[linha-1][col]!=PAREDE)
				if (iLab[linha-1][col-1]!=PAREDE)
				{
					//						arc(x-8,y+7,0,90,6);
					g.drawArc(x-8-6, y+7-6, 12,12,0, 100);
				}
				else
				{
					g.drawLine(x-2,y,x-2,y+7);
				}
			else
			{
				g.drawLine(x-2,y-2,x-2,y+7);
				g.drawLine(x-2,y-2,x+7,y-2);
			}	
			break;

		case ctables.RIGHT:
			if (iLab[linha+1][col]!=PAREDE)
				if (iLab[linha+1][col+1]!=PAREDE)
				{
					g.drawArc(x+16+7-6, y+8-6, 12,12,180, 100);
				}
				else
				{
					g.drawLine(x+17,y+8,x+17,y+15);
				}
			else	
			{
				g.drawLine(x+8,y+17,x+17,y+17);
				g.drawLine(x+17,y+8,x+17,y+17);
			}	
			if (iLab[linha-1][col]!=PAREDE)
				if (iLab[linha-1][col+1]!=PAREDE)
				{
					g.drawArc(x+16+7-6, y+7-6, 12,12, 90, 100);
				}
				else
				{
					g.drawLine(x+17,y,x+17,y+7);
				}
			else
			{
				g.drawLine(x+8,y-2,x+17,y-2);
				g.drawLine(x+17,y-2,x+17,y+7);
			}
			break;

		case ctables.UP:
			if (iLab[linha][col-1]!=PAREDE)
				if (iLab[linha-1][col-1]!=PAREDE)
				{
					g.drawArc(x+7-6, y-8-6, 12,12, 180, 100);
				}
				else
				{
					g.drawLine(x,y-2,x+7,y-2);
				}

			if (iLab[linha][col+1]!=PAREDE)
				if (iLab[linha-1][col+1]!=PAREDE)
				{
					g.drawArc(x+8-6, y-8-6, 12,12, 270, 100);
				}
				else
				{
					g.drawLine(x+8,y-2,x+16,y-2);
				}
			break;

		case ctables.DOWN:
			if (iLab[linha][col-1]!=PAREDE)
				if (iLab[linha+1][col-1]!=PAREDE)
				{
					g.drawArc(x+7-6, y+16+7-6, 12,12, 90, 100);
				}
				else
				{
					g.drawLine(x,y+17,x+7,y+17);
				}

			if (iLab[linha][col+1]!=PAREDE)
				if (iLab[linha+1][col+1]!=PAREDE)
				{
					g.drawArc(x+8-6, y+16+7-6, 12,12, 0, 100);
				}
				else
				{	
					g.drawLine(x+8,y+17,x+15,y+17);
				}
			break;
		}	
	}

}

