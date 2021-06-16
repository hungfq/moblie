package hcmute.edu.vn.mssv18110276;

public class Cart {
    private int iID;
    private int iIDProduct;
    private int iIDUser;
    private int iQuantity;
    private int iChecked;

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
    public int getiChecked(){return iChecked;}
    public void setiChecked(int checked){this.iChecked = checked;}

    public Cart(){};

    public Cart(int ID, int IDProduct, int IDUser, int Quantity, int checked){
        iID = ID;
        iIDProduct = IDProduct;
        iIDUser = IDUser;
        iQuantity = Quantity;
        iChecked = checked;
    }

    public Cart(int IDProduct, int IDUser, int Quantity, int checked){
        iIDProduct = IDProduct;
        iIDUser = IDUser;
        iQuantity = Quantity;
        iChecked = checked;
    }

    public Cart(int IDProduct, int IDUser, int Quantity){
        iIDProduct = IDProduct;
        iIDUser = IDUser;
        iQuantity = Quantity;
    }

    public void insertDefaultCart(DatabaseHandler db){
        db.insertItemCart(new Cart(1,2,2));
        db.insertItemCart(new Cart(1,1,1));
    }
}