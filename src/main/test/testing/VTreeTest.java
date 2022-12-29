package testing;

import org.junit.jupiter.api.Test;
import types.UserInteger;
import vtree.VTree;
import vtree.VTreeFactory;

import static org.junit.jupiter.api.Assertions.*;

public class VTreeTest {
    @Test
    public void checkSizeAfterInsertion() {
        VTreeFactory builder = new VTreeFactory();
        builder.setType("UserInt");

        VTree tree = builder.createTree();
        UserInteger number = new UserInteger(Integer.valueOf(1));

        tree.Insert(number);
        tree.Insert(number);
        tree.Insert(number);

        int treeSize = tree.GetSize();
        int expectedSize=3;

        assertEquals(expectedSize,treeSize);
    }
    @Test
    public void checkEmptyOnCreation() {
        VTreeFactory builder = new VTreeFactory();
        builder.setType("UserInt");

        VTree tree = builder.createTree();

        assertTrue(tree.IsEmpty());
    }
    @Test
    public void checkNotEmptyAfterInsertion() {
        VTreeFactory builder = new VTreeFactory();
        builder.setType("UserInt");

        VTree tree = builder.createTree();
        UserInteger number = new UserInteger(Integer.valueOf(1));

        tree.Insert(number);

        assertFalse(tree.IsEmpty());
    }
    @Test
    public void checkSizeAfterRebalance() {
        VTreeFactory builder = new VTreeFactory();
        builder.setType("UserInt");

        VTree tree = builder.createTree();
        int totalElements=10;
        for (int i = 0; i<totalElements; i++){
            tree.Insert(new UserInteger(Integer.valueOf(i)));
        }
        tree.Remove(new UserInteger(Integer.valueOf(5)));
        tree.rebalance();
        assertEquals(tree.GetSize(),totalElements-1);
    }
    @Test
    public void checkElementsAfterRebalance() {
        VTreeFactory builder = new VTreeFactory();
        builder.setType("UserInt");

        VTree tree = builder.createTree();
        int totalElements=10;
        for (int i = 0; i<totalElements; i++){
            tree.Insert(new UserInteger(Integer.valueOf(i)));
        }
        tree.rebalance();
        for (int i = 0; i<totalElements; i++){
            assertTrue(tree.find(new UserInteger(Integer.valueOf(i)))!=null);
        }
    }
    @Test
    public void checkElementRemoved() {
        VTreeFactory builder = new VTreeFactory();
        builder.setType("UserInt");

        VTree tree = builder.createTree();
        int totalElements=10;
        for (int i = 0; i<totalElements; i++){
            tree.Insert(new UserInteger(Integer.valueOf(i)));
        }

        tree.Remove(new UserInteger(Integer.valueOf(5)));
        for (int i = 0; i<totalElements; i++){
            if(i==5){
                assertNull(tree.find(new UserInteger(Integer.valueOf(i))));
            } else {
                assertNotNull(tree.find(new UserInteger(Integer.valueOf(i))));
            }

        }
    }
    @Test
    public void checkDuplicateInsert() {
        VTreeFactory builder = new VTreeFactory();
        builder.setType("UserInt");

        VTree tree = builder.createTree();
        int totalElements=10;
        int value = 100;
        for (int i = 0; i<totalElements; i++){
            tree.Insert(new UserInteger(Integer.valueOf(value)));
        }

        // check size
        int treeSize = tree.GetSize();
        assertEquals(totalElements,treeSize);

        // check existence
        assertNotNull(tree.find(new UserInteger(Integer.valueOf(value))));
    }
    @Test
    public void checkMultipleDuplicateInsert() {
        VTreeFactory builder = new VTreeFactory();
        builder.setType("UserInt");

        VTree tree = builder.createTree();

        int groupSize1=10;
        int value1 = 100;
        for (int i = 0; i<groupSize1; i++){
            tree.Insert(new UserInteger(Integer.valueOf(value1)));
        }

        int groupSize2=10;
        int value2 = 500;
        for (int i = 0; i<groupSize2; i++){
            tree.Insert(new UserInteger(Integer.valueOf(value2)));
        }

        // check size
        int treeSize = tree.GetSize();
        assertEquals(groupSize1+groupSize2,treeSize);

        // check existence
        assertNotNull(tree.find(new UserInteger(Integer.valueOf(value1))));
        assertNotNull(tree.find(new UserInteger(Integer.valueOf(value2))));
    }
    @Test
    public void checkExtremeValues() {
        VTreeFactory builder = new VTreeFactory();
        builder.setType("UserInt");

        VTree tree = builder.createTree();

        int groupSize1=10;
        for (int i = 0; i<groupSize1; i++){
            tree.Insert(new UserInteger(Integer.MAX_VALUE));
        }

        int groupSize2=10;
        for (int i = 0; i<groupSize2; i++){
            tree.Insert(new UserInteger(Integer.MIN_VALUE));
        }


        // check size
        int treeSize = tree.GetSize();
        assertEquals(groupSize1+groupSize2,treeSize);

        // check existence
        assertNotNull(tree.find(new UserInteger(Integer.MAX_VALUE)));
        assertNotNull(tree.find(new UserInteger(Integer.MIN_VALUE)));
    }

}