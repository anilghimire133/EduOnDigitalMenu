package com.example.ayush.digitalmenu.MainMenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ayush.digitalmenu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {

    private Context mcontext;
    private List<MainMenuPojo> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(MainMenuPojo userInfo);
//        void onRefresh();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public MainMenuAdapter(Context context, List<MainMenuPojo> list) {

        this.mcontext = context;
        this.mExampleList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mcontext).inflate(R.layout.cardview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        MainMenuPojo info = mExampleList.get(position);

        String image = info.getImageUrl();
        String hsname = info.getCreator();

        holder.textView.setText(hsname);
        // below in picasso placeholder is used for default image...
        Picasso.with(mcontext).load(image).fit().centerInside().noFade().placeholder(R.drawable.cart_icon).into(holder.imageView);
//        Glide.with(mcontext).load(image).into(holder.imageView);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainMenuPojo userInfo = mExampleList.get(position);
                mListener.onItemClick(userInfo);
            }
        });
    }


    @Override
    public int getItemCount() {

        return mExampleList.size();
    }

    void setFilter(List<MainMenuPojo> listitem) {
        mExampleList = new ArrayList<>();
        mExampleList.addAll(listitem);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ImageView imageView;
        private final LinearLayout rootView;

        ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text1);
            imageView = itemView.findViewById(R.id.image_view);
            rootView = itemView.findViewById(R.id.rootView);

            if (imageView == null) {
                imageView.setImageResource(R.drawable.cart_icon);
            }
        }
    }
}
