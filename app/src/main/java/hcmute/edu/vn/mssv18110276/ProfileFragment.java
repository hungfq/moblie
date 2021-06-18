package hcmute.edu.vn.mssv18110276;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IDUSER = "iduser";
    private TextView tv_name;
    private ImageView iv_avatar;
    // TODO: Rename and change types of parameters
    private String mParamIDUser;
    private User user;
    private ListView lv_profile;
    private DatabaseHandler db;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        tv_name = view.findViewById(R.id.tv_nameuser);
        iv_avatar = view.findViewById(R.id.iv_avatar_user);
        db = new DatabaseHandler(getContext());
        user = db.getUser(Integer.parseInt(mParamIDUser));
        tv_name.setText(user.getsName());
        if(user.getsSource() == null){

        }
        else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getsSource(), 0, user.getsSource().length);
            iv_avatar.setImageBitmap(bitmap);
        }

        // Inflate the layout for this fragment
        String[] lItem = {"Edit Profile", "Change Password", "Order History", "Log Out"};
        lv_profile = (ListView)view.findViewById(R.id.lv_profile);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 ,lItem);
        lv_profile.setAdapter(adapter);
        return view;
    }
}