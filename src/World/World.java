package World;

import java.util.ArrayList;
import java.util.List;

import Shapes.Rectangle;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.androidproject.Screen;
import com.example.androidproject.Vector;

public abstract class World {
	public List<Rectangle> tiles = new ArrayList<Rectangle>();
	public Vector size = new Vector(100, 20);
	public World()
	{
		for(int x = 0; x < Screen.size.x; x+=Screen.size.x/10)
		{
			tiles.add(new Rectangle(new Vector(x, Screen.size.y - 30),new Vector(Screen.size.x/10-10,20)));
		}
	}
	
	public void Collision(Rectangle rect)//test collision against the map with the Vector
	{
		for(int x=0 ; x < tiles.size(); x++)
		{
			if(rect.Contains(tiles.get(x).Top(),tiles.get(x).Left()) || 
					rect.Contains(tiles.get(x).Top(),tiles.get(x).Right()) ||
					rect.Contains(tiles.get(x).Bot(),tiles.get(x).Left()) ||
					rect.Contains(tiles.get(x).Top(),tiles.get(x).Right()))
			{
				System.out.println("found tile");
				rect.paint.setColor(Color.GREEN);
			}
			else
				rect.paint.setColor(Color.RED);
		}
	}
	public void Draw(Canvas c)
	{
		for(int x=0 ; x < tiles.size(); x++)
		{
			tiles.get(x).Draw(c);
		}
	}
}
