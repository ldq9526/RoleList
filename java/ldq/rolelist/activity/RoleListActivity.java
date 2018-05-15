package ldq.rolelist.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ldq.rolelist.R;
import ldq.rolelist.db.RoleListDB;
import ldq.rolelist.model.Role;

public class RoleListActivity extends BaseActivity {

    private class RoleAdapter extends ArrayAdapter<String> {

        private class ViewHolder {
            ImageView thumb;
            TextView name;
        }

        private int resourceId;

        RoleAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context,textViewResourceId,objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if(convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId,null);
                viewHolder = new ViewHolder();
                viewHolder.thumb = (ImageView)view.findViewById(R.id.thumb);
                viewHolder.name = (TextView)view.findViewById(R.id.name);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            String roleName = getItem(position);
            Role role = roleListDB.getRoleByName(roleName);
            String file = role.romaji.split(" ")[1].toLowerCase().trim();
            viewHolder.thumb.setImageResource(getIdByImageName("thumb_"+file));
            viewHolder.name.setText(roleName);
            return view;
        }
    }

    RoleAdapter adapter;
    ListView listView;
    private RoleListDB roleListDB;
    private ArrayList<String> roleNames;
    private ArrayList<String> tagNames;

    private void initializeViews(Bundle savedInstanceState) {
        roleListDB = RoleListDB.getInstance(this);
        if(savedInstanceState != null) {
            roleNames = savedInstanceState.getStringArrayList("data");
        }
        else {
            roleNames = roleListDB.getAllNames();
        }
        listView = (ListView)findViewById(R.id.role_list);
        adapter = new RoleAdapter(RoleListActivity.this,R.layout.listview_item,roleNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = roleNames.get(position).trim();
                Role role = roleListDB.getRoleByName(name);
                RoleActivity.actionStart(RoleListActivity.this,role);
            }
        });
        EditText editText = (EditText)findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")) {
                    adapter = new RoleAdapter(RoleListActivity.this,R.layout.listview_item,roleNames);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String name = roleNames.get(position).trim();
                            Role role = roleListDB.getRoleByName(name);
                            RoleActivity.actionStart(RoleListActivity.this,role);
                        }
                    });
                } else {
                    tagNames = roleListDB.getNamesByTag(s.toString().trim());
                    adapter = new RoleAdapter(RoleListActivity.this,R.layout.listview_item,tagNames);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String name = tagNames.get(position).trim();
                            Role role = roleListDB.getRoleByName(name);
                            RoleActivity.actionStart(RoleListActivity.this,role);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_list);
        initializeViews(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("data",roleNames);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context,RoleListActivity.class);
        context.startActivity(intent);
    }
}
