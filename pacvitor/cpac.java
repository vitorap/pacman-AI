package pacvitor;

import java.awt.*;

public class cpac
{
	
	final int DOT_ESPERAR=4;
	int iDotEsperar;

	// posição e direção atual
	int iX, iY;
	int iDir;

	Window applet;
	Graphics graphics;
	Image [][] imagePac;
	clab lab;
	cpowerpellet powerPellet;
	cpac(Window a, Graphics g, clab m, cpowerpellet p)    {
		applet=a;
		graphics=g;
		lab=m;
		powerPellet=p;
		imagePac=new Image[4][4];
		for (int i=0; i<4; i++)
			for (int j=0; j<4; j++)
			{
				imagePac[i][j]=applet.createImage(18,18);
				cimage.drawPac(imagePac[i][j],i,j);
			}	
	}

	public void start()
	{
		iX=10*16;
		iY=10*16;
		iDir=1;		// apertar para cima para começar o jogo
		iDotEsperar=0;
	}

	public void draw()
	{
		lab.DrawDot(iX/16, iY/16);
		lab.DrawDot(iX/16+(iX%16>0?1:0), iY/16+(iY%16>0?1:0));

		int iImageStep=(iX%16 + iY%16)/2; 	
		if (iImageStep<4)
			iImageStep=3-iImageStep;
		else
			iImageStep-=4;
		graphics.drawImage(imagePac[iDir][iImageStep], iX-1, iY-1, applet);
	}	

	// retorna 1 se comeu bolinha
	// retorna 2 se comeu bolona
	public int move(int iNextDir)
	{
		int k=0;
		int eaten=0;

		if (iNextDir!=-1 && iNextDir!=iDir)	
		{
			if (iX%16!=0 || iY%16!=0)
			{
			
				if (iNextDir%2==iDir%2)
					iDir=iNextDir;
			}	
			else    
			{
				if ( labOK(iX/16+ ctables.iXDirecao[iNextDir],
						iY/16+ ctables.iYDirecao[iNextDir]) )
				{
					iDir=iNextDir;
					iNextDir=-1;
				}
			}
		}
		if (iX%16==0 && iY%16==0)
		{

			
			switch (lab.iLab[iY/16][iX/16])
			{
			case clab.DOT:
				eaten=1;
				lab.iLab[iY/16][iX/16]=clab.VAZIO;
				lab.iTotalDotcont--;
				iDotEsperar=DOT_ESPERAR;
				break;
			case clab.POWER_PELLET:
				eaten=2;
				powerPellet.eat(iX/16, iY/16);
				lab.iLab[iY/16][iX/16]=clab.VAZIO;
				break;
						
			}
				
			if (lab.iLab[iY/16+ ctables.iYDirecao[iDir]]
			               [iX/16+ ctables.iXDirecao[iDir]]==1)
			{
				return(eaten);	
			}
		}
		if (iDotEsperar==0)
		{
			iX+= ctables.iXDirecao[iDir];
			iY+= ctables.iYDirecao[iDir];
		}
		else	iDotEsperar--;
		return(eaten);
	}	

	boolean labOK(int iRow, int icol)
	{
		if ( (lab.iLab[icol][iRow] & ( clab.PAREDE | clab.PORTA)) ==0)
			return(true);
		return(false);
	}
}









