package hcmute.edu.vn.mssv18110276;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CategoryProductActivity extends AppCompatActivity {

    private List<CategoryProduct> lCategoryProducts;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        /*db = new DatabaseHandler(this);
        //show
        lCategoryProducts = db.getListCategoryProduct();

        RecyclerView rv_category = (RecyclerView)findViewById(R.id.item_category);
        *//*rv_category.setHasFixedSize(true);*//*
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_category.setLayoutManager(layoutManager);
        CategoryProductAdapter adapter = new CategoryProductAdapter(lCategoryProducts, this, );
        rv_category.setAdapter(adapter);*/
    }
/*
    private byte[] getByteArrayImage(String sURL) throws MalformedURLException {
        URL url = new URL(sURL);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }*/

}