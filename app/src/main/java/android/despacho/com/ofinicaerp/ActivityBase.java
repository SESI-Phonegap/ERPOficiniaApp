package android.despacho.com.ofinicaerp;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class ActivityBase extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (findViewById(R.id.tablet_view) == null) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }

    public void changeFragment(Fragment fragment, int resource, boolean isRoot, boolean backStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (isRoot) {
            transaction.add(resource, fragment);
        } else {
            transaction.replace(resource, fragment);
        }

        if (backStack) {
            transaction.addToBackStack(null);
        }
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.enter_from_left);
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }


}
