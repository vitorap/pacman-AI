/**
 * Essa parte do codigo, é responsavel por gerar as cerejas e apaga-las quando o pacman come
 * 
 */

package pacvitor;

import java.awt.*;



public class ccereja //aqui  diz onde imprime as power dots. Está imprimindo só 4
{
		
	int x=10;
	int y=10;
	int aux=0;

	int TempoCerejaViva=1250; // 1250 frames = 15 segundos
	int TempoCerejaRessucitar=4167; // 4167 frames = 50 segundos
	int cerejaVivaValid=1; 
	int cerejaRessValid;
		
	//int cerejaVivaValid = 1;

	// the applet this object is associated to
	Window applet;
	Graphics graphics;

	// imagem da cereja
	Image imageCereja;

	//imagem vazia
	Image imageBlank;
	
	
	


	ccereja(Window a, Graphics g)
	{
		applet=a;
		graphics=g;
		
		imageCereja=applet.createImage(16,16);
		cimage.drawCereja(imageCereja);

		imageBlank=applet.createImage(16,16);
		Graphics imageG=imageBlank.getGraphics();
		imageG.setColor(Color.black);
		imageG.fillRect(0,0,16,16);

	}
	
	public void gera()
	{
		// gera uma fruta no lugar certo
			while (aux==0) {
				x=(int)(Math.random() * (21));
				y=(int)(Math.random() * (16));
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
				cerejaVivaValid=0;
				clear();
			}
		
		
	}

	public void draw()
	{
		if (cerejaVivaValid!=0) {
			gera();
			graphics.drawImage(imageCereja,x*16, y*16, applet);
		}
	} 
//**	
	int testeCereja(int iPacX, int iPacY)
	{
		if (x*16==iPacX && y*16==iPacY)
			{
			if (cerejaVivaValid!=0) {
				clear();	
				cerejaVivaValid=0;
				draw();
				return(1);
			}
		}		
		return(0);
	}

//	**/
	
	
}
