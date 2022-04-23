package hcmute.edu.vn.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import hcmute.edu.vn.store.bean.CategoryProduct;
import hcmute.edu.vn.store.bean.Product;

public class ProductActivity extends AppCompatActivity {

    List<Product> lProduct;
    DatabaseHandler db;
    ListView lv_product;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        toolbar = (Toolbar)findViewById(R.id.toolbar_appbar);

        db = new DatabaseHandler(getApplicationContext());
        int idcategory = getIntent().getIntExtra("idcategory", 0);
        String iduser = getIntent().getStringExtra("iduser");
        lProduct = db.getListProductByCategory(idcategory);

        CategoryProduct ct;
        ct = db.getCategoryProduct(idcategory);
        toolbar.setTitle(ct.getsName());
        if(lProduct.size() != 0){
            ProductAdapter adapter = new ProductAdapter(this, lProduct);
            lv_product = (ListView)findViewById(R.id.lv_product);
            lv_product.setAdapter(adapter);


            lv_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product productdetail = (Product) lProduct.get(position);
                    Intent intentProduct = new Intent(getBaseContext(), ProductDetailActivity.class);
                    intentProduct.putExtra("idproduct",productdetail.getiID());
                    intentProduct.putExtra("iduser",iduser);
                    startActivity(intentProduct);
                }
            });
        }
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}