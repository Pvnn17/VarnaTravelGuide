package com.example.yovo_user.varnatravelguide;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yovo_user.varnatravelguide.databasePackage.hotelPackage.Hotel;
import com.example.yovo_user.varnatravelguide.databasePackage.imagePackage.Image;
import com.example.yovo_user.varnatravelguide.databasePackage.landmarkPackage.Landmark;
import com.example.yovo_user.varnatravelguide.databasePackage.placePackage.Place;
import com.example.yovo_user.varnatravelguide.databasePackage.priceCategoryPackage.PriceCategory;
import com.example.yovo_user.varnatravelguide.databasePackage.restaurantPackage.Restaurant;
import com.example.yovo_user.varnatravelguide.databasePackage.shoppingPlacePackage.ShoppingPlace;
import com.example.yovo_user.varnatravelguide.databasePackage.VTGDatabase;
import com.example.yovo_user.varnatravelguide.databasePackage.workHoursPackage.WorkHours;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private android.support.v7.widget.GridLayout  mainLinksGridL;
    private List<ListUrlLinksItem> listUrlLinks  = new ArrayList<>();

    private VTGDatabase vtgDatabase = VTGDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateViewPager();

        mainLinksGridL = (android.support.v7.widget.GridLayout) findViewById(R.id.mainLinksGridL);
        //TODO: links for the 4 main activities
        setClickEvents(mainLinksGridL);
        generateLinks();
        addLinksClickListener();

    }

    private void generateViewPager(){

        setViewPager((ViewPager) findViewById(R.id.viewPagerId));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(viewPagerAdapter),3000,3000);
    }

    private void generateLinks(){

        ListView listUrlLinksItemsFromView = (ListView) findViewById(R.id.listLinksItems);

        ListUrlLinksItem[] listUrlLinksItems = ListUrlLinksItem.populatelistUrlLinksITems();

        for(ListUrlLinksItem linksItem : listUrlLinksItems){
            listUrlLinks.add(linksItem);
        }

        ArrayAdapter<ListUrlLinksItem> adapter = new CustomAdapter();
        listUrlLinksItemsFromView.setAdapter(adapter);

    }

    private class CustomAdapter extends ArrayAdapter<ListUrlLinksItem> {
        public CustomAdapter() {
            super(MainActivity.this, R.layout.link_list_item, listUrlLinks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = getLayoutInflater().
                        inflate(R.layout.link_list_item, parent,false);
            }

            ListUrlLinksItem currentItem = listUrlLinks.get(position);

            ImageView linkImage = (ImageView) convertView.findViewById(R.id.leftIco);
            TextView heading = (TextView) convertView.findViewById(R.id.heading);
            TextView desc = (TextView) convertView.findViewById(R.id.desc);

            heading.setText(currentItem.getHeading());
            desc.setText(currentItem.getDesc());
            linkImage.setImageResource(currentItem.getImage());

            return convertView;
        }
    }

    private void addLinksClickListener() {

        ListView linksListView = (ListView) findViewById(R.id.listLinksItems);

        linksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListUrlLinksItem currentItem = listUrlLinks.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(currentItem.getUrl()));
                startActivity(i);
            }
        });
    }

    //this will be STAND BY while making the links for the main activity
    private void setClickEvents(android.support.v7.widget.GridLayout mainLinksGridL){
        CardView hotelsCV = (CardView)findViewById(R.id.hotelsCV_id);
        hotelsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListingPlacesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE_OF_PLACES","hotels");
                intent.putExtras(bundle);
                startActivity(intent);
                //code for switching to another view !!!
            }
        });

        CardView restaurantsCV = (CardView)findViewById(R.id.restaurantsCV_id);
        restaurantsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListingPlacesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE_OF_PLACES","restaurants");

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        CardView landmarksCV = (CardView)findViewById(R.id.landmarksCV_id);
        landmarksCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListingPlacesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE_OF_PLACES","landmarks");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        CardView shoppingPlacesCV = (CardView)findViewById(R.id.shopping_placesCV_id);
        shoppingPlacesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListingPlacesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE_OF_PLACES","shoppingPlaces");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public class MyTimerTask extends TimerTask {
        private ViewPagerAdapter viewPagerAdapter;
        public MyTimerTask(ViewPagerAdapter viewPagerAdapter) {
            this.viewPagerAdapter = viewPagerAdapter;
        }

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() == viewPagerAdapter.getCount()-1) {
                        viewPager.setCurrentItem (0);
                    }else {
                        viewPager.setCurrentItem(
                                viewPager.getCurrentItem()+1
                        );
                    }
                }
            });
        }
    }



    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }


    public android.support.v7.widget.GridLayout  getMainLinksGridL() {
        return mainLinksGridL;
    }

    public void setMainLinksGridL(android.support.v7.widget.GridLayout  mainLinksGridL) {
        this.mainLinksGridL = mainLinksGridL;
    }
}
