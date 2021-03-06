package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;

/**
 * This class is used to control the post information page.
 * @author Hai Tang
 *
 */
public class PostInformationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initView(savedInstanceState);
	}

	/**
	 * Method used to initialize the view
	 * @author: Hai Tang
	 */
	private void initView(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_information);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_information, menu);
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
	 * Method used for back button click
	 * @author: Hai Tang
	 */
	public void onBackButtonClick(View view){
		Intent intent = new Intent(this, SelectFriendsActivity.class);
		startActivity(intent);
	}

	/**
	 * Method used for post button click
	 * @author: Hai Tang
	 */
	public void onPostButtonClick(View view){
		Intent intent = new Intent(this, TabHostActivity.class);
		startActivity(intent);
	}
}
