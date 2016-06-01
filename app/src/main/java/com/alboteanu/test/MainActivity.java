package com.alboteanu.test;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DetailActivity} representing
 * person details. On tablets, the activity presents the list of items and
 * person details side-by-side using two vertical panes.
 */
public class MainActivity extends BaseActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    FirebaseRecyclerAdapter<Person, ItemViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = AddPersonFragment.newInstance();
                dialog.show(MainActivity.this.getFragmentManager(), "AddPersonFragment");
            }
        });

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupFirebaseRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.container_detail) != null) {
            // The detail_1 container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }



    private void setupFirebaseRecyclerView(@NonNull RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        final Firebase ref = new Firebase(Constants.FIREBASE_ROOT).child(Constants.FIREBASE_ITEMS);
        DatabaseReference mDatabase = Utils.getDatabase().getReference();
//        Query postsQuery = getQuery(mDatabase.child(Constants.FIREBASE_ITEMS));
        Query postsQuery = mDatabase.child(getString(R.string.persons_node));
        adapter = new FirebaseRecyclerAdapter<Person, ItemViewHolder>(Person.class, R.layout.person_layout, ItemViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final ItemViewHolder itemViewHolder, final Person person, int i) {
                itemViewHolder.name.setText(person.getName());
//                itemViewHolder.detail_1.setText(person.getDetail_1());
                itemViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String itemId = getRef(itemViewHolder.getAdapterPosition()).getKey() ;
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString(DetailFragment.ARG_ITEM_ID, itemId);
                            DetailFragment fragment = new DetailFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container_detail, fragment)
                                    .commit();
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, DetailActivity.class);
                            intent.putExtra(DetailFragment.ARG_ITEM_ID, itemId);
                            context.startActivity(intent);
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView name;
//        public final TextView detail_1;
        public Person person;
        public ItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
//            detail_1 = (TextView) itemView.findViewById(R.id.detail_1);
            view = itemView;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }

//    public abstract Query getQuery(DatabaseReference databaseReference);
}
