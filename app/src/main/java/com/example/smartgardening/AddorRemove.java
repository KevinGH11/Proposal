package com.example.smartgardening;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import static java.sql.Types.NULL;

public class AddorRemove extends AppCompatActivity{



    private TextView land;
    private Button anrLand;
    private Button anrPlant;
    private Button back;
    public static int registered_land[] = new int[2];
    public static int[] registered_plant= {0, 0};
    static int landEdit, landspace1=0, landspace2=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addor_remove);
        Bundle bundle = getIntent().getExtras();

        land = (TextView) findViewById(R.id.land);
        anrLand = (Button)findViewById(R.id.anrLand);
        anrPlant = (Button)findViewById(R.id.anrPlant);
        back = (Button)findViewById(R.id.btn_return);


/*Get which landspace is being modified*/
        String landNo = bundle.getString("landID");
        if(landNo.equals("LandSpace1"))
            landEdit = landspace1;
        else
            landEdit = landspace2;

        land.setText(landNo);
        display();

/*Action upon click*/
        anrLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registered_land[landEdit] == 0) {
                    Intent intent = new Intent(AddorRemove.this, listview.class);
                    intent.putExtra("landspace", landEdit);
                    startActivity(intent);
                    registered_land[landEdit] = 1;
                    display();
                }else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {  //pop up dialog
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GlobalClass globalClass = (GlobalClass) getApplicationContext();
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    registered_land[landEdit] = 0;
                                    registered_plant[landEdit] = 0;
                                    Intent intent = new Intent(AddorRemove.this, Afterlogin.class);
                                    intent.putExtra("clear", 1);
                                    if(landEdit==0){
                                        globalClass.setLandspace1Name("null");
                                    }
                                    if(landEdit==1){
                                        globalClass.setLandspace2Name("null");
                                    }
                                    startActivity(intent);
                                    display();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(anrLand.getContext());
                    builder.setMessage("Are you sure to remove land? (plant will also be removed)").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }

            }
        });

        anrPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registered_plant[landEdit] == 0) {
                    Intent intent = new Intent(AddorRemove.this, listview_plant.class);
                    intent.putExtra("landspace", landEdit);
                    startActivity(intent);
                    registered_plant[landEdit] = 1;
                    display();
                }else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GlobalClass globalClass = (GlobalClass) getApplicationContext();
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    registered_plant[landEdit] = 0;
                                    Intent intent = new Intent(AddorRemove.this, Afterlogin.class);
                                    intent.putExtra("clear", 1);
                                    if(landEdit==0){
                                        globalClass.setPlant1ID(NULL);
                                    }
                                    if(landEdit==1){
                                        globalClass.setPlant2ID(NULL);
                                    }
                                    startActivity(intent);
                                    display();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(anrLand.getContext());
                    builder.setMessage("Are you sure to remove plant?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddorRemove.this, Afterlogin.class);
                startActivity(intent);
            }
        });
    }
    private void display(){
        if(registered_land[landEdit] == 0) {
            anrLand.setText("Add Land");
            anrPlant.setEnabled(false);

        }else {
            anrLand.setText("Remove Land");
            anrPlant.setEnabled(true);

        }if(registered_plant[landEdit] == 0)
            anrPlant.setText("Add Plant");

        else
            anrPlant.setText("Remove Plant");

        return;
    }
}
