package hcmute.edu.vn.mssv18110276;

import android.widget.TextView;

public class CategoryProduct {
    private int iID;
    private String sName;
    private byte[] sSource;

    public int getiID() {
        return iID;
    }
    public void setiID(int ID){this.iID = ID; }

    public String getsName(){
        return sName;
    }
    public void setsName(String Name){this.sName = Name; }

    public byte[] getsSource() {return sSource;}
    public void setsSource(byte[] Source){this.sSource = Source;}

    public CategoryProduct(int ID, String Name, byte[] Source){
        this.iID = ID;
        this.sName = Name;
        this.sSource = Source;
    }

    public CategoryProduct(String Name, byte[] Source){
        this.sName = Name;
        this.sSource = Source;
    }

    public CategoryProduct(){};

    public void insertDefaultCategory(DatabaseHandler db){
        db.insertCategoryProduct(new CategoryProduct("Sandwich", null));
        db.insertCategoryProduct(new CategoryProduct("Drinks", null));
    }
}
