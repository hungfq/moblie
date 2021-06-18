package hcmute.edu.vn.mssv18110276;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "storedb.db";
    private static final String TABLE_PRODUCT = "product";
    private static final String TABLE_CATEGORYPRODUCT = "categoryproduct";
    private static final String TABLE_USER = "user";
    private static final String TABLE_ROLE = "role";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_BILL = "bill";
    private static final String TABLE_BILLDETAIL = "billdetail";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORYPRODUCT_TABLE = "CREATE TABLE " + TABLE_CATEGORYPRODUCT + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name NVARCHAR(255) NOT NULL UNIQUE,"
                + "source BLOB(255)"
                + ")";
        db.execSQL(CREATE_CATEGORYPRODUCT_TABLE);

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT
                + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name NVARCHAR(255) NOT NULL UNIQUE,"
                + "category int NOT NULL,"
                + "price DECIMAL DEFAULT(0) NOT NULL,"
                + "description VARCHAR(255),"
                + "source BLOB,"
                + "quantity int NOT NULL DEFAULT 0 CHECK(quantity < 1000),"
                + "state int NOT NULL DEFAULT 0,"
                + "CONSTRAINT fk_product_idcategory FOREIGN KEY(category) REFERENCES categoryproduct(id)"
                + ")";
        db.execSQL(CREATE_PRODUCT_TABLE);

        String CREATE_ROLE_TABLE = "CREATE TABLE " + TABLE_ROLE
                + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name VARCHAR(255) NOT NULL UNIQUE"
                + ")";

        db.execSQL(CREATE_ROLE_TABLE);
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER
                + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name NVARCHAR(255) NOT NULL,"
                + "email VARCHAR(255) NOT NULL UNIQUE,"
                + "phone VARCHAR(10) NOT NULL UNIQUE,"
                + "password VARCHAR(255),"
                + "verifyemail INTEGER,"
                + "state INTEGER,"
                + "role INTEGER,"
                + "source BLOB(255),"
                + "address NVARCHAR(255),"
                + "CONSTRAINT fk_user_idrole FOREIGN KEY(role) REFERENCES role(id)"
                +")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART
                + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "idproduct INTEGER NOT NULL,"
                + "iduser INTEGER NOT NULL,"
                + "quantity INTEGER CHECK(quantity > 0),"
                + "checked INTEGER DEFAULT 0,"
                + "CONSTRAINT fk_cart_idproduct FOREIGN KEY(idproduct) REFERENCES product(id),"
                + "CONSTRAINT fk_cart_iduser FOREIGN KEY(iduser) REFERENCES user(id)"
                + ")";

        db.execSQL(CREATE_CART_TABLE);

        String CREATE_BILL_TABLE = "CREATE TABLE " + TABLE_BILL
                + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "iduser INTEGER NOT NULL,"
                + "quantity INTEGER NOT NULL,"
                + "personname NVARCHAR(255) NOT NULL,"
                + "phone VARCHAR(10) NOT NULL,"
                + "address NVARCHAR(255) NOT NULL,"
                + "date TEXT NOT NULL,"
                + "state INT DEFAULT 0," // 0. Dang giao 1. Da nhan 2. Da huy
                + "CONSTRAINT fk_bill_iduser FOREIGN KEY(iduser) REFERENCES user(id)"
                + ")";

        db.execSQL(CREATE_BILL_TABLE);

        String CREATE_BILLDETAIL_TABLE = "CREATE TABLE " + TABLE_BILLDETAIL
                + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "idbill INTEGER NOT NULL,"
                + "idproduct INTEGER NOT NULL,"
                + "quantity INTEGER NOT NULL,"
                + "unitprice DECIMAL DEFAULT(0) NOT NULL,"
                + "CONSTRAINT fk_billdetail_idbill FOREIGN KEY(idbill) REFERENCES bill(id)"
                + ")";

        db.execSQL(CREATE_BILLDETAIL_TABLE);
    }

    // Upgrading database
    // Auto call when exist DB on Storage but another version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete old tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORYPRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLDETAIL);
        // Create new tables
        onCreate(db);
    }

    // Delete Record
    public void deleteAllRecord(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORYPRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLDETAIL);
        onCreate(db);
    }

    //region ROLE

    public void insertRole(Role role){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", role.getsName());
        db.insert(TABLE_ROLE, null, values);
        db.close();
    }

    //endregion

   //region CATEGORY PRODUCT
    public void insertCategoryProduct(CategoryProduct categoryProduct){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", categoryProduct.getsName());
        values.put("source", categoryProduct.getsSource());
        db.insert(TABLE_CATEGORYPRODUCT, null, values);
        db.close();
    }

    public int updateCategoryProduct(CategoryProduct categoryProduct){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", categoryProduct.getsName());
        values.put("source", categoryProduct.getsSource());
        return db.update(TABLE_CATEGORYPRODUCT, values, "id = ?", new String[]{String.valueOf(categoryProduct.getiID())});
    }

    public void deleteCategoryProduct(CategoryProduct categoryProduct){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CATEGORYPRODUCT, "id = ? ", new String[]{String.valueOf(categoryProduct.getiID())});
        db.close();
    }

    CategoryProduct getCategoryProduct(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CATEGORYPRODUCT + " WHERE id = " + String.valueOf(id);

        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setiID(Integer.parseInt(cursor.getString(0)));
        categoryProduct.setsName(cursor.getString(1));
        categoryProduct.setsSource(cursor.getBlob(2));
        cursor.close();
        return categoryProduct;
    }

    List<CategoryProduct> getListCategoryProduct(){
        List<CategoryProduct> categoryProductList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CATEGORYPRODUCT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                CategoryProduct categoryProduct = new CategoryProduct();
                categoryProduct.setiID(Integer.parseInt(cursor.getString(0)));
                categoryProduct.setsName(cursor.getString(1));
                categoryProduct.setsSource(cursor.getBlob(2));
                // Adding contact to list
                categoryProductList.add(categoryProduct);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return  categoryProductList;
    }
