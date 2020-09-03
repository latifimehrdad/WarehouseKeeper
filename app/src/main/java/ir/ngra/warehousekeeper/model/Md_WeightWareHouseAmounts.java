package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

public class Md_WeightWareHouseAmounts {

    @SerializedName("value")
    double value;

    @SerializedName("measure")
    MD_Measure measure;

    public Md_WeightWareHouseAmounts(double value, MD_Measure measure) {
        this.value = value;
        this.measure = measure;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public MD_Measure getMeasure() {
        return measure;
    }

    public void setMeasure(MD_Measure measure) {
        this.measure = measure;
    }
}
