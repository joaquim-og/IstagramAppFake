package commom.component;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.core.content.FileProvider;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

//import androidx.fragment.app.Fragment;

public class MediaHelper {

    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;

    private static WeakReference<MediaHelper> INSTANCE;
    private Activity activity;
    private Fragment fragment;
    private Uri mCropimageURI;
    private OnImageCroppedListener listener;
    private Uri mSavedImageUri;

    public MediaHelper cropView(CropImageView cropImageView) {
        cropImageView.setAspectRatio(1, 1);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setOnCropImageCompleteListener((view, result) -> {
            Uri uri = result.getUri();
            if (uri != null && listener != null) {
                listener.onImageCropped(uri);
                cropImageView.setVisibility(View.GONE);
            }
            
        });
        
        return this;
    }

    public static MediaHelper getInstance(Activity activity){
        if (INSTANCE == null){
            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setActivity(activity);
        }
        return INSTANCE.get();
    }

    public static MediaHelper getInstance(Fragment fragment){
        if (INSTANCE == null){
            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setFragment(fragment);
        }
        return INSTANCE.get();
    }

    public void chooserGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, GALLERY_CODE);
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        SharedPreferences myPrefes = getContext().getSharedPreferences("camera_image", 0);
        String url = myPrefes.getString("url", null);

        if (mCropimageURI == null && url != null){
            mCropimageURI = Uri.parse(url);
        }

        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), mCropimageURI)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (activity != null){
                        activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                    } else {
                        fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                    }
                }
            } else {
                listener.onImagePicked(mCropimageURI);
            }
        } else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            mCropimageURI = CropImage.getPickImageResultUri(getContext(), data);
            listener.onImagePicked(mCropimageURI);
        }
    }

    public MediaHelper listener(OnImageCroppedListener listener) {
        this.listener = listener;

        return this;
    }

    private Context getContext() {
        if (fragment != null && fragment.getActivity() != null)
            return fragment.getActivity();
        return activity;
    }

    public void cropImage(CropImageView cropImageView) {
        File getImage = getContext().getExternalCacheDir();

        if (getImage != null) {
            mSavedImageUri = Uri.fromFile(new File(getImage.getPath(), System.currentTimeMillis() + ".jpeg"));
        }

        cropImageView.saveCroppedImageAsync(mSavedImageUri);
    }

    public void chooserCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (i.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {

            }

            if (photoFile != null) {
                mCropimageURI = FileProvider.getUriForFile(getContext(), "com.joaquim.instagramfake.fileprovider", photoFile);

                SharedPreferences myPrefes = getContext().getSharedPreferences("camera_image", 0);
                SharedPreferences.Editor edit = myPrefes.edit();
                edit.putString("url", mCropimageURI.toString());
                edit.apply();

                i.putExtra(MediaStore.EXTRA_OUTPUT, mCropimageURI);
                activity.startActivityForResult(i, CAMERA_CODE);
            }

        }
    }

    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_"+ timestamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    public interface OnImageCroppedListener {
        void onImageCropped(Uri uri);

        void onImagePicked(Uri uri);
    }
    
}
