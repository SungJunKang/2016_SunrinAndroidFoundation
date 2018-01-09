package example.hello.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int requestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.et_text);
        button = (Button)findViewById(R.id.btn_confirm);

        preferences = getSharedPreferences("pref",MODE_PRIVATE);
        // SharedPreferences  초기화, 매개변수에는 파일이름과 접근 권한
        editor = preferences.edit();
        // Editor 초기화 / SharedPreferences 함수로

        boolean isSavedNickname = false;
        String nickname = preferences.getString("key", "");
        isSavedNickname = !nickname.equals("");

        Intent fromIntent = getIntent();
        requestCode= fromIntent.getIntExtra("requestCode", 0);
        // ChatActivity 에서 넘어오면 100, 그게아니면 0

        if(requestCode == 0 && isSavedNickname){
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = editText.getText().toString();

                editor.putString("key", nickname);
                editor.apply();

                if(editor.commit()){
                    if(requestCode == 0) {
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        startActivity(intent);
                    }
                }
                else if(requestCode == 100){
                    Intent intent = new Intent();
                    intent.putExtra("nickname", nickname);
                    setResult(200, intent);
                    finish();
                }
            }
        });
    }
}