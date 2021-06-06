package hcmute.edu.vn.mssv18110276;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    List<Product> lProduct;
    DatabaseHandler dbHandler;
    ListView lv_product;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        toolbar = (Toolbar)findViewById(R.id.toolbar_appbar);

        dbHandler = new DatabaseHandler(this);

        insertDefaultProduct();

        int idcategory = getIntent().getIntExtra("idcategory", 0);
        lProduct = dbHandler.getListProductByCategory(idcategory);
        if(lProduct.size() <= 0){

        }
        CategoryProduct ct;
        ct = dbHandler.getCategoryProduct(idcategory);
        toolbar.setTitle(ct.getsName());
        if(lProduct.size() != 0){
            ProductAdapter adapter = new ProductAdapter(this, lProduct);
            lv_product = (ListView)findViewById(R.id.lv_product);
            lv_product.setAdapter(adapter);
        }

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void insertDefaultProduct(){
        dbHandler.insertProduct(new Product("Tuna sandwich",1,22000,"It combine tuna, mayonnaise, celery, onion, relish, lemon juice, and garlic.",null));
        dbHandler.insertProduct(new Product("Shrimp sandwich",1,28000,"A loaded shrimp sandwich with a kick of heat and a double dose of avocado goodness!",null));
        dbHandler.insertProduct(new Product("Chicken sandwich",1,25000,"A delicious mix of mayonnaise, chicken, pepper and some veggies spread on the bread",null));
    }
}