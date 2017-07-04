package com.example.bluetoothinterphone;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity {
	MediaRecorder recorder;
	Button go;
	File file;
	//��Ҫ��ȫ��ʹ�õı���
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		go = (Button) findViewById(R.id.button2);
		go.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// ����¼������ʼ¼�����ɿ������
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					try {
						start_record();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					stop_record();
				}
				return false;
			}
		});
	}
	/*
	 * 
	 */
	public void start_record() throws Exception {
		file = mkNewFile();
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(file.getAbsolutePath());// ���ò�����·������ʽ����ʼ¼��
		recorder.prepare();
		recorder.start();
	}
	/*
	 * �����µ�����ļ����ڱ����ļ�
	 */
	protected File mkNewFile() {
		File dir = new File(Environment.getExternalStorageDirectory() + "/talker/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		java.util.Random random = new java.util.Random();
		File f = new File(Environment.getExternalStorageDirectory() + "/talker/" + random.nextInt(999999) + ".amr");
		return f;
	}

	public void stop_record() {
		if (recorder == null)
			return;
		recorder.stop();
		recorder.release();
		recorder = null;
		if(file.exists()){
			mkDialog();
			file=null;
		}
		super.onDestroy();
	}
	public void mkDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("��ʾ");
		builder.setMessage("��Ƶ�ļ�"+file.getAbsolutePath()+"�ѱ���");
		builder.show();
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
