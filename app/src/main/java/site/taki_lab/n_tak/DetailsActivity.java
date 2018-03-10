package site.taki_lab.n_tak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by n_tak on 2018/02/12.
 */

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String filename = intent.getStringExtra(ExtraString.EXTRA_FILENAME);

        CountingLog log = new CountingLog(this);
        List<String> list = log.ReadLog(filename);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_layout, list);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
