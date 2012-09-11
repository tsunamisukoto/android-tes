package World;

import Tools.SpriteSheet;
import Tools.Vector;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Level {
	int[][] map;
	SpriteSheet sprites;
	Vector size;
	public Level(SpriteSheet sprites)
	{
		size = new Vector(64,64);
		this.sprites= sprites;
		map = new int[][] 	
		{
			{3,4,3,4,3,4,65,66,66,66,66,66,66,66,66,66,66,66,66,67},
			{4,3,4,3,4,3,70,71,71,71,71,71,71,71,71,71,71,71,71,72},
			{0,0,0,0,0,0,70,71,71,71,71,71,71,71,71,71,71,71,71,72},
			{0,0,0,0,1,1,70,71,71,71,71,71,71,71,71,71,71,71,71,72},
			{0,0,0,0,1,1,70,71,71,71,71,71,71,71,71,71,71,71,71,72},
			{0,0,0,0,1,1,70,71,71,71,71,71,71,71,71,71,71,71,71,72},
			{0,0,0,0,1,1,70,71,71,71,71,71,71,71,71,71,71,71,71,72},
			{0,0,0,0,1,1,70,71,71,71,71,71,71,71,71,71,71,71,71,72},
			{0,0,0,0,1,1,70,71,71,71,71,71,71,71,71,71,71,71,71,72},
			{4,3,4,3,4,3,70,71,71,71,71,71,71,71,71,71,71,71,71,72},
			{4,4,3,4,3,4,70,71,71,71,71,71,71,71,71,71,71,71,71,72},
			{4,3,4,3,4,3,75,76,76,76,76,76,76,76,76,76,76,76,76,77}
		};
	}


	public void Draw(Canvas canvas,Paint paint)
	{

		for (int i = 0; i < map.length; i++)
		{
			int[] row = map[i];
			
			for (int j = 0; j < row.length; j++)
			{
				canvas.drawBitmap(sprites.tiles.get(map[i][j]), null, 
						new RectF(j * size.x , 
								i * size.y, 
								(j * size.x) + size.x, 
								(i * size.y) + size.y), 
						new Paint());
			}
		
		}
	}
	
	public boolean onTile()
	{
		return false;
	}
}