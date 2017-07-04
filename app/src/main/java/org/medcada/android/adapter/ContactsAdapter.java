package org.medcada.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.medcada.android.R;
import org.medcada.android.object.Contacts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shariq Khan on 6/7/2017.
 */

public class ContactsAdapter extends BaseAdapter {
    Context context;
    ArrayList<Contacts> list;

    public ContactsAdapter(Context context, ArrayList<Contacts> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lay_contact, null);
        ViewHolder holder = new ViewHolder(convertView);
        holder.tvName.setText(list.get(position).getName());
        holder.tvNumber.setText(list.get(position).getNumber());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_number)
        TextView tvNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
