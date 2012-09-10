package NPC;

import Game.GameObject;
import Tools.Vector;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Archie extends GameObject{

	Bitmap bitmap;
	public Archie(Bitmap bmp)
	{
		bitmap = bmp;
		rect = new RectF(0,0,100,100);
		position = new Vector(0,0);
		size = new Vector(100, 100);
	}
	public void Draw(Canvas canvas)
	{
		canvas.drawBitmap(bitmap, null, rect, paint);
	}
}
