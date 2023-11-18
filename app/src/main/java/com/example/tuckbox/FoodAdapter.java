package com.example.tuckbox;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuckbox.data.models.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foodList;

    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvDishName.setText(food.name);
        holder.tvEtcChoiceType.setText(food.extra_choice_type);
        holder.imgDish.setImageResource(getDishImage(food.name));

        holder.radioGroupSpiceLevel.removeAllViews();
        for (String option : food.getExtra_choice_options()) {
            RadioButton radioButton = new RadioButton(holder.radioGroupSpiceLevel.getContext());
            radioButton.setText(option);
            holder.radioGroupSpiceLevel.addView(radioButton);
        }

        // Add functionality for + and - buttons
        holder.btnAdd.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.edtDishCount.getText().toString());
            count++;
            holder.edtDishCount.setText(String.valueOf(count));
        });

        holder.btnSubtract.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.edtDishCount.getText().toString());
            if (count > 0) {
                count--;
                holder.edtDishCount.setText(String.valueOf(count));
            }
        });

        // Add TextWatcher to update the quantity in the food object
        holder.edtDishCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int quantity = Integer.parseInt(editable.toString());
                    food.setQuantity(quantity);
                } catch (NumberFormatException e) {
                    food.setQuantity(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private int getDishImage(String dishName) {
        switch (dishName) {
            case "Green Salad Lunch":
                return R.drawable.greenslad;
            case "Lamb Korma":
                return R.drawable.lambkorma;
            case "Open Chicken Sandwich":
                return R.drawable.sandwish;
            case "Beef Noodle Salad":
                return R.drawable.noodlesalad;
            default:
                return R.drawable.food;
        }
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvDishName;
        TextView tvEtcChoiceType;
        ImageView imgDish;
        RadioGroup radioGroupSpiceLevel;
        Button btnAdd, btnSubtract;
        EditText edtDishCount;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEtcChoiceType = itemView.findViewById(R.id.tvEtcChoiceType);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            imgDish = itemView.findViewById(R.id.imgDish);
            radioGroupSpiceLevel = itemView.findViewById(R.id.radioGroupSpiceLevel);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnSubtract = itemView.findViewById(R.id.btnSubtract);
            edtDishCount = itemView.findViewById(R.id.edtDishCount);
        }
    }
}
