package com.example.myproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.MainActivity2;
import com.example.myproject.Models.CartModel;
import com.example.myproject.R;
import com.example.myproject.orderDBhelper;

import java.util.ArrayList;
import java.util.List;
import static com.example.myproject.Adapters.MainAdapter.orderid;
import static com.example.myproject.MainActivity2.EMAIL;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.viewholder> {
    ArrayList<CartModel> list;
    Context context;
    orderDBhelper odb;
    String emaill;

    public CartAdapter(ArrayList<CartModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_design, parent, false);
        return new CartAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.viewholder holder, int position) {
        final CartModel model = list.get(position);
        holder.cname.setText(model.getName());
        holder.cprice.setText(model.getPrice());
        holder.cquantity.setText(model.getQuantity());
    }
    public void removeItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView cname, cprice, cquantity;
        Button remove;


        public viewholder(@NonNull final View itemView) {
            super(itemView);
            emaill = EMAIL;
            odb = new orderDBhelper(context);
            cname = itemView.findViewById(R.id.cartName);
            cprice = itemView.findViewById(R.id.cartPrice);
            cquantity = itemView.findViewById(R.id.cartQuantity);
            remove = itemView.findViewById(R.id.deleteButton);
            remove.setOnClickListener(v -> {
                String n = cname.getText().toString();
                String q = cquantity.getText().toString();
                Boolean Res = odb.deleteOrder(orderid, emaill, n, q);
                if (Res == true) {
                    Toast.makeText(itemView.getContext(), "Item Deleted Successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context, MainActivity2.class);
                    context.startActivity(i);
                } else {
                    Toast.makeText(itemView.getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            });






        }
    }
}

