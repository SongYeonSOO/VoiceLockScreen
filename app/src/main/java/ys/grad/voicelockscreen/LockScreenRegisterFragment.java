package ys.grad.voicelockscreen;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class LockScreenRegisterFragment extends Fragment {
    String TAG = "RegisterFragment";
    ImageView microphonButton,homeButton;
    TextView countRecordedNumView, regiTextView;
    int recordedNum;

    public LockScreenRegisterFragment() {
        // Required empty public constructor
    }

    public static LockScreenRegisterFragment newInstance(int recordedNum) {
        LockScreenRegisterFragment fragment = new LockScreenRegisterFragment();
        Bundle args = new Bundle();
        args.putInt("recordedNum", recordedNum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordedNum = getArguments().getInt("recordedNum");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lock_screen_register, container, false);
        microphonButton = (ImageView) v.findViewById(R.id.regi_microphone_button);
        countRecordedNumView = (TextView) v.findViewById(R.id.regi_count);
        regiTextView = (TextView) v.findViewById(R.id.regi_text);
        homeButton = (ImageView) v.findViewById(R.id.regi_home);
        if (recordedNum > 5) {
            microphonButton.setVisibility(View.GONE);
            countRecordedNumView.setVisibility(View.GONE);
            regiTextView.setText(getString(R.string.assign_finish));
            homeButton.setVisibility(View.VISIBLE);
        }else{
            String text = recordedNum+"/5";
            countRecordedNumView.setText(text);
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to AuthFragment
                FragmentManager fragManager = getFragmentManager();
                LockScreenAuthFragment frag = new LockScreenAuthFragment();
                fragManager.beginTransaction().replace(R.id.activity_main_fragment,frag).addToBackStack(null).commit();
            }
        });

        microphonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start to check identity
                regiTextView.setText(getString(R.string.assign_talk));
                Log.d(TAG, "onClick: start");
                Thread recordThread = new Thread(new RecordRunnable(getActivity(), microphonButton, false, recordedNum));
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
