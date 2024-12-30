package salma.elbahlouli.elderhealthappmobile.Reminder;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;

public class MedicineAlarmReceiver extends BroadcastReceiver {
    private static Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {
        String medicineName = intent.getStringExtra("medicine_name");
        String dosage = intent.getStringExtra("dosage");

        // Jouer le son d'alarme
        playAlarmSound(context);

        // Faire vibrer le téléphone
        vibrate(context);

        // Afficher un message
        Toast.makeText(context,
                "C'est l'heure de prendre " + medicineName + " (" + dosage + ")",
                Toast.LENGTH_LONG).show();

        // Créer une notification persistante
        showNotification(context, medicineName, dosage);
    }

    private void playAlarmSound(Context context) {
        try {
            if (ringtone != null && ringtone.isPlaying()) {
                ringtone.stop();
            }

            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();
            new Handler().postDelayed(ringtone::stop, 300000);
            // Arrêter le son après 30 secondes
          /*  android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(() -> {
                if (ringtone != null && ringtone.isPlaying()) {
                    ringtone.stop();
                }
            }, 30000); // 30 secondes

           */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(2000);
            }
        }
    }

    private void showNotification(Context context, String medicineName, String dosage) {
        createNotificationChannel(context); // Création du canal de notification
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Intent pour arrêter l'alarme
        Intent stopAlarmIntent = new Intent(context, StopAlarmReceiver.class);
        PendingIntent stopAlarmPendingIntent = PendingIntent.getBroadcast(
                context,
                medicineName.hashCode(),
                stopAlarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

      /*  NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MEDICINE_ALARMS")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Alarme médicament")
                .setContentText("Prenez " + medicineName + " (" + dosage + ")")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MEDICINE_ALARMS")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Alarme médicament")
                .setContentText("Prenez " + medicineName + " (" + dosage + ")")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_media_pause, "Arrêter l'alarme", stopAlarmPendingIntent);

        notificationManager.notify(medicineName.hashCode(), builder.build());
    }
    private void createNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager.getNotificationChannel("MEDICINE_ALARMS") == null) {
                NotificationChannel channel = new NotificationChannel(
                        "MEDICINE_ALARMS",
                        "Medicine Alarms",
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel.setDescription("Notifications pour les rappels de médicaments");
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public static void stopAlarm() {
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
    }
}