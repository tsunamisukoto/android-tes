package Particles;

import java.util.ArrayList;

import Tools.Vector;
import developmental.warlocks.GL.Grid;

/**
 * Created by Scott on 29/04/2015.
 */
public class LightningParticle extends glParticle {
    public LightningParticle(Vector position, Vector velocity, int lifeSpan, int _mResourceID, ArrayList<Grid> _g, float Rotation, float _height) {
        super(position, Vector.Zero(), lifeSpan, _mResourceID, _g);
        this.rotation = Rotation;
        this.height = _height;
    }

    @Override
    protected void Rotate() {

    }
}
