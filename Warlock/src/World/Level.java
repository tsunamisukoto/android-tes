package World;

import com.example.warlockgame.R;

import Tools.SpriteSheet;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Level {
	int[][] map;
	SpriteSheet sprites;
	 public Level(SpriteSheet sprites)
	 {
		 this.sprites= sprites;
		map = new int[][] {
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
	try
	{
		canvas.drawColor(Color.BLACK);
		canvas.drawRect(new Rect(0,0,100,100), paint);
		canvas.drawColor(Color.BLACK);
		int tmpx=0;
		int tileWidth = 32;
		int tileHeight = 32;
		int startX = 0;
		int startY = 0;
		for (int i = 0; i < map.length; i++)
		{
			int[] row = map[i];
			
			for (int j = 0; j < row.length; j++)
			{

				startX +=  tileWidth;
				canvas.drawBitmap(sprites.tiles.get(map[i][j]), null, new Rect(j * sprites.size , i * sprites.size, (j * sprites.size) + sprites.size, (i * sprites.size) + sprites.size), new Paint());
				//Log.d("IPT"," " + map.length  +"" +  j);
				
				/*if (map[i][j] ==25)
				{
				var wav = new BLCorner(stageRef, j, i+1);
				addChild(wav);
		
				}*/
			}
		
			startX = 0;
		
			startY +=  tileHeight;
		}
	
	}
	catch(Exception ex)
	{
		//System.out.println("asd");
	}
}
	 
}
