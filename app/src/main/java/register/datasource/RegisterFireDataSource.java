package register.datasource;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import commom.model.User;
import commom.presenter.Presenter;

public class RegisterFireDataSource implements RegisterDataSource {

    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setUuid(authResult.getUser().getUid());

                    FirebaseFirestore.getInstance().collection("user")
                            .document(authResult.getUser().getUid())
                            .set(user)
                            .addOnSuccessListener(aVoid -> presenter.onSuccess(authResult.getUser()))
                            .addOnCompleteListener(task -> presenter.onComplete());
                })
                .addOnFailureListener(e -> {
                    presenter.onError(e.getMessage());
                    presenter.onComplete();
                });
    }

    @Override
    public void addPhoto(Uri uri, Presenter presenter) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uri == null || uri == null || uri.getLastPathSegment() == null)
            return;

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imgRef = storageRef.child("images/")
                .child(FirebaseAuth.getInstance().getLanguageCode())
                .child(uri.getLastPathSegment());

        imgRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    long totalByteCount = taskSnapshot.getTotalByteCount();
                    Log.i("teste", "file upload size: " + totalByteCount);

                    imgRef.getDownloadUrl()
                            .addOnSuccessListener(uriResponse -> {
                                DocumentReference docUser = FirebaseFirestore.getInstance().collection("user").document(uid);
                                docUser.get()
                                        .addOnSuccessListener(documentSnapshot -> {
                                            User user = documentSnapshot.toObject(User.class);
                                            user.setPhotoUrl(uriResponse.toString());

                                            docUser.set(user)
                                                    .addOnSuccessListener(aVoid -> {
                                                        presenter.onSuccess(true);
                                                        presenter.onComplete();
                                                    });
                                        });

                            });
                });
    }

}
