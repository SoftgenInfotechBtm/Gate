package com.softgen.gate.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.freshdesk.hotline.Hotline;
import com.freshdesk.hotline.HotlineConfig;
import com.freshdesk.hotline.HotlineUser;
import com.freshdesk.hotline.exception.HotlineInvalidUserPropertyException;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;
import com.softgen.gate.gatedb.R;
import com.softgen.gate.provider.SharedUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 9Jeevan on 19-08-2016.
 */
public class HomeActivity extends AppCompatActivity {
    ImageButton share;
    CarouselView carouselView;
    ScrollView tabs;
    ImageListener imageListener = new ImageListener() {

        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            int[] sampleImages = {R.drawable.img8, R.drawable.img10, R.drawable.img11, R.drawable.img12, R.drawable.img13, R.drawable.img15, R.drawable.img9};
            //imageView.setImageResource(sampleImages[position]);
            Picasso.with(HomeActivity.this).load(sampleImages[position])
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resize(260, 150)
                    .centerInside()
                    .error(R.drawable.gate)
                    .into(imageView);
        }
    };
    private Context mActivity;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        manager = getFragmentManager();
        share = (ImageButton) findViewById(R.id.Share);
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        tabs = (ScrollView) findViewById(R.id.service_tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActivity = HomeActivity.this;
        initCaroselSlider(7);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Gate for u: https://play.google.com/store/apps/details?id=com.softgen.gatedb");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share Via"));
            }
        });
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.spacenav);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Profile", R.drawable.prof));
        spaceNavigationView.addSpaceItem(new SpaceItem("Settings", R.drawable.ic_menu_manage));
        spaceNavigationView.addSpaceItem(new SpaceItem("Profile1", R.drawable.ic_nav));
        spaceNavigationView.addSpaceItem(new SpaceItem("Settings2", R.drawable.service2));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                startActivity(new Intent(HomeActivity.this, OfferedActivity.class));
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemName) {
                    case "Profile":
                        startActivity(new Intent(HomeActivity.this, EditProfileActivity.class));
                        break;

                    case "Settings":
                        startActivity(new Intent(HomeActivity.this, HelpActivity.class));
                        break;
                    case "Profile1":
                        startActivity(new Intent(HomeActivity.this, EditProfileActivity.class));
                        break;

                    case "Settings2":
                        startActivity(new Intent(HomeActivity.this, EditProfileActivity.class));
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                switch (itemName) {
                    case "Profile":
                        startActivity(new Intent(HomeActivity.this, EditProfileActivity.class));
                        break;

                    case "Settings":
                        startActivity(new Intent(HomeActivity.this, HelpActivity.class));
                        break;
                    case "Profile1":
                        startActivity(new Intent(HomeActivity.this, EditProfileActivity.class));
                        break;

                    case "Settings2":
                        startActivity(new Intent(HomeActivity.this, EditProfileActivity.class));
                        break;
                }
            }
        });
        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                Toast.makeText(HomeActivity.this, "onCentreButtonLongClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                Toast.makeText(HomeActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setCancelable(false);
        builder.setMessage(" Do u want to Exit ?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                SharedUtils.saveLoginDisabled(mActivity, true);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.help) {
            initHotLineChats();
            Hotline.showFAQs(getApplicationContext());
            return true;
        } else if (id == R.id.rateus) {
            return true;
        } else if (id == R.id.signout) {
            Intent i4 = new Intent(this, LoginActivity.class);
            i4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            SharedUtils.saveLoginDisabled(mActivity, false);
            finish();
            startActivity(i4);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initHotLineChats() {
        HotlineConfig hConfig = new HotlineConfig("84ab2d70-5a36-4182-8cb1-4c1bb862f735", " 677744fb-9ed3-458c-bf68-13b42642174e");
        hConfig.setVoiceMessagingEnabled(true);
        hConfig.setCameraCaptureEnabled(true);
        hConfig.setPictureMessagingEnabled(true);
        Hotline.getInstance(getApplicationContext()).init(hConfig);
        HotlineUser hlUser = Hotline.getInstance(getApplicationContext()).getUser();
//        hlUser.setName(db.user_name);
//        hlUser.setEmail(SQLiteHandler.user_email);
//        hlUser.setExternalId(SQLiteHandler.user_id);
        // hlUser.setPhone(SQLiteHandler.user_phone);
//Call updateUser so that the user information is synced with Hotline's servers
        Hotline.getInstance(getApplicationContext()).updateUser(hlUser);
        /* Set any custom metadata to give agents more context, and for segmentation for marketing or pro-active messaging */
        Map<String, String> userMeta = new HashMap<String, String>();
//Call updateUserProperties to sync the user properties with Hotline's servers
        try {
            Hotline.getInstance(getApplicationContext()).updateUserProperties(userMeta);
        } catch (HotlineInvalidUserPropertyException e) {
            e.printStackTrace();
        }
    }

    void initCaroselSlider(int no_of_images) {
        // set ViewListener for custom view
        //no_of_ads = no_of_images;
        if (no_of_images > 0)
            carouselView.setPageCount(no_of_images);
        else
            carouselView.setPageCount(7);

        carouselView.setImageListener(imageListener);
    }
}
