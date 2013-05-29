package com.example.dcc.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
<<<<<<< HEAD
=======
import com.example.dcc.R;
>>>>>>> 3297504beea7dfe1393710dd1ec7226b753e61cb
import com.example.dcc.helpers.ActionItem;
import com.example.dcc.helpers.ObjectStorage;
import com.example.dcc.helpers.mysql.MySQLQuery;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
<<<<<<< HEAD
=======

>>>>>>> 3297504beea7dfe1393710dd1ec7226b753e61cb


@SuppressLint("NewApi")
public class ActionItemFrag extends Fragment implements OnClickListener{

    ListView listview;

    ArrayAdapter<Spanned> adapter;
    List<ActionItem> actionItems;
    Activity activity;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        activity = getActivity();

        GetNewsTask g = new GetNewsTask();
        g.execute((Void) null);
        try {
            g.get(20, TimeUnit.SECONDS);
            ObjectStorage.setActionItems(actionItems);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


        listview = (ListView)view.findViewById(R.id.newslist);
        adapter = new ArrayAdapter<Spanned>(getActivity(), R.layout.news_item);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ActionItemDetailFrag detailFrag = new ActionItemDetailFrag();
                Bundle bundle = new Bundle();
                bundle.putSerializable("actionitem", actionItems.get(i));
                detailFrag.setArguments(bundle);

                FragmentManager manager = activity.getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                ObjectStorage.setFragment(R.id.fragmentcontainerright, detailFrag);
                transaction.replace(R.id.fragmentcontainerright, detailFrag);

                transaction.commit();
            }
        });

        for(ActionItem n : actionItems){
            adapter.add(Html.fromHtml(n.toString()));
        }

        return view;
    }

    @Override
    public void onClick(View view) {

    }


    public class GetNewsTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            actionItems  = MySQLQuery.getActionItems("/DCC/getActionItems.php");
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {
        }
    }





}
