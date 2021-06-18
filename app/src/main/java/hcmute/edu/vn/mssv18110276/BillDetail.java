package hcmute.edu.vn.mssv18110276;

public class BillDetail {
    private int iID;
    private int iIDBill;
    private int iIDProduct;
    private int iQuantity;
    private long lUnitPrice;

    public int getiID(){return iID;}
    public void setiID(int ID){this.iID = ID;}

    public int getiIDBill(){return iIDBill;}
    public void setiIDBill(int ID){this.iIDBill = ID;}

    public int getiIDProduct(){return iIDProduct;}
    public void setiIDProduct(int idProduct){this.iIDProduct = idProduct;}

    public int getiQuantity() {
        return iQuantity;
    }
    public void setiQuantity(int quantity){this.iQuantity = quantity; }

    public long getlUnitPrice() {
        return lUnitPrice;
    }
    public void setlUnitPrice(long unitprice){this.lUnitPrice = unitprice; }

    public BillDetail(){}

    public BillDetail(int id, int idbill, int idproduct, int quantity, long unitprice){
        this.iID = id;
        this.iIDBill = idbill;
        this.iIDProduct = idproduct;
        this.iQuantity = quantity;
        this.lUnitPrice = unitprice;
    }

    public BillDetail(int idbill, int idproduct, int quantity, long unitprice){
        this.iIDBill = idbill;
        this.iIDProduct = idproduct;
        this.iQuantity = quantity;
        this.lUnitPrice = unitprice;
    }

}
