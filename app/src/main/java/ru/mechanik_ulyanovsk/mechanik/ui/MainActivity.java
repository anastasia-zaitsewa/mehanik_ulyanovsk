package ru.mechanik_ulyanovsk.mechanik.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import ru.mechanik_ulyanovsk.mechanik.R;
import ru.mechanik_ulyanovsk.mechanik.content.model.Section;
import ru.mechanik_ulyanovsk.mechanik.services.MechanicDataSource;
import ru.mechanik_ulyanovsk.mechanik.ui.adapter.SectionAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class MainActivity extends ActionBarActivity {

    private SectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration configuration =
                new ImageLoaderConfiguration
                        .Builder(this)
                        .build();
        ImageLoader.getInstance().init(configuration);

        adapter = new SectionAdapter(this);
        ListView listView = (ListView) findViewById(R.id.section_list);
        listView.setAdapter(adapter);

        MechanicDataSource.getInstance()
                .listSections(null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Section>>() {
                    @Override
                    public void call(List<Section> sections) {
                        adapter.setSections(sections);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
