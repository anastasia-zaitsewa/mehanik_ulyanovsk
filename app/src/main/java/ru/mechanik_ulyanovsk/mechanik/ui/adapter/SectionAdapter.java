package ru.mechanik_ulyanovsk.mechanik.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mechanik_ulyanovsk.mechanik.R;
import ru.mechanik_ulyanovsk.mechanik.content.model.Section;

/**
 * Adapter for {@link ru.mechanik_ulyanovsk.mechanik.content.model.Section}
 */
public class SectionAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private final List<Section> sections = new ArrayList<>();

    public SectionAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setSections(List<Section> input) {
        sections.clear();
        sections.addAll(input);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return sections.size();
    }

    @Override
    public Object getItem(int position) {
        return sections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return sections.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null){
            view = createView(parent);
        }else{
            view = convertView;
        }

        holder = (ViewHolder) view.getTag();
        bindView(position, holder);
        return view;
    }

    private void bindView(int position, ViewHolder holder) {
        Section section = sections.get(position);
        holder.sectionName.setText(section.getName());
    }

    private View createView(ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_section_item, parent, false);
        ViewHolder holder = new ViewHolder((TextView) view);
        view.setTag(holder);

        return view;
    }

    private class ViewHolder{
        final TextView sectionName;

        ViewHolder(TextView sectionName) {
            this.sectionName = sectionName;
        }
    }
}