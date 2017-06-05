package ys.grad.voicelockscreen;

import android.app.Activity;
import android.hardware.Camera;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioRouting;
import android.media.AudioTimestamp;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaSyncEvent;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;

public class VoiceRecorder extends MediaRecorder{
    String TAG = "VoiceRecorder";
    String fileName = null;
    @Override
    protected void finalize() {
        super.finalize();
    }

    public VoiceRecorder(int nowRecordNum) {
        super();
        // if there is no VLS Folder, create it
        File voiceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/VLS");
        if(!voiceDir.exists())voiceDir.mkdir();
        //  8 bit mono 8000Hz sampling
        this.setAudioSamplingRate(8000);
        this.setAudioEncodingBitRate(64000);
        this.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.setOutputFormat(OutputFormat.MPEG_4);
        fileName = voiceDir.getAbsolutePath()+"/nowVoiceAuth"+nowRecordNum+".mp3";
        this.setOutputFile(fileName);
        this.setAudioEncoder(AudioEncoder.AAC);
        try {
            this.prepare();
        }catch (IOException e){
            Log.d(TAG, "ys.grad.voicelockscreen.VoiceRecorder: prepare() failed");
        }
    }


    @Override
    public Surface getSurface() {
        return super.getSurface();
    }

    @Override
    public void setInputSurface(Surface surface) {
        super.setInputSurface(surface);
    }

    @Override
    public void setPreviewDisplay(Surface sv) {
        super.setPreviewDisplay(sv);
    }

    @Override
    public void setAudioSource(int i) throws IllegalStateException {
        super.setAudioSource(i);
    }

    @Override
    public void setVideoSource(int i) throws IllegalStateException {
        super.setVideoSource(i);
    }

    @Override
    public void setProfile(CamcorderProfile profile) {
        super.setProfile(profile);
    }

    @Override
    public void setCaptureRate(double fps) {
        super.setCaptureRate(fps);
    }

    @Override
    public void setOrientationHint(int degrees) {
        super.setOrientationHint(degrees);
    }

    @Override
    public void setLocation(float latitude, float longitude) {
        super.setLocation(latitude, longitude);
    }

    @Override
    public void setOutputFormat(int i) throws IllegalStateException {
        super.setOutputFormat(i);
    }

    @Override
    public void setVideoSize(int i, int i1) throws IllegalStateException {
        super.setVideoSize(i, i1);
    }

    @Override
    public void setVideoFrameRate(int i) throws IllegalStateException {
        super.setVideoFrameRate(i);
    }

    @Override
    public void setMaxDuration(int i) throws IllegalArgumentException {
        super.setMaxDuration(i);
    }

    @Override
    public void setMaxFileSize(long l) throws IllegalArgumentException {
        super.setMaxFileSize(l);
    }

    @Override
    public void setAudioEncoder(int i) throws IllegalStateException {
        super.setAudioEncoder(i);
    }

    @Override
    public void setVideoEncoder(int i) throws IllegalStateException {
        super.setVideoEncoder(i);
    }

    @Override
    public void setAudioSamplingRate(int samplingRate) {
        super.setAudioSamplingRate(samplingRate);
    }

    @Override
    public void setAudioChannels(int numChannels) {
        super.setAudioChannels(numChannels);
    }

    @Override
    public void setAudioEncodingBitRate(int bitRate) {
        super.setAudioEncodingBitRate(bitRate);
    }

    @Override
    public void setVideoEncodingBitRate(int bitRate) {
        super.setVideoEncodingBitRate(bitRate);
    }

    @Override
    public void setOutputFile(FileDescriptor fd) throws IllegalStateException {
        super.setOutputFile(fd);
    }

    @Override
    public void setOutputFile(String path) throws IllegalStateException {
        super.setOutputFile(path);
    }

    @Override
    public void prepare() throws IllegalStateException, IOException {
        super.prepare();
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
    }

    @Override
    public void resume() throws IllegalStateException {
        super.resume();
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public int getMaxAmplitude() throws IllegalStateException {
        return super.getMaxAmplitude();
    }

    @Override
    public void setOnErrorListener(OnErrorListener l) {
        super.setOnErrorListener(l);
    }

    @Override
    public void setOnInfoListener(OnInfoListener listener) {
        super.setOnInfoListener(listener);
    }

    @Override
    public void release() {
        super.release();
    }
}