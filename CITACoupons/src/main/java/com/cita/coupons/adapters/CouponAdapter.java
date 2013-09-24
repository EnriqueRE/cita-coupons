package com.cita.coupons.adapters;

import com.cita.coupons.R;
import com.cita.coupons.models.Coupon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CouponAdapter extends BaseAdapter {

    ArrayList<Coupon> DATA;

    private LayoutInflater mInflater;


    public CouponAdapter(Context context, ArrayList<Coupon> DATA) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        mInflater = LayoutInflater.from(context);
        this.DATA = DATA;

    }

    /**
     * @see android.widget.ListAdapter getCount()
     */
    public int getCount() {
        return DATA.size();
    }

    /**
     * @see android.widget.ListAdapter getItem(int)
     */
    public Object getItem(int position) {
        return DATA.get(position);
    }

    /**
     * Use the array index as a unique id.
     *
     * @see android.widget.ListAdapter getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Make a view to hold each row.
     *
     * @see android.widget.ListAdapter getView(int, android view View, android view ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unneccessary
        // calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no
        // need
        // to reinflate it. We only inflate a new View when the convertView
        // supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_coupon, null);

            // Creates a ViewHolder and store references to the two children
            // views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.article_title);
            holder.subtitle = (TextView) convertView
                    .findViewById(R.id.article_byline);
            holder.thumbnail = (ImageView) convertView
                    .findViewById(R.id.photo);

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(DATA.get(position).getCompany());
        holder.subtitle.setText(DATA.get(position).getDescription());
        holder.thumbnail.setImageResource(DATA.get(position).getLogo());

        return convertView;
    }

    static class ViewHolder {

        TextView title;

        TextView subtitle;

        ImageView thumbnail;
    }

}
