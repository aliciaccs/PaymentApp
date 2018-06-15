package com.amaita.paymentapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Payment.class}, version = 1)
public abstract class PaymentDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "payments";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile PaymentDatabase sInstance;

    public static PaymentDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            PaymentDatabase.class, PaymentDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }


    public abstract PaymentDao paymentDao();
}
