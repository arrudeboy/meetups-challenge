from fastapi import APIRouter, Depends, HTTPException
from typing import List
from sqlalchemy.orm import Session
from pydantic import Json

from security.auth import get_auth, required_roles
from security.admin import create_kc_user, update_kc_user, delete_kc_user
from core import schemas
from config.db import get_database
from services import user_service

users = APIRouter(prefix='/users')

@users.get('/all', response_model=List[schemas.User])
@required_roles("meetup_user")
def get_all(skip: int = 0, limit: int = 100, identity: Json = Depends(get_auth), db: Session = Depends(get_database)):
    users = user_service.get_users(db, skip=skip, limit=limit)
    return users

@users.get("/{id}", response_model=schemas.User)
@required_roles("meetup_user")
def get_user(id: int, identity: Json = Depends(get_auth), db: Session = Depends(get_database)):
    db_user = user_service.get_by_id(db, user_id=id)
    if db_user is None:
        raise HTTPException(status_code=500, detail='User with id {} not found'.format(id))

    return db_user

@users.post("/add", response_model=schemas.User)
@required_roles("meetup_admin")
def create_user(user: schemas.UserCreate, identity: Json = Depends(get_auth), db: Session = Depends(get_database)):
    db_user = user_service.get_by_username(db, username=user.username)
    if db_user:
        raise HTTPException(status_code=400, detail="Username already registered")
    try:
        new_user = user_service.create(db=db, user=user)
        create_kc_user(user.username, user.email, user.password)
        return new_user
    except AssertionError as e:
        raise HTTPException(status_code=500, detail='{}'.format(e))

@users.put("/update", response_model=schemas.User)
@required_roles("meetup_admin")
def update_user(user: schemas.UserUpdate, identity: Json = Depends(get_auth), db: Session = Depends(get_database)):
    db_user = user_service.get_by_id(db, user.id) 
    if db_user is None:
        raise HTTPException(status_code=500, detail='User with id {} not found'.format(user.id))
    
    updated_user = user_service.update(db, user, db_user)
    update_kc_user(updated_user.username, updated_user.email, updated_user.password)
    return updated_user

@users.delete("/{id}", response_model=dict)
@required_roles("meetup_admin")
def delete_user(id: int, identity: Json = Depends(get_auth), db: Session = Depends(get_database)):
    db_user = user_service.get_by_id(db, user_id=id)
    if db_user is None:
        raise HTTPException(status_code=500, detail='User with id {} not found'.format(id))

    response = user_service.delete(db, db_user)
    delete_kc_user(db_user.username)
    return response
