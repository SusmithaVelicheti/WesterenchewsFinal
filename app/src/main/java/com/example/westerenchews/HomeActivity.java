package com.example.westerenchews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    String userEmail;
    private FirebaseFirestore db;

    RecyclerView list;
    ArrayList<Product> arrayList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userEmail = getIntent().getExtras().getString("email");
        list = findViewById(R.id.list);

        db = FirebaseFirestore.getInstance();
        list.setLayoutManager(new GridLayoutManager(getApplicationContext(),2,RecyclerView.VERTICAL,false));
        list.setAdapter(new ProductAdapter(this,arrayList,userEmail,db));

        getProducts();

    }
    private void getProducts() {

        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Product product = new Product();
                                product.setId(document.getId());
                                product.setName(document.getString("name"));
                                product.setAmount(document.getString("price"));
                                product.setImageurl(document.getString("imageurl"));
                                arrayList.add(product);


                            }
                            list.getAdapter().notifyDataSetChanged();


                        }
                    }
                });
    }


}