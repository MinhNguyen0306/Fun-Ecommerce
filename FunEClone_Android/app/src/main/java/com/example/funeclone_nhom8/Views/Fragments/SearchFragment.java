package com.example.funeclone_nhom8.Views.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funeclone_nhom8.Adapters.SearchResultAdapter;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.Product;
import com.example.funeclone_nhom8.Datas.Models.Recommended;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.AppUtil;
import com.example.funeclone_nhom8.Views.Activitys.SignupSellerActivity;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment{
    private ImageButton btn_search;
    private EditText edt_search;
    private LinearLayout key_search;
    private TextView btn_close_key_search;
    private TextView count_cart;
    private TextView txt_search_key;
    private FlowLayout flowLayout;
    private RecyclerView rcv_also_like;
    private RecyclerView rcv_recently;
    private CardView register_seller_card_view;
    private Button btn_register_seller;
    private ArrayList<Recommended> recommendeds;
    private SearchResultAdapter searchResultAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initUI(view);

        Integer countCart = DataLocalManager.getCountCart();
        if(countCart > 0) {
            count_cart.setVisibility(View.VISIBLE);
            count_cart.setText(String.valueOf(countCart));
        } else {
            count_cart.setVisibility(View.GONE);
        }

        User user = DataLocalManager.getUser();
        if(user != null) {
            if(user.isSeller()) {
                register_seller_card_view.setVisibility(View.GONE);
            }
        }

        btn_search.setOnClickListener(view1 -> {
            openSearchResultFragment();
        });

        getRecommendedData();

        setDataRecommended();

        btn_register_seller.setOnClickListener(view1 -> {
            Intent intent = new Intent(this.getActivity(), SignupSellerActivity.class);
            startActivity(intent);
        });

        edt_search.setOnEditorActionListener((textView, i, keyEvent) -> {
            if(i == EditorInfo.IME_ACTION_DONE) {
                key_search.setVisibility(View.VISIBLE);
                txt_search_key.setText(edt_search.getText());
                edt_search.setText("");
                edt_search.clearFocus();
                AppUtil.closeKeyBoard(getActivity(), edt_search);
                return true;
            }
            return false;
        });

        btn_close_key_search.setOnClickListener(view1 -> {
            txt_search_key.setText("");
            key_search.setVisibility(View.GONE);
        });

        searchResultAdapter = new SearchResultAdapter(getContext());
        searchResultAdapter.setData(getProductData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL, false);
        rcv_recently.setLayoutManager(linearLayoutManager);
        rcv_recently.setAdapter(searchResultAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL, false);
        rcv_also_like.setLayoutManager(linearLayoutManager2);
        rcv_also_like.setAdapter(searchResultAdapter);

        return view;
    }

    private List<Product> getProductData() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Ba lô", 11, R.drawable.balo));
        productList.add(new Product(2, "Đồng hồ nam", 8, R.drawable.dongho));
        productList.add(new Product(3, "Giày thể thao", 15, R.drawable.giay));
        return productList;
    }

    private void getRecommendedData() {
        recommendeds = new ArrayList<>();
        recommendeds.add(new Recommended(1, "Denim Jeans"));
        recommendeds.add(new Recommended(2, "Mini Skirt"));
        recommendeds.add(new Recommended(3, "Jacket"));
        recommendeds.add(new Recommended(4, "Accessories"));
        recommendeds.add(new Recommended(5, "Sports Accessories"));
        recommendeds.add(new Recommended(6, "Yoga Pants"));
        recommendeds.add(new Recommended(7, "Eye Shadow"));
    }

    private void setDataRecommended() {
        if(flowLayout == null) {
            return;
        } else {
            flowLayout.removeAllViews();
            if(recommendeds != null && recommendeds.size() > 0) {
                for (Recommended recommended : recommendeds) {
                    TextView textView = new TextView(getContext());
                    FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(
                            FlowLayout.LayoutParams.WRAP_CONTENT,
                            FlowLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(0, 0, 20, 20);
                    textView.setLayoutParams(layoutParams);
                    textView.setId(recommended.getId());
                    textView.setText(recommended.getName());
                    textView.setPadding(20, 20, 20,  20);
                    textView.setBackgroundResource(R.drawable.bg_input_text);
                    textView.setTextColor(getResources().getColor(R.color.black));

                    flowLayout.addView(textView);
                }
            }
        }
    }

    private void openSearchResultFragment() {
        SearchResultFragment searchResultFragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", txt_search_key.getText().toString().trim());
        searchResultFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.content_nav, searchResultFragment);
        fragmentTransaction.commit();
    }

    private void initUI(View view) {
        btn_search = view.findViewById(R.id.btn_search);
        edt_search = view.findViewById(R.id.edt_search);
        flowLayout = view.findViewById(R.id.flow_layout);
        rcv_also_like = view.findViewById(R.id.rcv_also_like);
        rcv_recently = view.findViewById(R.id.rcv_recently);
        btn_register_seller = view.findViewById(R.id.btn_register_seller);
        register_seller_card_view = view.findViewById(R.id.register_seller_card_view);
        key_search = view.findViewById(R.id.key_search);
        txt_search_key = view.findViewById(R.id.txt_search_key);
        btn_close_key_search = view.findViewById(R.id.btn_close_key_search);
        count_cart = view.findViewById(R.id.count_cart);
    }
}
