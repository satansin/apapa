package com.satansin.android.apapa;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FindFriendActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_friend);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_friend, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_find_friend,
					container, false);
			return rootView;
		}
	}
	
	public void executeSearch(View view) {
		EditText editText = (EditText) findViewById(R.id.searchFriendEditText);
		String text = editText.getText().toString();
		
		ListView listView = (ListView) findViewById(R.id.searchResultListView);

		final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map;

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.test_icon_1);
		map.put("username", text + "1");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.test_icon_2);
		map.put("username", text + "2");
		list.add(map);

		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.search_item, new String[] { "icon", "username" },
				new int[] { R.id.userIcon, R.id.usernameText });
		listView.setAdapter(adapter);
		
		final Activity parentActivity = this;
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String personName = (String) list.get(position).get("username");
				Intent personDetailIntent = new Intent(parentActivity, PersonActivity.class);
				personDetailIntent.putExtra(MainActivity.PERSON_NAME, personName);
				startActivity(personDetailIntent);
			}
		});
	}

}
