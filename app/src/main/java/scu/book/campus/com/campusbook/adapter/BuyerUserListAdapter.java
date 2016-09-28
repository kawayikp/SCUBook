package scu.book.campus.com.campusbook.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import scu.book.campus.com.campusbook.R;
import scu.book.campus.com.campusbook.model.User;

/**
 * Created by Kawayipk on 5/25/16.
 */
public class BuyerUserListAdapter extends ArrayAdapter<User> {


    private final ArrayList<User> buyers;
    private Context ctxt;

    public BuyerUserListAdapter(Context context, int resource, ArrayList<User> buyers) {
        super(context, resource, buyers);
        this.buyers = buyers;
        ctxt = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ScrapViewHolder holder;

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.buyer_user_record_card, parent, false);

            holder = new ScrapViewHolder();
            holder.username = (TextView) row.findViewById(R.id.username);
            holder.useremail = (TextView) row.findViewById(R.id.useremail);

            row.setTag(holder);

        } else {
            holder = (ScrapViewHolder) row.getTag();
        }

        holder.username.setText(buyers.get(position).getName());
        holder.useremail.setText(buyers.get(position).getEmail());

        return row;
    }

    public class ScrapViewHolder {
        TextView username;
        TextView useremail;

    }
}
