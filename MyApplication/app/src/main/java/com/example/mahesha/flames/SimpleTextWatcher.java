package com.example.mahesha.flames;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * A class whose objects require only one method to be overriden, namely, onAfterTextChanged. It is
 * meant to cut down on the verbosity of using a TextWatcher object for an EditText text changed
 * listener.
 *
 * @author Daniel Dyba
 * @since 0.0.22
 */
public abstract class SimpleTextWatcher implements TextWatcher {

    public abstract void onAfterTextChanged(Editable s);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Don't care
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Don't care
    }

    @Override
    public void afterTextChanged(Editable s) {
        onAfterTextChanged(s);
    }
}
