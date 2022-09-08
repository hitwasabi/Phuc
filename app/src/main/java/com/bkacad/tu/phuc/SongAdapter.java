package com.bkacad.tu.phuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SongAdapter extends BaseAdapter {
    private Context context;
    private List<Song> songList;

    public SongAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int i) {
        return songList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            //Can truyen layout itemView

            view = LayoutInflater.from(context).inflate(R.layout.item_music,viewGroup,false);
        }
        //bind id cho item view

        TextView Title = view.findViewById(R.id.item_song_name);
        TextView Author = view.findViewById(R.id.item_song_author);
        //Do du lieu vao view
        Song song = songList.get(i);
        Title.setText(song.getTitle());
        Author.setText(song.getAuthor());
        return view;
    }
}