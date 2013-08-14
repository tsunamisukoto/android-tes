package HUD;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import Input.Pointer;
import Spells.Spell;

import com.developmental.myapplication.Global;
import com.developmental.myapplication.RenderThread;

public class Button  {
    Paint paint;
	public RectF rect;
	public boolean down = false;
	public int id = 0;
	public String name = "default";
	Paint paint2;
	Paint Cd;
	Spell s;
    Paint d = new Paint();

	public Button(RectF r, int i, Spell _s) {
	paint=new Paint();
		this.id = i;
		this.paint.setColor(Color.RED);
		this.paint2 = new Paint();
		this.paint2.setColor(Color.WHITE);
		this.rect = r;
		this.Cd = new Paint();
		this.Cd.setColor(Color.GREEN);
		this.s = _s;
        d.setStyle(Paint.Style.STROKE);
        d.setStrokeWidth(3);
	}

	public void Draw(Canvas canvas) {

		canvas.drawRect(this.rect, this.paint2);

		canvas.drawRect(new RectF(this.rect.left + 2, this.rect.top + 2,
				this.rect.right - 2, this.rect.bottom - 2), this.paint);
        s.DrawButton(canvas,(int)(this.rect.left),(int)(this.rect.top),rect.width(),rect.height());
		// Draw the cooldown

        canvas.drawRect(new RectF(this.rect.left + 20, this.rect.top+5 ,
                this.rect.right-20, this.rect.top+15), d);
		canvas.drawRect(new RectF(this.rect.left + 20, this.rect.top+5 ,
				this.rect.right
						- ((float) this.s.Current / (float) this.s.Cooldown)
						* (this.rect.width() - 40)-20, this.rect.top+15), this.Cd);

 	}

	public void Update() {
        if(!Global.LOCKSPELLMODE)
        {
            for (int x = 0; x < RenderThread.finger.pointers.size(); x++) {
                Pointer f = RenderThread.finger.pointers.get(x);

                if (!f.down)
                    continue;

                if (this.rect.contains(f.position.x, f.position.y)) {
                    this.paint.setColor(Color.RED);
                    this.down = true;
                    return;
                }
            }
            this.paint.setColor(Color.DKGRAY);
            this.down = false;

        }
        else {
        boolean b= down;
            for (int x = 0; x < RenderThread.finger.pointers.size(); x++) {
                Pointer f = RenderThread.finger.pointers.get(x);

                if (!f.down)
                    continue;

                if (this.rect.contains(f.position.x, f.position.y)) {
                    this.paint.setColor(Color.RED);
                    this.down = true;
                    return;
                }
            }


        }
	}
}
