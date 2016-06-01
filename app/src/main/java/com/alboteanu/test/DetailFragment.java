package com.alboteanu.test;

import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class DetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private String itemId;
    private DatabaseReference itemRef;
    private Person person;
    private TextView detail_view;
    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy name specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load name from a name provider.
//            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            itemId = getArguments().getString(ARG_ITEM_ID);
            itemRef = Utils.getDatabase().getReference().child(getString(R.string.persons_node)).child(itemId);
            itemRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    person = dataSnapshot.getValue(Person.class);
                    if(person !=null) {
                        detail_view.setText(person.getDetail_1());
                        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
                        if (appBarLayout != null) {
                            appBarLayout.setTitle(person.getName());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
                  /*  .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    person = dataSnapshot.getValue(Person.class);
                    if(person!=null){
                       detail_view.setText(person.getDetail_1());
                        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
                        if (appBarLayout != null) {
                            appBarLayout.setTitle(person.getName());
                        }
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });*/

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail, container, false);
        detail_view =  ((TextView) rootView.findViewById(R.id.item_detail_1_text_view));
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        itemRef.removeEventListener(itemEventListener);
    }
}
