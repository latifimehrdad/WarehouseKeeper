package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

public class MD_Weight {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("isSelected")
    boolean isSelected;

    public MD_Weight(String id, String title, boolean isSelected) {
        this.id = id;
        this.title = title;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
