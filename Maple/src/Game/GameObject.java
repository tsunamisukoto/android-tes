package Game;

import android.graphics.RectF;

import com.example.androidproject.Drawable;
import com.example.androidproject.Screen;
import com.example.androidproject.Vector;

public abstract class GameObject extends Drawable{
	public int id = 0;
	public String type = "default";
	public RectF rect,feet;
	public boolean AI = true;
	public Vector 
		position,
		size,
		velocity,
		maxVelocity,
		acceleration;
	public boolean jumping = false, grounded = false;
	
	public float health = 100, armour = 0, resist = 0;
	
	public GameObject()
	{
		super();
		position = new Vector(0,0);
		size = new Vector(50,50);
		velocity = new Vector(0,0);
		acceleration = new Vector(1,1);
		maxVelocity = new Vector(5,5);
		rect = new RectF(position.x, position.y, size.x, size.y);
		feet = new RectF(position.x,position.y,size.x,5);
	}
	public void Draw(Object obj)
	{
		//CollideScreen();
		feet = new RectF(Screen.size.x/2,position.y, Screen.size.x/ 2 + size.x,Screen.size.y/2+1);
		position = position.add(velocity);
		Physics();
		
		super.Draw(obj,rect);
	}
	public void Update()
	{
		
	}

	public void Physics()
	{
		if(!grounded)
			velocity.y += 0.5;
		if(grounded && !AI && !Screen.buttonDown)
		{
			velocity.x *= 0.95;
			if(velocity.x < 0.0001 || velocity.x > -0.0001)
				velocity.x = 0;
		}
		if("arrow".equals(type) && grounded)
			velocity.x *= 0.95;
	}
	public void jump()
	{
		if(jumping == false)
		{
			grounded = false;
			velocity.y = -10;
			jumping = true;
		}
	}
	
	public void CollideScreen()
	{
		if(rect.right > Screen.size.x || rect.left < 0 )
			velocity.x = -velocity.x;
	}
}
