package ys.grad.voicelockscreen;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LockScreenAuthCheckFragment extends Fragment {
    String TAG ="LockScreenAuthCheckFragment";
    ImageView homeButton, settingButton;
    TextView authText;
    boolean isSuccess;
    public LockScreenAuthCheckFragment() {

    }



    public static LockScreenAuthCheckFragment newInstance() {
        LockScreenAuthCheckFragment fragment = new LockScreenAuthCheckFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //여기서 계산 완료
        isSuccess=true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lock_screen_auth_check, container, false);
        homeButton = (ImageView) v.findViewById(R.id.auth_check_home);
        settingButton = (ImageView) v.findViewById(R.id.auth_check_setting);
        authText = (TextView) v.findViewById(R.id.auth_check_text);
        if(isSuccess){
            authText.setText(getString(R.string.auth_success));
        }else{
            authText.setText(getString(R.string.auth_fail));
            settingButton.setVisibility(View.GONE);
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
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to Register Fragment
                FragmentManager fragManager = getFragmentManager();
                LockScreenRegisterFragment frag = new LockScreenRegisterFragment();
                Bundle args = new Bundle();
                args.putInt("recordedNum", 1);
                frag.setArguments(args);
                fragManager.beginTransaction().replace(R.id.activity_main_fragment,frag).addToBackStack(null).commit();
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
