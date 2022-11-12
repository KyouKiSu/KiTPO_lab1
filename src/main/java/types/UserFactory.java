package types;

import javafx.util.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserFactory {
    public final static ArrayList<UserType> types = new ArrayList<>(Arrays.asList(new UserInteger(),new UserFloat(),new UserVector()));

    static public UserType getBuilderByName(String name) {
        for(UserType element: types){
            if(element.getClassName().equalsIgnoreCase(name)){
                return element.create();
            }
        }
        return null;
    }
}