package com.example.adhit.bikubiku.ui.listKabim;

import com.example.adhit.bikubiku.data.model.Kabim;

import java.util.List;

/**
 * Created by roy on 1/5/2018.
 */

public interface ListKabimFragmentView {

    void showData(List<Kabim> listKabim);

    void toRecentRoomChat(int id);
}
