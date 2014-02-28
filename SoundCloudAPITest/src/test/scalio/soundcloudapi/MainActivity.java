package test.scalio.soundcloudapi;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;

import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Endpoints;
import com.soundcloud.api.Http;
import com.soundcloud.api.Params;
import com.soundcloud.api.Request;
import com.soundcloud.api.Token;
import com.soundcloud.api.examples.CreateWrapper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	private static final String TAG = "SoundCloudWrapperTest";
	
    //netcipher
    private static final String ORBOT_HOST = "127.0.0.1";
    private static final int ORBOT_HTTP_PORT = 8118;
    private static final int ORBOT_SOCKS_PORT = 9050;
	
    
    //NEED to change to test local audio file
    private static File mAudioFile = new File (Environment.getExternalStorageDirectory().getPath() + "/DCIM/ink.mp3");
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		testUpload();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void testUpload() {
		new LongOperation().execute("");
	}
	
	private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {  	
        	uploadFile();
    		return "success";
        }

        @Override
        protected void onPostExecute(String result) {}
        @Override
        protected void onPreExecute() {}
        @Override
        protected void onProgressUpdate(Void... values) {}
    }
	
	private void uploadFile() {
		
		String clientId = "e2d4d40b81830314350f0154bc88977a";
		String clientSecret = "00aea4c562e3561614f1d177f1e672a7";
		String userName = "milucas22";
		String userPass = "bama1986";
		
		final ApiWrapper wrapper = new ApiWrapper(
	            clientId, //client_id
	            clientSecret, //client_secret
	            null,    //redirect URI
	            null     );//token
		
		URI uri = null;
		try {
			uri = new URI("http", null, ORBOT_HOST, ORBOT_HTTP_PORT, null, null, null);
			
		} catch (URISyntaxException e) {
			Log.v(TAG, "URISyntaxException: " + e.toString());
		}
		
		wrapper.setProxy(uri);
	    
		try {
			Token token = wrapper.login(userName, userPass);
			
			HttpResponse resp2 = wrapper.post(Request.to(Endpoints.TRACKS)
			                        .add(Params.Track.TITLE, mAudioFile.getName())
			                        .add(Params.Track.TAG_LIST, "storymaker upload")
			                        .withFile(Params.Track.ASSET_DATA, mAudioFile));
			
		} catch (IOException e) {
			Log.v(TAG, "IOException: " + e.toString());
		}  		      	
	}
}
