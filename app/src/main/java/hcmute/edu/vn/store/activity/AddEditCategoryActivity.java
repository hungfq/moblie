package hcmute.edu.vn.store.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hcmute.edu.vn.store.R;
import hcmute.edu.vn.store.bean.CategoryProduct;
import hcmute.edu.vn.store.db.DatabaseHandler;

public class AddEditCategoryActivity extends AppCompatActivity {

    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private EditText textTitle;
    private EditText textContent;
    private Button buttonSave;
    private Button buttonCancel;

    private CategoryProduct categoryProduct;
    private boolean needRefresh;
    private int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_category);

        this.textTitle = (EditText)this.findViewById(R.id.editText_category_title);
        this.textContent = (EditText)this.findViewById(R.id.editText_category_content);

        this.buttonSave = (Button)findViewById(R.id.button_save);
        this.buttonCancel = (Button)findViewById(R.id.button_cancel);
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
        this.categoryProduct = (CategoryProduct) intent.getSerializableExtra("note");
        if(categoryProduct== null)  {
            this.mode = MODE_CREATE;
        } else  {
            this.mode = MODE_EDIT;
            this.textTitle.setText(categoryProduct.getiID());
            this.textContent.setText(categoryProduct.getsName());
        }

    }

    // User Click on the Save button.
    public void buttonSaveClicked()  {
        DatabaseHandler db = new DatabaseHandler(this);

        String title = this.textTitle.getText().toString();
        String content = this.textContent.getText().toString();

        if(title.equals("") || content.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter title & content", Toast.LENGTH_LONG).show();
            return;
        }

        if(mode == MODE_CREATE ) {
            this.categoryProduct = new CategoryProduct(content);
            db.insertCategoryProduct(categoryProduct);
        } else  {
            this.categoryProduct.setsName(content);
            db.updateCategoryProduct(categoryProduct);
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