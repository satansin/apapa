package com.satansin.android.apapa.logic;

import java.util.ArrayList;
import java.util.HashMap;

import com.satansin.android.apapa.R;

import android.os.Bundle;

public class Initializer {

	public static final short BUNDLE_CONVERSATION_LIST = 1;
	public static final short BUNDLE_CONTACT_LIST = 2;
	
	private static Initializer instance;
	
	private Initializer() {
	}
	
	public static Initializer getInstance() {
		if (instance == null) {
			instance = new Initializer();
		}
		return instance;
	}

	public Bundle getInitialConversationBundle() {
		Bundle conversationBundle = new Bundle();
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map;
		
		map = new HashMap<String, Object>();
		map.put("id", "chat1");
		map.put("icon", R.drawable.test_icon_1);
		map.put("title", "USR1");
		map.put("info", "hahahahaha");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("id", "chat2");
		map.put("icon", R.drawable.test_icon_2);
		map.put("title", "USR2");
		map.put("info", "hahahahaha");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("id", "chat3");
		map.put("icon", R.drawable.test_icon_3);
		map.put("title", "USR3");
		map.put("info", "hahahahaha");
		list.add(map);
		
		conversationBundle.putSerializable("value_list", list);
		return conversationBundle;
	}

	public Bundle getInitialContactBundle() {
		Bundle contactBundle = new Bundle();
		ArrayList<String> contactList = new ArrayList<String>();
		contactList.add("abc");
		contactList.add("def");
		contactBundle.putStringArrayList("value_list", contactList);
		return contactBundle;
	}
	
	public Bundle getInitialChattingHistoryBundle(String conversationID) {
		Bundle chattingHistoryBundle = new Bundle();
		
		return chattingHistoryBundle;
	}

}
