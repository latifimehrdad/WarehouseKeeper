package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MD_ProfileInfo extends MR_Primary {

    @SerializedName("result")
    MD_Profile result;

    public MD_ProfileInfo(ArrayList<MD_Message> messages) {
        super(messages);
    }

    public MD_Profile getResult() {
        return result;
    }

    public void setResult(MD_Profile result) {
        this.result = result;
    }

    public class MD_Profile {

        @SerializedName("name")
        String name;

        @SerializedName("lastName")
        String lastName;

        @SerializedName("gender")
        Integer gender;

        @SerializedName("isProfileCompleted")
        Boolean isProfileCompleted;

        @SerializedName("isAddressCompleted")
        Boolean isAddressCompleted;

        public MD_Profile(String name, String lastName, Integer gender, Boolean isProfileCompleted, Boolean isAddressCompleted) {
            this.name = name;
            this.lastName = lastName;
            this.gender = gender;
            this.isProfileCompleted = isProfileCompleted;
            this.isAddressCompleted = isAddressCompleted;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public Boolean getProfileCompleted() {
            return isProfileCompleted;
        }

        public void setProfileCompleted(Boolean profileCompleted) {
            isProfileCompleted = profileCompleted;
        }

        public Boolean getAddressCompleted() {
            return isAddressCompleted;
        }

        public void setAddressCompleted(Boolean addressCompleted) {
            isAddressCompleted = addressCompleted;
        }
    }

}
