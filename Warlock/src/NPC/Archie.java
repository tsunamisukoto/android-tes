package NPC;


import Game.GameObject;
import Tools.Vector;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Archie extends GameObject{


	Bitmap bitmap1,bitmap2,curr;
	int timer = 0,timer2 =0 ;
	public Archie(Bitmap bmp, Bitmap bmp2)
	{
		bitmap1 = bmp;
		bitmap2 = bmp2;
		curr = bitmap1;
		rect = new RectF(0,0,100,100);
		position = new Vector(0,0);
		size = new Vector(100, 100);
	}
	public void Draw(Canvas canvas)
	{
		canvas.drawBitmap(curr, null, rect, paint);
	}
	public void Update()
	{
		rect = new RectF(position.x,position.y,position.x+size.x, position.y+size.y);
		if(timer < 20)
		{
			timer++;
			curr = bitmap1;
		}
		else if(timer2 < 20)
		{
			timer2++;
			curr = bitmap2;
		}
		else 
		{
			timer = 0;
			timer2 = 0;
		}
	}
}
