package ru.mechanik_ulyanovsk.mechanik.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

    private LayoutInflater inflater;
    private final List<Section> sections = new ArrayList<>();
    private final List<CatalogItem> catalogItems = new ArrayList<>();
    private final Context context;

    public ListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
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
            Picasso
                    .with(context)
                    .load(R.drawable.ic_folder_grey600_48dp)
                    .into(holder.listItemImage);
        } else {
            CatalogItem catalogItem = catalogItems.get(position - sections.size());
            holder.listItemText.setText(catalogItem.getName());
            String detailUri = catalogItem.getDetailUri();
            Picasso
                    .with(context)
                    .load(TextUtils.isEmpty(detailUri) ? null : Constants.SERVER_ROOT + detailUri)
                    .placeholder(R.drawable.ic_camera_alt_grey600_48dp)
                    .error(R.drawable.ic_camera_alt_grey600_48dp)
                    .into(holder.listItemImage);

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
