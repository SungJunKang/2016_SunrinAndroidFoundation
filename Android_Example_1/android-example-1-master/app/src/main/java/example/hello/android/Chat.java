package example.hello.android;

/**
 * @Author: Hyeonsu Park(hyeonsupark)
 */

import android.app.Activity;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Chat {
    private Context context;
    private Socket socket;

    private String nickname;

    private Listener messageListener;

    public Chat() {

    }

    public Chat(Context context, String nickname) {
        try {
            socket = IO.socket("http://ztz.kr:3333");
        } catch (URISyntaxException e) {
            e.printStackTrace();

        }
        socket.connect();

        this.context = context;
        this.nickname = nickname;

    }


    public void disconnect() {
        if (socket != null && socket.connected()) {
            socket.disconnect();
        }
    }
    // 메소드 이름이 같아도 매개변수가 다르면 역할 재정의 할 수 있다.
    // 클래스를 만들 때는 생성자를 만들어주고
    public void sendMessage(String message) {
        JSONObject chatObject = new JSONObject();
        try {
            chatObject.put("nickname", nickname);
            chatObject.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("send message", chatObject);


    }

    interface Listener {
        void receive(String nickname, String message, String nicknameColor, long timestamp);
    }

    public void setMessageListener(Listener listener) {
        messageListener = listener;
        socket.on("receive message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONObject chatObject = (JSONObject) args[0];
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            messageListener.receive(chatObject.getString("nickname"),
                                    chatObject.getString("message"),
                                    chatObject.getString("color"),
                                    chatObject.getLong("timestamp") * 1000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

}

