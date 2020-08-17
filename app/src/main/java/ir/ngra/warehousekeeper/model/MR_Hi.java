package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MR_Hi extends MR_Primary {

    @SerializedName("result")
    MD_Hi result;


    public MR_Hi(ArrayList<MD_Message> messages) {
        super(messages);
    }

    public MD_Hi getResult() {
        return result;
    }

    public void setResult(MD_Hi result) {
        this.result = result;
    }
}
