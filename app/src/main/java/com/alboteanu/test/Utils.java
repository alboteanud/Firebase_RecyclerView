package com.alboteanu.test;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Dan on 31/05/2016.
 */
public class Utils {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
