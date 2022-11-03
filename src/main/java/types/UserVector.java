package types;
import javafx.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class UserVector implements UserType {

    final static String className = "UserVector";

    private Float x;
    private Float y;

    public UserVector() { this.x = null; this.y = null; }

    public UserVector(Float _x, Float _y) { this.x = _x; this.y = _y; }

    @Override
    public UserType copy() { return (UserType) new UserVector(this.x,this.y); }
    @Override
    public UserType create() { return (UserType) new UserVector(); }

    @Override
    public UserType create(ArrayList<String> values) {
        return new UserVector(Float.parseFloat(values.get(0)),Float.parseFloat(values.get(1)));
    }

    @Override
    public ArrayList<Pair<String, String>> getFields() {
        return new ArrayList<>(Arrays.asList(new Pair<>("X","Float"),new Pair<>("Y","Float")));
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        if(x==null||y==null) return "null;null";
        return x +";"+ y;
    }


    @Override
    public UserType parseValue(JSONObject json) {
        try {
            return new UserVector(
                    json.getFloat("x"),
                    json.getFloat("y")
            );
        } catch (JSONException e) {
            return null;
        }
    }
    @Override
    public String packValue() {
        return "{\"className\":\""+className+ "\","+"\"x\""+":"+x.toString()+ ","+"\"y\""+":"+y.toString() + "}";
    }

    @Override
    public int compareTo(Object o) {
        Float tValue = x*x+y*y;
        Float oValue = ((UserVector) o).x*((UserVector) o).x+((UserVector) o).y*((UserVector) o).y;
        return (tValue).compareTo(oValue);
    }
}
