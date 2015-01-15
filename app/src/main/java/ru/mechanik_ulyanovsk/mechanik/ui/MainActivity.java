package ru.mechanik_ulyanovsk.mechanik.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import ru.mechanik_ulyanovsk.mechanik.R;
import ru.mechanik_ulyanovsk.mechanik.content.Constants;
import ru.mechanik_ulyanovsk.mechanik.content.model.CatalogItem;
import ru.mechanik_ulyanovsk.mechanik.content.model.Section;
import ru.mechanik_ulyanovsk.mechanik.services.MechanicDataSource;
import ru.mechanik_ulyanovsk.mechanik.ui.adapter.ListAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class MainActivity extends ActionBarActivity {

    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration configuration =
                new ImageLoaderConfiguration
                        .Builder(this)
                        .build();
        ImageLoader.getInstance().init(configuration);

        adapter = new ListAdapter(this);
        ListView listView = (ListView) findViewById(R.id.item_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (adapter.isSection(position)) {
                startActivity(new Intent(MainActivity.this, MainActivity.class)
                                .putExtra(Constants.ID_EXTRA, id)
                                .putExtra(
                                        Constants.SECTION_NAME_EXTRA,
                                        ((Section) adapter.getItem(position)).getName()
                                )
                );
            } else {
                startActivity(
                        new Intent(MainActivity.this, DetailActivity.class)
                                .putExtra(
                                        Constants.SERIALIZABLE_CATALOG_ITEM_EXTRA,
                                        (CatalogItem) adapter.getItem(position)
                                )

                );
            }
        });

        Bundle extras = getIntent().getExtras();
        Long itemId = extras != null ? extras.getLong(Constants.ID_EXTRA) : null;

        MechanicDataSource.getInstance()
                .listSections(itemId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::setSections);

        if (itemId != null) {
            MainActivity.this.setTitle(extras.getString(Constants.SECTION_NAME_EXTRA));

            MechanicDataSource.getInstance()
                    .listItems(itemId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(adapter::setCatalogItems);
        }
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
        if (id == R.id.action_search) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
