package com.example.adhit.bikubiku.ui.detailakun.profil.address;

/**
 * Created by adhit on 05/01/2018.
 */

public interface AddressView {

    void getDataAddress(String alamat);

    void onSuccessChangeAddress(String message);

    void onFailedChangeAddress(String message);
}
