package com.alboteanu.test;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

/**
 * Adds a new shopping list
 */
public class AddPersonFragment extends DialogFragment {
    EditText editName;
    EditText editDetail_1;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static AddPersonFragment newInstance() {
        AddPersonFragment addListDialogFragment = new AddPersonFragment();
        Bundle bundle = new Bundle();
        addListDialogFragment.setArguments(bundle);
        return addListDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.add_person, null);
        editName = (EditText) rootView.findViewById(R.id.edit_text_list_name);
        editDetail_1 = (EditText) rootView.findViewById(R.id.edit_text_detail_1);

        /**
         * Call addShoppingList() when user taps "Done" keyboard action
         */
        editName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addShoppingList();
                }
                return true;
            }
        });

        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton(R.string.positive_button_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addShoppingList();
                    }
                });

        return builder.create();
    }



    public void addShoppingList() {
        DatabaseReference mDatabase = Utils.getDatabase().getReference();
        String userEnteredName = editName.getText().toString();
        String userEnteredDetail_1 = editDetail_1.getText().toString();
        Person newPerson = new Person(userEnteredName, userEnteredDetail_1);
        mDatabase.child(getString(R.string.persons_node)).push().setValue(newPerson);
    }
}

