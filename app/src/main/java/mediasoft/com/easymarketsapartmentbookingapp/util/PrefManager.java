package mediasoft.com.easymarketsapartmentbookingapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mediasoft.com.easymarketsapartmentbookingapp.model.ApartmentData;

public class PrefManager
{
    private static final String PREF_NAME = "apartments_app_prefs";
    private static final String IS_FIRST_TIME_LAUNCH = "is_first_time_launch";
    private static PrefManager instance = null;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private final String[] imageUrlArr = {"", "", "", "", "", "", "", "", "", ""};
    private final String[] apartmentTitleArr = {"Apartment 1", "Apartment 2", "Apartment 3", "Apartment 4", "Apartment 5", "Apartment 6", "Apartment 7", "Apartment 8", "Apartment 9", "Apartment 10"};


    public PrefManager(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    private synchronized static void createInstance(Context context)
    {
        if (instance == null)
        {
            instance = new PrefManager(context);
        }
    }

    public static PrefManager getInstance(Context context)
    {
        if (instance == null)
        {
            createInstance(context);
        }

        return instance;
    }

    public void generateApartments()
    {
        List<ApartmentData> apartmentDataArrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            ApartmentData apartmentData = new ApartmentData();
            apartmentData.setImageUrl(imageUrlArr[i]);
            apartmentData.setTitle(apartmentTitleArr[i]);
            apartmentData.setPrice("200000$");
            apartmentData.setRoomNumber("1");
            apartmentData.setStatus("AVAILABLE");
            apartmentDataArrayList.add(apartmentData);
        }
        for (int i = 4; i < 7; i++)
        {
            ApartmentData apartmentData = new ApartmentData();
            apartmentData.setImageUrl(imageUrlArr[i]);
            apartmentData.setTitle(apartmentTitleArr[i]);
            apartmentData.setPrice("300000$");
            apartmentData.setRoomNumber("2");
            apartmentData.setStatus("AVAILABLE");
            apartmentDataArrayList.add(apartmentData);
        }

        for (int i = 7; i < 10; i++)
        {
            ApartmentData apartmentData = new ApartmentData();
            apartmentData.setImageUrl(imageUrlArr[i]);
            apartmentData.setTitle(apartmentTitleArr[i]);
            apartmentData.setPrice("400000$");
            apartmentData.setRoomNumber("3");
            apartmentData.setStatus("AVAILABLE");
            apartmentDataArrayList.add(apartmentData);
        }

        Gson gson = new Gson();
        String json = gson.toJson(apartmentDataArrayList);
        editor.putString("Apartments", json);
        editor.commit();
    }

    public ApartmentData[] getApartmentsData()
    {
        Gson gson = new Gson();
        String json = getPref().getString("Apartments", null);
        ApartmentData[] apartmentsData = gson.fromJson(json, ApartmentData[].class);
        return apartmentsData;
    }

    public boolean isFirstTimeLaunch()
    {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime)
    {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public SharedPreferences getPref()
    {
        return pref;
    }

    public void setPref(SharedPreferences pref)
    {
        this.pref = pref;
    }

    public SharedPreferences.Editor getEditor()
    {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor)
    {
        this.editor = editor;
    }
}
