package pacvitor;

import java.lang.Error;
import java.awt.*;

public class cfantasma
{
	final int IN=0;
	final int OUT=1;
	final int FRACO=2;
	final int OLHOS=3;

	final int[] passo=	{7, 7, 1, 1};
	final int[] frames=	{8, 8, 2, 1};

	final int INIT_FRACO_COUNT=583;	// Fica fugindo por 583 frames. Entre cada frame, existe um tempo de 12mS, totalizando 6,996 segundos, o mais proximo possivel de 7. 
	int fracoCont;

	cvelocidade velocidade=new cvelocidade();

	int iX, iY, iDir, iStatus;
	int iPisca, iFracoCont;

	final int DIR_FACTOR=2;
	final int POS_FACTOR=10;

	Window applet;
	Graphics graphics;

	// o labirinto conhecido pelos fantasmas
	clab lab;


	Image imageFantasma; 
	Image imageFraco;
	Image imageEye;

	cfantasma(Window a, Graphics g, clab m, Color color)
	{
		applet=a;
		graphics=g;
		lab=m;

		imageFantasma=applet.createImage(18,18);
		cimage.drawFantasma(imageFantasma, 0, color);

		imageFraco=applet.createImage(18,18);
		cimage.drawFantasma(imageFraco,1, Color.blue);

		imageEye=applet.createImage(18,18);
		cimage.drawFantasma(imageEye,2, Color.lightGray);
	}

	public void start(int initialPosition, int round)
	{
		if (initialPosition>=2)
			initialPosition++;
		iX=(8+initialPosition)*16; iY=8*16;
		iDir=3;
		iStatus=IN;

		fracoCont=INIT_FRACO_COUNT/((round+1)/2);

		velocidade.start(passo[iStatus], frames[iStatus]);
	}

	public void draw()
	{
		lab.DrawDot(iX/16, iY/16);
		lab.DrawDot(iX/16+(iX%16>0?1:0), iY/16+(iY%16>0?1:0));

		if (iStatus==FRACO && iPisca==1 && iFracoCont%32<16)
			graphics.drawImage(imageFantasma, iX-1, iY-1, applet);
		else if (iStatus==OUT || iStatus==IN)
			graphics.drawImage(imageFantasma, iX-1, iY-1, applet);
		else if (iStatus==FRACO)
			graphics.drawImage(imageFraco, iX-1, iY-1, applet);
		else 
			graphics.drawImage(imageEye, iX-1, iY-1, applet);
	}  

	public void move(int iPacX, int iPacY, int iPacDir)
	{
		if (iStatus==FRACO)
		{
			iFracoCont--;
			if (iFracoCont<fracoCont/3)
				iPisca=1;
			if (iFracoCont==0)
				iStatus=OUT;
			if (iFracoCont%2==1)	// fraco anda só com 1/2 velocidade
			return;
		}

		if (velocidade.isMove()==0)
			return;

		if (iX%16==0 && iY%16==0)
		{
			switch (iStatus)
			{
			case IN:
				iDir=INSelect();
				break;
			case OUT:
				iDir=OUTSelect(iPacX, iPacY, iPacDir);
				break;
			case FRACO:
				iDir=FRACOSelect(iPacX, iPacY, iPacDir);
				break;
			case OLHOS:
				iDir=OLHOSSelect();
			}
		}

		if (iStatus!=OLHOS)
		{
			iX+= ctables.iXDirecao[iDir];
			iY+= ctables.iYDirecao[iDir];
		}
		else
		{	
			iX+=2* ctables.iXDirecao[iDir];
			iY+=2* ctables.iYDirecao[iDir];
		}

	}

	public int INSelect()
	throws Error
	{
		int iM,i,iRand;
		int iDirTotal=0;

		for (i=0; i<4; i++)
		{
			iM=lab.iLab[iY/16 + ctables.iYDirecao[i]]
			              [iX/16 + ctables.iXDirecao[i]];
			if (iM!=clab.PAREDE && i != ctables.iBack[iDir] )
			{
				iDirTotal++;
			}
		}
		if (iDirTotal!=0)
		{
			iRand=cuty.RandSelect(iDirTotal);
			if (iRand>=iDirTotal)
				throw new Error("iRand out of range");
			for (i=0; i<4; i++)
			{
				iM=lab.iLab[iY/16+ ctables.iYDirecao[i]]
				              [iX/16+ ctables.iXDirecao[i]];
				if (iM!=clab.PAREDE && i != ctables.iBack[iDir] )
				{
					iRand--;
					if (iRand<0)
					{
						if (iM== clab.PORTA)
							iStatus=OUT;
						iDir=i;	break;
					}
				}
			}
		}	
		return(iDir);	
	}

