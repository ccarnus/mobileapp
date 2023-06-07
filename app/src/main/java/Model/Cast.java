package Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Cast implements Serializable {

    private String title;
    private String description;
    private String department;
    private String category;
    private String type;
    private String brightmindid;
    private String casturl;
    private String university;
    private Integer count_likes;
    private String[] likers_list;
    private Integer count_comments;
    private List<Comment> commentList;
    private String id;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBrightmindid(String brightmindid) {
        this.brightmindid = brightmindid;
    }

    public void setCasturl(String casturl) {
        this.casturl = casturl;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Integer getCount_likes() {
        return count_likes;
    }

    public void setCount_likes(Integer count_likes) {
        this.count_likes = count_likes;
    }

    public String[] getLikers_list() {
        return likers_list;
    }

    public void setLikers_list(String[] likers_list) {
        this.likers_list = likers_list;
    }

    public Integer getCount_comments() {
        return count_comments;
    }

    public void setCount_comments(Integer count_comments) {
        this.count_comments = count_comments;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Cast(String id, String title, String description, String department, String category, String type, String brightmindid, String casturl, String university, Integer count_likes, String[] likers_list, Integer count_comments, List<Comment> commentList) {
        this.title = title;
        this.brightmindid = brightmindid;
        this.department = department;
        this.category = category;
        this.type = type;
        this.casturl = casturl;
        this.university = university;
        this.description = description;
        this.count_likes = count_likes;
        this.likers_list = likers_list;
        this.count_comments = count_comments;
        this.commentList = commentList;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartment() {
        return department;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getBrightmindid() {
        return brightmindid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCasturl() {
        return casturl;
    }

    public String getUniversity() {
        return university;
    }

    public void LikeCast(Context context, String id, String email) {
        String url = "http://3.17.219.54:3000/cast/like/" + id;

        // Create a new JsonObject to hold the user data to be updated
        JSONObject userData = new JSONObject();
        try {
            userData.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a new JsonObjectRequest with the data and the endpoint
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, userData,
                response -> {
                    // Success response
                    Log.d("Response", "Like added");
                },
                error -> {
                    // Error response
                    Log.e("Response Error", error.getMessage());
                });

        // Add the request to the request queue using the Volley library
        Volley.newRequestQueue(context).add(postRequest);
    }

    public void CommentCast(Context context) {
        Toast.makeText(context, "Cast Comment in progress", Toast.LENGTH_SHORT).show();
    }

    public void ShareCast(Context context) {
        Toast.makeText(context, "Cast Share in progress", Toast.LENGTH_SHORT).show();
    }

    public static class Comment {
        private String author;
        private String content;

        public Comment(String author, String content) {
            this.author = author;
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }
    }
}
