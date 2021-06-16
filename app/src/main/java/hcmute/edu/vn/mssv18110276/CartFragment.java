package hcmute.edu.vn.mssv18110276;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, ItemCheckedListener  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IDUSER = "iduser";

    // TODO: Rename and change types of parameters
    private String mParamIDUser;
    private List<Cart> lCarts;
    DatabaseHandler db;
    private CartAdapter adapter;
    private FrameLayout frameLayout;
    private CheckBox cb_all;
    private TextView tv_totalprice;
    private Button btn_placeorder;
    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IDUSER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamIDUser = getArguments().getString(ARG_IDUSER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cb_all = view.findViewById(R.id.cb_all_item);
        tv_totalprice = view.findViewById(R.id.tv_totalprice);
        btn_placeorder = view.findViewById(R.id.btn_place_order);

        db = new DatabaseHandler(getContext());
        lCarts = db.getListCartOfUser(Integer.parseInt(mParamIDUser));

        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        frameLayout = view.findViewById(R.id.frame_container_cart);
        RecyclerView rv_cart = view.findViewById(R.id.item_cart);
        rv_cart.addItemDecoration(dividerHorizontal);
        GifImageView giv_empty = view.findViewById(R.id.empty_cart);
        if(lCarts.size() > 0){
            rv_cart.setVisibility(View.VISIBLE);
            giv_empty.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            rv_cart.setLayoutManager(layoutManager);
            adapter = new CartAdapter(lCarts, getContext(),this);
            adapter.notifyDataSetChanged();
            rv_cart.setAdapter(adapter);
            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv_cart);
        }
        else {
            rv_cart.setVisibility(View.GONE);
            giv_empty.setVisibility(View.VISIBLE);
        }

        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int total_price = 0;
                Locale locale = new Locale("vn","VN");
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                if(cb_all.isChecked()){
                    db.checkedAllItemCart(Integer.parseInt(mParamIDUser));
                    total_price = db.totalPriceCheckedInCart(Integer.parseInt(mParamIDUser));
                    adapter.selectAll();
                }
                else {
                    db.unCheckedAllItemCart(Integer.parseInt(mParamIDUser));
                    total_price = db.totalPriceCheckedInCart(Integer.parseInt(mParamIDUser));
                    adapter.unSelectAll();
                }
                tv_totalprice.setText(String.valueOf(currencyFormatter.format(total_price)));
            }
        });

        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lCarts = db.getListCartOfUserChecked(Integer.parseInt(mParamIDUser));

            }
        });

        return view;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            int id = lCarts.get(viewHolder.getAdapterPosition()).getiID();

            // backup of removed item for undo purpose
            final Cart deletedItem = lCarts.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());
            db.deleteItemCart(deletedItem);

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(frameLayout, " Removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onItemCheckedChange() {
        int total_checked = db.itemCheckedSize(Integer.parseInt(mParamIDUser));
        int total_price = 0;
        total_price = db.totalPriceCheckedInCart(Integer.parseInt(mParamIDUser));
        Locale locale = new Locale("vn","VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        if(total_checked == lCarts.size())
            cb_all.setChecked(true);
        else cb_all.setChecked(false);
        tv_totalprice.setText(String.valueOf(currencyFormatter.format(total_price)));
    }
}