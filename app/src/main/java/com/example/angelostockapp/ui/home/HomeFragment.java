package com.example.angelostockapp.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import com.example.angelostockapp.GlobalQuote;
import com.example.angelostockapp.GsonRequest;
import com.example.angelostockapp.R;
import com.example.angelostockapp.Stock;
import com.example.angelostockapp.StockRecyclerAdapter;

public class HomeFragment extends Fragment {

    private final String[] stocks = {"IBM","AAPL","GOOGL","AMZN","TSLA"};
    private ArrayList<Stock> theStocks;
    RecyclerView example;
    StockRecyclerAdapter exampleAdapter;

        private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        example = root.findViewById(R.id.recycler);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        theStocks = new ArrayList<Stock>();
        example = root.findViewById(R.id.recycler);

        for(String s : stocks) {
            String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol={{REPLACE_KEY}}&apikey=57b6f32738msh15337c251f22c71p199559jsn171b55dfe050".replace("{{REPLACE_KEY}}",s);
            RequestQueue queue = Volley.newRequestQueue(getActivity());


            GsonRequest<GlobalQuote> req = new GsonRequest<GlobalQuote>(url, GlobalQuote.class, null, new Response.Listener<GlobalQuote>() {
                @Override
                public void onResponse(GlobalQuote response) {

                    System.out.println("Successful Response");
                    System.out.println(response.getGlobalQuote().getSymbol());
                    theStocks.add(response.getGlobalQuote());

                    if(theStocks.size() == stocks.length){
                        doView();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work!");
                }
            });

            queue.add(req);
        }

        return root;
    }

    private void doView(){
        exampleAdapter = new StockRecyclerAdapter(theStocks);
        example.setAdapter(exampleAdapter);
        example.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}