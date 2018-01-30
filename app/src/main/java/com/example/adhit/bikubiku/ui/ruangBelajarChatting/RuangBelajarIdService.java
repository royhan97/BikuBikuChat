package com.example.adhit.bikubiku.ui.ruangBelajarChatting;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.qiscus.sdk.Qiscus;

/**
 * Created by adhit on 01/01/2018.
 */

public class RuangBelajarIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh(); // Must call super

        // Below is your own apps specific code
        // e.g register the token to your backend
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Register token to qiscus
        Qiscus.setFcmToken(refreshedToken);

        // Below is your own apps specific code
        // e.g register the token to your backend
//        sendTokenToMyBackend(refreshedToken);

    }
}
