from rest_framework import serializers
from . import models

class UserSerializer(serializers.ModelSerializer):
        class Meta:
                model = models.User
                fields = ('id', 'userName', 'password')

class LandSerializer(serializers.ModelSerializer):
        class Meta:
                model = models.Land
                fields = ('LandID', 'CurrentSoilMoisturelvl', 'CurrentLightIntensity')

class PlantSerializer(serializers.ModelSerializer):
        class Meta:
                model = models.Plant
                fields = ('LandID', 'PlantID', 'plantName', 'OptimalSoilMoisture')
