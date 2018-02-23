package cn.examsystem.common.pojo;

/**
 * Created by Administrator on 2018/2/9.
 * jsTree节点中的state对象
 */
public class TreeNodeState {
    //节点处于被选中状态
    private boolean selected;

    //节点处于打开状态
    private boolean opened;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public TreeNodeState(boolean selected, boolean opened) {
        this.selected = selected;
        this.opened = opened;
    }

    public TreeNodeState() {
    }
}
