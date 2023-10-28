package com.example.myapplication0920;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add24ButtonsForMePlease();
    }

    private void add24ButtonsForMePlease() {
        LinearLayout[] linearLayouts = add6LinearLayouts();
        addLayoutsToMain(linearLayouts);
        add4Buttons(linearLayouts[0], "%", "CE", "C", "BkSp");
        add4Buttons(linearLayouts[1], "1/x", "^2", "sqrt", "./.");
        add4Buttons(linearLayouts[2], "7", "8", "9", "*");
        add4Buttons(linearLayouts[3], "4", "5", "6", "-");
        add4Buttons(linearLayouts[4], "1", "2", "3", "+");
        add4Buttons(linearLayouts[5], "+/-", "0", ".", "=");
    }

    private void addLayoutsToMain(LinearLayout[] linearLayouts) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutMain);
        for (int i=0; i<linearLayouts.length; i++)
            linearLayout.addView(linearLayouts[i]);
    }

    private void add4Buttons(LinearLayout linearLayout, String s0, String s1, String s2, String s3) {
        linearLayout.addView(createAButton(s0));
        linearLayout.addView(createAButton(s1));
        linearLayout.addView(createAButton(s2));
        linearLayout.addView(createAButton(s3));
    }

    private Button createAButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setId(genNextId());
        return button;
    }

    private int nextID = 65000;
    private int genNextId() {
        return nextID++;
    }

    private LinearLayout[] add6LinearLayouts() {
        LinearLayout[] linearLayouts = new LinearLayout[6];
        for (int i=0; i<6; ++i)
        {
            linearLayouts[i] = new LinearLayout(this);
            linearLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
        }
        return linearLayouts;
    }
}