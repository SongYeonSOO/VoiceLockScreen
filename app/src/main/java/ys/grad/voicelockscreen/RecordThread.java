package ys.grad.voicelockscreen;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class RecordThread extends Thread {
    String TAG = "RecordThread";
    VoiceRecorder voiceRecorder;
    Activity activity;
    boolean isRecordStopped = false;
    boolean isAuthFragment;
    ImageView view;
    int recordedNum;
    public RecordThread(Activity activity, ImageView view, boolean isAuthFragment,int recordedNum) {
        super();
        voiceRecorder = new VoiceRecorder(recordedNum);
        this.view = view;
        this.activity = activity;
        this.isAuthFragment = isAuthFragment;
        this.recordedNum = recordedNum;
    }

    @Override
    public void run() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        });

        System.out.println("called");
        voiceRecorder.start();
        // check the voice finished
        //~~~~
        Log.d(TAG, "run: "+voiceRecorder.getMaxAmplitude());

        try {
            sleep(3000);
            Log.d(TAG, "run: "+voiceRecorder.getMaxAmplitude());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(true){
            if(voiceRecorder!=null && voiceRecorder.getMaxAmplitude() == 0){
                stopRecord();
            }
        }
    }

    public void stopRecord() {
        voiceRecorder.stop();
        voiceRecorder.release();
        voiceRecorder = null;
        System.out.println("stop");
        isRecordStopped = true;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setImageDrawable(activity.getDrawable(R.drawable.check_w));
                view.setVisibility(View.VISIBLE);
            }
        });
        //go Next Fragment
        try {
            sleep(800);
            if(isAuthFragment){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Authentication Fragment to AuthCheck Fragment
                        FragmentManager fragManager =  activity.getFragmentManager();
                        LockScreenAuthCheckFragment frag = new LockScreenAuthCheckFragment();
                        fragManager.beginTransaction().replace(R.id.activity_main_fragment,frag).addToBackStack(null).commit();

                    }
                });

            }else{
                //Register Fragment
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Authentication Fragment to AuthCheck Fragment
                        FragmentManager fragManager =  activity.getFragmentManager();
                        LockScreenRegisterFragment frag = new LockScreenRegisterFragment();
                        Bundle bundle = new Bundle();
                        //next recordNum
                        bundle.putInt("recordedNum",recordedNum+1);
                        frag.setArguments(bundle);
                        fragManager.beginTransaction().replace(R.id.activity_main_fragment,frag).addToBackStack(null).commit();

                    }
                });

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public boolean isRecordStopped() {
        return isRecordStopped;
    }
}
