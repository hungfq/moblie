package hcmute.edu.vn.mssv18110276;

public class Product {
    private int iID;
    private String sName;
    private int iIDCategory;
    private long lPrice;
    private String sDescription;
    private byte[] sSource;

    public int getiID() {
        return iID;
    }
    public void setiID(int ID){this.iID = ID; }

    public String getsName(){
        return sName;
    }
    public void setsName(String Name){this.sName = Name; }

    public int getiIDCategory() {
        return iIDCategory;
    }
    public void setiIDCategory(int Category){this.iIDCategory = Category; }

    public long getlPrice() {
        return lPrice;
    }
    public void setlPrice(long Price){this.lPrice = Price; }

    public String getsDescription() {
        return sDescription;
    }
    public void setsDescription(String Description){this.sDescription = Description; }

    public byte[] getsSource() {
        return sSource;
    }
    public void setsSource(byte[] Src){this.sSource = Src; }

    public Product(){};

    public Product(int ID, String Name, int IDCategory, long Price, String Description, byte[] Source){
        this.iID = ID;
        this.sName = Name;
        this.iIDCategory = IDCategory;
        this.lPrice = Price;
        this.sDescription = Description;
        this.sSource = Source;
    }

    public Product(String Name, int IDCategory, long Price, String Description, byte[] Source){
        this.sName = Name;
        this.iIDCategory = IDCategory;
        this.lPrice = Price;
        this.sDescription = Description;
        this.sSource = Source;
    }
}
