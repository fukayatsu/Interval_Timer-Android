package jp.atsfky.intervalTimer;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.*;

import java.lang.reflect.*;

import jp.atsfky.intervalTimer.R;

public class Edit extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);

		int m_tmr_0=0, m_itv_0=0, m_rep_0=0;
		//intentを解釈
		Bundle extras = getIntent().getExtras();
		if(extras !=null){
			m_tmr_0 = extras.getInt("TMR_0");
			m_itv_0 = extras.getInt("ITV_0");
			m_rep_0 = extras.getInt("REP_0");
		}


		//link with picker
		Button button01 = (Button)this.findViewById(R.id.Button01);

		Object np1 = findViewById(R.id.NumberPicker01);
		Object np2 = findViewById(R.id.NumberPicker02);
		Object np3 = findViewById(R.id.NumberPicker03);
		Object np4 = findViewById(R.id.NumberPicker04);
		Object np5 = findViewById(R.id.NumberPicker05);
		Object np6 = findViewById(R.id.NumberPicker06);
		Object np7 = findViewById(R.id.NumberPicker07);
        Class<? extends Object> c1 = np1.getClass();
        try{
            Method m1 = c1.getMethod("setRange", int.class, int.class);
            m1.invoke(np1, 0, 23);
            m1.invoke(np2, 0, 59);
            m1.invoke(np3, 0, 59);
            m1.invoke(np4, 0, 23);
            m1.invoke(np5, 0, 59);
            m1.invoke(np6, 0, 59);
            m1.invoke(np7, 0, 100);
            m1 = c1.getMethod("setCurrent", int.class);
            int hour = m_tmr_0/(60*60);
            m1.invoke(np1, hour);
            m1.invoke(np2, m_tmr_0/60 - hour*60);
            m1.invoke(np3, m_tmr_0%60);
            hour = m_itv_0/(60*60);
            m1.invoke(np4, hour);
            m1.invoke(np5, m_itv_0/60 - hour*60);
            m1.invoke(np6, m_itv_0%60);
            m1.invoke(np7, m_rep_0);
        }catch (Exception e){        	Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();}





		/*
		 * Button event
		 */
		button01.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Object np1 = findViewById(R.id.NumberPicker01);
				Object np2 = findViewById(R.id.NumberPicker02);
				Object np3 = findViewById(R.id.NumberPicker03);
				Object np4 = findViewById(R.id.NumberPicker04);
				Object np5 = findViewById(R.id.NumberPicker05);
				Object np6 = findViewById(R.id.NumberPicker06);
				Object np7 = findViewById(R.id.NumberPicker07);
		        Class<? extends Object> c1 = np1.getClass();

		        Intent intent = new Intent();
		        try{
		        	Method m1 = c1.getMethod("getCurrent");
		        	int m_tmr_0=(Integer)m1.invoke(np1)*3600+(Integer)m1.invoke(np2)*60+(Integer)m1.invoke(np3);
		        	intent.putExtra("TMR_0",  m_tmr_0);
		        	int m_itv_0=(Integer)m1.invoke(np4)*3600+(Integer)m1.invoke(np5)*60+(Integer)m1.invoke(np6);
		        	intent.putExtra("ITV_0",  m_itv_0);
		        	intent.putExtra("REP_0", (Integer)m1.invoke(np7));
		        }catch(Exception e){Toast.makeText(Edit.this, e.toString(), Toast.LENGTH_LONG).show();}
		        setResult(RESULT_OK, intent);
		        finish();
			}
		});

	}
}
