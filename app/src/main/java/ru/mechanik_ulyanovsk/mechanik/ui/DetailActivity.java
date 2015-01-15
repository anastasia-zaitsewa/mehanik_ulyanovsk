package ru.mechanik_ulyanovsk.mechanik.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import ru.mechanik_ulyanovsk.mechanik.R;
import ru.mechanik_ulyanovsk.mechanik.content.Constants;
import ru.mechanik_ulyanovsk.mechanik.content.model.CatalogItem;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView imageView = (ImageView) findViewById(R.id.detail_image);
        TextView textView = (TextView) findViewById(R.id.detail_text);

        ImageLoaderConfiguration configuration =
                new ImageLoaderConfiguration
                        .Builder(this)
                        .build();
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_menu_camera)
                .showImageOnFail(R.drawable.ic_menu_camera)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(configuration);


        CatalogItem catalogItem = (CatalogItem) getIntent()
                .getExtras()
                .get(Constants.SERIALIZABLE_CATALOG_ITEM_EXTRA);

        String detailUri = catalogItem.getDetailUri();
        imageLoader.displayImage(
                TextUtils.isEmpty(detailUri) ? null : Constants.SERVER_ROOT + detailUri,
                imageView,
                imageOptions
        );

        String catalogItemName = catalogItem.getName();
        textView.setText(catalogItemName);
        DetailActivity.this.setTitle(catalogItemName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.homeAsUp || id == R.id.home || id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
