package com.example.westerenchews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter <ProductAdapter.MyViewHolder>{
    Activity activity;
    String userEmail;
    FirebaseFirestore db;
    ArrayList<Product> arrayList;
    Context context;
    public ProductAdapter(Activity activity, ArrayList<Product> arrayList, String userEmail, FirebaseFirestore db) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.userEmail = userEmail;
        this.db = db;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(activity.getLayoutInflater().inflate(R.layout.row_person,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.MyViewHolder holder, int position) {
        holder.amount.setText(arrayList.get(position).getAmount());
        holder.name.setText(arrayList.get(position).getName());
        Glide.with(activity).load(arrayList.get(position).getImageurl()).into(holder.image);
        holder.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceOrder(arrayList.get(holder.getAdapterPosition()).getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,amount,buynow;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            buynow = itemView.findViewById(R.id.buynow);
        }
    }

    private void PlaceOrder(String productid) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", userEmail);
        user.put("productid", productid);


// Add a new document with a generated ID
        db.collection("orders")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {


                        Toast.makeText(activity, "Order Placed", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context,MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
