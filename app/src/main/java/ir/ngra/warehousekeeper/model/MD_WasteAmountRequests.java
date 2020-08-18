package ir.ngra.warehousekeeper.model;

import java.util.List;

public class MD_WasteAmountRequests {

    private String RequestCode;

    private List<MD_Collect> WasteAmountRequests;

    public MD_WasteAmountRequests(String requestCode, List<MD_Collect> wasteAmountRequests) {
        RequestCode = requestCode;
        WasteAmountRequests = wasteAmountRequests;
    }

    public String getRequestCode() {
        return RequestCode;
    }

    public void setRequestCode(String requestCode) {
        RequestCode = requestCode;
    }

    public List<MD_Collect> getWasteAmountRequests() {
        return WasteAmountRequests;
    }

    public void setWasteAmountRequests(List<MD_Collect> wasteAmountRequests) {
        WasteAmountRequests = wasteAmountRequests;
    }
}
