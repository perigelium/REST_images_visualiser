package ru.alexangan.developer.rest_imagesvisualizer.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.alexangan.developer.rest_imagesvisualizer.R;

public class GridViewAdapter extends ArrayAdapter<String>
{
    private int layoutResourceId;
    private ArrayList<String> imgPathsList;
    private Context galleryContext;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<String> imgPathsList)
    {
        super(context, layoutResourceId, imgPathsList);

        this.layoutResourceId = layoutResourceId;
        this.imgPathsList = imgPathsList;

        galleryContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ViewHolder holder;

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) galleryContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();

            holder.image = (ImageView) row.findViewById(R.id.grid_cell_image);
            holder.image.setScaleType(ImageView.ScaleType.FIT_XY);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        if(imgPathsList.size() != 0)
        {
            Picasso.with(galleryContext)
                    .load(imgPathsList.get(position))
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder_error)
                    .into(holder.image);
        }

        return row;
    }

    private class ViewHolder
    {
        ImageView image;
    }
}