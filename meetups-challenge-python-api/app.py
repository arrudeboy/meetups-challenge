from fastapi import FastAPI

from config.db import engine
from config.settings import settings
from routers.users import users
from core.models import Base

app = FastAPI(title=settings.PROJECT_NAME, version=settings.PROJECT_VERSION)
app.include_router(users)

Base.metadata.create_all(bind=engine)
