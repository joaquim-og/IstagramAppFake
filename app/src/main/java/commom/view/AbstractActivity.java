package commom.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.joaquim.instagramfake.R;
import com.joaquim.instagramfake.login.presentation.LoginActivity;

import butterknife.ButterKnife;
import commom.util.Drawables;

public abstract class AbstractActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    public Drawable findDrawable(@DrawableRes int drawableId) {
        return Drawables.getDrawable(this, drawableId);
    }

    protected abstract @LayoutRes int getLayout();

}
