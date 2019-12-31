package com.example.smartgardening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Afterlogin extends AppCompatActivity {

    private TextView landID1;
    private TextView landID2;
    private ImageButton landspace1;
    private ImageButton landspace2;
    private TextView plantname1;
    private TextView plantname2;
    private TextView userGreeting;
    private int statusimage1, statusimage2;
    int[] image = {R.drawable.brightdry, R.drawable.darkdry, R.drawable.brighthealthy, R.drawable.darkhealthy, R.drawable.brightwet, R.drawable.darkwet, R.drawable.empty};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        landID1 = (TextView) findViewById(R.id.txtLandID1);
        landID2 = (TextView) findViewById(R.id.txtLandID2);
        plantname1 = (TextView) findViewById(R.id.txtPlant1);
        plantname2 = (TextView) findViewById(R.id.txtPlant2);
        landspace1 = (ImageButton) findViewById(R.id.btnLandspace1);
        landspace2 = (ImageButton) findViewById(R.id.btnLandspace2);
        userGreeting = (TextView) findViewById(R.id.tv_usergreeting);

        Bundle bundle = getIntent().getExtras();
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        int landstate1 = AddorRemove.registered_land[0];
        int landstate2 = AddorRemove.registered_land[1];
        int plantstate1 = AddorRemove.registered_plant[0];
        int plantstate2 = AddorRemove.registered_plant[1];

        statuscheck(landstate1, landstate2);

/*displaying of text from GlobalClass variables*/
        userGreeting.setText("Welcome " +globalClass.getApparentUser());
        landID1.setText("Land ID: " +globalClass.getLandspace1Name());
        landID2.setText("Land ID: " +globalClass.getLandspace2Name());

        if(globalClass.getPlantName() != null) {
            if(plantstate1 ==1)
                plantname1.setText(globalClass.getPlantName().get(globalClass.getPlant1ID()));
            else
                plantname1.setText("");
            if(plantstate2 ==1)
                plantname2.setText(globalClass.getPlantName().get(globalClass.getPlant2ID()));
            else
                plantname2.setText("");
        } else{}

/*start new intent on button click*/
        landspace1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Afterlogin.this, AddorRemove.class);
                intent.putExtra("landID", "LandSpace1");
                startActivity(intent);
            }
        });

        landspace2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Afterlogin.this, AddorRemove.class);
                intent.putExtra("landID", "LandSpace2");
                startActivity(intent);
            }
        });



    }

/*function to determine image based on current soil moisture, ideal soil moisture, and current light intensity*/
    private int imagestatus(int currentSoilMoisture, int idealSoilMoisture, int currentLightIntensity) {
        int brightdry = 0, darkdry = 1, brighthealthy = 2, darkhealthy = 3, brightwet = 4, darkwet = 5;
        int ut_idealSM = idealSoilMoisture + 100;
        int lt_idealSM = idealSoilMoisture - 100;

        if (ut_idealSM > 1023)
            ut_idealSM = 1023;
        if (lt_idealSM < 0)
            lt_idealSM = 0;

        if (currentSoilMoisture < lt_idealSM)
            if (currentLightIntensity > 950)
                return brightdry;
            else
                return darkdry;
        if (currentSoilMoisture > ut_idealSM)
            if (currentLightIntensity > 950)
                return brightwet;
            else
                return darkwet;
        else if (currentLightIntensity > 950)
                return brighthealthy;
            else
                return darkhealthy;
    }

/*Updates image display*/
    private void statuscheck(int landstate1, int landstate2){
        GlobalClass globalClass = (GlobalClass) getApplicationContext();

        if (landstate1 == 0)
            landspace1.setImageResource(image[6]);

        else {
            if(AddorRemove.registered_plant[0] != 0) {
                int i = Integer.parseInt(globalClass.getLandspace1Name());
                int j = globalClass.getPlant1ID();
                int currentSoilMoisture = Integer.parseInt(globalClass.getCurrentSoilMoisture().get(i));
                int currentLightIntensity = Integer.parseInt(globalClass.getCurrentLightIntensity().get(i));
                int idealSoilMoisture = Integer.parseInt(globalClass.getOptimalSoilMoisture().get(j));

                statusimage1 = imagestatus(currentSoilMoisture, idealSoilMoisture, currentLightIntensity);
                landspace1.setImageResource(image[statusimage1]);
            }
        }

        if (landstate2 ==0)
            landspace2.setImageResource(image[6]);
        else {
            if(AddorRemove.registered_plant[1] != 0) {
                int i = Integer.parseInt(globalClass.getLandspace2Name());
                int j = globalClass.getPlant2ID();
                int currentSoilMoisture = Integer.parseInt(globalClass.getCurrentSoilMoisture().get(i));
                int idealSoilMoisture = Integer.parseInt(globalClass.getOptimalSoilMoisture().get(j));
                int currentLightIntensity = Integer.parseInt(globalClass.getCurrentLightIntensity().get(i));

                statusimage2 = imagestatus(currentSoilMoisture, idealSoilMoisture, currentLightIntensity);
                landspace2.setImageResource(image[statusimage2]);
            }
        }
    }


}
