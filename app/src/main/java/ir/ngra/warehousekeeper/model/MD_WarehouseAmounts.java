package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MD_WarehouseAmounts {

    @SerializedName("waste")
    private MD_ItemWaste Waste;

    @SerializedName("weight")
    private List<Md_WeightWareHouseAmounts> Weight;

    public MD_WarehouseAmounts(MD_ItemWaste waste, List<Md_WeightWareHouseAmounts> weight) {
        Waste = waste;
        Weight = weight;
    }

    public MD_ItemWaste getWaste() {
        return Waste;
    }

    public void setWaste(MD_ItemWaste waste) {
        Waste = waste;
    }

    public List<Md_WeightWareHouseAmounts> getWeight() {
        return Weight;
    }

    public void setWeight(List<Md_WeightWareHouseAmounts> weight) {
        Weight = weight;
    }
}
