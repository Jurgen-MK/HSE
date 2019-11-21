package kz.ktzh.hserailways.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kz.ktzh.hserailways.databinding.IncItemsDbBinding;
import kz.ktzh.hserailways.entity.Incidents;

public class IncAdapter extends RecyclerView.Adapter<IncAdapter.IncidentsViewHolder> {

    private List<Incidents> incList;


    public IncAdapter(List<Incidents> incList) {
        this.incList = incList;
    }



    @NonNull
    @Override
    public IncidentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        IncItemsDbBinding itemBinding = IncItemsDbBinding.inflate(layoutInflater, parent, false);
        return new IncidentsViewHolder(itemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull IncidentsViewHolder holder, int position) {
        Incidents incs = incList.get(position);
        holder.bind(incs);
    }

    @Override
    public int getItemCount() {
        return incList != null ? incList.size() : 0;
    }

    class IncidentsViewHolder extends RecyclerView.ViewHolder {
        // If your layout file is something_awesome.xml then your binding class will be SomethingAwesomeBinding
        // Since our layout file is item_movie.xml, our auto generated binding class is ItemMovieBinding
        private IncItemsDbBinding binding;

        //Define a constructor taking a ItemMovieBinding as its parameter
        public IncidentsViewHolder(IncItemsDbBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Incidents incs) {
            binding.setIncidents(incs);
            binding.executePendingBindings();
        }
    }
}
