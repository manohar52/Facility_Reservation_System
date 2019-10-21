package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facilityreservationsystem.R;

import java.util.List;

import model.Facility;
import model.Reservation;

public class noshowviolationadapter extends RecyclerView.Adapter<noshowviolationadapter.MyViewHolder> {

    private List<Reservation> resList;
    private static Context ct;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvresnum, tvdate, tvstime, tvnsvio;

        public MyViewHolder(View view) {
            super(view);
            ct = view.getContext();
            tvresnum = (TextView) view.findViewById(R.id.tvresnum);
            tvnsvio = (TextView) view.findViewById(R.id.tvnsvio);
            tvdate = (TextView) view.findViewById(R.id.tvdate);
            tvstime = (TextView) view.findViewById(R.id.tvstime);
        }
    }

    public noshowviolationadapter(List<Reservation> reservations){

        this.resList = reservations;
    }

    @NonNull
    @Override
    public noshowviolationadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noshowviolationitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull noshowviolationadapter.MyViewHolder holder, int position) {
        Reservation reservation = resList.get(position);
        holder.tvresnum.setText(Long.toString(reservation.getResId()));
        holder.tvnsvio.setText(reservation.getNoshowViolationStatus());
        holder.tvdate.setText(reservation.getDate());
        holder.tvstime.setText(reservation.getStime());
    }

    @Override
    public int getItemCount() {
        return resList.size();
    }
}
