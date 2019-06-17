package ncku.aad.popochord;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaRecorder;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import java.io.File;
import android.os.Environment;

import android.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.Calendar;
import java.lang.Object;

public class Record extends AppCompatActivity {

    private String fileName;

    private Button recordButn;
    private Button stopButn;
    private EditText fnameText;
    private TextView stattextView;
    private MediaRecorder mediaRecorder = null;
    private Toolbar toolbar;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 2;

    String formatCalendar(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 1月的值為0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // HOUR_OF_DAY是24小時制，HOUR是12小時制
        int minute = calendar.get(Calendar.MINUTE);
        //int second = calendar.get(Calendar.SECOND);
        return String.format("%d-%02d-%02d %02d:%02d", year, month, day, hour, minute);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.parent:
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("showFile", fileName);
                intent.putExtras(bundle);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recordButn = (Button) findViewById(R.id.recordButton);
        stopButn = (Button) findViewById(R.id.stopButton);
        fnameText = (EditText) findViewById(R.id.fileNameEditText);
        fnameText = (EditText) findViewById(R.id.fileNameEditText);
        stattextView = (TextView) findViewById(R.id.statusTextView);

        fnameText.setText(formatCalendar(Calendar.getInstance()));
        stopButn.setEnabled(false);

        //錄音
        recordButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Record.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(Record.this,
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {

                    //request the permission
                    ActivityCompat.requestPermissions(Record.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                } else {
                    // Permission has already been granted
                    //設定錄音檔名

                    fileName = Calendar.getInstance().getTime().toString();
                    //String fileName = Calendar.getInstance().getTime().toString();
                    String fileName = fnameText.getText().toString();

                    try {
                        File SDCardpath = Environment.getExternalStorageDirectory();
                        File myDataPath = new File( SDCardpath.getAbsolutePath() + "/popochord_record" );
                        if( !myDataPath.exists() ) myDataPath.mkdirs();
                        File recodeFile = new File(SDCardpath.getAbsolutePath() + "/popochord_record/"+fileName);

                        mediaRecorder = new MediaRecorder();

                        //設定音源
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        //設定輸出檔案的格式
                        mediaRecorder
                                .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                        //設定編碼格式
                        mediaRecorder
                                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        //設定錄音檔位置
                        mediaRecorder
                                .setOutputFile(recodeFile.getAbsolutePath());

                        mediaRecorder.prepare();

                        //開始錄音
                        mediaRecorder.start();

                        recordButn.setEnabled(false);
                        stopButn.setEnabled(true);
                        fnameText.setEnabled(false);
                        stattextView.setText("recording...");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                }
        });

        //停止錄音
        stopButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mediaRecorder != null) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    recordButn.setEnabled(true);
                    fnameText.setEnabled(true);
                    stattextView.setText("");
                    Toast toast = Toast.makeText(Record.this, fnameText.getText().toString()+" saved", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });

    }

}
