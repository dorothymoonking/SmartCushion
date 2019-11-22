package com.example.sns_project.activity;

//    <uses-permission android:name="android.permission.VIBRATE" />
//메니페스트에 추가

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sns_project.R;

public class AppdoFragment extends AppCompatActivity {
    LinearLayout Lay[] = new LinearLayout[64];
    Button btn1;
    EditText edt[] = new EditText[8];
    int value[] = new int[64];

    //알고리즘
    int RData = 0;
    int LData = 0;
    int pushData = 0;

    //진동
    private Vibrator vibrator;

    RadioGroup radioGroup;
    RadioButton r,c;

    //시간
    long mNow;
    Date mDate;
    int timeValueS=0;
    int timeValueM=0;
    int timeValueH=0;
    int allData = 0;
    boolean timeOnOff = false;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    TextView UserData1;

    Integer IntData[] = new Integer[64];
    //블루투스
    Button mBtnBluetoothOn;
    Button mBtnBluetoothOff;
    Button mBtnConnect;

    TextView tvToday,tvTime,tvData,UserData;

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //블루투스

    int[] ids = {
            R.id.Lay1, R.id.Lay2, R.id.Lay3, R.id.Lay4, R.id.Lay5, R.id.Lay6, R.id.Lay7, R.id.Lay8,
            R.id.Lay9, R.id.Lay10, R.id.Lay11, R.id.Lay12, R.id.Lay13, R.id.Lay14, R.id.Lay15, R.id.Lay16,
            R.id.Lay17, R.id.Lay18, R.id.Lay19, R.id.Lay20, R.id.Lay21, R.id.Lay22, R.id.Lay23, R.id.Lay24,
            R.id.Lay25, R.id.Lay26, R.id.Lay27, R.id.Lay28, R.id.Lay29, R.id.Lay30, R.id.Lay31, R.id.Lay32,
            R.id.Lay33, R.id.Lay34, R.id.Lay35, R.id.Lay36, R.id.Lay37, R.id.Lay38, R.id.Lay39, R.id.Lay40,
            R.id.Lay41, R.id.Lay42, R.id.Lay43, R.id.Lay44, R.id.Lay45, R.id.Lay46, R.id.Lay47, R.id.Lay48,
            R.id.Lay49, R.id.Lay50, R.id.Lay51, R.id.Lay52, R.id.Lay53, R.id.Lay54, R.id.Lay55, R.id.Lay56,
            R.id.Lay57, R.id.Lay58, R.id.Lay59, R.id.Lay60, R.id.Lay61, R.id.Lay62, R.id.Lay63, R.id.Lay64,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appdo);
        setTitle("방구석매니아");

        //원 네모 선택
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        c = (RadioButton) findViewById(R.id.circle);
        r = (RadioButton) findViewById(R.id.rectangle);

        UserData1 = (TextView) findViewById(R.id.UserData1);


        Button b = (Button)findViewById(R.id.btn1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        for(int i = 0; i<Lay.length; i++)
//        {
//            Lay[i] = (LinearLayout) findViewById(ids[i]);
//        }
        int z1 = 7;
        int z2 = 15;
        int z3 = 23;
        int z4 = 31;
        int z5 = 39;
        int z6 = 47;
        int z7 = 55;
        int z8  = 63;
        //레이아웃 0~7
        for(int i = 0; i<8; i++)
        {
            Lay[i] = (LinearLayout) findViewById(ids[z1]);
            z1--;
        }

        //레이아웃 8~15
        for(int i=8; i<16;i++)
        {
            Lay[i] = (LinearLayout) findViewById(ids[z2]);
            z2--;
        }

        //레이아웃 16~23
        for(int i=16; i<24;i++)
        {
            Lay[i] = (LinearLayout) findViewById(ids[z3]);
            z3--;
        }

        //레이아웃 24~31
        for(int i=24; i<32;i++)
        {
            Lay[i] = (LinearLayout) findViewById(ids[z4]);
            z4--;
        }

        //레이아웃 32~39
        for(int i=32; i<40;i++)
        {
            Lay[i] = (LinearLayout) findViewById(ids[z5]);
            z5--;
        }

        //레이아웃 40~47
        for(int i=40; i<48;i++)
        {
            Lay[i] = (LinearLayout) findViewById(ids[z6]);
            z6--;
        }

        //레이아웃 48~55
        for(int i=48; i<56;i++)
        {
            Lay[i] = (LinearLayout) findViewById(ids[z7]);
            z7--;
        }

        //레이아웃 56~63
        for(int i=56; i<64;i++)
        {
            Lay[i] = (LinearLayout) findViewById(ids[z8]);
            z8--;
        }

        //진동
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);


