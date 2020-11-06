package mediasoft.com.easymarketsapartmentbookingapp.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import mediasoft.com.easymarketsapartmentbookingapp.R;
import mediasoft.com.easymarketsapartmentbookingapp.adapters.ApartmentDataItemAdapter;
import mediasoft.com.easymarketsapartmentbookingapp.model.ApartmentData;


public class ApartmentListBodyFragment extends Fragment
{

    private ArrayList<ApartmentData> apartmentDataList;
    private ApartmentDataItemAdapter apartmentDataItemAdapter;
    private ListView apartmentsListView;
    private int tabType;

    public ApartmentListBodyFragment(Integer tabType, ArrayList<ApartmentData> apartmentDataList)
    {
        this.tabType = tabType;
        this.apartmentDataList = apartmentDataList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_apartment_list_body, container, false);
        apartmentsListView = view.findViewById(R.id.listview);


        apartmentDataItemAdapter = new ApartmentDataItemAdapter(getContext(), this.apartmentDataList);
        apartmentsListView.setAdapter(this.apartmentDataItemAdapter);
        apartmentsListView.setSaveFromParentEnabled(false);

        return view;
    }
}