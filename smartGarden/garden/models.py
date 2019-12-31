from django.db import models

# Create your models here.
class User(models.Model):
        userName = models.CharField(max_length=20)
        password = models.CharField(max_length=20)

class Land(models.Model):
        LandID = models.PositiveSmallIntegerField(primary_key=True)
        CurrentSoilMoisturelvl = models.PositiveSmallIntegerField()
        CurrentLightIntensitylvl = models.PositiveSmallIntegerField()

class Plant(models.Model):
        LandID = models.ForeignKey(Land, on_delete=models.CASCADE)
        PlantID = models.PositiveSmallIntegerField(primary_key=True)
        plantName = models.CharField(max_length=20)
        OptimalSoilMoisture = models.PositiveSmallIntegerField()
        OptimalLightIntensity = models.PositiveSmallIntegerField()
