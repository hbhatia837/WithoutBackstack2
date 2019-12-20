package com.example.withoutbackstack2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView tv1;
    Button button,pop,remove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=findViewById(R.id.text);
        button=findViewById(R.id.buttton);
        pop=findViewById(R.id.pop);
        remove=findViewById(R.id.remove);
        fragmentManager=getSupportFragmentManager();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment();

            }
        });

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
                // if we press back button
        {
            @Override
            public void onBackStackChanged() {
                Log.e("tag1","in back stack changed"+fragmentManager.getBackStackEntryCount());
                tv1.setText("fragment count in backstack" +fragmentManager.getBackStackEntryCount());
                StringBuilder backStackEntryMessage=new StringBuilder("Current  status of fragment transaction backstack"+fragmentManager.getBackStackEntryCount()+"\n");
                Log.e("tag1","in Strig Builder" +backStackEntryMessage);
                 for(int index=(fragmentManager.getBackStackEntryCount()-1); index>=0; index--)
                 {
                     FragmentManager.BackStackEntry entry=fragmentManager.getBackStackEntryAt(index);
                     Log.e("tag1","in entery"+entry);
                     backStackEntryMessage.append(entry.getName()+"\n");
                     Log.e(
                             "tag1","in Sample"+entry.getName());
                 }

            }
        });



    }

    private void addFragment() {
        Fragment fragment;
      //  Log.e("tag1","fragment"+fragment);
        fragment=fragmentManager.findFragmentById(R.id.fragmentContainer);
        Log.e("tag1","fragment"+fragment);
        if(fragment instanceof  SampleFragment)
        {
            fragment=new SecondFragment();
            Log.e("tag1","fragment"+fragment);
        }
        else if(fragment instanceof SecondFragment)
        {
            fragment=new ThirdFragment();
            Log.e("tag1","fragment"+fragment);
        }
        else
        {
            fragment=new SampleFragment();
            Log.e("tag1","default fragment"+fragment);
        }






        //   FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Log.e("tag1","fragment"+fragment);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, "demo fragment");

     //  fragmentTransaction.addToBackStack("Add"+fragment.toString());//this indicate event has backstack  // if you dont want any name for  backstack you pass null
        Log.e("tag1","addbackstack"+fragment);
        fragmentTransaction.commit();   // after   this line it is going to listener
        Log.e("tag1","after commit");
        // if any fragment we ara adding value will be updated in backstack listener


    }
    // without backstack

    public void onBackPressed() {
        Fragment fragment=fragmentManager.findFragmentById(R.id.fragmentContainer);
        if(fragment!=null)
        {
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.addToBackStack("Remove"+fragment.toString());
            Log.e("tag1","Remove fragment"+fragment);
            fragmentTransaction.commit();
        }
        else
        {
            super.onBackPressed();
        }
    }



}
