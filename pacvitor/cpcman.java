package pacvitor;

import java.awt.*;
import java.awt.event.*;

// A classe principal do jogo
 
public class cpcman extends Frame
implements Runnable, KeyListener, ActionListener, WindowListener
{
	
	Thread timer;
	int timerPeriod=12;  // in miliseconds
	int signalMove=0;
	final int canvasWidth=368;
	final int canvasHeight=288+1;
	int topOffset;
	int leftOffset;
	final int iLabX=16;
	final int iLabY=16;
	Image offScreen;
	Graphics offScreenG;

	// Obejtos
	clab lab;
	cpac pac;
	cpowerpellet powerPellet;
	cfantasma [] fantasmas;
	ccereja cereja;
	cmorango morango;

	// Contadores
	final int VIDAS=3;
	int Restantes;
	int mudarRestantes; 
	

	// pontuacao
	int pontuacao;
	int MaiorPontuacao;
	int scoreFantasma;	//Dobra valor a cada fantasma (se no mesmo evento da bolona)
	int mudarPontuacao;	
	int mudarMaiorPontuacao;  

	
	Image imgScore;
	Graphics imgScoreG;
	Image imgHiScore;
	Graphics imgHiScoreG;

	
	final int INITIMAGE=100; 
	final int STARTESPERAR=0; 
	final int RUNNING=1;
	final int MORTOESPERAR=2;  
	final int SUSPENDED=3;  
	int gameState;
	int fase;

	final int ESPERARCOUNT=100;	
	int esperar;	

	// rounds
	int round;  // round atual

	
	boolean newLab;

	
	MenuBar menuBar;
	Menu ajuda;
	MenuItem sobre;

	// Comandos
	int pacKeyDir;
	int key=0;
	final int NONE=0;
	final int SUSPEND=1;  // pausar/voltar
	
	
	public cpcman()
	{
		super("PAC MAN");

		MaiorPontuacao=0;
		gameState=INITIMAGE;
		initGUI();
		addWindowListener(this);
		addKeyListener(this);
		sobre.addActionListener(this);
		setSize(canvasWidth, canvasHeight);
		show();
		

	}

	void initGUI()
	{
		menuBar=new MenuBar();
		ajuda=new Menu("Ajuda");
		sobre=new MenuItem("Sobre");

		ajuda.add(sobre);
		menuBar.add(ajuda);

		setMenuBar(menuBar);

		addNotify();  
	}

	public void initImages()
	{
		
		offScreen=createImage(clab.iWidth, clab.iHeight); 
		if (offScreen==null)
			System.out.println("Deu ruim");
		offScreenG=offScreen.getGraphics();

		lab = new clab(this, offScreenG);

		// 4 fantasmas
		fantasmas = new cfantasma[4];
		for (int i=0; i<4; i++)
		{
			Color color;
			if (i==0)
				color=Color.red;
			else if (i==1)
				color=Color.cyan;
			else if (i==2)
				color=Color.pink;
			else 
				color=Color.orange;
			fantasmas[i]=new cfantasma(this, offScreenG, lab, color);
		}

		powerPellet = new cpowerpellet(this, offScreenG, fantasmas);
		//inicia cereja
		cereja = new ccereja(this, offScreenG);
		
		//inicia morango
		morango = new cmorango(this, offScreenG);
		

		pac = new cpac(this, offScreenG, lab, powerPellet);

		// initialize the pontuacao images
		imgScore=createImage(150,16);
		imgScoreG=imgScore.getGraphics();
		imgHiScore=createImage(150,16);
		imgHiScoreG=imgHiScore.getGraphics();

		imgHiScoreG.setColor(Color.black);
		imgHiScoreG.fillRect(0,0,150,16);
		imgHiScoreG.setColor(Color.red);
		imgHiScoreG.setFont(new Font("Helvetica", Font.BOLD, 12));
		imgHiScoreG.drawString("RECORDE", 0, 14);

		imgScoreG.setColor(Color.black);
		imgScoreG.fillRect(0,0,150,16);
		imgScoreG.setColor(Color.green);
		imgScoreG.setFont(new Font("Helvetica", Font.BOLD, 12));
		imgScoreG.drawString("PONTOS", 0, 14);
	}

	void startTimer()
	{   
		timer = new Thread(this);
		timer.start();
	}

	void startGame()
	{
		Restantes=VIDAS;
		mudarRestantes=1;

		pontuacao=0;
		mudarPontuacao=1;

		newLab=true;

		round=1;

		startRound();
	}

	void startRound()
	{
	
		if (newLab==true)
		{
			lab.start();
			powerPellet.start();
			newLab=false;
		}

		lab.draw();	

		pac.start();
		pacKeyDir=ctables.DOWN;
		for (int i=0; i<4; i++)
			fantasmas[i].start(i,round);

		gameState=STARTESPERAR;
		esperar=ESPERARCOUNT;
	}

	
	public void paint(Graphics g)
	{
		if (gameState == INITIMAGE)
		{	initImages();
			Insets insets=getInsets();

			topOffset=insets.top;
			leftOffset=insets.left;

			setSize(canvasWidth+insets.left+insets.right,
					canvasHeight+insets.top+insets.bottom);

			setResizable(false);
			startGame();	  

			startTimer();

		}

		g.setColor(Color.black);
		g.fillRect(leftOffset,topOffset,canvasWidth, canvasHeight);

		mudarPontuacao=1;
		mudarMaiorPontuacao=1;
		mudarRestantes=1;

		paintUpdate(g);
	}

	void paintUpdate(Graphics g)
	{

		powerPellet.draw();
//
		cereja.draw();
		morango.draw();
		
		for (int i=0; i<4; i++)
			fantasmas[i].draw();

		pac.draw();
		g.drawImage(offScreen, 
				iLabX+ leftOffset, iLabY+ topOffset, this); 
		if (mudarMaiorPontuacao==1)
		{
			imgHiScoreG.setColor(Color.black);
			imgHiScoreG.fillRect(70,0,80,16);
			imgHiScoreG.setColor(Color.red);
			imgHiScoreG.drawString(Integer.toString(MaiorPontuacao), 70,14);
			g.drawImage(imgHiScore, 
					8+ leftOffset, 0+ topOffset, this);

			mudarMaiorPontuacao=0;
		}

		if (mudarPontuacao==1)
		{
			imgScoreG.setColor(Color.black);
			imgScoreG.fillRect(70,0,80,16);
			imgScoreG.setColor(Color.green);
			imgScoreG.drawString(Integer.toString(pontuacao), 70,14);
			g.drawImage(imgScore, 
					158+ leftOffset, 0+ topOffset, this);

			mudarPontuacao=0;
		}

		// update pac life info
		if (mudarRestantes==1)
		{
			int i;
			for (i=1; i<Restantes; i++)
			{
				g.drawImage(pac.imagePac[0][0], 
						16*i+ leftOffset, 
						canvasHeight-18+ topOffset, this);
			}
			g.drawImage(powerPellet.imageBlank, 
					16*i+ leftOffset, 
					canvasHeight-17+ topOffset, this); 

			mudarRestantes=0;
		}
	}

	void move()
	{
		int k;
		int c;
		c=0;
		int oldScore=pontuacao;
		
		
		for (int i=0; i<4; i++)
			fantasmas[i].move(pac.iX, pac.iY, pac.iDir);

		k=pac.move(pacKeyDir);

		if (k==1)	//COMER BOLINJA
		{
			mudarPontuacao=1;
			pontuacao+= 10 ;
		}
		else if (k==2)	// COMER BOLONA
		{
			scoreFantasma=200;
			mudarPontuacao=1;
			pontuacao+= 50;
		}
		
		if (lab.iTotalDotcont==0)
		{
			gameState=MORTOESPERAR;
			esperar=ESPERARCOUNT;
			newLab=true;
			round++;
			return;
		}

		for (int i=0; i<4; i++)
		{
			k=fantasmas[i].testCollision(pac.iX, pac.iY);
			if (k==1)	// MORRER
			{
				Restantes--;
				mudarRestantes=1;
				gameState=MORTOESPERAR;	// PAUSAR
				esperar=ESPERARCOUNT;
				return;	
			}
			else if (k==2)	// PEGAR FANTASMA
			{
				pontuacao+= scoreFantasma;
				mudarPontuacao=1;
				scoreFantasma*=2;
			}		
		}
//*		
		k=cereja.testeCereja(pac.iX, pac.iY);
		if (k==1) //comeu cereja
		{
			mudarPontuacao=1;
			pontuacao+=100;

			cereja.cerejaRessValid=1;
		}	
		if (cereja.cerejaRessValid==1){
				cereja.TempoCerejaRessucitar--;
				if (cereja.TempoCerejaRessucitar<=0) {
					cereja = new ccereja(this, offScreenG);
				}
		}
		if (cereja.cerejaVivaValid==1)
		{
			cereja.TempoCerejaViva--;
			if (cereja.TempoCerejaViva<=0) {
				cereja.clear();
				cereja.cerejaVivaValid=0;
				cereja.cerejaRessValid=1;
				
			}
		}
		
		k=morango.testeMorango(pac.iX, pac.iY);
		if (k==1) //comeu morango
		{
			mudarPontuacao=1;
			pontuacao+=300;
			//morango = new cmorango(this, offScreenG);
			morango.morangoRessValid=1;
		}	
		if (morango.morangoRessValid==1){
				morango.TempoMorangoRessucitar--;
				if (morango.TempoMorangoRessucitar<=0) {
					morango = new cmorango(this, offScreenG);
				}
		}
		if (morango.morangoVivaValid==1)
		{
			morango.TempoMorangoViva--;
			if (morango.TempoMorangoViva<=0) {
				morango.clear();
				morango.morangoVivaValid=0;
				morango.morangoRessValid=1;
				
			}
		}
		
	
		if (pontuacao>MaiorPontuacao)
		{
			MaiorPontuacao=pontuacao;
			mudarMaiorPontuacao=1;
		}

		if ( mudarPontuacao==1 )
		{
			if ( pontuacao/10000 - oldScore/10000 > 0 )
			{
				Restantes++;			// Vida bonus por conseguir dez mil pontos
				mudarRestantes=1;
			}
		}
	
	
				
	}	

	public void update(Graphics g)
	{
		if (gameState == INITIMAGE)
			return;
		if (signalMove!=0)
		{
			signalMove=0;

			if (esperar!=0)
			{
				esperar--;
				return;
			}

			switch (gameState)
			{
			case STARTESPERAR: 
				if (pacKeyDir==ctables.UP)
					gameState=RUNNING;
				else
					return;
				break;
			case RUNNING:
				if (key==SUSPEND)
					gameState=SUSPENDED;
				else
					move();
				break;
			case MORTOESPERAR:
				if (Restantes>0)
					startRound();
				else
					startGame();
				gameState=STARTESPERAR;
				esperar=ESPERARCOUNT;
				pacKeyDir=ctables.DOWN;
				break;
			case SUSPENDED:
				if (key==SUSPEND)
					gameState=RUNNING;
				break;
			}
			key=NONE;
		}

		paintUpdate(g);	
	}

	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			pacKeyDir=ctables.RIGHT;
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			pacKeyDir=ctables.UP;
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			pacKeyDir=ctables.LEFT;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:	
			pacKeyDir=ctables.DOWN;
			break;
		case KeyEvent.VK_SPACE:
			key=SUSPEND;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	public void actionPerformed(ActionEvent e)
	{
		if (gameState==RUNNING)
			key=SUSPEND;
		new cabout(this);
	}
public void windowOpened(WindowEvent e)
	{}

	public void windowClosing(WindowEvent e)
	{
		dispose();
	}

	public void windowClosed(WindowEvent e)
	{}

	public void windowIconified(WindowEvent e)
	{}

	public void windowDeiconified(WindowEvent e)
	{}

	public void windowActivated(WindowEvent e)
	{}

	public void windowDeactivated(WindowEvent e)
	{}

	public void run()
	{
		while (true)
		{	
			try { Thread.sleep(timerPeriod); } 
			catch (InterruptedException e)
			{
				return;
			}

			signalMove++;
			repaint();
		}
	}
	boolean finalized=false;

	public void dispose()
	{
		timer.interrupt();
		offScreenG.dispose();
		offScreenG=null;

		lab=null;
		pac=null;
		powerPellet=null;
		for (int i=0; i<4; i++)
			fantasmas[i]=null;
		fantasmas=null;

		imgScore=null;
		imgHiScore=null;
		imgScoreG.dispose();
		imgScoreG=null;
		imgHiScoreG.dispose();
		imgHiScoreG=null;

		menuBar=null;
		ajuda=null;
		sobre=null;

		super.dispose();

		finalized=true;
	}

	public boolean isFinalized() {
		return finalized;
	}

	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}
}












