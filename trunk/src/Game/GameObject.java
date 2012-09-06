package Game;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.androidproject.Vector;

public abstract class GameObject {
	public Vector position,
		size,
		velocity;
	
	public Paint paint;
	public GameObject()
	{
		position = new Vector(0,0);
		size = new Vector(50,50);
	}
	public void Draw(Canvas c)
	{
		
	}
	public void Update()
	{
		
	}
}
