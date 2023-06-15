package edu.upc.dsa.restproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.upc.dsa.restproject.models.FAQ;


public class RecycleViewAdapterFAQ extends RecyclerView.Adapter<RecycleViewAdapterFAQ.ViewHolder>{

    public List<FAQ> faqs;
    private static RecyclerClickViewListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView question,answer;
        public ViewHolder(@NonNull View FAQview) {
            super(FAQview);
            question=(TextView)FAQview.findViewById(R.id.question);
            answer=(TextView)FAQview.findViewById(R.id.answer);
            FAQview.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            listener.recyclerViewListClicked(this.getLayoutPosition());
        }
    }

    public RecycleViewAdapterFAQ(List<FAQ> faqs, RecyclerClickViewListener listener) {
        this.faqs = faqs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecycleViewAdapterFAQ.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_faqcard,parent,false);
        RecycleViewAdapterFAQ.ViewHolder viewHolder= new RecycleViewAdapterFAQ.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.question.setText(faqs.get(position).getQuestion());
        holder.answer.setText(faqs.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}

