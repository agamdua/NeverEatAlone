package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.execchain.RequestAbortedException;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.AccountProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.ContactProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.MealProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.activityProperties.services.NotificationProperties;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters.ContactsInformationAdapter;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.NotificationAndPostCacheHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.themes.ThemeManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.views.ContactsView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services.RequestHandlerHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.helpers.GsonHelper;

/**
 * This class handles controller operations for the contacts tab
 * 
 * @author tejasvamsingh
 * @author Hai Tang
 */
public class ContactsActivity extends ListActivity {

	private ArrayAdapter<ContactProperties> contactsInformationAdapter;
	private TextView contactTitleObject;
	private Button friendRequestButtonObejct;
	private Button addFriendButtonObject;
	private Context context;
	private Activity activity;
	private ContactsView contactsView;

	String requestType;
	String requestID;

	List<ContactProperties> contactList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);
	}

	/**
	 * 
	 * @author tejasvamsingh
	 * @author: Hai Tang
	 * @param savedInstanceState
	 *            This method update the GUI
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		initContactView();
		contactTitleObject = (TextView) contactsView
				.getView("textView_contacts_title");
		friendRequestButtonObejct = (Button) contactsView
				.getView("button_contacts_notification");
		addFriendButtonObject = (Button) contactsView
				.getView("button_contacts_addcontacts");

		initContactView();

		contactTitleObject = (TextView) contactsView.getView("textView_contacts_title");
		friendRequestButtonObejct = (Button) contactsView.getView("button_contacts_notification");
		addFriendButtonObject = (Button) contactsView.getView("button_contacts_addcontacts");
		contactList = new ArrayList<ContactProperties>();
		contactsInformationAdapter = new ContactsInformationAdapter(this,
				contactList);
		setListAdapter(contactsInformationAdapter);

		fetchContacts();



		setTitleStyle();
		applyTheme();
	}

	/**
	 * Method used to initialize ContactView.
	 * @author: Hai Tang
	 */
	private void initContactView() {
		context = this;
		activity = this;
		contactsView = new ContactsView(context, activity);
	}

	/**
	 * This method applies the GUI's color theme.
	 * 
	 * @author Yueling Loh
	 * @author Hai Tang
	 */
	private void applyTheme() {
		initContactView();

		View mainLayout = contactsView.getView("main_contacts");
		View headerLayout = contactsView.getView("header_contacts");
		View buttonBar = contactsView.getView("buttons_contacts");

		View contactsNotificationButton = contactsView.getView("button_contacts_notification");
		View addContactsButton = contactsView.getView("button_contacts_addcontacts");

		ThemeManager.applyTheme(mainLayout, headerLayout);
		ThemeManager.applyButtonBarTheme(buttonBar);

		ThemeManager.applyButtonColor(contactsNotificationButton);
		ThemeManager.applyButtonColor(addContactsButton);
	}

	/**
	 * This method is used to set the font style of the title of each page
	 * 
	 * @author: Hai Tang
	 * @author: Yueling Loh
	 */
	private void setTitleStyle() {

		ThemeManager.setHeaderFont(contactTitleObject);
	}

	/**
	 * This method fetches contacts from the server if the cache is empty.
	 * 
	 * @author tejasvamsingh
	 * @return
	 */
	private void fetchContacts() {

		requestID = "Contact";
		requestType = "getAll";



		Map<String,Object> requestMap = new HashMap<String,Object>();
		requestMap.put("username",
				AccountProperties.getUserAccountInstance().getusername());

		try{


			List<Map<String, String>> resultMapList = RequestHandlerHelper
					.getRequestHandlerInstance().handleRequest(this,
							requestMap, requestID, requestType);
			contactList.clear();
			for(Map<String,String> result : resultMapList){
				if(result.isEmpty())
					continue;
				contactList.add(new ContactProperties(result));
			}

			MessageToasterHelper.toastMessage(this, contactList.get(0).getContactusername());
			contactsInformationAdapter.notifyDataSetChanged();
			NotificationAndPostCacheHelper.setServerFetchRequired("contact", false);


		} catch (RequestAbortedException e) {
			System.out.println(e.getMessage());
		}
	}



	/**
	 * Method for add friends button click
	 * 
	 * @author: Hai Tang
	 */
	public void onAddFriendsButtonClick(View view) {
		Intent intent = new Intent(ContactsActivity.this,
				AddFriendsActivity.class);
		ContactsActivity.this.startActivity(intent);
	}

	/**
	 * Method for contact notification button click
	 * 
	 * 
	 */
	public void onContactNotificationButtonClick(View view) {
		Intent intent = new Intent(ContactsActivity.this,
				DisplayContactNotificationActivity.class);
		ContactsActivity.this.startActivity(intent);
	}


	@Override
	protected void onResume(){
		super.onResume();
		System.out.println("FETCH STATUS :"+
				NotificationAndPostCacheHelper.isServerFetchRequired("contact"));
		if(NotificationAndPostCacheHelper.isServerFetchRequired("contact"))
			fetchContacts();
	}

	/**
	 * This method goes to the ContactsProfileActivity when clicking specific contacts. It also passes
	 * the ContactsProperties to the ContactsProfileActivity.
	 * @author Hai Tang
	 * @param position
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(ContactsActivity.this, ContactsProfileActivity.class);
		ContactsActivity.this.startActivity(intent);
		
		//TODO Need to be populated with real contacts data
	}
		
}
