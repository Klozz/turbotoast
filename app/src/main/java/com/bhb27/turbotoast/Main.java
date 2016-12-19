/*
 * Copyright (C) 2016 Felipe de Leon fglfgl27@gmail.com
 *
 * This file is part of Kernel Adiutor.
 *
 * Kernel Adiutor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kernel Adiutor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kernel Adiutor.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.bhb27.turbotoast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;

import com.bhb27.turbotoast.AboutActivity;
import com.bhb27.turbotoast.Constants;
import com.bhb27.turbotoast.FaqActivity;
import com.bhb27.turbotoast.root.RootUtils;
import com.bhb27.turbotoast.Tools;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        private final String settingsTAG = Constants.PREF_NAME;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceManager().setSharedPreferencesName(settingsTAG);
            addPreferencesFromResource(R.xml.preferences);
            // Set this to false empty pref are not load on preference.xml
            Tools.saveBoolean("pre", false, getActivity());
            final Tools tools_class = new Tools();
            // check on init if Root is enable if yes try to start Root
            SharedPreferences prefs = getActivity().getSharedPreferences(settingsTAG, 0);
            boolean RootTag = prefs.getBoolean("Root", false);
            if (RootTag == true) {
                if (RootUtils.rooted() && RootUtils.rootAccess()) {
                    tools_class.DoAToast(getString(R.string.root_guaranteed), getActivity());
                } else {
                    tools_class.DoAToast(getString(R.string.no_root_access), getActivity());
                }
            }

            getPreferenceManager().findPreference("teste").setOnPreferenceClickListener(new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    String settingsTAG = "pref";
                    SharedPreferences prefs = getActivity().getSharedPreferences(settingsTAG, 0);
                    boolean RootTag = prefs.getBoolean("Root", false);
                    if (RootTag == true) {
                        if (RootUtils.rooted() && RootUtils.rootAccess()) {
                            tools_class.DoAToast(getString(R.string.device_model) + " " + Build.MODEL + "\n" + getString(R.string.test_a_toast) + Tools.getChargingType(), getActivity());
                            return true;
                        } else {
                            tools_class.DoAToast(getString(R.string.no_root_access), getActivity());
                            return true;
                        }
                    } else {
                        tools_class.DoAToast(getString(R.string.device_model) + " " + Build.MODEL + "\n" + getString(R.string.test_a_toast) + Tools.getChargingTypeN(), getActivity());
                        return true;
                    }
                }
            });
            getPreferenceManager().findPreference("about").setOnPreferenceClickListener(new OnPreferenceClickListener() {
                Intent myIntent = new Intent(getActivity(), AboutActivity.class);
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(myIntent);
                    return true;
                }
            });
            getPreferenceManager().findPreference("faq").setOnPreferenceClickListener(new OnPreferenceClickListener() {
                Intent myIntent = new Intent(getActivity(), FaqActivity.class);
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(myIntent);
                    return true;
                }
            });
        }
    }
}
