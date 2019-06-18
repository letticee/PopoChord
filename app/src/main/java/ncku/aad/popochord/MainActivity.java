package ncku.aad.popochord;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String FileName;
    private TextView showFile;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(data!=null && resultCode==RESULT_OK)
        {
            Bundle bData = data.getExtras();
            if(bData!=null)
            {
                FileName = bData.getString("showFile");
                showFile.setText(FileName);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFile = (TextView) findViewById(R.id.showFile);

    }

    public void onRecord(View view) {
        Intent settingsIntent = new Intent(this,
                Record.class);
        startActivityForResult(settingsIntent, 0);
    }

    public void onBack(View view) {
        Intent settingsIntent = new Intent( this,
                                            ListRecord.class);
        startActivityForResult(settingsIntent, 0);
    }
}
