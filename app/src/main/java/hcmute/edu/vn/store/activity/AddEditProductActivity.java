package hcmute.edu.vn.store.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.store.R;
import hcmute.edu.vn.store.bean.CategoryProduct;
import hcmute.edu.vn.store.bean.Product;
import hcmute.edu.vn.store.db.DatabaseHandler;

public class AddEditProductActivity extends AppCompatActivity {

    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private TextView textId;
    private EditText textName;
    private EditText textPrice;
    private EditText textDescription;
    private EditText textQuantity;
    private Button buttonSave;
    private Button buttonCancel;

    private Product product;
    private boolean needRefresh;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);


        this.textId = (TextView) this.findViewById(R.id.txt_product_id);
        this.textName = (EditText) this.findViewById(R.id.txt_product_name);
        this.textPrice = (EditText)this.findViewById(R.id.txt_product_price);
        this.textDescription = (EditText) this.findViewById(R.id.txt_product_description);
        this.textQuantity= (EditText)this.findViewById(R.id.txt_product_quantity);

        this.buttonSave = (Button)findViewById(R.id.btn_product_save);
        this.buttonCancel = (Button)findViewById(R.id.btn_product_cancel);


        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonSaveClicked();
            }
        });

        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonCancelClicked();
            }
        });

        Intent intent = this.getIntent();
        this.product = (Product) intent.getSerializableExtra("product");
        if(product== null)  {
            this.mode = MODE_CREATE;
        } else  {
            this.mode = MODE_EDIT;
            System.out.println(product.getiID());
            this.textId.setText(String.valueOf(product.getiID()) );
            this.textName.setText(product.getsName());
            this.textPrice.setText(String.valueOf(product.getlPrice()));
            this.textQuantity.setText(String.valueOf(product.getiQuantity()));
            this.textDescription.setText(String.valueOf(product.getsDescription()));
        }

    }

    // User Click on the Save button.
    public void buttonSaveClicked()  {
        DatabaseHandler db = new DatabaseHandler(this);

        String id = this.textId.getText().toString();
        String name = this.textName.getText().toString();
        String price = this.textPrice.getText().toString();
        String textDescription = this.textDescription.getText().toString();
        String textQuantity = this.textQuantity.getText().toString();

        if(name.equals("")||price.equals("")||textQuantity.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter title & content", Toast.LENGTH_LONG).show();
            return;
        }

        if(mode == MODE_CREATE ) {
            this.product = new Product(name,Long.parseLong(price),textDescription,Integer.parseInt(textQuantity));
            db.insertProduct(product);
        } else  {
            System.out.println("data get in form");
            this.product.setsName(name);
            this.product.setlPrice(Long.parseLong(price));
            this.product.setsDescription(textDescription);
            this.product.setiQuantity(Integer.parseInt(textQuantity));
            db.updateProduct(product);
        }

        this.needRefresh = true;

        // Back to MainActivity.
        this.onBackPressed();
    }

    // User Click on the Cancel button.
    public void buttonCancelClicked()  {
        // Do nothing, back MainActivity.
        this.onBackPressed();
    }


    // When completed this Activity,
    // Send feedback to the Activity called it.
    @Override
    public void finish() {

        // Create Intent
        Intent data = new Intent();

        // Request MainActivity refresh its ListView (or not).
        data.putExtra("needRefresh", needRefresh);

        // Set Result
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}