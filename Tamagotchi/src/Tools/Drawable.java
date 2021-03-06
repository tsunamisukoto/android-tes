package Tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Drawable {

	public Matrix matrix;
	public Paint paint = new Paint();
	public Bitmap bmp;
	public boolean debug = true;

	public Drawable() {
		this.paint.setColor(Color.BLUE);

	}

	public void Draw(Canvas obj, RectF r) {
		Canvas c = obj;
		if (this.debug)
			c.drawRect(r, this.paint);
	}

	public void Draw(Object obj) {
		Canvas c = (Canvas) obj;
		if (this.bmp != null)
			c.drawBitmap(this.bmp, 0, 0, this.paint);

		// imageView.setImageBitmap(bmp);
	}
}
