from rest_framework import viewsets
from . import models
from . import serializers

class UserViewset (viewsets.ModelViewSet):
	queryset = models.User.objects.all()
	serializer_class = serializers.UserSerializer

class LandViewset (viewsets.ModelViewSet):
	queryset = models.Land.objects.all()
	serializer_class = serializers.LandSerializer

class PlantViewset (viewsets.ModelViewSet):
	queryset = models.Plant.objects.all()
	serializer_class = serializers.PlantSerializer
