package pacvitor;

class cpacmove
{
	//movimento dos fantasmas
	//não esta implementado conforme o exigido
	int iDirScore[];

	int iValid[]; 

	cpac cPac;
	cfantasma [] cFantasma;
	clab cLab;

	cpacmove(cpac pac, cfantasma fantasma[], clab lab)
	{
		iDirScore=new int[4];

		iValid=new int [4];
		cPac=pac;

		cFantasma=new cfantasma[4];
		for (int i=0; i<4; i++)
			cFantasma[i]=fantasma[i];

		cLab=lab;
	}

	public int GetNextDir()
	throws Error
	{
		int i;
		for (i=0; i<4; i++)
			iDirScore[i]=0;
		AddDotScore();
		AddFantasmaScore();
		AddPowerDotScore();

		for (i=0; i<4; i++)
			iValid[i]=1;

		int iHigh, iHDir;

		while (true) 
		{
			iHigh=-1000000;
			iHDir=-1;
			for (i=0; i<4; i++)
			{
				if (iValid[i] == 1 && iDirScore[i]>iHigh)
				{
					iHDir=i;
					iHigh=iDirScore[i];
				}
			}

			if (iHDir == -1)
			{
				throw new Error("cpacmove: Não achou caminho?");
			}

			if ( cPac.iX%16 == 0 && cPac.iY%16==0)
			{
				if ( cPac.labOK(cPac.iX/16 + ctables.iXDirecao[iHDir],
						cPac.iY/16 + ctables.iYDirecao[iHDir]) )
					return(iHDir);
			}
			else
				return(iHDir);

			iValid[iHDir]=0;

		}}

	void AddFantasmaScore()
	{
		int iXDis, iYDis;	// distancia
		double iDis;		

		int iXFact, iYFact;

		
		for (int i=0; i<4; i++)
		{
			iXDis=cFantasma[i].iX - cPac.iX;
			iYDis=cFantasma[i].iY - cPac.iY;

			iDis=Math.sqrt(iXDis*iXDis+iYDis*iYDis);

			if (cFantasma[i].iStatus == cFantasma[i].FRACO)
			{


			}
			else
			{
				iDis=18*13/iDis/iDis;
				iXFact=(int)(iDis*iXDis);
				iYFact=(int)(iDis*iYDis);

				if (iXDis >= 0)
				{
					iDirScore[ctables.LEFT] += iXFact;
				}
				else
				{
					iDirScore[ctables.RIGHT] += -iXFact;
				}

				if (iYDis >= 0)
				{
					iDirScore[ctables.UP] += iYFact;
				}
				else
				{
					iDirScore[ctables.DOWN] += -iYFact;
				}
			}
		}
	}

	void AddDotScore()
	{
		int i, j;

		int dDis, dShortest;
		int iXDis, iYDis;
		int iX=0, iY=0;

		dShortest=1000;

		for (i=0; i < clab.HEIGHT; i++)
			for (j=0; j < clab.WIDTH; j++)
			{
				if (cLab.iLab[i][j]==clab.DOT)
				{
					iXDis=j*16-8-cPac.iX;
					iYDis=i*16-8-cPac.iY;
					dDis=iXDis*iXDis+iYDis*iYDis;

					if (dDis<dShortest)
					{
						dShortest=dDis;

						iX=iXDis; iY=iYDis;
					}
				}	
			}

		// iX e iY vira o destino

		int iFact=100000;

		if (iX > 0)
		{
			iDirScore[ctables.RIGHT] += iFact;
		}
		else if (iX < 0)
		{
			iDirScore[ctables.LEFT] += iFact;
		}

		if (iY > 0)
		{
			iDirScore[ctables.DOWN] += iFact;
		}
		else if (iY<0)
		{
			iDirScore[ctables.UP] += iFact;
		}
	}

	void AddPowerDotScore()
	{

	}
}
