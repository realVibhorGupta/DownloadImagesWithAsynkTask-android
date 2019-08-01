package com.example.vibhor.downloadimageswithasyntask;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class NonUIFragment extends Fragment {

//Activity objects on Attach() has been deprecated


     MyTask myTask;
     Context context;






    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NonUIFragment() {
        // Required empty public constructor
    }
    public void beginTask(String... arguments)
    {
        myTask =new MyTask(context);
        myTask.execute(arguments);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        L.m("Fragments detach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
       if(myTask != null)
       {
           myTask.onAttach(context);
       }else
       {

       }
        L.m("Fragments attach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.m("Activity created");
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.m("Fragments Destroyed");

    }

    @Override
    public void onPause() {
        super.onPause();
        L.m("Paused");

    }

    @Override
    public void onResume() {
        super.onResume();
        L.m("Fragemnts Resumed");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.m("Fragemnts Stopped");
    }

    @Override
    public void onStart() {
        super.onStart();
        L.m("Fragents Started");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.m("Fragments detach");


        if(myTask != null)
        {
            myTask.onDetach();
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
