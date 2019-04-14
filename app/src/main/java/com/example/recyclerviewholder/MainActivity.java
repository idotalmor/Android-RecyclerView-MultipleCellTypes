package com.example.recyclerviewholder;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));

        ArrayList<CellDataObject> data = new ArrayList<>();

        CellDataObject firstObject = new CellDataObject("first title","first description","https://upload.wikimedia.org/wikipedia/commons/f/f9/Phoenicopterus_ruber_in_S%C3%A3o_Paulo_Zoo.jpg");
        CellDataObject secondObject = new CellDataObject("second title","second description","https://upload.wikimedia.org/wikipedia/commons/f/f9/Phoenicopterus_ruber_in_S%C3%A3o_Paulo_Zoo.jpg");

        data.add(firstObject);
        data.add(secondObject);

        recyclerView.setAdapter(new CustomAdapter(data));

        //we can notify data change set from outside the adapter as well -

        recyclerView.getAdapter().notifyDataSetChanged();

        //we can add third object to the adapter - the add method calls the notifyDataSetChanged as well so we covered
        CellDataObject thirdObject = new CellDataObject("third title","third description",null);

        if(recyclerView.getAdapter() instanceof CustomAdapter){
        ((CustomAdapter)recyclerView.getAdapter()).addObject(thirdObject);}

    }

    public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        /**
         * Create custom classes for each cell type
         */
        public class FirstViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView title, description;

            public FirstViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.recycler_item_image);
                title = itemView.findViewById(R.id.recycler_item_title);
                description = itemView.findViewById(R.id.recycler_item_description);
            }
        }

        public class SecondViewHolder extends RecyclerView.ViewHolder {

            TextView title, description;

            public SecondViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.recycler_item_title);
                description = itemView.findViewById(R.id.recycler_item_description);
            }
        }

        ArrayList<CellDataObject> dataSet;

        /**
         * Overloading constructors
         */

        public CustomAdapter(ArrayList<CellDataObject> dataSet) {
            this.dataSet = dataSet;
        }

        public CustomAdapter() {
            dataSet = new ArrayList<>();
        }

        /**
         * Adding and removing data from list
         */
        public void addObject(CellDataObject data) {
            dataSet.add(data);
            this.notifyDataSetChanged();
        }

        public void removeObject(int index) {
            dataSet.remove(index);
            this.notifyDataSetChanged();
        }

        /**
         * Overriding the methods of the adapter
         */

        //I can add multiple cell types to the adapter - in iOS this will be prototype cell
        public static final int first_type_cell = 0;
        public static final int second_type_cell = 1;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

            //Inflate cell view according to type
            if (viewType == first_type_cell) {

                View view = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.item_layout, viewGroup, false);

                return new FirstViewHolder(view);

            } else {

                View view = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.item_layout2, viewGroup, false);

                return new SecondViewHolder(view);

            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            CellDataObject cell = dataSet.get(i);

            //check for cell type - to know the class and the cell to inflate data inside
            if (first_type_cell == getItemViewType(i)) {

                FirstViewHolder customViewHolder = (FirstViewHolder) viewHolder;
                Picasso.get().load(Uri.parse(cell.getUrl())).error(R.drawable.round_user).into(customViewHolder.image);
                customViewHolder.title.setText(cell.getTitle());
                customViewHolder.description.setText(cell.getDescription());

            } else {

                SecondViewHolder customViewHolder = (SecondViewHolder) viewHolder;
                customViewHolder.title.setText(cell.getTitle());
                customViewHolder.description.setText(cell.getDescription());
            }

        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        @Override
        public int getItemViewType(int position) {

            //return cell type - second cell for object without url
            CellDataObject cellDataObject = dataSet.get(position);

            if (cellDataObject.getUrl() != null) {
                return first_type_cell;
            } else {
                return second_type_cell;
            }

        }
    }

}
