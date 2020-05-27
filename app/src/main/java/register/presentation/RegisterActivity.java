package register.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.joaquim.instagramfake.R;

import butterknife.BindView;
import commom.view.AbstractActivity;
import main.presentation.MainActivity;
import register.datasource.RegisterLocalDataSource;

public class RegisterActivity extends AbstractActivity implements RegisterView {

    @BindView(R.id.register_scroolview)
    ScrollView scrollView;

    private RegisterPresenter presenter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDak();

    }

    @Override
    protected void onInject() {
        RegisterLocalDataSource dataSource = new RegisterLocalDataSource();
        presenter = new RegisterPresenter(dataSource);
        presenter.setRegisterView(this);

        showNextView(RegisterSteps.EMAIL);


    }

    @Override
    public void showNextView(RegisterSteps step) {
        Fragment frag = null;

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scrollView.getLayoutParams();

        switch (step) {
            case EMAIL:
                layoutParams.gravity = Gravity.BOTTOM;
                frag = RegisterEmailFragment.newInstance(presenter);
                break;
            case NAME_PASSWORD:
                layoutParams.gravity = Gravity.BOTTOM;
                frag = RegisterNamePasswordFragment.newInstance(presenter);
                break;
            case WELCOME:
                layoutParams.gravity = Gravity.BOTTOM;
                frag = RegisterWelcomeFragment.newInstance(presenter);
                break;
            case PHOTO:
                layoutParams.gravity = Gravity.TOP;
                frag = RegisterPhotoFragment.newInstance(presenter);
                break;
        }

        scrollView.setLayoutParams(layoutParams);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (manager.findFragmentById(R.id.register_fragment) == null) {
            transaction.add(R.id.register_fragment, frag, step.name());
        } else {
            transaction.replace(R.id.register_fragment, frag, step.name());
            transaction.addToBackStack(step.name());
        }

        transaction.commit();
    }

    @Override
    public void onUserCreated() {
        MainActivity.launch(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

}
