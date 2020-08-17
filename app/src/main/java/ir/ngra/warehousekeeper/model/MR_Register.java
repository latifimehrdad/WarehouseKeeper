package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MR_Register extends MR_Primary {

    @SerializedName("result")
    MD_Register result;

    public MR_Register(ArrayList<MD_Message> messages, MD_Register result) {
        super(messages);
        this.result = result;
    }

    public MD_Register getResult() {
        return result;
    }

    public void setResult(MD_Register result) {
        this.result = result;
    }
}
