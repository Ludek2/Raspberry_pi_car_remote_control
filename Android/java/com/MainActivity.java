package com.ludek;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;




public class MainActivity extends ActionBarActivity {

    NetCon netcon;
    int counter;
    int counter2;
    private String str;

    private float touchX;
    private float touchY;
    private float previousTouchX;
    private float previousTouchY;
    boolean clickedTimeWindow=false;
    boolean firstFingerMove=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        netcon= new NetCon( 80, "192.168.1.30");
        final TextView text = (TextView) findViewById(R.id.textView);
        setText2("Start");

        Button firstButton = (Button) findViewById(R.id.FirstButton);

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;

                if (counter % 2 == 1) {
                    text.setText("You Clicked1");
                } else {
                    text.setText("You Clicked2");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class NetCon implements Runnable {

        private Socket socket;
        //private OutputStream out;
        //private PrintWriter pout;
        PrintWriter out;

        private int serverPort;
        private String serverIP;
        InetAddress serverAddr;



        public NetCon(int serverPort, String serverIP){
            this.serverPort=serverPort;
            try {
                this.serverAddr=InetAddress.getByName(serverIP);  //make sure that it is working with domain name!!!!!!!!
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            new Thread( this ).start();
        }

        public void sendMessage(String message){
            out.println( message );
            out.flush();
        }

        @Override
        public void run() {

            try {
                socket = new Socket(serverAddr, serverPort);
                socket.setTcpNoDelay(true);
                socket.shutdownInput();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            //if(socket.isConnected()) {
            try {

                out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())), true);
                //out.println(str);

                //out = socket.getOutputStream();
                //pout = new PrintWriter(new OutputStreamWriter(out, "8859_1"), true);

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();

        touchY = event.getY();

        double differenceX;
        double differenceY;


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                clickedTimeWindow = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setClickedTimeWindow(false);
                        //timer.cancel();
                    }
                }, 150);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                if(firstFingerMove==true) {// stay with the cursor in the same point
                    previousTouchX=touchX;
                    previousTouchY=touchY;
                }
                firstFingerMove=false;

                differenceX= touchX-previousTouchX;
                differenceY= touchY-previousTouchY;

                previousTouchX=touchX;
                previousTouchY=touchY;

                double coef =2.1;

                differenceX*=coef;
                differenceY*=coef;

                counter++;
                str = "X=" + Integer.toString((int) differenceX) + "&Y=" + Integer.toString((int) differenceY) + "&MOUSE_MOVE";
                //setText2(str);

                if(counter==1){
                    netcon.sendMessage(str);
                    setText2(str);
                    counter=0;
                }
                // out.flush();
                break;
            }

            case MotionEvent.ACTION_UP:{
                setFirstFingerMove(true );
                if (clickedTimeWindow == true) {
                    str = "X=" + Integer.toString((int) touchX) + "&Y=" + Integer.toString((int) touchY) + "&MOUSE_CLICKED";
                    netcon.sendMessage(str);

                }
                break;
            }
            default:
                return false;
        }

        return true;
    }

    public synchronized void setClickedTimeWindow(boolean clickedTimeWindow) {
        this.clickedTimeWindow = clickedTimeWindow;
    }

    public synchronized void setFirstFingerMove(boolean firstFingerMove) {
        this.firstFingerMove = firstFingerMove;
    }

    public synchronized void setText2 (String Text){
        final TextView text2 = (TextView) findViewById(R.id.textView2);
        text2.setText(Text);
    }
}
