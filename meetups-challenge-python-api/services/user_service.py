from sqlalchemy.orm import Session
from core.schemas import UserCreate, UserUpdate
from core.models import User

def get_all(db: Session, skip: int = 0, limit: int = 100):
    return db.query(User).offset(skip).limit(limit).all()

def get_by_id(db: Session, user_id: int):
    return db.query(User).filter(User.id == user_id).first()

def get_by_username(db: Session, username: str):
    return db.query(User).filter(User.username == username).first()

def create(db: Session, user: UserCreate):
    db_user = User(username=user.username, email=user.email, password=user.password)
    db.add(db_user)
    db.commit()
    db.refresh(db_user)
    return db_user

def update(db: Session, user: UserUpdate, db_user: User):
    db_user.email = user.email
    db_user.password = user.password
    db.commit()
    db.refresh(db_user)
    return db_user

def delete(db: Session, db_user: User):
    db.delete(db_user)
    db.commit()
    return {"message": "User has been deleted"}





