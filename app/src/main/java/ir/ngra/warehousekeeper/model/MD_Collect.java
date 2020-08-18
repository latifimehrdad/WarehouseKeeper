package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

public class MD_Collect {

    @SerializedName("waste")
    private MD_ItemWaste Waste;

    @SerializedName("value")
    private String Amount;

    @SerializedName("weight")
    private MD_Weight Weight;

    public MD_Collect(MD_ItemWaste waste, String amount, MD_Weight weight) {
        Waste = waste;
        Amount = amount;
        Weight = weight;
    }

    public MD_ItemWaste getWaste() {
        return Waste;
    }

    public void setWaste(MD_ItemWaste waste) {
        Waste = waste;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public MD_Weight getWeight() {
        return Weight;
    }

    public void setWeight(MD_Weight weight) {
        Weight = weight;
    }
}
