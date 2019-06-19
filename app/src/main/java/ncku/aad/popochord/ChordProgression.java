package ncku.aad.popochord;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ChordProgression extends AppCompatActivity
                                implements ListChordAdapter.OnItemClickHandler{
    private TextView fileName = null;
    private TextView Marker;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                chord_list.addLast("");
                return true;
            case R.id.delete:
                chord_list.remove(current_chord);
                if(current_chord==0) {
                    refreshPrevNext("", chord_list.get(current_chord), "");
                }
                else if(current_chord==chord_list.size()-1) {
                    refreshPrevNext(chord_list.get(current_chord-1), chord_list.get(current_chord), "");
                }
                else{
                    refreshPrevNext(chord_list.get(current_chord-1), chord_list.get(current_chord), chord_list.get(current_chord+1));
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private Spinner key_spinner, tonal_spinner, bass_spinner;
    private RecyclerView mRecyclerView;
    private Button previous_btn, next_btn;
    private TextView previous_textview, current_textview, next_textview;
    private ListChordAdapter mAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chord, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private LinkedList<String> chord_list = new LinkedList<String>(Arrays.asList(""));
    private Integer current_chord;
    private Integer key, tonal, bass;

    final String [][] chord_Array = {
            {"Maj",  "m",    "m",    "Maj",  "Maj",    "m",     "dim",  "Maj7"},    // Ionion
            {"m",    "m",    "Maj",  "Maj",  "m",      "dim",   "Maj",  "m7"},      // Dorian
            {"m",    "Maj",  "Maj",  "m",    "dim",    "Maj",   "m",    "7"},       // Phyrigian
            {"Maj",  "Maj",  "m",    "dim",  "Maj",    "m",     "m",    "Maj7"},    // Lydian
            {"Maj",  "m",    "dim",  "Maj",  "m",      "m",     "Maj",  "m7"},      // Mixolydian
            {"m",    "dim",  "m",    "m",    "m",      "Maj",   "Maj",  "m7"},        // Aeolian
            {"dim",  "m",    "m",    "m",    "Maj",    "Maj",   "m",    "Maj7"},      // Locrian
            {"Maj7", "m7",   "m7",   "Maj7", "Maj7",   "m7",    "7",    " "},    // Ionion7
            {"m7",   "m7",   "Maj7", "Maj7",  "m7",    "7",     "Maj7", " "},      // Dorian7
            {"m7",   "Maj7", "Maj7", "m7",    "7",     "Maj7",  "m7",   " "},       // Phyrigian7
            {"Maj7", "Maj7", "m7",   "7",     "Maj7",  "m7",    "m7",   " "},    // Lydian7
            {"Maj7", "m7",   "7",    "Maj7",  "m7",    "m7",    "Maj7", " "},      // Mixolydian7
            {"m7",   "7",    "m7",   "m7",    "m7",    "Maj7",  "Maj7", " "},        // Aeolian7
            {"7",    "m7",   "m7",   "m7",    "Maj7",  "Maj7",  "m7",   " "},      // Locrian7
    };
    final Integer [][] Root_Array = {
            {1,3,5,6,8,10,12},
            {1,3,4,6,8,10,11},
            {1,2,4,6,8,9,11},
            {1,3,5,7,8,10,12},
            {1,3,5,6,8,10,11},
            {1,3,4,6,8,9,11},
            {1,2,4,6,7,8,10},
            {1,3,5,6,8,10,12},
            {1,3,4,6,8,10,11},
            {1,2,4,6,8,9,11},
            {1,3,5,7,8,10,12},
            {1,3,5,6,8,10,11},
            {1,3,4,6,8,9,11},
            {1,2,4,6,7,8,10}
    };
    final String [] Root_Char = {"C", "C#/Db", "D", "D#/Eb", "E", "F", "F#/Gb", "G", "G#/Ab",
                                 "A", "A#/Bb", "B"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chordprogression);

        fileName      = (TextView) findViewById(R.id.fileName);
        key_spinner   = (Spinner) findViewById(R.id.Key);
        tonal_spinner = (Spinner) findViewById(R.id.Toanl);
        bass_spinner  = (Spinner) findViewById(R.id.Bass);
        previous_btn = (Button) findViewById(R.id.previous_chord);
        next_btn     = (Button) findViewById(R.id.next_chord);
        previous_textview = (TextView) findViewById(R.id.previous_chord_text);
        next_textview     = (TextView) findViewById(R.id.next_chord_text);
        current_textview  = (TextView) findViewById(R.id.current_chord_text);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Marker        = (TextView) findViewById(R.id.chord_marker);

        ArrayAdapter<CharSequence> twelve_note_list =
                ArrayAdapter.createFromResource(ChordProgression.this,
                                                        R.array.twelve_note,
                                                        android.R.layout.simple_dropdown_item_1line
                                                );
        ArrayAdapter<CharSequence> tonal_list =
                ArrayAdapter.createFromResource(ChordProgression.this,
                        R.array.tonal_mode,
                        android.R.layout.simple_dropdown_item_1line);
        key_spinner.setAdapter(twelve_note_list);
        bass_spinner.setAdapter(twelve_note_list);
        tonal_spinner.setAdapter(tonal_list);


        Bundle bundle = getIntent().getExtras();
        String file = bundle.getString("showFile");
        fileName.setText(file);


        // key, tonal, bass
        key = -1;
        tonal = -1;
        bass = -1;

        // recycler view
        mRecyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        current_chord = 0;
        refreshChordList(current_chord);
        refreshPrevNext("", "", "");


        // onclick
        previous_btn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(current_chord-1>0){
                    current_chord--;
                    refreshPrevNext(chord_list.get(current_chord-1), chord_list.get(current_chord), chord_list.get(current_chord+1));
                    Marker.setText(current_chord.toString());
                }

                else if(current_chord-1 == 0){
                    current_chord--;
                    refreshPrevNext("", chord_list.get(current_chord), chord_list.get(current_chord+1));
                    Marker.setText(current_chord.toString());
                }
            }
        });

        next_btn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(current_chord<chord_list.size()-2){
                    current_chord++;
                    refreshPrevNext(chord_list.get(current_chord-1), chord_list.get(current_chord), chord_list.get(current_chord+1));
                    Marker.setText(current_chord.toString());
                }

                else if(current_chord == chord_list.size()-2){
                    current_chord++;
                    refreshPrevNext(chord_list.get(current_chord-1), chord_list.get(current_chord), "");
                    Marker.setText(current_chord.toString());
                }


            }
        });

        tonal_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshChordList(current_chord);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        key_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshChordList(current_chord);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onItemClick(String text, int pos) {
        chord_list.set(current_chord, text);
        if(current_chord==0) {
            refreshPrevNext("", chord_list.get(current_chord), "");
        }
        else if(current_chord==chord_list.size()-1) {
            refreshPrevNext(chord_list.get(current_chord-1), chord_list.get(current_chord), "");
        }
        else{
            refreshPrevNext(chord_list.get(current_chord-1), chord_list.get(current_chord), chord_list.get(current_chord+1));
        }
    }


    private void refreshChordList(Integer m){
        // Marker
        Marker.setText(m.toString());
        // RecyclerView
        int tonal_id = tonal_spinner.getSelectedItemPosition();
        int bass_id = bass_spinner.getSelectedItemPosition();
        int key_id = key_spinner.getSelectedItemPosition();

        LinkedList<String> myList = new LinkedList<String>();
        for(int i=0;i<7;i++){
            myList.addLast(Root_Char[(Root_Array[key_id][i]+key_id-1)%12] + chord_Array[tonal_id][i]);
        }

        mAdapter = new ListChordAdapter(this, myList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void refreshPrevNext(String p, String n, String t){
        if(p!=null && t!=null) {
            previous_textview.setText("<-" + p);
            current_textview.setText("   |" + n +"|    ");
            next_textview.setText(t + "->");
        }
    }
}
