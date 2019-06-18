package ncku.aad.popochord;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

public class ListRecord extends AppCompatActivity
                            implements ListRecordAdapter.OnItemClickHandler{
    private RecyclerView recyclerView;
    private ListRecordAdapter mAdapter;
    private String fileName = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_record_list );


        recyclerView = (RecyclerView) findViewById( R.id.my_recycler_view );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        String[] RecordFiles = new File(Environment.getExternalStorageDirectory().toString() + "/popochord_record").list();
        LinkedList<String> RecordList = new LinkedList<String>(Arrays.asList(RecordFiles));
        mAdapter = new ListRecordAdapter(this, RecordList, this);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("showFile", fileName);

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_record:
                onBackPressed();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(String text) {
        fileName = text;
        Toast.makeText(this, fileName + " selected", Toast.LENGTH_SHORT).show();
    }
}
