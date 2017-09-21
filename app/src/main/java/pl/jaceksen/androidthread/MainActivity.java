package pl.jaceksen.androidthread;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sb1 = (SeekBar) findViewById(R.id.seekBar);
        tvCounter = (TextView) findViewById(R.id.textView);
        tvWatek = (TextView) findViewById(R.id.tvWatek);
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sb1.setProgress(counterUp);
                            tvCounter.setText("licznik: " + counterUp);
                        }
                    });

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

}
