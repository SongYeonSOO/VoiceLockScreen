package ys.grad.voicelockscreen;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class LockScreenAuthFragment extends Fragment {
ImageView microphonButton;
    TextView authTextView;
    String TAG ="ys.grad.voicelockscreen.LockScreenAuthFragment";

    public LockScreenAuthFragment() {
    }


    public static LockScreenAuthFragment newInstance(String param1, String param2) {
        LockScreenAuthFragment fragment = new LockScreenAuthFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lock_screen, container, false);
        microphonButton = (ImageView) v.findViewById(R.id.auth_microphone_button);
        authTextView = (TextView) v.findViewById(R.id.auth_text);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        microphonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authTextView.setText(getString(R.string.auth_talk));
                //start to check identity
                Log.d(TAG, "onClick: start");
                RecordThread recordThread =new RecordThread(getActivity(),microphonButton,true,-1);
                recordThread.start();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
