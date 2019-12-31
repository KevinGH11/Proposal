from rest_framework import routers
from garden import views as myapp_views

router = routers.DefaultRouter()
router.register(r'User', myapp_views.UserViewset)
router.register(r'Land', myapp_views.LandViewset)
router.register(r'Plant', myapp_views.PlantViewset)
