package com.example.adhit.bikubiku.ui.listRequestToKabim;

import com.example.adhit.bikubiku.data.model.RequestToKabim;

import java.util.HashMap;
import java.util.List;

/**
 * Created by roy on 1/22/2018.
 */

public interface ListRequestToKabimView {

    void setListRequestToKabim(List<RequestToKabim> requestToKabimList);

    void showError(String s);
}
