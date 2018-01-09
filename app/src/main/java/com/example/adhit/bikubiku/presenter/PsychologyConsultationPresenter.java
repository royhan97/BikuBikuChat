package com.example.adhit.bikubiku.presenter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.local.SavePsychologyConsultationRoomChat;
import com.example.adhit.bikubiku.data.local.SessionChatPsychology;
import com.example.adhit.bikubiku.data.model.Psychologist;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.ui.psychologyconsultation.PsychologyConsultationView;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.util.QiscusRxExecutor;

import java.util.ArrayList;

/**
 * Created by adhit on 07/01/2018.
 */

public class PsychologyConsultationPresenter {
    private PsychologyConsultationView psychologyConsultationView;
    public PsychologyConsultationPresenter(PsychologyConsultationView psychologyConsultationView){
        this.psychologyConsultationView = psychologyConsultationView;
    }

    public void psychologyList(){
        ArrayList<Psychologist> psychologistArrayList = new ArrayList<>();
        psychologistArrayList.add(new Psychologist(23, "Cahyo Adhi", "20.000"));

        psychologistArrayList.add(new Psychologist(14, "Roy1", "15.000"));
        psychologistArrayList.add(new Psychologist(15, "Roy", "15.000"));
        psychologyConsultationView.showData(psychologistArrayList);
    }

    public void isRoomChatBuild(){
        psychologyConsultationView.showBlock(SessionChatPsychology.getInstance().isRoomChatPsychologyConsultationBuild());
    }

    public void openRoomChat(Context context){
        ShowAlert.showProgresDialog(context);
        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(SavePsychologyConsultationRoomChat.getInstance().getPsychologyConsultationRoomChat()),
                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                    @Override
                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                        psychologyConsultationView.openRoomChat(qiscusChatRoom);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_ONE_SHOT);
                        ShowAlert.closeProgresDialog();
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        ShowAlert.closeProgresDialog();
                    }
                });

    }

}
