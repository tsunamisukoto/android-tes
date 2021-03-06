package Scene;

import Tools.Vector;
import developmental.warlocks.GL.NewHeirarchy.GameObject;

public class Action {
    private Type ActionType;
    private Vector Destination;
    private GameObject Target;

    Action() {

    }

    public Action(Type _a, Vector _destination) {
        this.Destination = _destination;
        this.ActionType= _a;
    }

    public Action(Type _a, GameObject _Target) {
        this.Target = _Target;
        this.ActionType= _a;
    }

    public enum Type {
        Kill, GoTo
    }

    boolean Done() {
        switch (this.ActionType) {
            case Kill:
                if (this.Target.health > 0)
                    return false;
                else
                    return true;
            case GoTo:

                break;

        }
        return false;
    }

}
