package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class MD_WasteRequest {

    @SerializedName("requestCode")
    String requestCode;

    @SerializedName("warehouseState")
    Integer wasteCollectionState;

    @SerializedName("subscriptionCode")
    String subscriptionCode;


    @SerializedName("address")
    String address;

    @SerializedName("citizen")
    MD_Citizen citizen;


    @SerializedName("requestDate")
    Date requestDate;

    @SerializedName("deliverDate")
    Date deliverDate;

    @SerializedName("userFullName")
    String userFullName;

    @SerializedName("phoneNumber")
    String phoneNumber;

    @SerializedName("location")
    MD_Location location;

    @SerializedName("warehouseAmounts")
    List<MD_WarehouseAmounts> warehouseAmounts;


    public MD_WasteRequest(String requestCode, Integer wasteCollectionState, String subscriptionCode, String address, MD_Citizen citizen, Date requestDate, Date deliverDate, String userFullName, String phoneNumber, MD_Location location, List<MD_WarehouseAmounts> warehouseAmounts) {
        this.requestCode = requestCode;
        this.wasteCollectionState = wasteCollectionState;
        this.subscriptionCode = subscriptionCode;
        this.address = address;
        this.citizen = citizen;
        this.requestDate = requestDate;
        this.deliverDate = deliverDate;
        this.userFullName = userFullName;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.warehouseAmounts = warehouseAmounts;
    }


    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public Integer getWasteCollectionState() {
        return wasteCollectionState;
    }

    public void setWasteCollectionState(Integer wasteCollectionState) {
        this.wasteCollectionState = wasteCollectionState;
    }

    public String getSubscriptionCode() {
        return subscriptionCode;
    }

    public void setSubscriptionCode(String subscriptionCode) {
        this.subscriptionCode = subscriptionCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MD_Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(MD_Citizen citizen) {
        this.citizen = citizen;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MD_Location getLocation() {
        return location;
    }

    public void setLocation(MD_Location location) {
        this.location = location;
    }

    public List<MD_WarehouseAmounts> getWarehouseAmounts() {
        return warehouseAmounts;
    }

    public void setWarehouseAmounts(List<MD_WarehouseAmounts> warehouseAmounts) {
        this.warehouseAmounts = warehouseAmounts;
    }
}
