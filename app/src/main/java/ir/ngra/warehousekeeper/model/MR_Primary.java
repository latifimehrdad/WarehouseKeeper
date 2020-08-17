package ir.ngra.warehousekeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MR_Primary {

    @SerializedName("messages")
    ArrayList<MD_Message> messages;

    public MR_Primary(ArrayList<MD_Message> messages) {
        this.messages = messages;
    }

    public ArrayList<MD_Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MD_Message> messages) {
        this.messages = messages;
    }
}
