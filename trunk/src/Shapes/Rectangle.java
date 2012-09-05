package Shapes;
import com.example.androidproject.Vector;

import android.graphics.Canvas;

public class Rectangle extends Shape {

	public Rectangle(Vector v, Vector v2) {
		super();
		this.type = "rectangle";
		this.position = v;
		this.size = v2;

	}
	public void Draw(Canvas c)
	{
		super.Draw(c);
		c.drawRect(Left(), Top(), Right(), Bottom(), paint);
	}

	public void Update()
	{
		
	}
}
