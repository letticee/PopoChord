package ncku.aad.popochord;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

public class ListRecord extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_record_list );


        recyclerView = (RecyclerView) findViewById( R.id.my_recycler_view );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        String[] RecordFiles = new File(Environment.getExternalStorageDirectory().toString() + "/popochord_record").list();
        LinkedList<String> RecordList = new LinkedList<String>(Arrays.asList(RecordFiles));
        mAdapter = new ListRecordAdapter(this, RecordList);
        recyclerView.setAdapter(mAdapter);


    }
}
