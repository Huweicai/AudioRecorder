package com.example.bluetoothinterphone;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity {
	MediaRecorder recorder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button go = (Button) findViewById(R.id.button2);
		go.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event){
				//摁下录音键开始录音，松开则结束
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					try {
						start_record();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(event.getAction()==MotionEvent.ACTION_UP){
					stop_record();
				}
				return false;
			}
		});
	}
	public void start_record() throws Exception{
		java.util.Random random=new java.util.Random();
		File f=new File("/"+random.nextInt(999999)+".amr");
		recorder=new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(f.getAbsolutePath());//设置参数，路径，格式，开始录音
		recorder.prepare();
		recorder.start();
	}
	public void stop_record(){
		recorder.stop();
		recorder.release();
		recorder=null;
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		// hello
		return super.onOptionsItemSelected(item);
	}
}
