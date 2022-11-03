package vtree;

import org.json.JSONException;
import org.json.JSONObject;
import types.UserFactory;
import types.UserType;

import java.util.ArrayList;

public class VTreeFactory {
    final static ArrayList<String> typeNames = UserFactory.getTypeNames();
    private UserType typeInstance;
    private UserFactory factory;
    public VTreeFactory(int id){
        factory = new UserFactory();
        setType(id);
    }
    public VTreeFactory(){
        factory = new UserFactory();
        setType(0);
    }
    public UserType getTypeInstance(){
        return typeInstance.create();
    }
    public void setType(int id) {
        typeInstance=factory.getBuilderByName(typeNames.get(id));
    }
    public void setType(String name) {
        typeInstance=factory.getBuilderByName(name);
    }
    public <T extends UserType> VTree<T> parseTree(JSONObject json) {
        Node<T> node = parseNode(json);
        VTree<T> _vtree = new VTree<>();
        _vtree.SetRoot(node);
        return _vtree;
    }
    public <T extends UserType> VTree<T> createTree(){
        VTree<T> tree = new VTree<>();
        return tree;
    }
    private <T extends UserType> Node<T> parseNode(JSONObject json){
        String _nodeClassName = json.getString("className");
        assert(_nodeClassName != Node.className);
        JSONObject _valueJSON = null;
        T _value = null;
        try{
            _valueJSON = json.getJSONObject("value");
            setType(_valueJSON.getString("className"));
            if (typeInstance == null) {
                throw new Exception("Wrong type");
            }
            _value=(T)typeInstance.parseValue(_valueJSON);
        } catch (Exception e){
            System.out.println(e);
        }

        int _cnt = json.getInt("cnt");
        Node<T> _leftChild = null;
        Node<T> _rightChild = null;
        try{
            _leftChild=parseNode(json.getJSONObject("leftChild"));
        } catch (JSONException e){
        }
        try{
            _rightChild=parseNode(json.getJSONObject("rightChild"));
        } catch (JSONException e){
        }
        Node<T> node = new Node<>(_value);
        node.setCount(_cnt);
        node.setLeftChild(_leftChild);
        node.setRightChild(_rightChild);
        return node;
    }
}
