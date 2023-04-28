package Model;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HttpService {

    @Multipart
    @POST("/cast")
    Call<FileModel> createCast(
            //@Part MultipartBody.Part image);
            @Part("cast") RequestBody castRequestBody,
            @Part MultipartBody.Part video
    );
}
