package com.satansin.android.apapa;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.satansin.android.apapa.logic.Initializer;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener {
	
	public static final String CONVERSATION_ID = "com.satansin.android.apapa.CONVERSATION_ID";

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	private static Initializer initializer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializer = Initializer.getInstance();

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.action_search) {
			startSearch();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void startSearch() {
		Intent searchIntent = new Intent(this, FindFriendActivity.class);
		startActivity(searchIntent);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private ConversationListViewFragment conversationListFragment;
		private ContactListViewFragment contactListViewFragment;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			conversationListFragment = new ConversationListViewFragment();
			contactListViewFragment = new ContactListViewFragment();
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			switch (position) {
			case 0:
				return conversationListFragment;
			case 1:
				return contactListViewFragment;
			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_conversation).toUpperCase(l);
			case 1:
				return getString(R.string.title_contact).toUpperCase(l);
			}
			return null;
		}
	}

	public static class ConversationListViewFragment extends Fragment {

		private static ConversationListViewFragment instance;

		public ConversationListViewFragment() {
			setArguments(initializer.getInitialConversationBundle());
		}

		public static ConversationListViewFragment getInstance() {
			if (instance == null) {
				instance = new ConversationListViewFragment();
			}
			return instance;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			ListView listView = (ListView) rootView
					.findViewById(R.id.mainListView);
			@SuppressWarnings("unchecked")
			final List<Map<String, ?>> list = (List<Map<String, ?>>) getArguments()
					.getSerializable("value_list");
			SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), list,
					R.layout.conversation_item, new String[] { "title", "info",
							"icon" }, new int[] { R.id.title, R.id.info,
							R.id.icon });
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String conversationId = (String) list.get(position).get("id");
					Intent conversationIntent = new Intent(getActivity(), ChatActivity.class);
					conversationIntent.putExtra(CONVERSATION_ID, conversationId);
					startActivity(conversationIntent);
				}
			});
			return rootView;
		}

	}

	public static class ContactListViewFragment extends Fragment {

		private static ContactListViewFragment instance;

		public ContactListViewFragment() {
			setArguments(initializer.getInitialContactBundle());
		}

		public static ContactListViewFragment getInstance() {
			if (instance == null) {
				instance = new ContactListViewFragment();
			}
			return instance;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			ListView listView = (ListView) rootView
					.findViewById(R.id.mainListView);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					this.getActivity(),
					android.R.layout.simple_expandable_list_item_1, getArguments().getStringArrayList(
							"value_list"));
			listView.setAdapter(adapter);
			return rootView;
		}

	}

}
