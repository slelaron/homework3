package com.example.nikita.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.util.Locale;

/**
 * Created by nikita on 29.11.16.
 */
public class ImageReceiver extends BroadcastReceiver {
	public  ImageReceiver() {}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("Locale", Locale.getDefault().getLanguage());
		if (Locale.getDefault().getLanguage().equals("en")) {
			String filePath = context.getFilesDir().toString() + '/' + context.getString(R.string.picture_name);
			File file = new File(filePath);
			if (file.exists()) {
				if (!file.delete()) {
					Log.d("State", "Deleting picture");
				}
			}
			return;
		}
		Log.d("Receiver", "Starting service");
		Intent action = new Intent(context, ImageService.class);
		action.putExtra("picture_name", context.getString(R.string.picture_name));
		context.startService(action);
	}
}
