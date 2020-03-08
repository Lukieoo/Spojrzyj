package com.anioncode.spojrzyj.Classes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.anioncode.spojrzyj.Adapter.AdapterStore;
import com.anioncode.spojrzyj.AddLensActivity;
import com.anioncode.spojrzyj.DatabaseHelper;
import com.anioncode.spojrzyj.DatabaseSQL.DatabaseHelperStore;
import com.anioncode.spojrzyj.Model.StoreModel;
import com.anioncode.spojrzyj.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    LinearLayout show1;
    RelativeLayout show2;

    CardView card1;
    CardView card2;
    FloatingActionButton fab;

    private AdapterStore adapter;
    private ArrayList<StoreModel> listStore;

    public BottomSheetDialog(AdapterStore adapter, ArrayList<StoreModel> listStore) {
        this.adapter = adapter;
        this.listStore = listStore;
    }

    Spinner spinner;
    EditText pojemnosc;
    EditText sztuk;

    EditText okol;
    EditText okop;

    int curentSelected = 0;
    private String Typ = "";

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        View contentView = View.inflate(getContext(), R.layout.model_bottom_sheet_layout, null);
        dialog.setContentView(contentView);

        okol = contentView.findViewById(R.id.okol);
        okop = contentView.findViewById(R.id.okop);

        fab = contentView.findViewById(R.id.fab);
        pojemnosc = contentView.findViewById(R.id.pojemnosc);
        sztuk = contentView.findViewById(R.id.sztuk);

        card1 = contentView.findViewById(R.id.card1);
        card2 = contentView.findViewById(R.id.card2);


        show1 = contentView.findViewById(R.id.show1);
        show2 = contentView.findViewById(R.id.Rel1);

        card1.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                card1.setBackgroundColor(contentView.getContext().getColor(R.color.colorAccent));
                card2.setBackgroundColor(contentView.getContext().getColor(R.color.colorWhite));
            }
            show1.setVisibility(View.VISIBLE);
            show2.setVisibility(View.GONE);
            curentSelected = 2;
        });

        card2.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                card2.setBackgroundColor(contentView.getContext().getColor(R.color.colorAccent));
                card1.setBackgroundColor(contentView.getContext().getColor(R.color.colorWhite));
            }

            show1.setVisibility(View.GONE);
            show2.setVisibility(View.VISIBLE);
            curentSelected = 1;
        });
        final SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        spinner = contentView.findViewById(R.id.spinner);
        spinner(sharedPref);


        fab.setOnClickListener(v -> {
            if (curentSelected == 1) {

                if (!okol.getText().toString().trim().equals("") && !okop.getText().toString().trim().equals("")) {

                    listStore.add(new StoreModel(sumint()+1, curentSelected, Double.parseDouble(okol.getText().toString()), Double.parseDouble(okop.getText().toString()),
                            Typ));
                    AddData(sumint()+1, Double.parseDouble(okol.getText().toString()), Double.parseDouble(okop.getText().toString()), Typ, curentSelected, 0);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

            } else if (curentSelected == 2) {

                if (!pojemnosc.getText().toString().trim().equals("") && Integer.parseInt(pojemnosc.getText().toString()) > 0) {
                    if (Integer.parseInt(sztuk.getText().toString()) > 0) {
                        for (int i = 0; i < Integer.parseInt(sztuk.getText().toString()); i++) {
                            listStore.add(new StoreModel(sumint()+1, curentSelected, Integer.parseInt(pojemnosc.getText().toString())));
                            AddData(sumint()+1, 0, 0, "", curentSelected, Integer.parseInt(pojemnosc.getText().toString()));

                        }
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                }

            }


        });
    }

    private int sumint() {

        DatabaseHelperStore mDatabaseHelper = new DatabaseHelperStore(getActivity());
        Cursor data = mDatabaseHelper.getData();
        int suma = 0;
        while (data.moveToNext()) {

            suma += data.getInt(6);
        }
        return suma;
    }

    public void AddData(int numberSize, double lewe_oko, double prawe_oko, String rodzaj, int typ, int pojemnosc) {

        DatabaseHelperStore mDatabaseHelper = new DatabaseHelperStore(getActivity());
        mDatabaseHelper.addData(lewe_oko, prawe_oko, rodzaj, typ, pojemnosc, numberSize);


    }

    public void spinner(SharedPreferences sharedPreferences) {


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Typ = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> categories = new ArrayList<String>();

        categories.add(getString(R.string.jednodniowe));
        categories.add(getString(R.string.dwutygodniowe));
        categories.add(getString(R.string.miesieczne));
        categories.add(getString(R.string.kwartalne));
        categories.add(getString(R.string.roczne));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories) {
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(ResourcesCompat.getFont(getActivity(), R.font.productsanslight));
                return v;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        String compareValue = sharedPreferences.getString("typ", "");
        // String compareValue = String.valueOf(trees.getWysokosc());

        if (compareValue != null) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            spinner.setSelection(spinnerPosition);

        }

    }
}
