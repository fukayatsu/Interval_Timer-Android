package jp.atsfky.intervalTimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.*;

import java.util.Timer;
import java.util.TimerTask;

import jp.atsfky.intervalTimer.R;


public class Main extends Activity {
	private final static int SHOW_EDIT = 0;
	Times times = new Times();	//時間を保有するクラス

	Timer timer = new Timer();	//タイマーとタスク
	TimerTask task = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //widgetとリンク
        final TextView textview01 = (TextView)this.findViewById(R.id.TextView01);	// 00:03:00 (main)
        final TextView textview02 = (TextView)this.findViewById(R.id.TextView02);	// 00:01:00 (interval)
        final TextView textview03 = (TextView)this.findViewById(R.id.TextView03); // 1 of 10
        final TextView textview04 = (TextView)this.findViewById(R.id.TextView04); // interval
        final Button button01 = (Button)this.findViewById(R.id.Button01);			// edit
        final Button button02 = (Button)this.findViewById(R.id.Button02);			// Reset
        final Button button03 = (Button)this.findViewById(R.id.Button03);			// Start
        final CheckBox checkbox01 = (CheckBox)this.findViewById(R.id.CheckBox01);	// Alerm
        final CheckBox checkbox02 = (CheckBox)this.findViewById(R.id.CheckBox02); // Vivration
        final CheckBox checkbox03 = (CheckBox)this.findViewById(R.id.CheckBox03); // Repeat
        final CheckBox checkbox04 = (CheckBox)this.findViewById(R.id.CheckBox04); // Interval
        
        /*
         * 初期設定
         */
        button02.setEnabled(false);
        textview01.setTextColor(Color.GREEN);
		textview02.setTextColor(Color.DKGRAY);
		final MediaPlayer mp1 = MediaPlayer.create(Main.this, R.raw.alarm1);
		final MediaPlayer mp2 = MediaPlayer.create(Main.this, R.raw.alarm2);


        final android.os.Handler handler = new android.os.Handler();
        final Runnable runnable = new Runnable(){
			@Override
			public void run() {
				
				Vibrator vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
				// TODO 自動生成されたメソッド・スタブ
				//1秒ごとの処理
				if(checkbox03.isChecked()){
					//リピート設定時
					if(textview02.getVisibility() == View.VISIBLE){
						//インターバルあり
						if(times.isMain()){
							//メインタイマーの時
							times.tmrTick();
							if(times.getTmr()==0){
								if((times.getRep_0() != 0) && (times.getRep()==times.getRep_0())){
									//終了処理
									task.cancel();
									task = null;
									textview01.setTextColor(Color.RED);
									button01.setEnabled(true);
									button02.setEnabled(true);
									button03.setEnabled(false);
									button03.setText("start");

									if(checkbox01.isChecked()){
										try{
											
											mp2.start();

										}catch(Exception e){}
									}
									if(checkbox02.isChecked()){vibrator.vibrate(2000);}
								}else{
									times.setTmr(times.getTmr_0());
									times.turn();
									textview02.setTextColor(Color.GREEN);
									textview01.setTextColor(Color.DKGRAY);
									if(checkbox01.isChecked()){
										try{
											
											mp1.start();
										}catch(Exception e){}
									}
									if(checkbox02.isChecked()){vibrator.vibrate(500);}
								}
							}
						}else{
							//インターバルタイマーの時
							times.itvTick();
							if(times.getItv()==0){
								times.setItv(times.getItv_0());
								times.turn();
								times.repTick();
								textview01.setTextColor(Color.GREEN);
								textview02.setTextColor(Color.DKGRAY);
								if(checkbox01.isChecked()){
									try{
										mp1.start();
										
									}catch(Exception e){}
								}
								if(checkbox02.isChecked()){vibrator.vibrate(500);}
							}
						}
					}else{
						//インターバル無し
						times.tmrTick();
						if(times.getTmr()==0){
							if((times.getRep_0() != 0) && (times.getRep()==times.getRep_0())){
								//終了処理
								task.cancel();
								task = null;
								textview01.setTextColor(Color.RED);
								button01.setEnabled(true);
								button02.setEnabled(true);
								button03.setEnabled(false);
								button03.setText("start");

								if(checkbox01.isChecked()){
									try{
										
										mp2.start();
									}catch(Exception e){}
								}
								if(checkbox02.isChecked()){vibrator.vibrate(2000);}
							}else{
								times.setTmr(times.getTmr_0());
								times.repTick();
								textview01.setTextColor(Color.GREEN);
								textview02.setTextColor(Color.DKGRAY);
								
								if(checkbox02.isChecked()){vibrator.vibrate(2000);}
								if(checkbox01.isChecked()){
									try{
										
										mp1.start();
									}catch(Exception e){}
								}
							}
						}
					}
				}else{
					//リピート無し
					times.tmrTick();
					if(times.getTmr()==0){
						//終了処理
						task.cancel();
						task = null;
						textview01.setTextColor(Color.RED);
						button01.setEnabled(true);
						button02.setEnabled(true);
						button03.setEnabled(false);
						button03.setText("start");

						if(checkbox01.isChecked()){
							try{
								
								mp2.start();
							}catch(Exception e){}
						}
						if(checkbox02.isChecked()){vibrator.vibrate(2000);}
					}
				}
				//描写
				textview01.setText(times.getTmrString());
				textview02.setText(times.getItvString());
				textview03.setText(times.getRepString());
				
				
			}
			
        };


