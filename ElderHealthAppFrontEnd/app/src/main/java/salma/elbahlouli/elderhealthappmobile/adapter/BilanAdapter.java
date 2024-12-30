package salma.elbahlouli.elderhealthappmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import salma.elbahlouli.elderhealthappmobile.R;
import salma.elbahlouli.elderhealthappmobile.model.Bilan;

import java.util.List;

public class BilanAdapter extends RecyclerView.Adapter<BilanAdapter.BilanViewHolder> {

    private final List<Bilan> bilanList;
    private final OnDeleteClickListener deleteClickListener;
    private final OnEditClickListener editClickListener;  // Ajouter l'interface d'édition

    // Constructor avec OnDeleteClickListener et OnEditClickListener
    public BilanAdapter(List<Bilan> bilanList, OnDeleteClickListener deleteClickListener, OnEditClickListener editClickListener) {
        this.bilanList = bilanList;
        this.deleteClickListener = deleteClickListener;
        this.editClickListener = editClickListener;
    }

    @NonNull
    @Override
    public BilanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bilan, parent, false);
        return new BilanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BilanViewHolder holder, int position) {
        Bilan bilan = bilanList.get(position);
        holder.nomTestTextView.setText(bilan.getNomTest());
        holder.valeurTextView.setText(bilan.getValeur());

        // Gérer le clic sur "Supprimer"
        holder.deleteButton.setOnClickListener(v -> deleteClickListener.onDeleteClick(bilan, position));

        // Gérer le clic sur "Modifier"
        holder.editButton.setOnClickListener(v -> editClickListener.onEditClick(bilan, position));
    }

    @Override
    public int getItemCount() {
        return bilanList.size();
    }

    public void updateList(List<Bilan> newList) {
        bilanList.clear();
        bilanList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class BilanViewHolder extends RecyclerView.ViewHolder {
        TextView nomTestTextView;
        TextView valeurTextView;
        ImageView deleteButton;
        ImageView editButton;

        public BilanViewHolder(@NonNull View itemView) {
            super(itemView);
            nomTestTextView = itemView.findViewById(R.id.textNomTest);
            valeurTextView = itemView.findViewById(R.id.textValeurTest);
            deleteButton = itemView.findViewById(R.id.btnDelete);
            editButton = itemView.findViewById(R.id.btnEdit);
        }
    }

    // Interface pour supprimer un élément
    public interface OnDeleteClickListener {
        void onDeleteClick(Bilan bilan, int position);
    }

    // Interface pour modifier un élément
    public interface OnEditClickListener {
        void onEditClick(Bilan bilan, int position);
    }
}
