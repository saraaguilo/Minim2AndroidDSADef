package edu.upc.dsa.restproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.upc.dsa.restproject.models.Item;

public class RecyclerViewAdapterItems extends RecyclerView.Adapter<RecyclerViewAdapterItems.ViewHolder> {
    private static RecyclerClickViewListener listener;
    public List<Item> items;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView idItem,name,description,price;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idItem=(TextView)itemView.findViewById(R.id.idItem);
            name=(TextView)itemView.findViewById(R.id.name);
            description=(TextView)itemView.findViewById(R.id.description);
            price=(TextView)itemView.findViewById(R.id.price);
            image=(ImageView) itemView.findViewById(R.id.itemImage);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            listener.recyclerViewListClicked(this.getLayoutPosition());
        }
    }

    public RecyclerViewAdapterItems(List<Item> items, RecyclerClickViewListener listener) {
        this.items = items;
        this.listener =listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_itemcard,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.idItem.setText(items.get(position).getIdItem());
        holder.name.setText(items.get(position).getName());
        holder.description.setText(items.get(position).getDescription());
        holder.price.setText(Integer.toString((int) items.get(position).getPrice()));
        Picasso.get().load(items.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}

