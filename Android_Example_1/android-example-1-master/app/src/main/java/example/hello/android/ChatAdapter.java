package example.hello.android;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChatAdapter extends ArrayAdapter<Model>{

    private Context context;
    private int resource;

    private LayoutInflater inflater;

    public ChatAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;

        this.inflater = inflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = this.inflater.inflate(this.resource, parent, false);
        }

        TextView tvNickname = (TextView) convertView.findViewById(R.id.tv_nickname);
        TextView tvMessage = (TextView) convertView.findViewById(R.id.tv_message);

        Model model = getItem(position);

        tvNickname.setText(model.getNickname());
        tvMessage.setText(model.getMessage());

        return convertView;
    }

}
