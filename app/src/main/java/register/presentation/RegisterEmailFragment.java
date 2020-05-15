package register.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.joaquim.instagramfake.R;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import commom.view.AbstractFragment;
import commom.view.LoadingButton;

public class RegisterEmailFragment extends AbstractFragment implements RegisterView.EmailView {

    @BindView(R.id.register_edit_text_email_input)
    TextInputLayout inputLayoutEmail;

    @BindView(R.id.register_edit_text_email)
    EditText editTextEmail;

    @BindView(R.id.register_button_next)
    LoadingButton buttonNext;

    public RegisterEmailFragment(){}

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_email;
    }

    @OnClick(R.id.register_button_next)
    public void onButtonNextClick(){

    }

    @OnClick(R.id.register_text_view_email_login)
    public void onTextViewLoginClick(){
        if (isAdded() && getActivity() != null) {
            getActivity().finish();
        }
    }

    @OnTextChanged(R.id.register_edit_text_email)
    public void onTextChanged(CharSequence s) {
        buttonNext.setEnabled(!editTextEmail.getText().toString().isEmpty());

        editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background));
        inputLayoutEmail.setError(null);
        inputLayoutEmail.setErrorEnabled(false);
    }

    @Override
    public void onFailureForm(String emailError) {

    }

}
