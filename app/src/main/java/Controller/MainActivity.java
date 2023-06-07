package Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottom_navigation_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Fragments definition
        LeaderBoardFragment leaderboardfragment = new LeaderBoardFragment();
        TrackFragment trackfragment = new TrackFragment();
        LibraryFragment libraryfragment = new LibraryFragment();
        CastFragment castfragment = new CastFragment();

        //Bottom Navigation
        bottom_navigation_main = findViewById(R.id.bottom_navigation_main_view);
        bottom_navigation_main.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_menu_library:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,libraryfragment).commit();
                        return true;
                    case R.id.bottom_nav_menu_cast:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,castfragment).commit();
                        return true;
                    case R.id.bottom_nav_menu_Leaderboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,leaderboardfragment).commit();
                        return true;
                    case R.id.bottom_nav_menu_track:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,trackfragment).commit();
                        return true;
                    default: return true;
                }
            }
        });
        bottom_navigation_main.setSelectedItemId(R.id.bottom_nav_menu_cast);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent_settings = new Intent(MainActivity.this, SettingsActivity.class);
        switch (item.getItemId()) {
            case R.id.toolbar_menu_settings_view:
                startActivity(intent_settings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}