package com.example.nikita.homework3;

import android.net.Uri;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nikita on 29.11.16.
 */

public final class ImageAPI {
	private final static Uri BASE_URI = Uri.parse("http://picslife.ru/wp-content/uploads/2012/12/fotografiya-zemli-v-vyisokom-razreshenii_8000x8000.jpg"
													/*"http://boombob.ru/img/picture/Oct/12/a6938b73f59624f22c8f0c9a67773baf/1.jpg"*/);

	public static HttpURLConnection getImage() throws IOException {
		return (HttpURLConnection) new URL(BASE_URI.toString()).openConnection();
	}
}
