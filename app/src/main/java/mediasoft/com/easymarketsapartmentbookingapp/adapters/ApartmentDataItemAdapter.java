package mediasoft.com.easymarketsapartmentbookingapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mediasoft.com.easymarketsapartmentbookingapp.R;
import mediasoft.com.easymarketsapartmentbookingapp.model.ApartmentData;
import mediasoft.com.easymarketsapartmentbookingapp.util.PrefManager;

public class ApartmentDataItemAdapter extends BaseAdapter
{

    private final Context context;
    public List<ApartmentData> apartmentDataList;
    private PrefManager prefManager;

    public ApartmentDataItemAdapter(@NonNull Context context, @NonNull ArrayList<ApartmentData> list)
    {
        this.context = context;
        this.apartmentDataList = list;
        prefManager = PrefManager.getInstance(context);
    }

    @Override
    public int getCount()
    {
        return apartmentDataList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return apartmentDataList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.apartment_item_view, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        ApartmentData apartmentData = (ApartmentData) getItem(i);
        viewHolder.priceTextView.setText("Price : " + apartmentData.getPrice());
        viewHolder.roomNumberTextView.setText("Room Number : "  + apartmentData.getRoomNumber());
        viewHolder.titleTextView.setText(apartmentData.getTitle());


        Glide.with(context)
                .load(apartmentData.getImageUrl())
                .centerCrop()
                .thumbnail(0.1f)
                .placeholder(R.drawable.ic_launcher_background)
                .into(new CustomTarget<Drawable>()
                {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition)
                    {
                        viewHolder.apartmentImageView.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder)
                    {

                    }
                });




        viewHolder.bookToggleButton.setOnCheckedChangeListener((compoundButton, isChecked) ->
        {
            ApartmentData[] apartmentsData = prefManager.getApartmentsData();
            if(isChecked)
            {
                apartmentData.setStatus("BOOKED");
            }
            else
            {
                apartmentData.setStatus("AVAILABLE");
            }
            for(int k = 0; k< apartmentsData.length; k++)
            {
                if(apartmentsData[k].getTitle().equals(apartmentData.getTitle()))
                {
                    apartmentsData[k] = apartmentData;
                }
            }
            Gson gson = new Gson();
            String json = gson.toJson(apartmentsData);
            prefManager.getEditor().putString("Apartments", json);
            prefManager.getEditor().commit();
        });

        return convertView;
    }

    public class ViewHolder
    {
        ToggleButton bookToggleButton;
        ImageView apartmentImageView;
        TextView titleTextView;
        TextView priceTextView;
        TextView roomNumberTextView;

        public ViewHolder(View view)
        {


            bookToggleButton = view.findViewById(R.id.book_toggle_button);
            apartmentImageView = view.findViewById(R.id.apartment_imageview);
            titleTextView = view.findViewById(R.id.title_textview);

            priceTextView = view.findViewById(R.id.price_textview);
            roomNumberTextView = view.findViewById(R.id.room_number_textview);

        }
    }

}
