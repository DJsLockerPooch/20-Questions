package pkg20questions;

import java.io.Serializable;

public class TreeNode implements Serializable {

    public TreeNode(String data) {
        this.data = data;
    }

    public String data;
    public TreeNode left;
    public TreeNode right;

}
