package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

public class MD_Hi {

    //______________________________________________________________________________________________ Value
    @SerializedName("applicationUrl")
    String applicationUrl;

    @SerializedName("fileName")
    String fileName;

    @SerializedName("name")
    String name;

    @SerializedName("version")
    String version;
    //______________________________________________________________________________________________ Value


    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
