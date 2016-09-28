package scu.book.campus.com.campusbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import scu.book.campus.com.campusbook.Constants.SharedData;
import scu.book.campus.com.campusbook.adapter.FireBaseAdapter;
import scu.book.campus.com.campusbook.adapter.ImageListAdapter;
import scu.book.campus.com.campusbook.model.Books;

/**
 * Created by kushahuja on 5/18/16.
 */
public class Home extends Fragment implements View.OnClickListener, TextWatcher {

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    ViewPager mViewPager;

    public EditText edtSearch;
    private List<Books> booksList = new ArrayList<>();
    Firebase firebaseRef;
    RecyclerView imageListView;
    ImageListAdapter adapter;
   SharedPreferences myPrefs ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.search_page, container, false);

        myPrefs= getContext().getSharedPreferences("myPrefs", getContext().MODE_PRIVATE);
        edtSearch = (EditText) rootView.findViewById(R.id.edt_tags_search);
        edtSearch.setOnClickListener(this);
        edtSearch.addTextChangedListener(this);

        imageListView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mViewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        adapter = new ImageListAdapter(getContext());
        imageListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        System.out.println("Book name is " + adapter.getItemCount());

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        imageListView.setLayoutManager(gridLayoutManager);
        imageListView.setItemAnimator(new DefaultItemAnimator());
        imageListView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), imageListView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Gson gson = new Gson();
                String json = gson.toJson(SharedData.booksList.get(position));
                Log.d("Book json", json);

                SharedPreferences.Editor prefsEditor = myPrefs.edit();
                prefsEditor.putString("selectedbook", json);
                prefsEditor.commit();

                Buyer.bookSelected= true;

                mViewPager.setCurrentItem(1);
                System.out.println("Hello grid item clicked" + position);



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return rootView;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String searchString = s.toString();

        if (searchString.trim().length() > 0) {
        } else {
        }

    }

    @Override
    public void onClick(View v) {

    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Home.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Home.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }
}
