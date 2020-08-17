package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

public class MD_Register {

    @SerializedName("unconfirmedMobile")
    boolean unconfirmedMobile;

    public MD_Register(boolean unconfirmedMobile) {
        this.unconfirmedMobile = unconfirmedMobile;
    }

    public boolean isUnconfirmedMobile() {
        return unconfirmedMobile;
    }

    public void setUnconfirmedMobile(boolean unconfirmedMobile) {
        this.unconfirmedMobile = unconfirmedMobile;
    }
}
