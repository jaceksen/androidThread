package pl.jaceksen.androidthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar sb1;
    int maxCounter = 100;
    TextView tvCounter;
    TextView tvWatek;
    int liczbaWatkow = 0;
    MyHandler mh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sb1 = (SeekBar) findViewById(R.id.seekBar);
        tvCounter = (TextView) findViewById(R.id.textView);
        tvWatek = (TextView) findViewById(R.id.tvWatek);
        mh = new MyHandler();
        sb1.setMax(maxCounter);
    }

    boolean isRunning = false;
    int counterUp=0;

    public void buStart(View view) {

        isRunning = true;
        MyThread mt = new MyThread();
        liczbaWatkow++;
        tvWatek.setText("liczba wątków: " + liczbaWatkow);
        mt.start();

    }

    public void buStop(View view) {

        isRunning=false;
        liczbaWatkow=0;
        tvWatek.setText("liczba wątków: " + liczbaWatkow);

    }

    public void buReset(View view) {
        isRunning=false;
        counterUp=0;
        sb1.setProgress(0);
        tvCounter.setText("licznik: 0");
        liczbaWatkow=0;
        tvWatek.setText("liczba wątków: " + liczbaWatkow);
    }

    class MyThread extends Thread {

        @Override
        public void run(){

            while(isRunning){

                if(counterUp<maxCounter){

                    //dostaję sie do UI
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            sb1.setProgress(counterUp);
////                            tvCounter.setText("licznik: " + counterUp);
//                        }
//                    });

                    Message msg = mh.obtainMessage();
                    Bundle bu = new Bundle();
                    bu.putInt("counter",counterUp);
                    msg.setData(bu);
                    mh.sendMessage(msg);

                    counterUp++;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

    }

    class MyHandler extends Handler {
        public void handleMessage(Message msg) {
            int count = msg.getData().getInt("counter");
            sb1.setProgress(count);
            tvCounter.setText("licznik: " + count);
        }
    }

}
