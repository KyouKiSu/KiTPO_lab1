package types;
import javafx.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class UserInteger implements UserType {

    final static String className = "UserInteger";

    private Integer value;

    public UserInteger() { this.value = null; }

    public UserInteger(Integer data) { this.value = data; }

    @Override
    public UserType copy() { return (UserType) new UserInteger(this.value); }
    @Override
    public UserType create() { return (UserType) new UserInteger(); }
    @Override
    public UserType create(ArrayList<String> values) {
        return new UserInteger(Integer.parseInt(values.get(0)));
    }

    @Override
    public ArrayList<Pair<String, String>> getFields() {
        return new ArrayList<>(Arrays.asList(new Pair<>("Value","Integer")));
    }

    @Override
    public String getClassName() {
        return className;
    }


    @Override
    public UserType parseValue(JSONObject json) {
        try {
            return new UserInteger(
                    json.getInt("raw_value")
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
    public String toString() {
        if(value==null) return "null";
        return value.toString();
    }

    @Override
    public int compareTo(Object o) {
        return value.compareTo(((UserInteger) o).value);
    }
}
