package com.example.nikita.homework3;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by nikita on 29.11.16.
 */

public final class ImageService extends IntentService {

	public ImageService() {
		super("My service");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String filePath = getApplication().getFilesDir().toString() + '/' + intent.getStringExtra("picture_name");
		File file = new File(filePath);
		FileOutputStream output;
		if (file.exists()) {
			Log.d("State", "Exist or not writable");
			return;
		}
		HttpURLConnection connection = null;
		try {
			connection = ImageAPI.getImage();
			InputStream input = connection.getInputStream();
			byte[] buffer = new byte[8096];
			int length;
			file = new File(filePath);
			output = new FileOutputStream(file);
			while ((length = input.read(buffer)) != -1) {
				output.write(buffer, 0, length);
			}
			output.close();
			Intent action = new Intent("Simple action");
			LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(action);
			Log.d("State", "End of writing");
		} catch (IOException ex) {
			Log.d("State", "Internal error");
			ex.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
