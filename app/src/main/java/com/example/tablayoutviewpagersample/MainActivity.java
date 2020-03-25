package com.example.tablayoutviewpagersample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.tablayoutviewpagersample.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {

    public static FragmentManager fragmentManager;
    private ActivityMainBinding binding;
    private int indicatorWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();


        bindViewPagerAdapter(binding.viewPager);
        bindViewPagerTabs(binding.tab, binding.viewPager);
        tabSettings();
    }

    public void bindViewPagerAdapter(final ViewPager view) {
        final SamplePagerAdapter adapter = new SamplePagerAdapter(view.getContext(), fragmentManager);
        view.setAdapter(adapter);
    }

    public void bindViewPagerTabs(final TabLayout view, final ViewPager pagerView) {
        view.setupWithViewPager(pagerView, true);

    }

    private void tabSettings() {
        binding.tab.post(() -> {
            indicatorWidth = binding.tab.getWidth() / binding.tab.getTabCount();
            FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) binding.indicator.getLayoutParams();
            indicatorParams.width = indicatorWidth;
            binding.indicator.setLayoutParams(indicatorParams);
        });

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetP
            ) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.indicator.getLayoutParams();
                float translationOffset = (positionOffset + position) * indicatorWidth;
                params.leftMargin = (int) translationOffset;
                binding.indicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class SamplePagerAdapter extends FragmentStatePagerAdapter {

        private static final int FIRST_TAB = 0;
        private static final int SECOND_TAB = 1;

        private int[] TABS;

        private Context mContext;

        public SamplePagerAdapter(final Context context, final FragmentManager fm) {
            super(fm);
            mContext = context.getApplicationContext();
            TABS = new int[]{FIRST_TAB, SECOND_TAB};
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (TABS[position]) {
                case FIRST_TAB:
                    bundle.putString("param1","FIRST TAB");
                    BlankFragment blankFragment=new BlankFragment();
                    blankFragment.setArguments(bundle);
                    return blankFragment;

                case SECOND_TAB:
                    bundle.putString("param1","SECOND TAB");
                    BlankFragment blankFragment1=new BlankFragment();
                    blankFragment1.setArguments(bundle);
                    return blankFragment1;

            }
            return null;

        }

        @Override
        public int getCount() {
            return TABS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (TABS[position]) {
                case FIRST_TAB:
                    return "FIRST TAB";
                case SECOND_TAB:
                    return "SECOND TAB";
            }
            return null;
        }
    }
}
