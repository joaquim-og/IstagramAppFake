package register.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.joaquim.instagramfake.R;

import butterknife.BindView;
import butterknife.OnClick;
import commom.view.AbstractFragment;
import commom.component.LoadingButton;

public class RegisterWelcomeFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.WelcomeView {

    @BindView(R.id.register_button_next)
    LoadingButton buttonNext;
    @BindView(R.id.register_text_view_welcome)
    TextView textViewWelcome;



    public static RegisterWelcomeFragment newInstance(RegisterPresenter presenter) {
        RegisterWelcomeFragment fragment = new RegisterWelcomeFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

   public RegisterWelcomeFragment(){}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonNext.setEnabled(true);
        textViewWelcome.setText(getString(R.string.welcome_to_instagram, presenter.getName()));
    }
    
    @OnClick(R.id.register_button_next)
    public void onButtonNextClick() {
       presenter.showPhotoView();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_welcome;
    }
}
