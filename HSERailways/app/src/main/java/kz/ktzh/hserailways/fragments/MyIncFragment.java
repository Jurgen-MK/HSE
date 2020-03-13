package kz.ktzh.hserailways.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kz.ktzh.hserailways.R;
import kz.ktzh.hserailways.adapters.IncAdapter;
import kz.ktzh.hserailways.databinding.FragmentMyIncBinding;
import kz.ktzh.hserailways.entity.Incidents;
import kz.ktzh.hserailways.network.NetworkServiceResource;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyIncFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyIncFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyIncFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String accessToken;
    List<Incidents> glistInc;
    private FragmentMyIncBinding binding;
    int index = 0;

    private OnFragmentInteractionListener mListener;

    public MyIncFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyIncFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyIncFragment newInstance(String param1, String param2) {
        MyIncFragment fragment = new MyIncFragment();
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
        accessToken = NetworkServiceResource.getInstance().getAccessToken();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.i("huitag", "token" + accessToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_my_inc);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_inc, container, false);
        setupRecyclerView();
        return binding.getRoot();
    }




    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.rvIncList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        glistInc = new ArrayList<Incidents>();
        try {
            glistInc = NetworkServiceResource.getInstance().getJSONApi().getIncidentsByUserId("Bearer " + accessToken, NetworkServiceResource.getInstance().getUserInfo().getId()).execute().body();
            for (Incidents incs: glistInc) {
                incs.imageUrl = NetworkServiceResource.getInstance().getBaseUrl()+"/incidents/image/"+incs.getContent_path();

                //incs.imageUrl = incs.getContent_path();
                Log.i("tag", "imgurl - "+ incs.imageUrl);
            }
            Log.i("huitag", "Size - " + glistInc.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        IncAdapter adapter = new IncAdapter(glistInc);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.devider));
        recyclerView.addItemDecoration(itemDecorator);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
