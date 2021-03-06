package Tools;

import java.io.Serializable;

public class iVector implements Serializable {

	public int x, y;
	//public float w, h;

	public iVector() {
		this(0, 0);
	}

	public iVector(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public iVector add(iVector v) {
		return new iVector(this.x + v.x, this.y + v.y);
		// return new Vector(x+v.x, x+v.y);
	}
    public iVector multiply(iVector v, int x)
    {
        return new iVector(v.x*x, v.y*y);
    }


	public iVector get() {
		return new iVector(this.x, this.y);
	}

	public iVector subtract(iVector v) {
		return new iVector(this.x - v.x, this.x - v.y);
	}

	public void addX(float val) {
		this.x += val;
	}

	public float getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
