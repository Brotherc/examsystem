package cn.examsystem.common.pojo;

import java.util.List;

/**
 * Created by Administrator on 2018/2/9.
 * jsTree
 */
public class TreeNode {
    private String id;
    private String text;
    private TreeNodeState state;
    private List<TreeNode> children;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TreeNodeState getState() {
        return state;
    }

    public void setState(TreeNodeState state) {
        this.state = state;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TreeNode() {
    }

    public TreeNode(String id, String text, TreeNodeState state, List<TreeNode> children, String type) {
        this.id = id;
        this.text = text;
        this.state = state;
        this.children = children;
        this.type = type;
    }
}
