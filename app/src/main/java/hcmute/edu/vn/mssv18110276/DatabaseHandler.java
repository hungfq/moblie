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

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORYPRODUCT_TABLE = "CREATE TABLE " + TABLE_CATEGORYPRODUCT + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name VARCHAR(255) NOT NULL UNIQUE,"
                + "source BLOB(255)"
                + ")";
        db.execSQL(CREATE_CATEGORYPRODUCT_TABLE);

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT
                + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name VARCHAR(255) NOT NULL UNIQUE,"
                + "category int NOT NULL,"
                + "price DECIMAL DEFAULT(0) NOT NULL,"
                + "description VARCHAR(255),"
                + "source BLOB,"
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
                + "name VARCHAR(255) NOT NULL UNIQUE,"
                + "email VARCHAR(255),"
                + "phone VARCHAR(255),"
                + "password VARCHAR(255),"
                + "verifyemail INTEGER,"
                + "state INTEGER,"
                + "role INTEGER,"
                + "CONSTRAINT fk_user_idrole FOREIGN KEY(role) REFERENCES role(id)"
                +")";
        db.execSQL(CREATE_USER_TABLE);
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

        onCreate(db);
    }

    // ROLE
    public void insertRole(Role role){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", role.getsName());
        db.insert(TABLE_ROLE, null, values);
        db.close();
    }

   // CATEGORY PRODUCT
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

    // PRODUCT
    public void insertProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", product.getsName());
        values.put("category", product.getiIDCategory());
        values.put("price", product.getlPrice());
        values.put("description", product.getsDescription());
        values.put("source", product.getsSource());

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

        return db.update(TABLE_PRODUCT, values, "id = ?", new String[]{String.valueOf(product.getiID())});
    }

    public void deleteProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PRODUCT, "id = ? ", new String[]{String.valueOf(product.getiID())});
        db.close();
    }

    Product getProduct(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCT + " WHERE id = " + String.valueOf(id);

        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Product product = new Product(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), Long.parseLong(cursor.getString(3)), cursor.getString(4), cursor.getBlob(5));
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
                product.setiID(Integer.parseInt(cursor.getString(0)));
                product.setsName(cursor.getString(1));
                product.setiIDCategory(Integer.parseInt(cursor.getString(2)));
                product.setlPrice(Long.parseLong(cursor.getString(3)));
                product.setsDescription(cursor.getString(4));
                product.setsSource(cursor.getBlob(5));
                // Adding contact to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    public List<Product> getListProductByCategory(int idcategory){
        List<Product> productList = new ArrayList<Product>();
        String query = "SELECT * FROM " + TABLE_PRODUCT + " WHERE category = " + String.valueOf(idcategory);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setiID(Integer.parseInt(cursor.getString(0)));
                product.setsName(cursor.getString(1));
                product.setiIDCategory(idcategory);
                product.setlPrice(Long.parseLong(cursor.getString(3)));
                product.setsDescription(cursor.getString(4));
                product.setsSource(cursor.getBlob(5));
                // Adding contact to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }
}
