package NPC;

import com.example.warlockgame.R;

import Game.GameObject;
import Tools.SpriteSheet;
import Tools.Vector;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Archie extends GameObject{


	Bitmap bitmap1,bitmap2;
	int timer = 0,timer2 =0 ;
	public Archie(Bitmap bmp, Bitmap bmp2)
	{
		bitmap1 = bmp;
		bitmap2 = bmp2;
		rect = new RectF(0,0,100,100);
		position = new Vector(0,0);
		size = new Vector(100, 100);
	}
	public void Draw(Canvas canvas)
	{
		if(timer<20)
		{
			timer++;
			canvas.drawBitmap(bitmap1, null, rect, paint);
		}
		else if(timer2 < 20)
		{
			timer2++;
			canvas.drawBitmap(bitmap2, null, rect, paint);
		}
		else 
		{
			timer = 0;
			timer2 = 0;
			canvas.drawBitmap(bitmap2, null, rect, paint);
		}
		
	}
}
