package ir.ngra.warehousekeeper.model;

import java.util.List;

public class MD_WasteAmountRequests {

    private String RequestCode;

    private List<MD_Collect> WasteAmountRequests;

    private String app_token;


    public MD_WasteAmountRequests(String requestCode, List<MD_Collect> wasteAmountRequests, String app_token) {
        RequestCode = requestCode;
        WasteAmountRequests = wasteAmountRequests;
        this.app_token = app_token;
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

    public String getApp_token() {
        return app_token;
    }

    public void setApp_token(String app_token) {
        this.app_token = app_token;
    }
}
