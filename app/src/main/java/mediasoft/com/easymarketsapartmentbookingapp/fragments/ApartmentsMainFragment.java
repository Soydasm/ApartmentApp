package mediasoft.com.easymarketsapartmentbookingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import mediasoft.com.easymarketsapartmentbookingapp.R;
import mediasoft.com.easymarketsapartmentbookingapp.adapters.TabAdapter;
import mediasoft.com.easymarketsapartmentbookingapp.constants.ApartmentsMainFragmentConstants;
import mediasoft.com.easymarketsapartmentbookingapp.model.ApartmentData;
import mediasoft.com.easymarketsapartmentbookingapp.util.PrefManager;

public class ApartmentsMainFragment extends Fragment
{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    private PrefManager prefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragments_main_apartments, container, false);


        viewPager = view.findViewById(R.id.fragment_main_apartments_viewpager);
        tabLayout = view.findViewById(R.id.fragment_main_apartments_tablayout);
        tabAdapter = new TabAdapter(getChildFragmentManager());

        prefManager = PrefManager.getInstance(getContext());
        if (prefManager.isFirstTimeLaunch())
        {
            prefManager.generateApartments();
            prefManager.setFirstTimeLaunch(false);
        }

        tabAdapter.addFragment(new ApartmentListBodyFragment(ApartmentsMainFragmentConstants.AVAILABLE_APARTMENT_FRAGMENT_TYPE, getApartments(prefManager.getApartmentsData(), "AVAILABLE")), ApartmentsMainFragmentConstants.AVAILABLE_TITLE);
        tabAdapter.addFragment(new ApartmentListBodyFragment(ApartmentsMainFragmentConstants.BOOKED_APARTMENT_FRAGMENT_TYPE, getApartments(prefManager.getApartmentsData(), "BOOKED")), ApartmentsMainFragmentConstants.BOOKED_TITLE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setAdapter(tabAdapter);
        viewPager.setSaveFromParentEnabled(false);

        tabLayout.setupWithViewPager(viewPager);
    }


    public ArrayList<ApartmentData> getApartments(ApartmentData[] apartmentsData, String which)
    {
        ArrayList<ApartmentData> apartments = new ArrayList<>();
        for (ApartmentData data : apartmentsData)
        {
            if (which.equals(data.getStatus()))
            {
                apartments.add(data);
            }
        }

        return apartments;
    }
}
