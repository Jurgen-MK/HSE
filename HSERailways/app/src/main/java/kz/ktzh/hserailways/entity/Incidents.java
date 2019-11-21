package kz.ktzh.hserailways.entity;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;


import kz.ktzh.hserailways.R;
import kz.ktzh.hserailways.network.NetworkServiceResource;


public class Incidents {

	Integer inc_id;
	Integer user_id;
	String dated;
	String discription;
	Integer content_type;
	String content_path;
	public String imageUrl;

	protected Incidents() {}
	
	public Incidents(Integer user_id, String dated, String discription, Integer content_type, String content_path) {
		this.user_id = user_id;
		this.dated = dated;
		this.discription = discription;
		this.content_type = content_type;
		this.content_path = content_path;


	}

	@BindingAdapter("imageUrl")
	public static void loadImage(ImageView view, String imageUrl) {
		Log.i("token",""+ NetworkServiceResource.getInstance().getAccessToken());
		GlideUrl glideUrl = new GlideUrl(imageUrl,
				new LazyHeaders.Builder()
						.addHeader("Authorization", "Bearer " + NetworkServiceResource.getInstance().getAccessToken())
						.build());
		Glide.with(view.getContext())
				.load(glideUrl).placeholder(R.mipmap.ic_launcher).error(R.drawable.ic_menu_send)
				.into(view);
	}

	public Integer getInc_id() {
		return inc_id;
	}

	public void setInc_id(Integer inc_id) {
		this.inc_id = inc_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getDated() {
		return dated;
	}

	public void setDated(String dated) {
		this.dated = dated;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Integer getContent_type() {
		return content_type;
	}

	public void setContent_type(Integer content_type) {
		this.content_type = content_type;
	}

	public String getContent_path() {
		return content_path;
	}

	public void setContent_path(String content_path) {
		this.content_path = content_path;
	}



	
}
