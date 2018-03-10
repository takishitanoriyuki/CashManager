package site.taki_lab.n_tak;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecodeListActivity extends AppCompatActivity {

    List<ListData> record = new ArrayList<ListData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recode_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] fileList = this.fileList();
        List<String> list = new ArrayList<String>();
        for (String file : fileList ) {
            if(!file.equals(getString(R.string.filename))){
                SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy/MM/dd");
                String text = "";
                try {
                    Date formatDate = fileFormat.parse(file);
                    text = displayFormat.format(formatDate);
                }catch (Exception e){

                }
                ListData listData = new ListData(file, text);
                record.add(listData);
                list.add(text);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_layout, list);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(RecodeListActivity.this, DetailsActivity.class);
                intent.putExtra(ExtraString.EXTRA_FILENAME, record.get(position).getFilename());
                startActivity(intent);
            }
        });
    }

}

class ListData {
    public String getFilename() {
        return _filename;
    }

    private String _filename;

    public String getDisplay() {
        return _display;
    }

    private String _display;
    public ListData(String filename, String display){
        _display = display;
        _filename = filename;
    }
}
