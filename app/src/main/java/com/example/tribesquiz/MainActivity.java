package com.example.tribesquiz;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import net.atomation.atomationsdk.api.IMultiSenseAtom;
import net.atomation.atomationsdk.api.IOnButtonPressedListener;
import net.atomation.atomationsdk.api.IOnLightSavedListener;
import net.atomation.atomationsdk.api.IOnTemperatureSavedListener;
import net.atomation.atomationsdk.ble.MultiSenseAtomManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.startButton) Button startButton;
    public void startGame(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

//    private static final String MAC1 = "48:1A:84:00:19:B1";
//    private static final String MAC2 = "48:1A:84:00:1B:08";
//    private MultiSenseAtomManager manager1;
//    private MultiSenseAtomManager manager2;
//    private IMultiSenseAtom atom1;
//    private IMultiSenseAtom atom2;


    private boolean user1Logged = false;
    private boolean user2Logged = false;

    Handler h;

    final int RECIEVE_MESSAGE = 1;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket1 = null;
    private BluetoothSocket btSocket2 = null;
    private StringBuilder sb = new StringBuilder();

    private ConnectedThread mConnectedThread1;
    private ConnectedThread mConnectedThread2;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    private static String address1 = "00:06:66:43:45:7F";
    private static String address2 = "98:D3:31:20:B6:37";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Q4US");
        ButterKnife.bind(this);
        try {
            Intent intent = getIntent();
            if ( intent != null) {
                mConnectedThread1.write("disconnect\n");
                mConnectedThread2.write("disconnect\n");
            }
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:
                        // if receive massage
                        synchronized (sb) {
                            byte[] readBuf = (byte[]) msg.obj;
                            String strIncom = new String(readBuf, 0, msg.arg1);                 // create string from bytes array
                            sb.append(strIncom);                                                // append string
                            int endOfLineIndex = sb.indexOf("\r\n");                            // determine the end-of-line
                            if (endOfLineIndex > 0) {                                            // if end-of-line,
                                  String sbprint = sb.substring(0, endOfLineIndex);               // extract string
                                  sb.delete(0, sb.length());                                      // and clear
                                if(sbprint.matches("User1 Ready!!!")) {
                                    Toast.makeText(getBaseContext(), "User 1 Connected", Toast.LENGTH_SHORT).show();
                                    user1Logged = true;
                                }
                                else if (sbprint.matches("USER2")) {
                                    Toast.makeText(getBaseContext(), "User 2 Connected", Toast.LENGTH_SHORT).show();
                                    user2Logged = true;
                                }

                                if (user1Logged && user2Logged) startButton.setEnabled(true);
                            }
                            break;
                        }
                }
            }
        };
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();
        btAdapter.startDiscovery();

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
//
//
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "...onResume - try connect...");
        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device1 = btAdapter.getRemoteDevice(address1);
        BluetoothDevice device2 = btAdapter.getRemoteDevice(address2);
        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket1 = createBluetoothSocket(device1);
        } catch (IOException e1) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e1.getMessage() + ".");
        }

        try {
            btSocket2 = createBluetoothSocket(device2);
        } catch (IOException e1) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e1.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket1.connect();
            Log.d(TAG, "...Connection ok...");
        } catch (IOException e) {
            try {
                btSocket1.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }
        try {
            btSocket2.connect();
            Log.d(TAG, "...Connection ok...");
        } catch (IOException e) {
            try {
                btSocket2.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }
        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        mConnectedThread1 = new ConnectedThread(btSocket1);
        mConnectedThread1.start();

        mConnectedThread2 = new ConnectedThread(btSocket2);
        mConnectedThread2.start();
        synchronized (mConnectedThread1)  {

            mConnectedThread1.write("connect\n");
        }

        synchronized (mConnectedThread2){
            mConnectedThread2.write("connect\n");
        }
//        new CountDownTimer(2200, 1000){
//            public void onTick(long millisUntilFinished){
//                ;
//            }
//            public  void onFinish(){
//
//                startButton.setEnabled(true);
//            }
//        }.start();

    }
    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "...In onPause()...");

        try     {
            btSocket1.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }

        try     {
            btSocket2.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }
    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if (btAdapter == null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
    private void errorExit(String title, String message) {
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        @Override
        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler
                } catch (IOException e) {
                    break;
                }
            }
        }
        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if (Build.VERSION.SDK_INT >= 10) {
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection", e);
            }
        }
        return device.createRfcommSocketToServiceRecord(MY_UUID);
    }
//
//    private void lightUp(IMultiSenseAtom atom) {
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
//    }


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
