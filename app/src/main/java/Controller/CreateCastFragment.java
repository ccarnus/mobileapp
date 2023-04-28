package Controller;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.io.File;

import Model.FileModel;
import Model.FileUtils;
import Model.HttpService;
import Model.RetrofitBuilder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCastFragment extends Fragment {

    private CardView cardView;
    private ImageView imageView;
    private Button button;
    private CharSequence[] options= {"Camera","Gallery","Cancel"};
    private String selectedVideo;

    public CreateCastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_cast, container, false);

        cardView = view.findViewById(R.id.cardview);
        imageView = view.findViewById(R.id.imageview);
        button = view.findViewById(R.id.button);

        requirePermission();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select Image");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(options[which].equals("Camera")){
                            Intent takePic = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            startActivityForResult(takePic, 0);
                        }
                        else if(options[which].equals("Gallery")) {
                            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gallery, 1);
                        }
                        else {
                            dialog.dismiss();
                        }
                    }
                });

                builder.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFileToServer();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode !=RESULT_CANCELED){

            switch (requestCode){
                case 0:
                    if(resultCode == RESULT_OK && data !=null){
                        Uri videoUri = data.getData();
                        if (videoUri != null) {
                            String[] projection = { MediaStore.Video.Media.DATA };
                            Cursor cursor = getActivity().getContentResolver().query(videoUri, projection, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                                selectedVideo = cursor.getString(columnIndex);
                                cursor.close();
                                // Use the videoPath here
                                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                                retriever.setDataSource(selectedVideo);
                                Bitmap frame = retriever.getFrameAtTime(1000);
                                imageView.setImageBitmap(frame);
                            } else {
                                // Handle null cursor or empty result set
                            }
                        } else {
                            // Handle null videoUri
                        }
                    }
                    break;
                case 1:
                    if(resultCode == RESULT_OK && data !=null){

                        Uri video = data.getData();
                        selectedVideo = FileUtils.getPath(getContext(),video);
                        //Picasso.get().load(video).into(imageView);
                        // Use the videoPath here
                        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                        retriever.setDataSource(selectedVideo);
                        Bitmap frame = retriever.getFrameAtTime(1000);
                        imageView.setImageBitmap(frame);
                    }
            }

        }
    }

    /*public Uri getImageUri(Context context, Bitmap bitmap){
        String path = MediaStore.Video.Media(context.getContentResolver(), bitmap, "myImage","");

        return Uri.parse(path);
    }*/


    public void requirePermission(){
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }


    public void uploadFileToServer(){

        File file = new File(Uri.parse(selectedVideo).getPath());

        String castJson = "{\"title\":\"My Video Title\",\"description\":\"My Video Description\",\"department\":\"My Video Department\",\"type\":\"My Video Type\",\"brightmindid\":\"12345\",\"category\":\"My Video Category\",\"university\":\"My Video University\"}";
        RequestBody castRequestBody = RequestBody.create(MediaType.parse("application/json"), castJson);

        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        String contentType = "video/" + extension;
        RequestBody requestBody = RequestBody.create(MediaType.parse(contentType), file);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("video",file.getName(),requestBody);

        HttpService service = RetrofitBuilder.getClient().create(HttpService.class);

        Call<FileModel> call = service.createCast(castRequestBody, filePart);
        call.enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                FileModel fileModel = response.body();
                Log.d("clement",String.valueOf(fileModel.getStatus()));
                Toast.makeText(getContext(), String.valueOf(fileModel.getMessage()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}