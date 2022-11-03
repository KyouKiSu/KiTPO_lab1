package types;

import javafx.util.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserFactory {
    final static ArrayList<UserType> types = new ArrayList<>(Arrays.asList(new UserInteger(),new UserFloat(),new UserVector()));
    final static ArrayList<String> typeNames = new ArrayList<>(types.stream().map(i->i.getClassName()).collect(Collectors.toList()));
    final static Map<String, ArrayList<Pair<String, String>>> typeInfo = types.stream().collect(Collectors.toMap(UserType::getClassName, UserType::getFields));

    static public ArrayList<String> getTypeNames(){
        return (ArrayList<String>) typeNames.clone();
    }

    static public  Map<String, ArrayList<Pair<String, String>>> getTypeInfo(){
        return typeInfo;
    }

    public UserType getBuilderByName(String name) {
        for(UserType element: types){
            if(element.getClassName().equalsIgnoreCase(name)){
                return element.create();
            }
        }
        return null;
    }
}