package ru.mechanik_ulyanovsk.mechanik.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import ru.mechanik_ulyanovsk.mechanik.R;
import ru.mechanik_ulyanovsk.mechanik.content.Constants;
import ru.mechanik_ulyanovsk.mechanik.content.model.CatalogItem;
import ru.mechanik_ulyanovsk.mechanik.content.model.Section;

/**
 * Adapter for {@link ru.mechanik_ulyanovsk.mechanik.content.model.Section}
 */
public class ListAdapter extends BaseAdapter {

    public static final String DRAWABLE_PATH_PREFIX = "drawable://";
    private LayoutInflater inflater;
    private final List<Section> sections = new ArrayList<>();
    private final List<CatalogItem> catalogItems = new ArrayList<>();
    private final DisplayImageOptions imageOptions;
    private final ImageLoader imageLoader = ImageLoader.getInstance();

    public ListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        imageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_menu_camera)
                .showImageOnFail(R.drawable.ic_menu_camera)
                .build();

    }

    public void setSections(List<Section> input) {
        sections.clear();
        sections.addAll(input);
        notifyDataSetChanged();
    }

    public void setCatalogItems(List<CatalogItem> input) {
        catalogItems.clear();
        catalogItems.addAll(input);
        notifyDataSetChanged();
    }

    public boolean isSection(int position) {
        return position < sections.size();
    }

    @Override
    public int getCount() {
        return sections.size() + catalogItems.size();
    }

    @Override
    public Object getItem(int position) {
        return isSection(position)
                ? sections.get(position)
                : catalogItems.get(position - sections.size());
    }

    @Override
    public long getItemId(int position) {
        return isSection(position)
                ? sections.get(position).getId()
                : catalogItems.get(position - sections.size()).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = createView(parent);
        } else {
            view = convertView;
        }

        holder = (ViewHolder) view.getTag();
        bindView(position, holder);
        return view;
    }

    private void bindView(int position, ViewHolder holder) {
        if (isSection(position)) {
            Section section = sections.get(position);
            holder.listItemText.setText(section.getName());
            imageLoader.displayImage(
                    DRAWABLE_PATH_PREFIX + R.drawable.ic_menu_archive,
                    holder.listItemImage
            );
        } else {
            CatalogItem catalogItem = catalogItems.get(position - sections.size());
            holder.listItemText.setText(catalogItem.getName());
            String detailUri = catalogItem.getDetailUri();
            imageLoader.displayImage(
                    TextUtils.isEmpty(detailUri) ? null : Constants.SERVER_ROOT + detailUri,
                    holder.listItemImage,
                    imageOptions
            );
        }
    }

    private View createView(ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_section_item, parent, false);

        ViewHolder holder = new ViewHolder(
                (TextView) view.findViewById(R.id.item_text),
                (ImageView) view.findViewById(R.id.item_image)
        );
        view.setTag(holder);

        return view;
    }


    private class ViewHolder {
        final TextView listItemText;
        final ImageView listItemImage;

        ViewHolder(TextView listItemText, ImageView listItemImage) {
            this.listItemText = listItemText;
            this.listItemImage = listItemImage;
        }
    }
}
