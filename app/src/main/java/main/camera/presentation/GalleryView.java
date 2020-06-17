package main.camera.presentation;

import android.net.Uri;

import java.util.List;

import commom.view.View;

public interface GalleryView extends View {

    void onPicturesLoaded(List<Uri> uris);

}
