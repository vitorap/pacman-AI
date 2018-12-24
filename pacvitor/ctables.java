package pacvitor;

public class ctables 
{
	public static final int[] iXDirecao={1,0,-1,0};
	public static final int[] iYDirecao={0,-1,0,1};
	public static final int[] iDirecao=
	{
		-1,	// 0:
		1,	// 1: x=0, y=-1
		-1,	// 2:
		-1,	// 3:
		2,	// 4: x=-1, y=0
		-1,	// 5:
		0,	// 6: x=1, y=0
		-1,	// 7
		-1,     // 8
		3     	// 9: x=0, y=1
	};

	// IR PARA TRAS
	public static final int[] iBack={2,3,0,1};

	// DIREÇAO
	public static final int RIGHT=0;
	public static final int UP=1;
	public static final int LEFT=2;
	public static final int DOWN=3;

			
	// LABIRINTO
	public static final String[] LabDefine=
	{	
			"XXXXXXXXXXXXXXXXXXXXX",	
			"XO........X........OX",	
			"X.XXX.XXX.X.XXX.XXX.X",	
			"X.X....X..X.........X",	
			"X.X.X..X.XXX.XX.X.X.X",	
			"X...XX..........X.X.X",	
			"X.X..X.XXX-XXX.XX.X.X",	
			"X.XX.X.X     X....X.X",	
			"X......X     X.X.XX.X",	
			"X.XXXX.XXXXXXX.X.XX.X",	
			"X...XX.... .........X",	
			"X.X.XXX.X.X.XX.X.X..X",	
			"X.X..X..X.X....X.X..X",	
			"X.X..X..X.X.X.XX.XX.X",	
			"XO......X..........OX",	
			"XXXXXXXXXXXXXXXXXXXXX",	

	};	


	
}