	public int OUTSelect(int iPacX, int iPacY, int iPacDir)
	throws Error
	{
		int iM,i,iRand;
		int iDirTotal=0;
		int[] iDirCont=new int [4];

		for (i=0; i<4; i++)
		{
			iDirCont[i]=0;
			iM=lab.iLab[iY/16 + ctables.iYDirecao[i]]
			              [iX/16+ ctables.iXDirecao[i]];
			if (iM!=clab.PAREDE && i!= ctables.iBack[iDir] && iM!= clab.PORTA )
			{
				iDirCont[i]++;
				iDirCont[i]+=iDir==iPacDir?
						DIR_FACTOR:0;
				switch (i)
				{
				case 0:	// DIREITA
					iDirCont[i] += iPacX > iX ? POS_FACTOR:0;
					break;
				case 1:	// CIMA
					iDirCont[i]+=iPacY<iY?
							POS_FACTOR:0;
					break;
				case 2:	// ESQUE
					iDirCont[i]+=iPacX<iX?
							POS_FACTOR:0;
					break;
				case 3:	// BAIXO
					iDirCont[i]+=iPacY>iY?
							POS_FACTOR:0;
					break;
				}
				iDirTotal+=iDirCont[i];
			}
		}	
		if (iDirTotal!=0)
		{	
			iRand=cuty.RandSelect(iDirTotal);
			if (iRand>=iDirTotal)
				throw new Error("iRand out of range");
			for (i=0; i<4; i++)
			{
				iM=lab.iLab[iY/16+ ctables.iYDirecao[i]]
				              [iX/16+ ctables.iXDirecao[i]];
				if (iM!=clab.PAREDE && i!= ctables.iBack[iDir] && iM!= clab.PORTA )
				{	
					iRand-=iDirCont[i];
					if (iRand<0)
					{
						iDir=i;	break;
					}
				}
			}	
		}
		else	
			throw new Error("iDirTotal out of range");
		return(iDir);
	}

	public void fraco()
	{
		if (iStatus==FRACO || iStatus==OUT)
		{
			iStatus=FRACO;
			iFracoCont=fracoCont;
			iPisca=0;
			if (iX%16!=0 || iY%16!=0)
			{
				iDir= ctables.iBack[iDir];
				int iM;
				iM=lab.iLab[iY/16+ ctables.iYDirecao[iDir]]
				              [iX/16+ ctables.iXDirecao[iDir]];
				if (iM == clab.PORTA)
					iDir=ctables.iBack[iDir];
			}
		}
	}

	public int OLHOSSelect()
	throws Error
	{
		int iM,i,iRand;
		int iDirTotal=0;
		int [] iDirCont= new int [4];

		for (i=0; i<4; i++)
		{
			iDirCont[i]=0;
			iM=lab.iLab[iY/16 + ctables.iYDirecao[i]]
			              [iX/16+ctables.iXDirecao[i]];
			if (iM!= clab.PAREDE && i!= ctables.iBack[iDir])
			{
				iDirCont[i]++;
				switch (i)
				{
				case 0:	// DIREITA
					iDirCont[i]+=160>iX?
							POS_FACTOR:0;
					break;
				case 1:	// CIMA
					iDirCont[i]+=96<iY?
							POS_FACTOR:0;
					break;
				case 2:	// ESQUERDA
					iDirCont[i]+=160<iX?
							POS_FACTOR:0;
					break;
				case 3:	// BAIXO
					iDirCont[i]+=96>iY?
							POS_FACTOR:0;
					break;
				}
				iDirTotal+=iDirCont[i];
			}	
		}
		if (iDirTotal!=0)
		{
			iRand= cuty.RandSelect(iDirTotal);
			if (iRand>=iDirTotal)
				throw new Error("RandSelect out of range");
			for (i=0; i<4; i++)
			{
				iM=lab.iLab[iY/16+ ctables.iYDirecao[i]]
				              [iX/16+ ctables.iXDirecao[i]];
				if (iM!= clab.PAREDE && i!= ctables.iBack[iDir])
				{
					iRand-=iDirCont[i];
					if (iRand<0)
					{
						if (iM== clab.PORTA)
							iStatus=IN;
						iDir=i;	break;
					}
				}
			}
		}
		else
			throw new Error("iDirTotal out of range");
		return(iDir);	
	}	

	public int FRACOSelect(int iPacX, int iPacY, int iPacDir)
	throws Error
	{
		int iM,i,iRand;
		int iDirTotal=0;
		int [] iDirCont = new int [4];

		for (i=0; i<4; i++)
		{
			iDirCont[i]=0;
			iM=lab.iLab[iY/16+ ctables.iYDirecao[i]][iX/16+ ctables.iXDirecao[i]];
			if (iM != clab.PAREDE && i != ctables.iBack[iDir] && iM != clab.PORTA)
			{
				iDirCont[i]++;
				iDirCont[i]+=iDir==iPacDir?
						DIR_FACTOR:0;
				switch (i)
				{
				case 0:	
					iDirCont[i]+=iPacX<iX?
							POS_FACTOR:0;
					break;
				case 1:	
					iDirCont[i]+=iPacY>iY?
							POS_FACTOR:0;
					break;
				case 2:	
					iDirCont[i]+=iPacX>iX?
							POS_FACTOR:0;
					break;
				case 3:	
					iDirCont[i]+=iPacY<iY?
							POS_FACTOR:0;
					break;
				}
				iDirTotal+=iDirCont[i];
			}	
		}
		if (iDirTotal!=0)
		{
			iRand=cuty.RandSelect(iDirTotal);
			if (iRand>=iDirTotal)
				throw new Error("RandSelect out of range");
			for (i=0; i<4; i++)
			{	
				iM=lab.iLab[iY/16+ ctables.iYDirecao[i]]
				              [iX/16+ ctables.iXDirecao[i]];
				if (iM!= clab.PAREDE && i!= ctables.iBack[iDir])
				{	
					iRand-=iDirCont[i];
					if (iRand<0)
					{
						iDir=i;	break;
					}
				}
			}
		}
		else
			throw new Error("iDirTotal out of range");
		return(iDir);
	}
	int testCollision(int iPacX, int iPacY)
	{
		if (iX<=iPacX+2 && iX>=iPacX-2
				&& iY<=iPacY+2 && iY>=iPacY-2)
		{
			switch (iStatus)
			{
			case OUT:
				return(1);
			case FRACO:
				iStatus=OLHOS;
				iX=iX/4*4;
				iY=iY/4*4;
				return(2);
			}	
		}
		return(0);
	}
}


