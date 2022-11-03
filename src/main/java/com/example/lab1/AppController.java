package com.example.lab1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.json.JSONObject;
import types.UserFactory;
import types.UserType;
import vtree.VTree;
import vtree.VTreeFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AppController {
    VTreeFactory vTreeFactory = new VTreeFactory();
    VTree<UserType> tree;
    @FXML
    private ChoiceBox userTypeChoiceBox;

    @FXML
    private TextArea showTextArea;
    @FXML
    private GridPane userTypeFieldGrid;
    @FXML
    private Button changeUserTypeButton;
    @FXML
    private TextField filenameField;

    public void initialize() {
        // init choice box with user types
        ObservableList<String> list = FXCollections.observableArrayList(UserFactory.getTypeNames());
        userTypeChoiceBox.setItems(list);
        userTypeChoiceBox.setValue(list.get(0));
    }
    protected void initTree(){
        setFactoryClasses();
        resetTree();
        refreshTree();
    }
    protected void resetTree(){
        tree = vTreeFactory.createTree();
    }
    protected void refreshTree(){
        showTextArea.setText(tree.print());
    }

    protected void setFactoryClasses(){
        String selectedClass = (String) userTypeChoiceBox.getValue();
        vTreeFactory.setType(selectedClass);
        // System.out.println(selectedClass);
        // System.out.println(vTreeFactory.getTypeInstance());
    }

    protected UserType createElement(){
        ArrayList<String> values = new ArrayList<>();
        for(Node element : userTypeFieldGrid.getChildren()){
            if(element.getClass().equals(TextField.class)){
                TextField field = (TextField) element;
                values.add(field.getText());
            }
        }
        UserType builder = vTreeFactory.getTypeInstance();
        UserType instance = builder.create(values);
        return instance;
    }

    protected void insertElement(UserType element){
        tree.Insert(element);
    }

    protected void rebalanceTree(){
        tree.rebalance();
    }

    protected void removeElement(UserType element){
        tree.Remove(element);
    }


    @FXML
    protected void onChangeUserTypeButtonClick(){
        // update selected class in factory
        setFactoryClasses();

        // update grid with fields from factory
        String selectedClass = (String) userTypeChoiceBox.getValue();
        ArrayList<Pair<String, String>> userTypeFieldInfo = UserFactory.getTypeInfo().get(selectedClass);

        userTypeFieldGrid.getChildren().clear();
        int row = 0;
        for (Pair<String, String> field: userTypeFieldInfo) {
            Label label = new Label(field.getKey());
            TextField text = new TextField();
            text.setPromptText(field.getValue());
            userTypeFieldGrid.add(label,0,row);
            userTypeFieldGrid.add(text,1,row);
            row+=1;
        }
        initTree();
        //userTypeFieldGrid.getChildren().addAll(label);
    }

    @FXML
    protected void onAddButtonClick(){
        UserType element = createElement();
        insertElement(element);
        refreshTree();
    }

    @FXML
    protected void onRebalanceButtonClick(){
        rebalanceTree();
        refreshTree();
    }

    @FXML
    protected void onRemoveButtonClick(){
        UserType element = createElement();
        removeElement(element);
        refreshTree();
    }

    @FXML
    protected void onResetButtonClick(){
        resetTree();
        refreshTree();
    }

    @FXML
    protected void onRefreshButtonClick(){
        refreshTree();
    }

    @FXML
    protected void onShowJSONButtonClick(){
        showTextArea.setText(new JSONObject(tree.packValue()).toString(4));
    }

    @FXML
    protected void onToJSONButtonClick(){
        String packed = tree.packValue();
        String path = filenameField.getText();
        try {
            Files.write(Paths.get(path), packed.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refreshTree();
    }
    @FXML
    protected void onFromJSONButtonClick(){
        String path = filenameField.getText();
        try {
            String json = Files.readString(Paths.get(path));
            tree=vTreeFactory.parseTree(new JSONObject(json));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refreshTree();
    }


}