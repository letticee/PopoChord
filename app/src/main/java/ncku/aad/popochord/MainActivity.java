package ncku.aad.popochord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String FileName;
    private TextView showFile;

    @Override
    protected void onResume() {
        super.onResume();
        Bundle FileBundle = getIntent().getExtras();

        if (FileBundle != null){
            FileName = FileBundle.getString("showFile");
            showFile.setText(FileName);
        }
        else
            showFile.setText("No File selected");
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
        startActivity(settingsIntent);
    }

    public void onBack(View view) {
        Intent settingsIntent = new Intent( this,
                                            ListRecord.class);
        startActivity(settingsIntent);
    }
}
