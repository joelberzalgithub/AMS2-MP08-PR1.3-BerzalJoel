package com.example.listilla;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

/*
    Abans de començar hem de fer:
    (1) Afegir al layout activity_main una ListView anomenada recordsView
            R.id.recordsView
    (2) Un layout (lineal) per a la ListView anomenat list_item
            R.layout.list_item
        Aquest contindrà 2 TextView amb IDs: nom i intents
            R.id.nom
            R.id.intents
    (3) Un botó amb id=button (per generar entrades al model)
 */
public class MainActivity extends AppCompatActivity {
    // Model: Record (intents = puntuació, nom, imatge)
    public static class Record {
        public int intents;
        public String nom;
        public int imatge;
        public Record(int _intents, String _nom, int _imatge) {
            intents = _intents;
            nom = _nom;
            imatge = _imatge;
        }
    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;
    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;
    // Llista de l'id de totes les imatges de la ruta res/drawable
    int[] imatges = { R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9, R.drawable.image10, R.drawable.image11, R.drawable.image12, R.drawable.image13, R.drawable.image14, R.drawable.image15, R.drawable.image16, R.drawable.image17, R.drawable.image18, R.drawable.image19, R.drawable.image20 };
    // Objecte Random per poder generar números aleatoris
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        records.add(new Record(33,"Manolo", imatges[random.nextInt(imatges.length)]));
        records.add(new Record(12,"Pepe", imatges[random.nextInt(imatges.length)]));
        records.add(new Record(42,"Laura", imatges[random.nextInt(imatges.length)]));

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>(this, R.layout.list_item, records) {
            @SuppressLint("SetTextI18n")
            @NonNull
            @Override
            public View getView(int pos, View convertView, @NonNull ViewGroup container) {

                // GetView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if (convertView == null) {
                    // Inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" els valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(Objects.requireNonNull(getItem(pos)).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(Objects.requireNonNull(getItem(pos)).intents));
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(Objects.requireNonNull(getItem(pos)).imatge);
                return convertView;
            }
        };

        // Busquem la ListView i li endollem l'ArrayAdapter
        ListView lv =  findViewById(R.id.recordsView);
        lv.setAdapter(adapter);

        // Botó per afegir entrades a la ListView
        Button b1 = findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] noms = { "María", "Juan", "Ana", "Carlos", "Laura", "Pedro", "Sofía", "Luís", "Valentina", "Alejandro", "Andrea", "Diego", "Gabriela", "Antonio", "Isabella", "Miguel", "Camila", "Rafael", "Valeria", "Javier" };
                String[] cognoms = { "García", "López", "Rodríguez", "Martínez", "Pérez", "González", "Fernández", "Sánchez", "Ramírez", "Torres", "González", "Vargas", "Castro", "Jiménez", "Ruiz", "Herrera", "Morales", "Ortega", "Navarro", "Soto" };

                String nom = noms[random.nextInt(noms.length)] + " " + cognoms[random.nextInt(cognoms.length)];
                records.add(new Record(random.nextInt(101), nom, imatges[random.nextInt(imatges.length)]));

                // Notifiquem l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });

        // Botó per ordenar l'ArrayList de rècords
        Button b2 = findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                records.sort(new Comparator<Record>() {
                    @Override
                    public int compare(Record record_1, Record record_2) {
                        return Integer.compare(record_1.intents, record_2.intents);
                    }
                });
                // Notifiquem l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });
    }
}
