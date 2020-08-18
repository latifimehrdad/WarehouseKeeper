package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MR_ResponseWaste extends MR_Primary {

    @SerializedName("result")
    List<MD_WasteRequest> result;


    public MR_ResponseWaste(ArrayList<MD_Message> messages, List<MD_WasteRequest> result) {
        super(messages);
        this.result = result;
    }

    public List<MD_WasteRequest> getResult() {
        return result;
    }

    public void setResult(List<MD_WasteRequest> result) {
        this.result = result;
    }

}
