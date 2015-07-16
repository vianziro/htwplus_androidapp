package htw_berlin.de.htwplus.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import net.hamnaberg.json.Collection;

import java.util.ArrayList;
import java.util.List;

import htw_berlin.de.htwplus.ApplicationController;
import htw_berlin.de.htwplus.PostAdapter;
import htw_berlin.de.htwplus.R;
import htw_berlin.de.htwplus.datamodel.ApiError;
import htw_berlin.de.htwplus.datamodel.Post;
import htw_berlin.de.htwplus.datamodel.User;
import htw_berlin.de.htwplus.util.JsonCollectionHelper;

public class PostListViewActivity extends Activity implements Response.Listener, Response.ErrorListener {

    private ArrayList<Post> mPostlist;
    private ArrayList<Post> mPostCommentlist;
    private ArrayList<User> mUserlist;
    private PostAdapter mPostAdapter;
    private ListView mlistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list_view);
        mPostlist = new ArrayList<Post>();
        mPostCommentlist = new ArrayList<Post>();
        mUserlist = new ArrayList<User>();
        ApplicationController.getVolleyController().getPostsFromNewsstream(this, this, this);
        ApplicationController.getVolleyController().getUsers(this, this, this);
        mlistview = (ListView) findViewById(R.id.list);
        mPostAdapter = new PostAdapter(this, R.layout.post_listview_item_row, mPostlist, mUserlist);
        mlistview.setAdapter(mPostAdapter);
        /*
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ShowUserActivity.class);
                intent.putExtra("Firstname", mPostlist.get(position).getFirstName());
                intent.putExtra("Lastname", mPostlist.get(position).getLastName());
                intent.putExtra("Email", mPostlist.get(position).getEmail());
                intent.putExtra("Class", mPostlist.get(position).getClass());
                UserListViewActivity.this.startActivity(intent);
            }
        });
        */
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error != null) {
            Toast.makeText(getApplicationContext(), "Error\n", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error is emtpy\n", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        try {
            Collection collection = JsonCollectionHelper.parse(response.toString());
            if (!JsonCollectionHelper.hasError(collection)) {
                if (collection.getHref().get().getPath().contains("api/posts")) {
                    List<Post> posts = JsonCollectionHelper.toPosts(collection);
                    for (Post post : posts) {
                        if (post.isCommentPost())
                            mPostCommentlist.add(post);
                        else
                            mPostlist.add(post);
                    }
                } else if (collection.getHref().get().getPath().contains("api/users")) {
                    List<User> users = JsonCollectionHelper.toUsers(collection);
                    for (User user : users)
                        mUserlist.add(user);
                }
                mPostAdapter.notifyDataSetChanged();
            } else {
                ApiError apiError = JsonCollectionHelper.toError(collection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
