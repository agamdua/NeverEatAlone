package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.R;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.models.ContactsModel;

/**
 * 
 * @author Hai Tang
 *This adapter adds a listener on the Checkbox view . 
 *If the checkbox is selected the underlying data of the model is changed. 
 *Checkbox gets the corresponding model element assigned via the getTag() method.
 */
public class InteractiveArrayAdapter extends ArrayAdapter<ContactsModel> {

  private final List<ContactsModel> list;
  private final Activity context;

  public InteractiveArrayAdapter(Activity context, List<ContactsModel> list) {
    super(context, R.layout.row_contact_item_layout, list);
    this.context = context;
    this.list = list;
  }

  static class ViewHolder {
    protected TextView text;
    protected CheckBox checkbox;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = null;
    if (convertView == null) {
      LayoutInflater inflator = context.getLayoutInflater();
      view = inflator.inflate(R.layout.row_contact_item_layout, null);
      final ViewHolder viewHolder = new ViewHolder();
      viewHolder.text = (TextView) view.findViewById(R.id.contacts_name);
      viewHolder.checkbox = (CheckBox) view.findViewById(R.id.contacts_check);
      viewHolder.checkbox
          .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                boolean isChecked) {
            	ContactsModel element = (ContactsModel) viewHolder.checkbox
                  .getTag();
              element.setSelected(buttonView.isChecked());

            }
          });
      view.setTag(viewHolder);
      viewHolder.checkbox.setTag(list.get(position));
    } else {
      view = convertView;
      ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
    }
    ViewHolder holder = (ViewHolder) view.getTag();
    holder.text.setText(list.get(position).getName());
    holder.checkbox.setChecked(list.get(position).isSelected());
    return view;
  }
} 
