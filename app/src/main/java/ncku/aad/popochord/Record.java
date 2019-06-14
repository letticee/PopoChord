package ncku.aad.popochord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaRecorder;
import android.widget.Button;
import android.view.View;
import java.io.File;
import android.os.Environment;
import java.io.IOException;

public class Record extends AppCompatActivity {

    private Button recordButn;
    private Button stopButn;
    private MediaRecorder mediaRecorder = null;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        recordButn = (Button) findViewById(R.id.recordButton);
        stopButn = (Button) findViewById(R.id.stopButton);

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
                    String fileName = "record.amr";
                    try {
                        File SDCardpath = Environment.getExternalStorageDirectory();
                        File myDataPath = new File( SDCardpath.getAbsolutePath() + "/download" );
                        if( !myDataPath.exists() ) myDataPath.mkdirs();
                        File recodeFile = new File(SDCardpath.getAbsolutePath() + "/download/"+fileName);

                        mediaRecorder = new MediaRecorder();

                        //設定音源
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        //設定輸出檔案的格式
                        mediaRecorder
                                .setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                        //設定編碼格式
                        mediaRecorder
                                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        //設定錄音檔位置
                        mediaRecorder
                                .setOutputFile(recodeFile.getAbsolutePath());

                        mediaRecorder.prepare();

                        //開始錄音
                        mediaRecorder.start();

                        recordButn.setClickable(false);
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
                    recordButn.setClickable(true);
                }
            }
        });

    }
}
