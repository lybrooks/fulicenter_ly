package myFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import day.myfulishe.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_personal.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_personal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_personal extends Fragment {





    public Fragment_personal() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }




}
