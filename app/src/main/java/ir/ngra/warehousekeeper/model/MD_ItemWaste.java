package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MD_ItemWaste {

    @SerializedName("id")
    Integer Id;

    @SerializedName("title")
    String Title;

    @SerializedName("cover")
    @Expose
    String Cover;


    public MD_ItemWaste(Integer id, String title, String cover) {
        Id = id;
        Title = title;
        Cover = cover;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }
}
