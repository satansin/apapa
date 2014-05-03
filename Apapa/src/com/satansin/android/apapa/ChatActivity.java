package com.satansin.android.apapa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ChatActivity extends ActionBarActivity {
	
	private static ArrayList<HashMap<String, Object>> list;
	private static SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_chat, container,
					false);
			ListView listView = (ListView) rootView
					.findViewById(R.id.chattingListView);

			list = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map;

			map = new HashMap<String, Object>();
			map.put("icon", R.drawable.test_icon_1);
			map.put("text", "是在前台触发,一个是后台服务触发。 要...");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("icon", R.drawable.test_icon_2);
			map.put("text",
					"An Intent is an object that provides runtime binding between separate components (such as two activities). The Intent represents an app’s \"intent to do something.\" You can use intents for a wide variety of tasks, but most often they’re used to start another activity.");
			list.add(map);

			adapter = new SimpleAdapter(getActivity(), list,
					R.layout.chatting_item, new String[] { "icon", "text" },
					new int[] { R.id.chattingIcon, R.id.chattingText });
			listView.setAdapter(adapter);
			
			listView.setSelection(listView.getBottom());
			
//			final ListView finalListView = listView;
//			EditText editText = (EditText) rootView.findViewById(R.id.chattingEditText);
//			editText.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					finalListView.setSelection(finalListView.getBottom());
//				}
//			});
			
			return rootView;
		}
		
	}
	
	public void appendText(View view) {
		EditText editText = (EditText) findViewById(R.id.chattingEditText);
		String text = editText.getText().toString();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("icon", R.drawable.test_icon_3);
		map.put("text", text);
		list.add(map);
		adapter.notifyDataSetChanged();
		
		editText.setText("");
		ListView listView = (ListView) findViewById(R.id.chattingListView);
		listView.setSelection(listView.getBottom());
		
		new MyTask().execute();
		
//		new Thread() {
//			@Override
//			public void run() {
//				try {
//					System.out.println("thread begin");
//					Socket socket = new Socket("192.110.165.234", 80);
//					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//					final String advice = reader.readLine();
//					editText.post(new Runnable() {
//						@Override
//						public void run() {
//							editText.setText(advice);
//						}
//					});
//					reader.close();
//					socket.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}.start();
	}
	
	private class MyTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			Socket socket;
			String advice = "";
			try {
				socket = new Socket("192.110.165.234", 80);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				advice = reader.readLine();
				reader.close();
				socket.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return advice;
		}
		
		@Override  
	    protected void onPostExecute(String result) {
			final EditText editText = (EditText) findViewById(R.id.chattingEditText);
			editText.setText(result);
	    }
	}

}
