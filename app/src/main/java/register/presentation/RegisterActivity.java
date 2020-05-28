package register.presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.joaquim.instagramfake.R;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.OnClick;
import commom.component.MediaHelper;
import commom.view.AbstractActivity;
import main.presentation.MainActivity;
import register.datasource.RegisterLocalDataSource;

public class RegisterActivity extends AbstractActivity implements RegisterView, MediaHelper.OnImageCroppedListener {

    @BindView(R.id.register_root_container)
    FrameLayout rootContainer;

    @BindView(R.id.register_scroolview)
    ScrollView scrollView;

    @BindView(R.id.register_crop_image_view)
    CropImageView cropImageView;

    @BindView(R.id.register_button_crop)
    Button buttonCrop;

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

        switch (step) {
            case EMAIL:
                frag = RegisterEmailFragment.newInstance(presenter);
                break;
            case NAME_PASSWORD:
                frag = RegisterNamePasswordFragment.newInstance(presenter);
                break;
            case WELCOME:
                frag = RegisterWelcomeFragment.newInstance(presenter);
                break;
            case PHOTO:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) scrollView.getLayoutParams();
                layoutParams.gravity = Gravity.TOP;
                scrollView.setLayoutParams(layoutParams);
                frag = RegisterPhotoFragment.newInstance(presenter);
                break;
        }

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
    public void onImageCropped(Uri uri) {
        presenter.setUri(uri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        cropViewEnabled(true);
        MediaHelper mediaHelper = MediaHelper.getInstance(this);
        mediaHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUserCreated() {
        MainActivity.launch(this);
    }

    @Override
    public void showCamera() {
        MediaHelper.getInstance(this)
                .cropView(cropImageView)
                .listener(this)
                .chooserCamera();
    }

    @Override
    public void showGallery() {
        MediaHelper.getInstance(this)
                .cropView(cropImageView)
                .listener(this)
                .chooserGallery();
    }

    @OnClick(R.id.register_button_crop)
    public void onButtonCropClick() {
        cropViewEnabled(false);
        MediaHelper.getInstance(this).cropImage();
    }

    private void cropViewEnabled(boolean enabled) {
        scrollView.setVisibility(enabled ? View.GONE : View.VISIBLE);
        buttonCrop.setVisibility(enabled ? View.VISIBLE : View.GONE);
        rootContainer.setBackgroundColor(enabled ? findColor(android.R.color.black) : findColor(android.R.color.white));

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }
}
