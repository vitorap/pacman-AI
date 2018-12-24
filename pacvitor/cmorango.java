/**
 * Essa parte do codigo, é responsavel por gerar as morangos e apaga-las quando o pacman come
 * 
 */

package pacvitor;

import java.awt.*;



public class cmorango 
{		
	int x=10;
	int y=10;
	int aux=0;

	int TempoMorangoViva=1250; // 1250 frames = 15 segundos
	int TempoMorangoRessucitar=6250; // 6250 frames = 75 segundos
	int morangoVivaValid=1; 
	int morangoRessValid;
		
	Window applet;
	Graphics graphics;

	// imagem da morango
	Image imageMorango;

	//imagem vazia
	Image imageBlank;
	
	cmorango(Window a, Graphics g)
	{
		applet=a;
		graphics=g;
		imageMorango=applet.createImage(16,16);
		cimage.drawMorango(imageMorango);
		imageBlank=applet.createImage(16,16);
		Graphics imageG=imageBlank.getGraphics();
		imageG.setColor(Color.black);
		imageG.fillRect(0,0,16,16);	
	}
	
	public void gera()
	{
		// gera uma fruta no lugar certo
			while (aux==0) {
				y=(int)(Math.random() * (21));
				x=(int)(Math.random() * (16));
				if (ctables.LabDefine[y].charAt(x)== ('.')||ctables.LabDefine[y].charAt(x)== ('O')||ctables.LabDefine[y].charAt(x)== (' ')) {
					aux=1;
				}
			}			
	}			

	void clear()
	{
		graphics.drawImage(imageBlank, x*16, y*16, applet);
	}

	void eat(int iCol, int iLinha)
	{
		if (x==iCol && y==iLinha)
			{
				morangoVivaValid=0;
				clear();
			}
		
		
	}

	public void draw()
	{
		if (morangoVivaValid!=0) {
			gera();
			graphics.drawImage(imageMorango,x*16, y*16, applet);
		}
	} 
//**	
	int testeMorango(int iPacX, int iPacY)
	{
		if (x*16==iPacX && y*16==iPacY)
			{
			if (morangoVivaValid!=0) {
				clear();	
				morangoVivaValid=0;
				draw();
				return(1);
			}
		}		
		return(0);
	}

//	**/
	
	
}
