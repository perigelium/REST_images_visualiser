package ru.alexangan.developer.rest_imagesvisualizer.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import ru.alexangan.developer.rest_imagesvisualizer.Fragments.FragPhotoGalleryGrid;
import ru.alexangan.developer.rest_imagesvisualizer.Interfaces.Communicator;
import ru.alexangan.developer.rest_imagesvisualizer.R;

public class MainActivity extends Activity implements Communicator
{
    private FragmentManager mFragmentManager;

    FragPhotoGalleryGrid fragPhotoGallery;

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_container);

        mFragmentManager = getFragmentManager();

        fragPhotoGallery = (FragPhotoGalleryGrid) mFragmentManager.findFragmentById(R.id.fragContainer);

        if(fragPhotoGallery == null)
        {
            fragPhotoGallery = new FragPhotoGalleryGrid();
        }

        if( ! fragPhotoGallery.isAdded())
        {
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

            mFragmentTransaction.add(R.id.fragContainer, fragPhotoGallery);
            mFragmentTransaction.addToBackStack(null);

            mFragmentTransaction.commit();
        }
    }

    @Override
    public void popFragmentBackStack()
    {
        mFragmentManager.popBackStack();

        if(mFragmentManager.getBackStackEntryCount() == 0)
        {
            this.finish();
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}





