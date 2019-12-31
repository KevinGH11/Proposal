6from rest_framework import serializers
from . import models

class UserSerializer(serializers.ModelSerializer):
	class Meta:
		model = models.User
		
class LandSerializer(serializers.ModelSerializer):
	class Meta:
		model = models.Land
		
class PlantSerializer(serializers.ModelSerializer):
	class Meta:
		model = models.Plant
		
