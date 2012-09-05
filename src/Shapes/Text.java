package Shapes;

import com.example.androidproject.Vector;

import android.graphics.Canvas;

public class Text extends Shape {
	
	public Text()
	{
		super();
		this.type = "text";
	}
	public void Draw(Canvas c)
	{
		this.Draw(c, position.get());
	}
	public void Draw(Canvas c, Vector v)
	{
		this.Draw(c, text, v);
	}
	public void Draw(Canvas c, String text, Vector v)
	{
		super.Draw(c);
		c.drawText(text, v.x, v.y, paint);
	}
}
