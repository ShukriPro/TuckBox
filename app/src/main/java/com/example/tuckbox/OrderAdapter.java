package com.example.tuckbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuckbox.data.OrderFood;
import com.example.tuckbox.data.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private final List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvAddress.setText("Address: " + order.getAddress());
        holder.tvOrderName.setText("Name: " + order.getFullName());
        holder.tvTimeSlot.setText("Time: " + order.getTimeSlot());

        if(order.getFoods() != null && !order.getFoods().isEmpty()) {
            StringBuilder foodDetails = new StringBuilder();
            for (OrderFood food : order.getFoods()) {
                foodDetails.append(food.getFoodName())
                        .append(" (qty: ").append(food.getQuantity()).append(")\n");
            }
            holder.tvDishName.setText(foodDetails.toString());
            //holder.tvEtcChoiceType.setText("Spice Choices: N/A");
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDish;
        TextView tvDishName, tvEtcChoiceType, tvQuantity, tvTimeSlot, tvOrderName, tvAddress;

        OrderViewHolder(View itemView) {
            super(itemView);
            imgDish = itemView.findViewById(R.id.imgDish);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvEtcChoiceType = itemView.findViewById(R.id.tvEtcChoiceType);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTimeSlot = itemView.findViewById(R.id.tvTimeSlot);
            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }
    }
}