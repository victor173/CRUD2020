package adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;import android.widget.TextView;import android.widget.Toast;
import androidx.annotation.NonNull;import androidx.recyclerview.widget.RecyclerView;

import com.example.crud2020.R;

import java.util.List;

import entidades.DTO;

public class adaptadorart extends RecyclerView.Adapter<adaptadorart.adaptadorartViewHolder> {
    private Context myContext;
    private List<DTO> artiList;

    public  adaptadorart(Context myContext, List<DTO> artiList){
        this.myContext = myContext;
        this.artiList = artiList;
    }




    @NonNull
    @Override
    public adaptadorartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.diseno_recy, null);


        return new adaptadorartViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorartViewHolder holder, int position) {
        DTO dti = artiList.get(position);

        holder.tvrecycod.setText("Cod: "+dti.getCodigo());
        holder.tvrecydesc.setText("Des: "+dti.getDescripcion());
        holder.tvrecypz.setText("$ :  "+dti.getPrecio());
        holder.tvcounter.setText("Articulo N° "+(position+1)+" de "+getItemCount());


    }

    @Override
    public int getItemCount() {
        return artiList.size();
    }

    public class adaptadorartViewHolder extends RecyclerView.ViewHolder {
        TextView tvrecycod,tvrecydesc,tvrecypz,tvcounter;
        public adaptadorartViewHolder( View itemView) {
            super(itemView);
            tvrecycod = itemView.findViewById(R.id.tvrecycod);
            tvrecydesc = itemView.findViewById(R.id.tvrecydesc);
            tvrecypz = itemView.findViewById(R.id.tvrecypz);
            tvcounter = itemView.findViewById(R.id.tvcounter);

        }
    }


    public  void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
}
