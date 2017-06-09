package ys.grad.voicelockscreen;

import android.app.Activity;
import android.app.FragmentManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

// do record + make wav file
//  8 bit mono 8000Hz sampling
public class RecordRunnable implements Runnable {
    private final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_8BIT;
    private final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;  //안드로이드 녹음시 채널 상수값
    private final int WAVE_CHANNEL_MONO = 1;  //wav 파일 헤더 생성시 채널 상수값
    private final int HEADER_SIZE = 0x2c;
    private final int RECORDER_BPP = 8;
    private final int RECORDER_SAMPLERATE = 0x1f40;
    private final int BUFFER_SIZE;
    private final String TEMP_FILE_NAME = "test_temp.bak";

    private AudioRecord record;

    private boolean isRecording;
    private int nowRecordNum;
    private BufferedInputStream bIStream;
    private BufferedOutputStream bOStream;
    private int audioLen = 0;

    private Activity activity;
    private boolean isAuthFragment;
    private ImageView view;
    public RecordRunnable(Activity activity, ImageView view, boolean isAuthFragment, int nowRecordNum){
        super();
        this.activity = activity;
        this.view = view;
        this.isAuthFragment = isAuthFragment;

        this.nowRecordNum = nowRecordNum;
        BUFFER_SIZE = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);
        this.record = new AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, BUFFER_SIZE);
        isRecording = false; // not recorded
    }
    @Override
    public void run() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        });
        record.startRecording();
        isRecording = true;

        //Record for 3 secs
        TimerTask recordTimerTask = new TimerTask() {
            @Override
            public void run() {
                stopRecording();
            }
        };
        Timer timer = new Timer("threeSecs");
        timer.schedule(recordTimerTask,3000);

        //after recording
        byte[] buffer = new byte[BUFFER_SIZE];
        byte[] data = new byte[BUFFER_SIZE];
        File voiceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/VLS");
        if(!voiceDir.exists())voiceDir.mkdir();
        File waveFile = new File(voiceDir.getAbsolutePath()+"/nowVoiceAuth"+nowRecordNum+".wav");
        File tempFile = new File(Environment.getExternalStorageDirectory()+"/"+TEMP_FILE_NAME);

        try {
            bOStream = new BufferedOutputStream(new FileOutputStream(tempFile));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        int read = 0;
        int len = 0;
        if (bOStream != null) {
            try {
                while (isRecording) {
                    read = record.read(data, 0, BUFFER_SIZE);
                    if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                        bOStream.write(data);
                    }
                }

                bOStream.flush();
                audioLen = (int)tempFile.length();
                bIStream = new BufferedInputStream(new FileInputStream(tempFile));
                bOStream.close();
                bOStream = new BufferedOutputStream(new FileOutputStream(waveFile));
                bOStream.write(getFileHeader());
                len = HEADER_SIZE;
                while ((read = bIStream.read(buffer)) != -1) {
                    bOStream.write(buffer);
                }
                bOStream.flush();
                bIStream.close();
                bOStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    private byte[] getFileHeader() {
        byte[] header = new byte[HEADER_SIZE];
        int totalDataLen = audioLen + 40;
        long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * WAVE_CHANNEL_MONO/8;
        header[0] = 'R';  // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';  // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = (byte)1;  // format = 1 (PCM방식)
        header[21] = 0;
        header[22] =  WAVE_CHANNEL_MONO;
        header[23] = 0;
        header[24] = (byte) (RECORDER_SAMPLERATE & 0xff);
        header[25] = (byte) ((RECORDER_SAMPLERATE >> 8) & 0xff);
        header[26] = (byte) ((RECORDER_SAMPLERATE >> 16) & 0xff);
        header[27] = (byte) ((RECORDER_SAMPLERATE >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) RECORDER_BPP * WAVE_CHANNEL_MONO/8;  // block align
        header[33] = 0;
        header[34] = RECORDER_BPP;  // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte)(audioLen & 0xff);
        header[41] = (byte)((audioLen >> 8) & 0xff);
        header[42] = (byte)((audioLen >> 16) & 0xff);
        header[43] = (byte)((audioLen >> 24) & 0xff);
        return header;
    }

    private void stopRecording() {
        if (null != record) {
            isRecording = false;
            record.stop();
            record.release();
            record = null;
        }

        System.out.println("stop");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setImageDrawable(activity.getDrawable(R.drawable.check_w));
                view.setVisibility(View.VISIBLE);
            }
        });
        //go Next Fragment
        try {
            Thread.sleep(800);
            if (isAuthFragment) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Authentication Fragment to AuthCheck Fragment
                        FragmentManager fragManager = activity.getFragmentManager();
                        LockScreenAuthCheckFragment frag = new LockScreenAuthCheckFragment();
                        fragManager.beginTransaction().replace(R.id.activity_main_fragment, frag).addToBackStack(null).commit();

                    }
                });

            } else {
                //Register Fragment
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Authentication Fragment to AuthCheck Fragment
                        FragmentManager fragManager = activity.getFragmentManager();
                        LockScreenRegisterFragment frag = new LockScreenRegisterFragment();
                        Bundle bundle = new Bundle();
                        //next recordNum
                        bundle.putInt("recordedNum", nowRecordNum + 1);
                        frag.setArguments(bundle);
                        fragManager.beginTransaction().replace(R.id.activity_main_fragment, frag).addToBackStack(null).commit();

                    }
                });

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}