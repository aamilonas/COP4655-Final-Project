package com.example.angelostockapp.ui.home;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angelostockapp.Login;
import com.example.angelostockapp.MainActivity;
import com.example.angelostockapp.Stock;
import com.example.angelostockapp.AlphaVantage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.angelostockapp.R;
import com.example.angelostockapp.StockListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {


    EditText mQuery;
    ImageButton mSearch;
    ListView mList;
    ArrayList<Stock> mStocks;
    String[] mTestStocks = {"IBM","AAPL"};
    ListView mStockListView;
    StockListAdapter mStockListAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mStocks = new ArrayList<Stock>();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AlphaVantage av = AlphaVantage.getInstance();
        mStockListView = getView().findViewById(R.id.searchView);
        Stock stonk;
        for(String s : mTestStocks){
            stonk = av.getStock(s);
            if(stonk != null){
                mStocks.add(stonk);
            }

        }
        mStockListAdapter = new StockListAdapter(getContext(),mStocks);
        mStockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Add code here to do what you want when an Item is clicked
                //Toast.makeText(getContext(), mStocks.get(i).getSymbol().toString(), Toast.LENGTH_SHORT).show();
                if(MainActivity.isFav(mStocks.get(i).getSymbol())){
                    MainActivity.remFav(mStocks.get(i).getSymbol());
                    Toast.makeText(getContext(), mStocks.get(i).getSymbol() + " REMOVED FROM FAVORITES", Toast.LENGTH_SHORT).show();
                }
                else{
                   MainActivity.addFav(mStocks.get(i).getSymbol());
                    Toast.makeText(getContext(), mStocks.get(i).getSymbol() + " ADDED TO FAVORITES", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mStockListView.setAdapter(mStockListAdapter);
        mSearch = getView().findViewById(R.id.searchButton);
        mQuery = getView().findViewById(R.id.query);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });

    }




    public void onSearch(){
        //TODO: Validation
        AlphaVantage av = AlphaVantage.getInstance();
        String q = mQuery.getText().toString();
        Stock stk = av.getStock(q);
        if (stk != null) {
            mStocks.add(stk);
        }
        mStockListView.setAdapter(mStockListAdapter);


    }
}