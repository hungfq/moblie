package hcmute.edu.vn.mssv18110276;

public class Cart {
    private int iID;
    private int iIDProduct;
    private int iIDUser;
    private int iQuantity;

    public int getiID() {
        return iID;
    }
    public void setiID(int ID){this.iID = ID; }

    public int getiIDProduct() {
        return iIDProduct;
    }
    public void setiIDProduct(int ID){this.iIDProduct = ID; }

    public int getiIDUser() {
        return iIDUser;
    }
    public void setiIDUser(int ID){this.iIDUser = ID; }

    public int getiQuantity() {
        return iQuantity;
    }
    public void setiQuantity(int quantity){this.iQuantity = quantity; }

    public Cart(){};

    public Cart(int ID, int IDProduct, int IDUser, int Quantity){
        iID = ID;
        iIDProduct = IDProduct;
        iIDUser = IDUser;
        iQuantity = Quantity;
    }

    public Cart(int IDProduct, int IDUser, int Quantity){
        iIDProduct = IDProduct;
        iIDUser = IDUser;
        iQuantity = Quantity;
    }
}
