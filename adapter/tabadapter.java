package com.example.user.bloodconnect.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.bloodconnect.fragment.fragment11;
import com.example.user.bloodconnect.fragment.fragment22;
import com.example.user.bloodconnect.fragment.fragment33;

/**
 * Created by User on 27-09-2017.
 */

public class tabadapter extends FragmentPagerAdapter {
    public tabadapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:

                return new fragment11();

            case 1:

                return new fragment22();

            case 2:

                return new fragment33();

            default:
                break;

        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return "CURRENT NEED";
            case 1:
                return "DONATION HISTORY";
            case 2:
                return "BlOOD BANKS";

            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return 3;
    }
}
