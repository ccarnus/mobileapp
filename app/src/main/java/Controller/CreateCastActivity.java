package Controller;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.R;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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

public class CreateCastActivity extends AppCompatActivity {

    private CardView cardView;
    private ImageView imageView;
    private Button button;
    private CharSequence[] options = {"Camera", "Gallery", "Cancel"};
    private String selectedVideo;
    private String university_selected;
    private String type_selected;
    private String department_selected;
    private String category_selected;
    private EditText title;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cast);

        cardView = findViewById(R.id.create_cast_cardview);
        imageView = findViewById(R.id.create_cast_imageview);
        button = findViewById(R.id.create_cast_button);
        title = findViewById(R.id.create_cast_title_edittext);
        description = findViewById(R.id.create_cast_description_edittext);

        // Spinner for Universities
        Spinner spinner_university = findViewById(R.id.create_cast_university_spinner);
        List<String> universities = Arrays.asList(getResources().getStringArray(R.array.universities));

        ArrayAdapter<String> adapter_universities = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, universities);
        adapter_universities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_university.setAdapter(adapter_universities);
        spinner_university.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                university_selected = universities.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner for Type
        Spinner spinner_type = findViewById(R.id.create_cast_type_spinner);
        List<String> types = Arrays.asList(getResources().getStringArray(R.array.type));

        ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter_type);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type_selected = types.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner for Department
        Spinner spinner_department = findViewById(R.id.create_cast_department_spinner);
        List<String> departments = Arrays.asList(getResources().getStringArray(R.array.department));

        ArrayAdapter<String> adapter_department = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departments);
        adapter_department.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_department.setAdapter(adapter_department);
        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                department_selected = departments.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner for Category
        Spinner spinner_category = findViewById(R.id.create_cast_category_spinner);
        List<String> categories = Arrays.asList(getResources().getStringArray(R.array.category));

        ArrayAdapter<String> adapter_category = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(adapter_category);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category_selected = categories.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        requirePermission();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateCastActivity.this);
                builder.setTitle("Select Image");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which].equals("Camera")) {
                            Intent takePic = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            startActivityForResult(takePic, 0);
                        } else if (options[which].equals("Gallery")) {
                            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gallery, 1);
                        } else {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri videoUri = data.getData();
                        if (videoUri != null) {
                            String[] projection = {MediaStore.Video.Media.DATA};
                            Cursor cursor = getContentResolver().query(videoUri, projection, null, null, null);
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
                    if (resultCode == RESULT_OK && data != null) {

                        Uri video = data.getData();
                        selectedVideo = FileUtils.getPath(CreateCastActivity.this, video);
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

    public void requirePermission() {
        ActivityCompat.requestPermissions(CreateCastActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public void uploadFileToServer() {

        if (validateField()) {
            File file = new File(Uri.parse(selectedVideo).getPath());

            String castJson = "{" +
                    "\"likes\": {\"count\": 0,\"user\": []},\"comments\": {\"count\": 0,\"comment\": []},"+
                    "\"title\":\""+title.getText()+
                    "\",\"description\":\""+description.getText()+
                    "\",\"department\":\"" + department_selected +
                    "\",\"type\":\"" + type_selected +
                    "\",\"brightmindid\":\"12345\",\"category\":\""
                    + category_selected +
                    "\",\"university\":\""+
                    university_selected + "\"}";
            RequestBody castRequestBody = RequestBody.create(MediaType.parse("application/json"), castJson);

            String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            String contentType = "video/" + extension;
            RequestBody requestBody = RequestBody.create(MediaType.parse(contentType), file);

            MultipartBody.Part filePart = MultipartBody.Part.createFormData("video", file.getName(), requestBody);

            HttpService service = RetrofitBuilder.getClient().create(HttpService.class);

            Call<FileModel> call = service.createCast(castRequestBody, filePart);
            call.enqueue(new Callback<FileModel>() {
                @Override
                public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                    FileModel fileModel = response.body();
                    //Toast.makeText(CreateCastActivity.this, String.valueOf(fileModel.getMessage()), Toast.LENGTH_SHORT).show();
                    Log.d("clement",String.valueOf(castJson));
                    Toast.makeText(CreateCastActivity.this, "Cast uploaded", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<FileModel> call, Throwable t) {
                    Toast.makeText(CreateCastActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(CreateCastActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateField() {
        boolean is_valid = true;

        if (TextUtils.isEmpty(title.getText())) {
            is_valid = false;
            title.setError("This field is required.");
        }

        if (TextUtils.isEmpty(description.getText())) {
            is_valid = false;
            description.setError("This field is required.");
        }

        return is_valid;
    }
}
