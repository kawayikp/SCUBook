package scu.book.campus.com.campusbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import scu.book.campus.com.campusbook.R;
import scu.book.campus.com.campusbook.model.Books;
import scu.book.campus.com.campusbook.model.BuyerList;

/**
 * Created by Kawayipk on 5/25/16.
 */
public class BuyerBookListAdapter extends ArrayAdapter<BuyerList> {
    private final List<BuyerList> books;
    private Context ctxt;

    public BuyerBookListAdapter(Context context, int resource, List<BuyerList> books) {
        super(context, resource, books);
        this.books = books;
        ctxt = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ScrapViewHolder holder;

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.buyer_book_record_card, parent, false);

            holder = new ScrapViewHolder();
            holder.bookname = (TextView) row.findViewById(R.id.bookname);
            holder.bookprice = (TextView) row.findViewById(R.id.bookprice);

            row.setTag(holder);

        } else {
            holder = (ScrapViewHolder) row.getTag();
        }

        holder.bookname.setText(books.get(position).getBookName());
        holder.bookprice.setText(books.get(position).getBookPrice());

        return row;
    }

    public class ScrapViewHolder {
        TextView bookname;
        TextView bookprice;

    }
}