        /*
         * ボタンイベント
         */
        //Edit
        button01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent intent = new Intent(Main.this, Edit.class);
				intent.putExtra("TMR_0", times.getTmr_0());
				intent.putExtra("ITV_0", times.getItv_0());
				intent.putExtra("REP_0", times.getRep_0());
				startActivityForResult(intent, SHOW_EDIT);
			}
		});
        //reset
        button02.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mp2.stop();
				try{
					mp2.prepare();
				}catch(Exception e){}
				// TODO 自動生成されたメソッド・スタブ
				times.reset();
				textview01.setText(times.getTmrString());
				textview02.setText(times.getItvString());
				textview03.setText(times.getRepString());
				button02.setEnabled(false);
				button03.setEnabled(true);
				textview01.setTextColor(Color.GREEN);
				textview02.setTextColor(Color.DKGRAY);
			}
		});
        //start/stop
        button03.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				if(task == null){
					
					
					button01.setEnabled(false);
					button02.setEnabled(false);
					button03.setText("stop");
					task = new TimerTask(){
						@Override
						public void run() {
							// TODO 自動生成されたメソッド・スタブ
							handler.post(runnable);
						}
			        };
					timer.schedule(task, 1000, 1000);
				}else{

					button01.setEnabled(true);
					button02.setEnabled(true);
					button03.setText("start");
					task.cancel();
					task=null;

				}
			}
		});

        /*
         * チェックボックス
         */
        checkbox03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			//Repeat
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO 自動生成されたメソッド・スタブ
				if(isChecked){
					textview03.setVisibility(View.VISIBLE);
					checkbox04.setVisibility(View.VISIBLE);
					if(checkbox04.isChecked()){
						textview02.setVisibility(View.VISIBLE);
						textview04.setVisibility(View.VISIBLE);
					}
				}else{
					textview03.setVisibility(View.INVISIBLE);
					checkbox04.setVisibility(View.GONE);
					textview02.setVisibility(View.INVISIBLE);
        			textview04.setVisibility(View.INVISIBLE);
				}
			}
		});

        checkbox04.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
        	//Interval
        	@Override
        	public void  onCheckedChanged(CompoundButton cb, boolean b){
        		if(b){
        			textview02.setVisibility(View.VISIBLE);
        			textview04.setVisibility(View.VISIBLE);
        		}else{
        			textview02.setVisibility(View.INVISIBLE);
        			textview04.setVisibility(View.INVISIBLE);
        		}
        	}
        });

    }

    @Override
    public void onStop(){
    	super.onStop();
        final Button button01 = (Button)this.findViewById(R.id.Button01);			// edit
        final Button button02 = (Button)this.findViewById(R.id.Button02);			// Reset
        final Button button03 = (Button)this.findViewById(R.id.Button03);			// Start
    	if(task != null){
    		button01.setEnabled(true);
			button02.setEnabled(true);
			button03.setText("start");
    		task.cancel();
    		task=null;
    	}
    }

    //呼び出し先から
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

    	TextView textview01 = (TextView)this.findViewById(R.id.TextView01);
    	TextView textview02 = (TextView)this.findViewById(R.id.TextView02);
    	TextView textview03 = (TextView)this.findViewById(R.id.TextView03);

    	if(requestCode==SHOW_EDIT){
    		if(resultCode == RESULT_OK){
    			times.setTmr_0((Integer)data.getIntExtra("TMR_0",9));
    			times.setItv_0((Integer)data.getIntExtra("ITV_0",9));
    			times.setRep_0((Integer)data.getIntExtra("REP_0",9));
    			times.reset();
    			textview01.setText(times.getTmrString());
    			textview02.setText(times.getItvString());
    			textview03.setText(times.getRepString());

    		}
    	}
    }

}

/*
 * 時間等を保持するクラス
 */
class Times{
	private int tmr_0=180, tmr=180;	//main timer
	private int itv_0=60, itv=60;	//Internal timer
	private int rep_0=3, rep=1;	//repeat
	private boolean main =true;

	public void tmrTick(){tmr--;}
	public void itvTick(){itv--;}
	public void repTick(){rep++;}

	public int getTmr_0(){return tmr_0;}
	public int getItv_0(){return itv_0;}
	public int getRep_0(){return rep_0;}
	public int getTmr(){return tmr;}
	public int getItv(){return itv;}
	public int getRep(){return rep;}

	public void setTmr_0(int i){if(i<1)i=1;tmr_0 = i;}
	public void setItv_0(int i){if(i<1)i=1;itv_0 = i;}
	public void setRep_0(int i){rep_0 = i;}
	public void setTmr(int i){tmr = i;}
	public void setItv(int i){itv = i;}
	public void setRep(int i){rep = i;}

	public void reset(){tmr=tmr_0; itv=itv_0; rep=1; main = true;}

	public String getTmrString(){
		Integer h,m,s;
		h=tmr/3600; m=tmr/60-h*60; s=tmr%60;
		return String.format("%02d:%02d:%02d", h,m,s);
	}
	public String getItvString(){
		Integer h,m,s;
		h=itv/3600; m=itv/60-h*60; s=itv%60;
		return String.format("%02d:%02d:%02d", h,m,s);
	}
	public String getRepString(){
		if(rep_0 == 0){
			return String.format("%d       ", rep);
		}else{
			return String.format("%d of %d", rep,rep_0);
		}
	}

	public void turn(){main = !main;}
	public boolean isMain(){return main;}

}