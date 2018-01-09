package example.hello.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    SharedPreferences preferences;
    ListView listView;

    EditText editText;
    Button buttonSend;

    ChatAdapter chatAdapter;

    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.et_message);
        buttonSend = (Button) findViewById(R.id.btn_send);

        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        final String nickname = preferences.getString("key", "Not found");

        chatAdapter = new ChatAdapter(ChatActivity.this, R.layout.row_chat);

        listView.setAdapter(chatAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChatActivity.this,chatAdapter.getItem(position).getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                Model model = new Model();
                model.setNickname(nickname);
                model.setMessage(message);

                chatAdapter.add(model);

                editText.setText("");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && requestCode == 200){
            String nickname = data.getStringExtra("nickname");
            this.nickname = nickname;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.setting:
                Intent intent = new Intent(ChatActivity.this,MainActivity.class);
                intent.putExtra("requestCode", 100)
                startActivityForResult(intent, 100);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
