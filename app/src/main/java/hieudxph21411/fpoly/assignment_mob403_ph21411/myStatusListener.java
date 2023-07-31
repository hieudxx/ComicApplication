package hieudxph21411.fpoly.assignment_mob403_ph21411;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class myStatusListener implements Response.ErrorListener {
    private OnStatus listener;

    @Override
    public void onErrorResponse(VolleyError error) {
        int statusCode = 0;
        String message = "";
        JSONObject json = null;
        if (error.networkResponse != null) {
            NetworkResponse response = error.networkResponse;
            statusCode = error.networkResponse.statusCode;

            switch (statusCode) {
                case 200:
                    setMsg(message, json, response);
                    break;
                case 301:
//                    Moved Permanently: Trang đã được chuyển vĩnh viễn sang địa chỉ mới
                    setMsg(message, json, response);
                    break;
                case 400:
//                    Bad Request: Yêu cầu không hợp lệ
                    setMsg(message, json, response);
                    break;
                case 401:
//                    Unauthorized: Chưa xác thực
                    setMsg(message, json, response);
                    break;
                case 403:
//                    Unauthorized: Chưa xác thực
                    setMsg(message, json, response);
                    break;
                case 404:
//                    Not Found: Không tìm thấy trang
                    setMsg(message, json, response);
                    break;
                case 500:
//                    Not Found: Không tìm thấy trang
                    setMsg(message, json, response);
                    break;
                default:
                    break;
            }
        }
    }

    public myStatusListener(OnStatus listener) {
        this.listener = listener;
    }
    private void setMsg(String message, JSONObject json, NetworkResponse response){
        try {
            json = new JSONObject(new String(response.data));
            message = json.getString("message");
        } catch (Exception e) {
            e.printStackTrace();
        }
        listener.onStatus(message);
    }

    public interface OnStatus{ // interface để truyền dữ liệu ra ngoài
        void onStatus(String message);
    }
}
