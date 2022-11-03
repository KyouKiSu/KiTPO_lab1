package types;

import javafx.util.Pair;
import org.json.JSONObject;

import java.util.ArrayList;

public interface UserType extends Comparable{
    public UserType copy();
    public UserType create();
    public String toString();
    public String getClassName();
    public UserType parseValue(JSONObject json);
    public String packValue();
    public UserType create(ArrayList<String> values);
    public ArrayList<Pair<String, String>> getFields();
}
