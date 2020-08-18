package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_Weights extends MR_Primary{

    @SerializedName("result")
    List<MD_Weight> result;


    public MR_Weights(ArrayList<MD_Message> messages, List<MD_Weight> result) {
        super(messages);
        this.result = result;
    }

    public List<MD_Weight> getResult() {
        return result;
    }

    public void setResult(List<MD_Weight> result) {
        this.result = result;
    }
}
