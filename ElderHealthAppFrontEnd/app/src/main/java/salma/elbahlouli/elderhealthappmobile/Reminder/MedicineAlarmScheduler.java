package salma.elbahlouli.elderhealthappmobile.Reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import salma.elbahlouli.elderhealthappmobile.Reminder.MedicineAlarmReceiver;
import salma.elbahlouli.elderhealthappmobile.model.Medicine;

public class MedicineAlarmScheduler {
    public static void scheduleAlarm(Context context, Medicine medicine) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Créer l'intent pour l'alarme
        Intent intent = new Intent(context, MedicineAlarmReceiver.class);
        intent.putExtra("medicine_name", medicine.getNom());
        intent.putExtra("dosage", medicine.getDosage());

        // Créer un ID unique pour chaque alarme basé sur le médicament
        int alarmId = medicine.getId();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                alarmId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Calculer l'heure de l'alarme
        LocalTime medicineTime = LocalTime.parse(medicine.getHeure(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime alarmTime = now.with(medicineTime);

        // Si l'heure est déjà passée aujourd'hui, programmer pour demain
        if (now.toLocalTime().isAfter(medicineTime)) {
            alarmTime = alarmTime.plusDays(1);
        }

        // Convertir en milliseconds
        long alarmTimeMillis = alarmTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        // Programmer l'alarme quotidienne
     /*   alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                alarmTimeMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
*/
        try {
            // Schedule the alarm with the exact time
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmTimeMillis,
                    pendingIntent
            );
        } catch (SecurityException e) {
            // Handle the case where the permission is not granted
            e.printStackTrace();
            Toast.makeText(context, "Failed to schedule the alarm. Permission is missing.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void cancelAlarm(Context context, Medicine medicine) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MedicineAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                medicine.getId(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        // Arrêter le son s'il joue
        MedicineAlarmReceiver.stopAlarm();
    }
}