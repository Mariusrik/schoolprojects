package com.example.mariu.appliksjon1.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.mariu.appliksjon1.R;

/**
 *
 */
public class MenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        //Remove all views to prevent fragments from stacking in frame
        FrameLayout fl = (FrameLayout) getActivity().findViewById(R.id.main);
        fl.removeAllViews();
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_menu, null);

        return root;
    }

}