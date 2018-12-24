package pacvitor;


public class cvelocidade
{
	int passo;
	int frames;

	int frameCont;
	int cntdCont;

	float frameStepRatio;

	cvelocidade()
	{
		start(1,1);
	}

	public void start(int s, int f)
	throws Error
	{
		if (f<s)
			throw new Error("Cvelocidade.init(...): frame precisa ser>= cntd");

		passo=s;
		frames=f;
		frameStepRatio=(float)frames/(float)passo;

		cntdCont=passo;
		frameCont=frames;
	}

	public int isMove()	
	{
		frameCont--;

		float ratio=(float)frameCont/(float)cntdCont;

		if (frameCont==0)
			frameCont=frames;

		if (ratio < frameStepRatio)
		{
			cntdCont--;
			if (cntdCont==0)
				cntdCont=passo;
			return(1);		
		}
		return(0);
	}
}
