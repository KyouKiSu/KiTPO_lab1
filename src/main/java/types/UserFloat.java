package types;
import javafx.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class UserFloat implements UserType {

    final static String className = "UserFloat";

    private Float value;

    public UserFloat() { this.value = null; }

    public UserFloat(Float data) { this.value = data; }

    @Override
    public UserType copy() { return (UserType) new UserFloat(this.value); }
    @Override
    public UserType create() { return (UserType) new UserFloat(); }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        if(value==null) return "null";
        return value.toString();
    }


    @Override
    public UserType parseValue(JSONObject json) {
        try {
            return new UserFloat(
                    json.getFloat("raw_value")
            );
        } catch (JSONException e) {
            return null;
        }
    }
    @Override
    public String packValue() {
        return "{\"className\":\""+className+ "\","+"\"raw_value\""+":"+value.toString() + "}";
    }

    @Override
    public UserType create(ArrayList<String> values) {
        return new UserFloat(Float.parseFloat(values.get(0)));
    }

    @Override
    public ArrayList<Pair<String, String>> getFields() {
        return new ArrayList<>(Arrays.asList(new Pair<>("Value","Integer")));
    }

    @Override
    public int compareTo(Object o) {
        return value.compareTo(((UserFloat) o).value);
    }


}
