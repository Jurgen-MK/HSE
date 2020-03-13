package kz.ktzh.hserailways.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.List;

import kz.ktzh.hserailways.databinding.IncItemsDbBinding;
import kz.ktzh.hserailways.entity.Incidents;
import kz.ktzh.hserailways.network.NetworkService;
import kz.ktzh.hserailways.network.NetworkServiceResource;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.MediaType.parse;

public class IncAdapter extends RecyclerView.Adapter<IncAdapter.IncidentsViewHolder> {

    private List<Incidents> incList;
    private String accessToken;


    public IncAdapter(List<Incidents> incList) {
        this.incList = incList;
    }



    @NonNull
    @Override
    public IncidentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        accessToken = NetworkServiceResource.getInstance().getAccessToken();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        IncItemsDbBinding itemBinding = IncItemsDbBinding.inflate(layoutInflater, parent, false);
        return new IncidentsViewHolder(itemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull IncidentsViewHolder holder, final int position) {
        Incidents incs = incList.get(position);
        holder.bind(incs);
        holder.binding.imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                NetworkServiceResource.getInstance()
                        .getJSONApi()
                        .removeInc("Bearer " + accessToken, incList.get(position).getInc_id())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                incList.remove(position);  // remove the item from list
                                notifyItemRemoved(position); // notify the adapter about the removed item
                                notifyItemRangeChanged(position, incList.size());
                                Toast.makeText(v.getContext(), "Инцидент удален", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(v.getContext(), "Ошибка удаления инцидента", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
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
