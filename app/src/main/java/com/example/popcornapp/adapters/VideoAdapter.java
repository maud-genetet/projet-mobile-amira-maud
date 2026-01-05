package com.example.popcornapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.popcornapp.Models.Video;
import com.example.popcornapp.R;

import java.util.List;

public class VideoAdapter extends ArrayAdapter<Video> {

    private Context context;
    private List<Video> videoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        super(context, 0, videoList);
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.videoImage = convertView.findViewById(R.id.videoImage);
            viewHolder.videoName = convertView.findViewById(R.id.videoName);
            viewHolder.videoType = convertView.findViewById(R.id.videoType);
            viewHolder.videoDuration = convertView.findViewById(R.id.videoDuration);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Video video = videoList.get(position);

        if (video != null) {
            viewHolder.videoName.setText(video.getName());
            viewHolder.videoType.setText("Type: " + video.getType());

            int runtimeSeconds = video.getRuntimeSeconds();
            int minutes = runtimeSeconds / 60;
            int seconds = runtimeSeconds % 60;
            String duration = String.format("%d:%02d", minutes, seconds);
            viewHolder.videoDuration.setText("Durée: " + duration);

            if (video.getPrimaryImage() != null && video.getPrimaryImage().getUrl() != null) {
                Glide.with(context)
                        .load(video.getPrimaryImage().getUrl())
                        .into(viewHolder.videoImage);
            }

            convertView.setOnClickListener(v -> {
                String videoUrl = "https://www.imdb.com/video/" + video.getId();
                openVideoLink(videoUrl);
            });
        }

        return convertView;
    }

    private void openVideoLink(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Erreur: impossible d'ouvrir le lien", Toast.LENGTH_SHORT).show();
        }
    }
}