        mBtnBluetoothOn = (Button)findViewById(R.id.btnBluetoothOn);
        mBtnBluetoothOff = (Button)findViewById(R.id.btnBluetoothOff);
        mBtnConnect = (Button)findViewById(R.id.btnConnect);

        tvToday = (TextView) findViewById(R.id.tvToday);
        tvToday.setText(getToDay());

        //tvData = (TextView) findViewById(R.id.tvData1);

        tvTime = (TextView) findViewById(R.id.tvTime);

        UserData = (TextView) findViewById(R.id.UserData);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.circle:
                        for(int ii = 0; ii<64; ii++)
                        {
                            Lay[ii].setBackgroundResource(R.drawable.shape2);
                        }
                        break;

                    case R.id.rectangle:
                        for(int ii = 0; ii<64; ii++)
                        {
                            Lay[ii].setBackgroundResource(R.drawable.shape);
                        }
                        break;
                }
            }
        });

        //블루투스부분
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        mBtnBluetoothOn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothOn();
            }
        });
        mBtnBluetoothOff.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothOff();
            }
        });
        mBtnConnect.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPairedDevices();

            }
        });

        mBluetoothHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == BT_MESSAGE_READ){
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    //mTvReceiveData.setText(readMessage);
                    //String result = readMessage.substring(readMessage.lastIndexOf(",")+1);
                    //데이터 처리해야됨 여기서
                    String AduinoData[] = readMessage.split(",");

                    for(int i=0; i<64; i++)
                    {
                        IntData[i] = Integer.parseInt(AduinoData[i]);
                    }

                    for(int i = 0; i<64; i++) {
                        allData = IntData[i] + allData;
                        //tvData.setText(Integer.toString(allData));
                        if (IntData[i] == 0) {
                            GradientDrawable bgShape = (GradientDrawable) Lay[i].getBackground();
                            bgShape.setColor(Color.rgb(255, 255, 255));
                        } else if (IntData[i] > 0 && IntData[i] <= 50) {
                            GradientDrawable bgShape = (GradientDrawable) Lay[i].getBackground();
                            bgShape.setColor(Color.rgb(196,196,255));
                        } else if (IntData[i] > 50 && IntData[i] <= 100) {
                            GradientDrawable bgShape = (GradientDrawable) Lay[i].getBackground();
                            bgShape.setColor(Color.rgb(140,140,255));
                        } else if (IntData[i] > 100 && IntData[i] <= 150) {
                            GradientDrawable bgShape = (GradientDrawable) Lay[i].getBackground();
                            bgShape.setColor(Color.rgb(81,81,255));
                        } else if (IntData[i] > 150 && IntData[i] <= 200) {
                            GradientDrawable bgShape = (GradientDrawable) Lay[i].getBackground();
                            bgShape.setColor(Color.rgb(2,2,255));
                        } else if (IntData[i] > 200 && IntData[i] <= 250) {
                            GradientDrawable bgShape = (GradientDrawable) Lay[i].getBackground();
                            bgShape.setColor(Color.rgb(255, 230, 233));
                        } else if (IntData[i] > 250 && IntData[i] <= 300) {
                            GradientDrawable bgShape = (GradientDrawable) Lay[i].getBackground();
                            bgShape.setColor(Color.rgb(255, 170, 181));
                        } else if (IntData[i] > 300 && IntData[i] <= 350) {
                            GradientDrawable bgShape = (GradientDrawable) Lay[i].getBackground();
                            bgShape.setColor(Color.rgb(255, 134, 149));
                        } else if (IntData[i] > 350) {
                            GradientDrawable bgShape = (GradientDrawable) Lay[i].getBackground();
                            bgShape.setColor(Color.rgb(255, 0, 0));
                        }
                    }//for문

                    //데이터 수집 알고리즘
                    for(int i = 0; i<64; i++)
                    {
                        //왼쪽데이터 수집
                        if(IntData[i]>=0 && IntData[i]<4 &&
                                IntData[i] >=8 && IntData[i] < 12 &&
                                IntData[i] >=16 && IntData[i] < 20 &&
                                IntData[i] >=24 && IntData[i] < 28 &&
                                IntData[i] >=32 && IntData[i] < 26 &&
                                IntData[i] >=40 && IntData[i] < 44 &&
                                IntData[i] >=48 && IntData[i] < 52 &&
                                IntData[i] >=56 && IntData[i] < 60)
                        {
                            LData = LData + IntData[i];
                        }

                        //오른쪽 데이터 수집
                        else if(IntData[i]>=4 && IntData[i]<8 &&
                                IntData[i] >=12 && IntData[i] < 16 &&
                                IntData[i] >=20 && IntData[i] < 24 &&
                                IntData[i] >=28 && IntData[i] < 32 &&
                                IntData[i] >=26 && IntData[i] < 40 &&
                                IntData[i] >=44 && IntData[i] < 48 &&
                                IntData[i] >=52 && IntData[i] < 56 &&
                                IntData[i] >=60)
                        {
                            RData = RData + IntData[i];
                        }

                    }//입력값 알고리즘 포문

                    //비교알고리즘
                    pushData = LData - RData;
                    if(pushData <= 300 && pushData <=-300)
                    {
                        UserData.setText("바른자세");
                    }
                    else if(pushData >300)
                    {
                        UserData.setText("오른쪽으로 치우쳐 있습니다.");
                    }
                    else if(pushData > -300)
                    {
                        UserData.setText("왼쪽으로 치우쳐 있습니다.");
                   }

                    //앉은시간 알고리즘
                    if (allData > 100)
                    {
                        timeValueS++;
                        if (timeValueS == 60) {
                            timeValueS = 0;
                            timeValueM++;
                            tvTime.setText(Integer.toString(timeValueH) + "시" + Integer.toString(timeValueM) + "분" + Integer.toString(timeValueS) + "초");
                            if (timeValueM == 60) {
                                timeValueS = 0;
                                timeValueM = 0;
                                timeValueH++;
                                tvTime.setText(Integer.toString(timeValueH) + "시" + Integer.toString(timeValueM) + "분" + Integer.toString(timeValueS) + "초");
                            }
                        }
                        tvTime.setText(Integer.toString(timeValueH) + "시" + Integer.toString(timeValueM) + "분" + Integer.toString(timeValueS) + "초");
                        allData = 0;
                    }


                }
            }
        };

    }
    void bluetoothOn() {
        if(mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
        }
        else {
            if (mBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG).show();
                //mTvBluetoothStatus.setText("활성화");
            }
            else {
                Toast.makeText(getApplicationContext(), "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
            }
        }
    }
    void bluetoothOff() {
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show();
            //mTvBluetoothStatus.setText("비활성화");
        }
        else {
            Toast.makeText(getApplicationContext(), "블루투스가 이미 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BT_REQUEST_ENABLE:
                if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
                    Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();
                    //mTvBluetoothStatus.setText("활성화");
                } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                    Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                    //mTvBluetoothStatus.setText("비활성화");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    void listPairedDevices() {
        if (mBluetoothAdapter.isEnabled()) {
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<String>();
                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(items[item].toString());
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    void connectSelectedDevice(String selectedDeviceName) {
        for(BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice = tempDevice;
                break;
            }
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
    }

    private class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            String abc;

            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(1000);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                        //abc = Integer.toString(bytes);
                        //edt1.setText(abc);
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }
        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }


    }

    //날짜함수
    private String getToDay(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    //타임함수
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            timeValueS++;
            if (timeValueS == 60) {
                timeValueS = 0;
                timeValueM++;
                tvTime.setText(Integer.toString(timeValueH) + "시" + Integer.toString(timeValueM) + "분" + Integer.toString(timeValueS) + "초");
                if (timeValueM == 60) {
                    timeValueS = 0;
                    timeValueM = 0;
                    timeValueH++;
                    tvTime.setText(Integer.toString(timeValueH) + "시" + Integer.toString(timeValueM) + "분" + Integer.toString(timeValueS) + "초");
                }
            }
            tvTime.setText(Integer.toString(timeValueH) + "시" + Integer.toString(timeValueM) + "분" + Integer.toString(timeValueS) + "초");
            // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };

}