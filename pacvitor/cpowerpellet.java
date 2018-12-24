/**
 * Essa parte do codigo, é responsavel por draw as dots, powerpellets, e apaga-las quando o pacman come
 * Tambem liga o poder da power dot
 */

package pacvitor;

import java.awt.*;

public class cpowerpellet //aqui  diz onde imprime as power dots. Está imprimindo só 4
{
	final int iX[]={1,19,19,1};
	final int iY[]={1,14,1,14};
	
	final int iX2[]={1,19,1,19};
	final int iY2[]={2,2,13,13};

	final int iShowCont=32;
	final int iHideCont=16;
	int frameCont;
	int showStatus;

	int iValid[];
	int pDotNumber =4;

	Window applet;
	Graphics graphics;
	cfantasma [] fantasmas;
	Image imagePowerDot;
	Image imageBlank;
	cpowerpellet(Window a, Graphics g, cfantasma [] gh)
	{
		applet=a;
		graphics=g;
		fantasmas=gh;
		iValid = new int[5];

		imagePowerDot=applet.createImage(16,16);
		cimage.drawPowerDot(imagePowerDot);

		imageBlank=applet.createImage(16,16);
		Graphics imageG=imageBlank.getGraphics();
		imageG.setColor(Color.black);
		imageG.fillRect(0,0,16,16);

		frameCont=iShowCont;
		showStatus=1;	// show
	}

	public void start()
	{
		for (int i=0; i<pDotNumber; i++)
			iValid[i]=1;
	}

	void clear(int dot)
	{
		graphics.drawImage(imageBlank, iX[dot]*16, iY[dot]*16, applet);
	}

	void eat(int iCol, int iLinha)
	{
		for (int i=0; i<pDotNumber; i++)
		{
			if (iX[i]==iCol && iY[i]==iLinha)
			{
				iValid[i]=0;
				clear(i);
			}
		}
		for (int i=0; i<4; i++) // 4 = numero de fantasmas
			fantasmas[i].fraco();
	}

	public void draw()
	{
		frameCont--;
		if (frameCont==0)
		{
			if (showStatus==1)
			{
				showStatus=0;
				frameCont=iHideCont;
			}
			else
			{
				showStatus=1;
				frameCont=iShowCont;
			}
		}
		for (int i=0; i<pDotNumber; i++)
		{
			if (iValid[i]==1 && showStatus==1)
				graphics.drawImage(imagePowerDot,iX[i]*16, iY[i]*16, applet);
			else
				graphics.drawImage(imageBlank, iX[i]*16, iY[i]*16, applet);
		}
	} 
}
