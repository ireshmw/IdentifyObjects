package com.mw.iresh.identifyobjects;

import android.view.View;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iresh on 3/17/17.
 */

public class PopUpObjects {

    ImageButton fatherBtn, motherBtn, daughterBtn, sonBtn;
    Map<String, ImageButton> ObjectMap;

    public PopUpObjects(View v) {

        fatherBtn = (ImageButton) v.findViewById(R.id.fatherBtn);
        sonBtn = (ImageButton) v.findViewById(R.id.sonBtn);
        daughterBtn = (ImageButton) v.findViewById(R.id.daughterBtn);
        motherBtn = (ImageButton) v.findViewById(R.id.motherBtn);

        ObjectMap = new HashMap<>();
    }

    public PopUpObjects(Map<String, ImageButton> objectMap, View v) {
        ObjectMap = objectMap;
    }
}
