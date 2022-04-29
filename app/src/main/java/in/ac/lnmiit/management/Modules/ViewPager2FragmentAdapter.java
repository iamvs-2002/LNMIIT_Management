package in.ac.lnmiit.management.Modules;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2FragmentAdapter extends FragmentStateAdapter {

    private String[] titles = new String[]{"ALL","Sick","CL"};

    public ViewPager2FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new TabAllLeaves();
            case 1:
                return new TabSickLeaves();
            case 2:
                return new TabClLeaves();
        }
        return new TabAllLeaves();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
