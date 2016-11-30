package com.example.nikita.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

	private BroadcastReceiver receiver;
	private ImageView image;
	private TextView text;
	private static String filePath;

	protected static boolean getImageInformation() {
		File fileTmp = new File(filePath);
		return fileTmp.exists();
	}

	public static int makeNormalSize(BitmapFactory.Options options, int width, int height) {
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;
		int nxtSize = 1;

		if (imageHeight > height || imageWidth > width) {
			nxtSize = (int) Math.max(Math.round((double)imageHeight / (double) height), Math.round((double)imageWidth / (double) width));
		}
		return nxtSize;
	}

	protected void setImage() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		int height = 1024;
		int width = 1024;
		options.inSampleSize = makeNormalSize(options, width, height);
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		image.setImageBitmap(bitmap);
		text.setVisibility(TextView.INVISIBLE);
		image.setVisibility(ImageView.VISIBLE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		filePath = getFilesDir().toString() + '/' + getString(R.string.picture_name);

		if (receiver == null) {
			receiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					setImage();
				}
			};
			IntentFilter intent = new IntentFilter("Simple action");
			LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intent);
		}

		setContentView(R.layout.activity_main);

		image = (ImageView) findViewById(R.id.image);
		text = (TextView) findViewById(R.id.Error_text);
		image.setVisibility(ImageView.INVISIBLE);
		text.setVisibility(TextView.VISIBLE);


		if (getImageInformation()) {
			setImage();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!getImageInformation()) {
			text.setVisibility(TextView.VISIBLE);
			image.setVisibility(ImageView.INVISIBLE);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (receiver != null) {
			LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
			receiver = null;
		}
	}
}
