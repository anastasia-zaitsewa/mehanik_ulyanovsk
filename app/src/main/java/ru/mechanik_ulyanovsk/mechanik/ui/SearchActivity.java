package ru.mechanik_ulyanovsk.mechanik.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.mechanik_ulyanovsk.mechanik.R;
import ru.mechanik_ulyanovsk.mechanik.content.Constants;
import ru.mechanik_ulyanovsk.mechanik.content.model.CatalogItem;
import ru.mechanik_ulyanovsk.mechanik.services.MechanicDataSource;
import ru.mechanik_ulyanovsk.mechanik.ui.adapter.ListAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public class SearchActivity extends ActionBarActivity {

    private static final int THRESHOLD_SEC = 1;
    private static final int THRESHOLD_CHAR = 2;
    private ListAdapter adapter;
    private View emptyView;
    private final BehaviorSubject<String> searchUpdates = BehaviorSubject.create("");
    private final CompositeSubscription subscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        adapter = new ListAdapter(this);
        ListView listView = (ListView) findViewById(R.id.search_result);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> startActivity(
                new Intent(SearchActivity.this, DetailActivity.class)
                        .putExtra(
                                Constants.SERIALIZABLE_CATALOG_ITEM_EXTRA,
                                (CatalogItem) adapter.getItem(position)
                        )

        ));

        EditText searchView = (EditText) findViewById(R.id.search_view);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUpdates.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emptyView = findViewById(R.id.empty_view);

        subscription.add(searchUpdates
                        .sample(THRESHOLD_SEC, TimeUnit.SECONDS)
                        .filter(s -> s.length() > THRESHOLD_CHAR)
                        .switchMap(input -> MechanicDataSource.getInstance().listItems(input))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(throwable -> Observable.empty())
                        .subscribe(catalogItems -> {
                                    adapter.setCatalogItems(catalogItems);
                                    emptyView.setVisibility(
                                            catalogItems.isEmpty()
                                                    ? View.VISIBLE
                                                    : View.GONE
                                    );
                                },
                                throwable -> Toast
                                        .makeText(
                                                SearchActivity.this,
                                                Constants.LOADING_ERROR,
                                                Toast.LENGTH_SHORT
                                        ).show())
        );

        subscription.add(searchUpdates.subscribe(s -> {
                            if (s.length() <= THRESHOLD_CHAR) {
                                adapter.setCatalogItems(Collections.<CatalogItem>emptyList());
                            }
                        },
                        throwable -> Toast
                                .makeText(
                                        SearchActivity.this,
                                        Constants.LOADING_ERROR,
                                        Toast.LENGTH_SHORT
                                ).show())
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
