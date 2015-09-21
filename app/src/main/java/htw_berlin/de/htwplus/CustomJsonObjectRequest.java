package htw_berlin.de.htwplus;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class CustomJsonObjectRequest extends JsonObjectRequest {

    public CustomJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/vnd.collection+json; charset=utf-8");
        headers.put("Content-Type", "application/vnd.collection+json; charset=utf-8");
        return headers;
    }

    @Override
    public String getBodyContentType() {
        return "application/vnd.collection+json; charset=utf-8";
    }

    @Override
    protected Map<String, String> getPostParams() throws AuthFailureError {
        // TODO Auto-generated method stub
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", "value");
        return params;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        // here you can write a custom retry policy
        return super.getRetryPolicy();
    }

    /*
    Volley comes with a few predefined popular types of requests for ease of use, but
    you can always create your own. I suggest making a custom request based on
    JsonObjectRequest, but with a different implementation of the parseNetworkResponse()
    method that does not expect the response to be a JSON object, since you aren't receiving
    one. You can add any other changes you need there.
    http://android.phonesdevelopers.com/657_20092545/
    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            if (response.data.length > 0) {
                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException ueex) {

        }
    }
    */

    public static Map<String, Object> toMap(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new Gson().fromJson(String.valueOf(jsonObject.getJSONObject("collection")), new TypeToken<HashMap<String, Object>>() {}.getType());
        return map;
    }

}
