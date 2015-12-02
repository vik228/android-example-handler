package com.fragmentinteraction;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by vikas-pc on 30/11/15.
 */
public class BaseFragment extends Fragment implements View.OnClickListener {
   private Addition add;
    public static String COLOR_FRAGMENT="color_fragment";
    int color_one ;
    private static HandlerThread mHandlerThread;
    private NetworkHandler mNetworkHandler;
    private Handler myHandler;
TextView text;

    Button button;
    Button buttonSwitch;
    private static final String TAG = "BaseFragment";

    static {
        mHandlerThread = new HandlerThread(BaseFragment.class.getSimpleName());
    }

    public static BaseFragment newInstance(int color) {

        Bundle args = new Bundle();
args.putInt(COLOR_FRAGMENT, color);
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        int color=getArguments().getInt(COLOR_FRAGMENT);
        text= (TextView) view.findViewById(R.id.text);
        if(color != -1) {
            text.setBackgroundColor(color);
        }
        buttonSwitch =(Button) view.findViewById(R.id.switch_button);
        buttonSwitch.setOnClickListener(this);
        button= (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);
        myHandler = new Handler();
        if (!mHandlerThread.isAlive()) {
            mHandlerThread.start();
        }
        mNetworkHandler= new NetworkHandler(mHandlerThread.getLooper(), myHandler, getActivity(),add, text);
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Addition){
            add=(Addition)context;
        }
    }

    @Override
    public void onClick(View v) {

       switch (v.getId()){
           case R.id.button:{
              // add.onFragmentInteract(text,"test");
               mNetworkHandler.obtainMessage(1).sendToTarget();
               break;
           }

          case R.id.switch_button:{
            add.switchActivity();
          }
       }

    }

    public interface Addition{
         void onFragmentInteract(TextView tv, String message);

        void switchActivity();
    }

    private static class NetworkHandler extends Handler {

        Looper looper;
        Handler mHandler;
        Context mContext;
        TextView mTextView;
        Addition add;
        MassageModel massageModel;

        public NetworkHandler(Looper looper, Handler handler, Context context, Addition add, TextView textView){
            super(looper);
            this.looper= looper;
            this.mContext=context;
            this.mHandler= handler;
            this.add = add;
            this.mTextView = textView;
        }

        @Override
        public void handleMessage(Message msg) {
             switch (msg.what){
                 case 1: {
                     Response response =NetworkUtils.doGetCall("http://192.168.0.108:1337/api/v0/controllers/get_string",
                             mContext);
                     try {
                         String ab = response.body().string();
                     massageModel = new Gson().fromJson(ab, MassageModel.class);

                     } catch (IOException e) {
                         e.printStackTrace();
                     }

                     mHandler.post(new Runnable() {
                       @Override
                       public void run() {
                          // add.onFragmentInteract(mTextView, massageModel.getMessage());
                           mTextView.setText(massageModel.getMessage());
                       }
                   });
                 break;
                 }
             }
        }
    }
}
