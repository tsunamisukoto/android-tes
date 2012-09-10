package Tools;

import java.util.ArrayList;
import java.util.List;

import com.example.warlockgame.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SpriteSheet {
	Bitmap bmp;
	public List<Bitmap> tiles = new ArrayList<Bitmap>();
	public SpriteSheet(Bitmap bmp)
	{
		this.bmp = bmp;
		Load(32);
	}
	public void Load(int size)
	{
		for(int y=0;y<bmp.getHeight();y+=size)
		{
			for(int x=0;x<bmp.getWidth();x+=size)
			{
				tiles.add(Bitmap.createBitmap(bmp,x,y,size ,size));
			}
		}
		//tiles.add(BitmapFactory.decodeResource(res, R.drawable.tilesheet));
	}
	
}
