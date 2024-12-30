package salma.elbahlouli.elderhealthappmobile.Reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import salma.elbahlouli.elderhealthappmobile.Reminder.MedicineAlarmReceiver;

public class StopAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Arrêter l'alarme
        MedicineAlarmReceiver.stopAlarm();
    }
}
