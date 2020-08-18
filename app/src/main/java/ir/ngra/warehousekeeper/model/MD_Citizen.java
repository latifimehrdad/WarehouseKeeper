package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

public class MD_Citizen {

    @SerializedName("buildingUse")
    String buildingUse;

    @SerializedName("avatar")
    String avatar;

    public MD_Citizen(String buildingUse, String avatar) {
        this.buildingUse = buildingUse;
        this.avatar = avatar;
    }

    public String getBuildingUse() {
        return buildingUse;
    }

    public void setBuildingUse(String buildingUse) {
        this.buildingUse = buildingUse;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
