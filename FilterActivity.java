package com.example.kundan6singh.testproject.Filter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kundan6singh.testproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterActivity extends AppCompatActivity {
     RecyclerView rvView;
    ImageButton btnVoice;
    RelativeLayout rlByName;
    SearchView search;
   // AutoCompleteTextView autoComTV;
    AppCompatImageButton imgBtnSearchType;
     Adapter adapter;
    FilterAdapter filterAdapterObj;
    ArrayList<Product> productList = new ArrayList<>();
    List<String> alCategoryFilterList = new ArrayList<>();

    ArrayList<FilterListModel> filterItemList = new ArrayList<>();
    ArrayList<String> alSelectedFilterList = new ArrayList<>();
    FilterListModel filterListModelObj;
    CheckBox cbClearAll;
    Product productObj;
    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        rvView = (RecyclerView) findViewById(R.id.rv);
       /* autoComTV = (AutoCompleteTextView) findViewById(R.id.autoComTV);
        btnFilter = (Button) findViewById(R.id.btnFilter);*/
        search=(SearchView)findViewById(R.id.search);
        btnVoice = (ImageButton) findViewById(R.id.btnVoice);
        imgBtnSearchType = (AppCompatImageButton) findViewById(R.id.imgBtnSearchType);
        rlByName=(RelativeLayout)findViewById(R.id.rlByName);
        getJsonData();
        rvView.setHasFixedSize(true);
        adapter = new Adapter(FilterActivity.this, productList);
        rvView.setAdapter(adapter);
        rvView.setLayoutManager(new LinearLayoutManager(this));
        rvView.setItemAnimator(new DefaultItemAnimator());
        imgBtnSearchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(FilterActivity.this, imgBtnSearchType);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(FilterActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        String value = item.getTitle().toString();
                        switch (value) {
                            case "By Category":
                                showCustomDialogForFilter(alCategoryFilterList, filterItemList);
                                rlByName.setVisibility(View.GONE);
                                break;
                            case "By Name":
                                populateRecyclerView();
                                rlByName.setVisibility(View.VISIBLE);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
       /* autoComTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.v("before", "Before Change");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v("onText", "on text Change");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.v("aftertext", "after text Change");
                autoComTV.setThreshold(1);//will start working from first character
                // autoComTV.setAdapter(alCategoryFilterList);//setting the adapter data into the AutoCompleteTextView
                autoComTV.setTextColor(Color.RED);
            }
        });*/
        // autoComTV.setOnApplyWindowInsetsListener();

    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            final ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty()) {
                String Query = matches.get(0);
                search.setQuery(Query, false);
                btnVoice.setEnabled(false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showCustomDialogForFilter(final List<String> alCategoryFilterList, final ArrayList<FilterListModel> filterItemList) {
        final RecyclerView rvFilter;
        final TextView tvCancel, tvSearch, tvClearAll, tvSelectAll;
        final CheckBox cbSelectAll;
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_filter_dialog_layout);
        tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        tvSearch = (TextView) dialog.findViewById(R.id.tvSearch);
        tvClearAll = (TextView) dialog.findViewById(R.id.tvClearAll);
        cbClearAll = (CheckBox) dialog.findViewById(R.id.cbClearAll);
        tvSelectAll = (TextView) dialog.findViewById(R.id.tvSelectAll);
        cbSelectAll = (CheckBox) dialog.findViewById(R.id.cbSelectAll);

        rvFilter = (RecyclerView) dialog.findViewById(R.id.rvFilter);

        rvFilter.setHasFixedSize(true);
        final FilterListAdapter filderListAdapter = new FilterListAdapter(FilterActivity.this, filterItemList);
        rvFilter.setAdapter(filderListAdapter);
        rvFilter.setLayoutManager(new LinearLayoutManager(this));
        rvFilter.setItemAnimator(new DefaultItemAnimator());
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alSelectedFilterList.clear();
                for (int i = 0; i < alCategoryFilterList.size(); i++) {
                    FilterListModel filterListModel = filterItemList.get(i);
                    String groupName = filterListModel.getName();
                    boolean isCheckdByUser = filterListModel.isChecked();
                    if (isCheckdByUser)
                        alSelectedFilterList.add(groupName);
                    Log.v("checked", String.valueOf(isCheckdByUser));
                }
                showFilterListRecyclerView(alSelectedFilterList);
                dialog.dismiss();
            }
        });
        cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setClearAllSelection();
                for (int i = 0; i < alCategoryFilterList.size(); i++) {
                    FilterListModel filterListModel = filterItemList.get(i);
                    filterListModel.setChecked(true);
                }
                final FilterListAdapter filderListAdapter = new FilterListAdapter(FilterActivity.this, filterItemList);
                rvFilter.setAdapter(filderListAdapter);
            }
        });
        cbClearAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbSelectAll.setChecked(false);
                for (int i = 0; i < alCategoryFilterList.size(); i++) {
                    FilterListModel filterListModel = filterItemList.get(i);
                    filterListModel.setChecked(false);
                }
                final FilterListAdapter filderListAdapter = new FilterListAdapter(FilterActivity.this, filterItemList);
                rvFilter.setAdapter(filderListAdapter);
                //filderListAdapter.notifyDataSetChanged();
            }
        });
        dialog.show();

    }

    private void showFilterListRecyclerView(ArrayList<String> alSelectedFilterList) {
        FilterProduct filterProductobj;
        ArrayList<FilterProduct> alAfterFilterList = new ArrayList<>();
        for (int j = 0; j < alSelectedFilterList.size(); j++) {
            String selectedCatValueStr = alSelectedFilterList.get(j).toString();
            for (int i = 0; i < productList.size(); i++) {
                filterProductobj = new FilterProduct();
                productObj = productList.get(i);
                String categoryDataValue = productObj.getCatValue();
                if (selectedCatValueStr.equalsIgnoreCase(categoryDataValue)) {
                    String nameDataValue = productObj.getTnaValue();
                    String uidDataValue = productObj.getTnaValue();
                    filterProductobj.setImageNameDataValue(nameDataValue);
                    filterProductobj.setUIDDataValue(uidDataValue);
                    alAfterFilterList.add(filterProductobj);
                }

            }
        }
        filterAdapterObj = new FilterAdapter(FilterActivity.this, alAfterFilterList);
        rvView.setAdapter(filterAdapterObj);
    }

    private void getJsonData() {
        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("ProductReg");

            for (int i = 0; i < m_jArry.length(); i++) {
                productObj = new Product();
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("CAT"));
                String cat_value = jo_inside.getString("CAT");
                String tna_value = jo_inside.getString("TNA");
                String uid_value = jo_inside.getString("UID");
                productObj.setCatValue(cat_value);
                productObj.setTnaValue(tna_value);
                productObj.setUidValue(uid_value);
                alCategoryFilterList.add(cat_value);
                productList.add(productObj);
            }

            setDataForFilter();
            populateRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDataForFilter() {
        Set<String> hs = new HashSet<>();
        hs.addAll(alCategoryFilterList);
        alCategoryFilterList.clear();
        alCategoryFilterList.addAll(hs);
        filterItemList.clear();
        for (int i = 0; i < alCategoryFilterList.size(); i++) {
            filterListModelObj = new FilterListModel();
            filterListModelObj.setName(alCategoryFilterList.get(i).toString());
            filterListModelObj.setChecked(true);
            filterItemList.add(filterListModelObj);
        }
    }


    private void populateRecyclerView() {
        adapter = new Adapter(FilterActivity.this, productList);
        rvView.setAdapter(adapter);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("json.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void setClearAllSelection() {
        if (cbClearAll.isChecked())
            cbClearAll.setChecked(false);
    }

    public void reFreashAdapter(List<Product> mFilteredList) {
        adapter = new Adapter(FilterActivity.this, mFilteredList);
        rvView.setAdapter(adapter);
    }
}