//endregion

    //region PRODUCT
    public void insertProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", product.getsName());
        values.put("category", product.getiIDCategory());
        values.put("price", product.getlPrice());
        values.put("description", product.getsDescription());
        values.put("source", product.getsSource());
        values.put("quantity", product.getiQuantity());
        values.put("state", product.getiState());
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public int updateProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", product.getsName());
        values.put("category", product.getiIDCategory());
        values.put("price", product.getlPrice());
        values.put("description", product.getsDescription());
        values.put("source", product.getsSource());
        values.put("quantity", product.getiQuantity());
        values.put("state", product.getiState());
        return db.update(TABLE_PRODUCT, values, "id = ?", new String[]{String.valueOf(product.getiID())});
    }

    public void deleteProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PRODUCT, "id = ? ", new String[]{String.valueOf(product.getiID())});
        db.close();
    }

    Product getProduct(int id){
        String query = "SELECT * FROM " + TABLE_PRODUCT + " WHERE id = ? ";
        Product product = new Product();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{id + ""});
        if (cursor.moveToFirst()) {
            product.setiID(cursor.getInt(0));
            product.setsName(cursor.getString(1));
            product.setiIDCategory(cursor.getInt(2));
            product.setlPrice(cursor.getLong(3));
            product.setsDescription(cursor.getString(4));
            product.setsSource(cursor.getBlob(5));
            product.setiQuantity(cursor.getInt(6));
            product.setiState(cursor.getInt(7));
        }
        cursor.close();
        return product;
    }

    public List<Product> getListProduct(){
        List<Product> productList = new ArrayList<Product>();
        String query = "SELECT * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setiID(cursor.getInt(0));
                product.setsName(cursor.getString(1));
                product.setiIDCategory(cursor.getInt(2));
                product.setlPrice(cursor.getLong(3));
                product.setsDescription(cursor.getString(4));
                product.setsSource(cursor.getBlob(5));
                product.setiQuantity(cursor.getInt(6));
                product.setiState(cursor.getInt(7));
                // Adding contact to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    public List<Product> getListProductByCategory(int idcategory){
        List<Product> productList = new ArrayList<Product>();
        String query = "SELECT * FROM " + TABLE_PRODUCT + " WHERE category = ? AND state = 1 " ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{idcategory + ""});
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setiID(cursor.getInt(0));
                product.setsName(cursor.getString(1));
                product.setiIDCategory(idcategory);
                product.setlPrice(cursor.getLong(3));
                product.setsDescription(cursor.getString(4));
                product.setsSource(cursor.getBlob(5));
                product.setiQuantity(cursor.getInt(6));
                product.setiState(cursor.getInt(7));
                // Adding contact to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    //endregion

    //region REGISTER
    public boolean checkIfEmailExists(String userEmail){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select email from " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);
        String existEmail;

        if (cursor.moveToFirst()) {
            do {

                existEmail = cursor.getString(0);
                if (existEmail.equals(userEmail)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    public boolean checkIfNameExists(String userName){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select name from " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);
        String existName;

        if (cursor.moveToFirst()) {
            do {

                existName = cursor.getString(0);
                if (existName.equals(userName)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    public boolean checkIfPhoneExists(String userPhone){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select phone from " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);
        String existPhone;

        if (cursor.moveToFirst()) {
            do {
                existPhone = cursor.getString(0);
                if (existPhone.equals(userPhone)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    public void registerUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getsName());
        values.put("email", user.getsEmail());
        values.put("phone", user.getsPhone());
        values.put("password", user.getsPassword());
        values.put("verifyemail", user.getiVerifyEmail());
        values.put("state", user.getiState());
        values.put("role", user.getiRole());
        values.put("source", user.getsSource());
        values.put("address",user.getsAddress());
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    //endregion

    //region User
    public List<User> getListUser(){
        List<User> userList = new ArrayList<User>();
        String query = "SELECT id,name,email,phone,role,state,source,address FROM " + TABLE_USER ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setiID(cursor.getInt(0));
                user.setsName(cursor.getString(1));
                user.setsEmail(cursor.getString(2));
                user.setsPhone(cursor.getString(3));
                user.setiRole(cursor.getInt(4));
                user.setbState(cursor.getInt(5));
                user.setsSource(cursor.getBlob(6));
                user.setsAddress(cursor.getString(7));
                // Adding contact to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    int getIDUser(String emailInput){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id FROM " + TABLE_USER + " WHERE email = '" + emailInput + "'";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        int iduser = Integer.parseInt(cursor.getString(0));
        cursor.close();
        return iduser;
    }

    public User getUser(int id){
        String query = "SELECT id,name,email,phone,role,state,source,address FROM " + TABLE_USER + " WHERE id = ? ";
        User user = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{id + ""});
        if (cursor.moveToFirst()) {
            user.setiID(cursor.getInt(0));
            user.setsName(cursor.getString(1));
            user.setsEmail(cursor.getString(2));
            user.setsPhone(cursor.getString(3));
            user.setiRole(cursor.getInt(4));
            user.setbState(cursor.getInt(5));
            user.setsSource(cursor.getBlob(6));
            user.setsAddress(cursor.getString(7));
        }
        cursor.close();
        return user;
    }
    //endregion

    //region Login
    public boolean checkLogin(String userEmail, String passWord){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select email,password from " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);
        String email;
        String password;

        if (cursor.moveToFirst()) {
            do {
                email = cursor.getString(0);
                password = cursor.getString(1);
                if (email.equals(userEmail) && password.equals(passWord)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }
    //endregion

    //region Cart
    public void insertItemCart(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("idproduct", cart.getiIDProduct());
        values.put("iduser", cart.getiIDUser());
        values.put("quantity", cart.getiQuantity());

        db.insert(TABLE_CART, null, values);
        db.close();
    }

    public void undoItemCart(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id", cart.getiID());
        values.put("idproduct", cart.getiIDProduct());
        values.put("iduser", cart.getiIDUser());
        values.put("quantity", cart.getiQuantity());

        db.insert(TABLE_CART, null, values);
        db.close();
    }

    public int updateQuantityCart(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("idproduct", cart.getiIDProduct());
        values.put("iduser", cart.getiIDUser());
        values.put("quantity", cart.getiQuantity());

        return db.update(TABLE_CART, values, "id = ?", new String[]{String.valueOf(cart.getiID())});
    }

    public void deleteItemCart(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CART, "id = ? ", new String[]{String.valueOf(cart.getiID())});
        db.close();
    }

    public List<Cart> getListCartOfUser(int iduser){
        List<Cart> cartList = new ArrayList<Cart>();
        String query = "SELECT * FROM " + TABLE_CART + " WHERE iduser = " + String.valueOf(iduser);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setiID(cursor.getInt(0));
                cart.setiIDProduct(cursor.getInt(1));
                cart.setiIDUser(iduser);
                cart.setiQuantity(cursor.getInt(3));
                cart.setiChecked(cursor.getInt(4));
                // Adding contact to list
                cartList.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cartList;
    }

    public List<Cart> getListCartOfUserChecked(int iduser){
        List<Cart> cartList = new ArrayList<Cart>();
        String query = "SELECT * FROM " + TABLE_CART + " WHERE iduser = " + String.valueOf(iduser) + " AND checked = 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setiID(cursor.getInt(0));
                cart.setiIDProduct(cursor.getInt(1));
                cart.setiIDUser(iduser);
                cart.setiQuantity(cursor.getInt(3));
                cart.setiChecked(cursor.getInt(4));
                // Adding contact to list
                cartList.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cartList;
    }

    public int hasProductInCart(int idproduct, int iduser){
        List<Cart> cartList = new ArrayList<Cart>();
        String query = "SELECT id FROM " + TABLE_CART + " WHERE iduser = ? AND idproduct = ? " ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{iduser + "", idproduct + ""});
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }

    Cart getCart(int id){
        String query = "SELECT * FROM " + TABLE_CART + " WHERE id = ? ";
        Cart cart = new Cart();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{id + ""});
        if (cursor.moveToFirst()) {
            cart.setiID(cursor.getInt(0));
            cart.setiIDProduct(cursor.getInt(1));
            cart.setiIDUser(cursor.getInt(2));
            cart.setiQuantity(cursor.getInt(3));
            cart.setiChecked(cursor.getInt(4));
        }
        cursor.close();
        return cart;
    }

    public int checkedItemCart(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("checked", 1);
        return db.update(TABLE_CART, values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int checkedAllItemCart(int iduser){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("checked", 1);
        return db.update(TABLE_CART, values, "iduser = ?", new String[]{String.valueOf(iduser)});
    }

    public int unCheckedItemCart(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("checked", 0);
        return db.update(TABLE_CART, values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int unCheckedAllItemCart(int iduser){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("checked", 0);
        return db.update(TABLE_CART, values, "iduser = ?", new String[]{String.valueOf(iduser)});
    }

    public int itemCheckedSize(int iduser){
        String query = "SELECT count(id) FROM " + TABLE_CART + " WHERE iduser = ? AND checked = 1 " ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{iduser + ""});
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }

    public long totalPriceCheckedInCart(int iduser){
        String query = "SELECT sum(product.price * cart.quantity) FROM " + TABLE_CART + " INNER JOIN " + TABLE_PRODUCT + " WHERE iduser = ? AND cart.checked = 1 AND cart.idproduct = product.id" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{iduser + ""});
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }

    public int totalQuantityCheckedInCart(int iduser){
        String query = "SELECT sum(cart.quantity) FROM " + TABLE_CART + " WHERE iduser = ? AND cart.checked = 1" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{iduser + ""});
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
    //endregion

    //region BILL
    public int insertBill(Bill bill){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("iduser", bill.getiIDUser());
        values.put("quantity", bill.getiQuantity());
        values.put("personname", bill.getsPersonName());
        values.put("phone", bill.getsPhone());
        values.put("address", bill.getsAddress());
        values.put("date", bill.getsDate());
        values.put("state", bill.getiState());
        db.insert(TABLE_BILL, null, values);
        db.close();
        db = this.getReadableDatabase();
        String query = "SELECT MAX(id)  FROM " + TABLE_BILL ;
        Cursor cursor = db.rawQuery(query,null);
        int idbill = 0;
        if(cursor.moveToFirst()){
             idbill = cursor.getInt(0);
        }
        return idbill;
    }

    public int updateBill(Bill bill){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("iduser", bill.getiIDUser());
        values.put("quantity", bill.getiQuantity());
        values.put("personname", bill.getsPersonName());
        values.put("phone", bill.getsPhone());
        values.put("address", bill.getsAddress());
        values.put("date", bill.getsDate());
        values.put("state", bill.getiState());
        return db.update(TABLE_BILL, values, "id = ?", new String[]{String.valueOf(bill.getiID())});
    }
    //endregion

    //region BILL DETAIL
    public void insertBillDetail(BillDetail bill){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("idbill", bill.getiIDBill());
        values.put("idproduct", bill.getiIDProduct());
        values.put("quantity", bill.getiQuantity());
        values.put("unitprice", bill.getlUnitPrice());
        db.insert(TABLE_BILLDETAIL, null, values);
        db.close();
    }
    //endregion
}
