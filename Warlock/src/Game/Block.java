package Game;

import android.graphics.RectF;

import com.example.warlockgame.RenderThread;

import Tools.Vector;

public class Block extends GameObject {
    int i = 0;
	public Block() {
		super();
		this.acceleration = 0.3f;
	}
    @Override
    public void Update()
    {
        super.Update();
        i++;
        if(i%100 ==0)
        {
            this.Spells[6].Cast(this.position.add(new Vector(10,0)));
        }
    }
    public Block(int x, int y)
    {
        this();
        position = new Vector(x,y);
        this.rect = new RectF(this.position.x, this.position.y, this.position.x
                + this.size.x, this.position.y + this.size.y);

    }
}
