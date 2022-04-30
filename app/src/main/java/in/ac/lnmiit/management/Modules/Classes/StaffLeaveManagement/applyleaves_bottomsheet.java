package in.ac.lnmiit.management.Modules.Classes.StaffLeaveManagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import in.ac.lnmiit.management.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link applyleaves_bottomsheet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class applyleaves_bottomsheet extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextInputLayout til_leaves;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> arrayList_leaves;
    ArrayAdapter<String> stringArrayAdapter_leaves;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public applyleaves_bottomsheet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment applyleaves_bottomsheet.
     */
    // TODO: Rename and change types and number of parameters
    public static applyleaves_bottomsheet newInstance(String param1, String param2) {
        applyleaves_bottomsheet fragment = new applyleaves_bottomsheet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_applyleaves_bottomsheet, container, false);
    }
}