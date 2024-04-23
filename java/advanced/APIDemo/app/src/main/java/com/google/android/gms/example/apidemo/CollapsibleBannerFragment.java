/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.example.apidemo;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.example.apidemo.databinding.FragmentCollapsibleBannerBinding;
import java.util.UUID;

/** The [CollapsibleBannerFragment] class demonstrates how to use a collapsible banner ad. */
public class CollapsibleBannerFragment extends Fragment {

  private AdView adView;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    FragmentCollapsibleBannerBinding fragmentBinding =
        FragmentCollapsibleBannerBinding.inflate(inflater);

    adView = new AdView(requireContext());
    fragmentBinding.adViewContainer.addView(adView);

    return fragmentBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    loadCollapsibleBanner();
  }

  private void loadCollapsibleBanner() {
    // Get the ad size based on the screen width.
    AdSize adSize =
        AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(requireContext(), getAdWidth());

    // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
    adView.setAdUnitId(TEST_AD_UNIT_ID);
    adView.setAdSize(adSize);

    // Create an extra parameter that aligns the bottom of the expanded ad to
    // the bottom of the bannerView.
    Bundle extras = new Bundle();
    extras.putString("collapsible", "bottom");

    // Pass a UUID as a collapsible_request_id to limit collapsible ads on ad refreshing.
    extras.putString("collapsible_request_id", UUID.randomUUID().toString());

    // Create an ad request.
    AdRequest adRequest =
        new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();

    // Start loading a collapsible banner ad.
    adView.loadAd(adRequest);
  }

  // Determine the screen width to use for the ad width.
  private int getAdWidth() {
    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    int adWidthPixels = displayMetrics.widthPixels;
    float density = displayMetrics.density;
    return (int) (adWidthPixels / density);
  }

  private static final String TEST_AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741";
}
