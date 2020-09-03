package ir.ngra.warehousekeeper.model;


public class MD_ItemsWasteList {

    Integer Id;

    String Name;

    String Amount1Id;

    String Amount2Id;

    String title1;

    String title2;

    String Amount1;

    String Amount2;


    public MD_ItemsWasteList(Integer id, String name, String amount1Id, String amount2Id, String title1, String title2, String amount1, String amount2) {
        Id = id;
        Name = name;
        Amount1Id = amount1Id;
        Amount2Id = amount2Id;
        this.title1 = title1;
        this.title2 = title2;
        Amount1 = amount1;
        Amount2 = amount2;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmount1Id() {
        return Amount1Id;
    }

    public void setAmount1Id(String amount1Id) {
        Amount1Id = amount1Id;
    }

    public String getAmount2Id() {
        return Amount2Id;
    }

    public void setAmount2Id(String amount2Id) {
        Amount2Id = amount2Id;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getAmount1() {
        return Amount1;
    }

    public void setAmount1(String amount1) {
        Amount1 = amount1;
    }

    public String getAmount2() {
        return Amount2;
    }

    public void setAmount2(String amount2) {
        Amount2 = amount2;
    }
}
