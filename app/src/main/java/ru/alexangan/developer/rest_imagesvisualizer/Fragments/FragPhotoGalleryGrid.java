package ru.alexangan.developer.rest_imagesvisualizer.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import ru.alexangan.developer.rest_imagesvisualizer.Adapters.GridViewAdapter;
import ru.alexangan.developer.rest_imagesvisualizer.Interfaces.Communicator;
import ru.alexangan.developer.rest_imagesvisualizer.Interfaces.RetrofitAPI;
import ru.alexangan.developer.rest_imagesvisualizer.R;
import ru.alexangan.developer.rest_imagesvisualizer.Utils.NetworkUtils;
import ru.alexangan.developer.rest_imagesvisualizer.Utils.ViewUtils;

public class FragPhotoGalleryGrid extends Fragment implements retrofit2.Callback<List<String>>
{
    GridView gvPhotoGallery;
    Activity activity;
    private Communicator mCommunicator;
    ArrayList<String> l_imgPathItems;
    retrofit2.Call<List<String>> callGetImgUrls;
    private GridViewAdapter gridAdapter;

    public FragPhotoGalleryGrid()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        mCommunicator = (Communicator) getActivity();

        l_imgPathItems = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.photo_gallery_grid, container, false);

        gvPhotoGallery = (GridView) rootView.findViewById(R.id.gvPhotoGallery);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if (l_imgPathItems!= null && l_imgPathItems.size() != 0)
        {
            displayImages();
        } else
        {
            if (NetworkUtils.isNetworkAvailable(activity))
            {
                downloadImgUrls();
            }
            else
            {
                ViewUtils.showToastMessage(activity, getString(R.string.msg_CheckInternetConnection));
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    private void displayImages()
    {
        if(gridAdapter == null)
        {
            gridAdapter = new GridViewAdapter(activity, R.layout.grid_item_layout, l_imgPathItems);
        }
        else
        {
            gridAdapter.clear();
        }

        gvPhotoGallery.setAdapter(gridAdapter);
    }

    private void downloadImgUrls()
    {
        if (NetworkUtils.isNetworkAvailable(activity))
        {
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    RetrofitAPI retrofitService = RetrofitAPI.retrofit.create(RetrofitAPI.class);

                    callGetImgUrls = retrofitService.getImgUrls();

                    callGetImgUrls.enqueue(FragPhotoGalleryGrid.this);
                }
            }, 100);

        } else
        {
            ViewUtils.showToastMessage(activity, getString(R.string.msg_CheckInternetConnection));
        }
    }

    @Override
    public void onResponse(retrofit2.Call<List<String>> call, retrofit2.Response<List<String>> response)
    {
        if (response.isSuccessful())
        {
            l_imgPathItems = (ArrayList<String>) response.body();

            if (l_imgPathItems == null)
            {
                l_imgPathItems = new ArrayList<>();
            }

            if (l_imgPathItems.size() != 0)
            {
                displayImages();
            } else
            {
                ViewUtils.showToastMessage(activity, getString(R.string.msg_ListIsEmpty));
            }
        } else
        {
            ViewUtils.showToastMessage(activity, getString(R.string.msg_ServerError));

            //int statusCode = response.code();
            ResponseBody errorBody = response.errorBody();

            try
            {
                Log.d("DEBUG", errorBody != null ? errorBody.string() : "");

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(retrofit2.Call<List<String>> call, Throwable t)
    {
        ViewUtils.showToastMessage(activity, getString(R.string.msg_ServerAnswerNotReceived));

        mCommunicator.popFragmentBackStack();
    }
}
