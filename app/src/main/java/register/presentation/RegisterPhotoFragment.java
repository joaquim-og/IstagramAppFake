package register.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.joaquim.instagramfake.R;

import butterknife.BindView;
import butterknife.OnClick;
import commom.view.AbstractFragment;
import commom.view.CustomDialog;
import commom.view.LoadingButton;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {

    @BindView(R.id.register_button_next)
    LoadingButton buttonNext;

    public static RegisterPhotoFragment newInstance(RegisterPresenter presenter) {
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();

        fragment.setPresenter(presenter);

        return fragment;
    }

    public RegisterPhotoFragment () {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNext.setEnabled(true);

//        CustomDialog customDialog = new CustomDialog.Builder(getContext())
//                .setTitle(R.string.define_photo_profile)
//                .addButton((v) -> {
//                }, R.string.take_picture, R.string.search_gallery)
//                .build();
//
//        customDialog.show();
    }

    @OnClick(R.id.register_button_next)
    public void onButtonNexClick() {
        // TODO:
    }

    @OnClick (R.id.register_button_jump)
    public void onButtonJumpClick() {
        presenter.jumpRegistration();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_photo;
    }
}
