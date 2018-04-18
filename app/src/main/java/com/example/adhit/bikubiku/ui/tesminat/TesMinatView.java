package com.example.adhit.bikubiku.ui.tesminat;

import com.example.adhit.bikubiku.data.model.TesMinat;

import java.util.List;

public interface TesMinatView {
    void onFailedShowTesMinatQuestion(String message, boolean b);

    void onSuccessShowTesMinatQuestion(List<TesMinat> tesMinatList);

    void onFailedResetTesMinat(String message);

    void onSuccessResetTesMinat();

    void onFailedSubmitTesMinat(String message);

    void onSuccessSubmitTesMinat(String message);
}
