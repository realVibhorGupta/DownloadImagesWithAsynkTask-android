package com.example.vibhor.downloadimageswithasyntask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText mEditText;
    String[] listImages;
    private ProgressBar mProgressBar;
    private ListView mListView;
    private LinearLayout mLoadingSection = null;
    //  MyTask myTask=null;
    NonUIFragment nonUIFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mProgressBar = (ProgressBar) findViewById(R.id.download_bar);
        mListView = (ListView) findViewById(R.id.list_view);
        listImages = getResources().getStringArray(R.array.image_urls);
        mListView.setOnItemClickListener(this);
        mLoadingSection = (LinearLayout) findViewById(R.id.downloading_image_layout);


        if (savedInstanceState == null) {
            nonUIFragment = new NonUIFragment();
            getSupportFragmentManager().beginTransaction().add(nonUIFragment, "Fragments Non UI").commit();
        } else {
            nonUIFragment = (NonUIFragment) getSupportFragmentManager().findFragmentByTag("Fragments Non UI");
        }
        if(nonUIFragment != null)
        {
            if(nonUIFragment.myTask !=null && nonUIFragment.myTask.getStatus() == AsyncTask.Status.RUNNING)
            {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

    }

    public void onDownload(View view) {
        if (mEditText.getText().toString() != null &&
                mEditText.getText().toString().length() > 0) {
            //  MyTask myTask = new MyTask();
            //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //   myTask.execute((Runnable) mEditText);


            nonUIFragment.beginTask(mEditText.getText().toString());

        }

    }


    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mEditText.setText(listImages[position]);

    }


    public void updateProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

    public void showProgressBarBeforeDownloading() {
        if (nonUIFragment.myTask != null) {
            /**this will check the maximum and minimum visibility of the task
             *
             */
            if (mProgressBar.getVisibility() != View.VISIBLE && mProgressBar.getProgress() != mProgressBar.getMax()) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

    }

    public void hideProgressBarAfterDownloading() {
        if (nonUIFragment.myTask != null) {
            if (mProgressBar.getVisibility() == View.VISIBLE) {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }

}
