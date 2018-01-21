package site.taki_lab.n_tak;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private int Count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ファイルの存在を確認
        File file = this.getFileStreamPath(getString(R.string.filename));
        if(file.exists()){
            try {
                FileInputStream fileInputStream;
                fileInputStream = openFileInput(getString(R.string.filename));
                byte[] readBytes = new byte[fileInputStream.available()];
                fileInputStream.read(readBytes);
                String readString = new String(readBytes);
                Count = Integer.parseInt(readString);
                fileInputStream.close();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }else{
            try {
                FileOutputStream fileOutputStream = openFileOutput(getString(R.string.filename), MODE_PRIVATE);
                String writeString = "0";
                fileOutputStream.write(writeString.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }

        TextView tv = (TextView) findViewById(R.id.money);
        tv.setText(String.valueOf(Count) + "円");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, RecodeListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ShowDialog() {
        final EditText editView = new EditText(MainActivity.this);
        editView.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.title)
                //setViewにてビューを設定します。
                .setView(editView)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String text = editView.getText().toString();
                        if(text.length() == 0) {
                            return;
                        }
                        int value = Integer.parseInt(text);
                        Count += value;
                        try {
                            FileOutputStream fileOutputStream = openFileOutput(getString(R.string.filename), MODE_PRIVATE);
                            String writeString = String.valueOf(Count);
                            fileOutputStream.write(writeString.getBytes());
                            TextView tv = (TextView) findViewById(R.id.money);
                            tv.setText(String.valueOf(Count) + "円");
                            fileOutputStream.close();

                            CountingLog log = new CountingLog(MainActivity.this);
                            log.WriteLog(value);
                        } catch (FileNotFoundException e) {
                        } catch (IOException e) {
                        }
                    }
                })
                .setNegativeButton(R.string.sub, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String text = editView.getText().toString();
                        if(text.length() == 0) {
                            return;
                        }
                        int value = Integer.valueOf(text);
                        Count -= value;
                        try {
                            FileOutputStream fileOutputStream = openFileOutput(getString(R.string.filename), MODE_PRIVATE);
                            String writeString = String.valueOf(Count);
                            fileOutputStream.write(writeString.getBytes());
                            TextView tv = (TextView) findViewById(R.id.money);
                            tv.setText(String.valueOf(Count) + "円");
                            fileOutputStream.close();

                            CountingLog log = new CountingLog(MainActivity.this);
                            log.WriteLog(-1 * value);
                        } catch (FileNotFoundException e) {
                        } catch (IOException e) {
                        }
                    }
                })
                .show();
    }
}
