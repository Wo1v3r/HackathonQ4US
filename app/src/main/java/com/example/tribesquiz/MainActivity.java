package com.example.tribesquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.atomation.atomationsdk.api.IMultiSenseAtom;
import net.atomation.atomationsdk.api.IOnButtonPressedListener;
import net.atomation.atomationsdk.api.IOnLightSavedListener;
import net.atomation.atomationsdk.api.IOnTemperatureSavedListener;
import net.atomation.atomationsdk.ble.MultiSenseAtomManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String MAC1 = "48:1A:84:00:19:B1";
    private static final String MAC2 = "48:1A:84:00:1B:08";

    private MultiSenseAtomManager manager1;
    private MultiSenseAtomManager manager2;

    private IMultiSenseAtom atom1;
    private IMultiSenseAtom atom2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        manager1 = MultiSenseAtomManager.getInstance(this);
//        manager2 = MultiSenseAtomManager.getInstance(this);
//
//        atom1 = manager1.getMultiSenseAtom(MAC1);
//        if (atom1 == null) {
//            atom1 = manager1.createMultiSenseDevice(MAC1);
//        }
//        atom2 = manager2.getMultiSenseAtom(MAC2);
//        if (atom2 == null) {
//            atom2 = manager2.createMultiSenseDevice(MAC2);
//        }
//
//
//        initAtom(atom1);
//        initAtom(atom2);


    }


    public void startGame(View view){
        Intent intent = new Intent(this,QuizActivity.class);
        startActivity(intent);
    }

//    private void lightUp(IMultiSenseAtom atom){
//
//    }
//    private void initAtom(final IMultiSenseAtom atom) {
//
//        atom.enableTemperatureSensor(new IOnTemperatureSavedListener() {
//            @Override
//            public void onSave(boolean enable) {
//                Log.d(TAG, "Temp enabled");
//            }
//
//            @Override
//            public void onError(int errorCode) {
//
//            }
//        });
//
//
//
//        atom.setButtonPressedListener(new IOnButtonPressedListener() {
//            @Override
//            public void onPress(int reason) {
//                if (atom == atom1)
//                    Log.d(TAG,"Atom 1 Pressed");
//                else
//                    Log.d(TAG,"Atom 2 pressed");
//
//
//            }
//
//    });
//        atom.activate();
//    }
}
