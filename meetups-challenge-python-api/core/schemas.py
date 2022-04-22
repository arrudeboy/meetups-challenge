from typing import List, Optional
from pydantic import BaseModel

class UserUpdate(BaseModel):
    id: int
    email: str
    password: str

class UserCreate(BaseModel):
    username: str
    email: str
    password: str

class User(UserUpdate):
    username: str

    class Config:
        orm_mode = True

class LocationBase(BaseModel):
    country_code: str
    city: str

class LocationCreate(LocationBase):
    pass

class Location(LocationBase):
    id: int

    class Config:
        orm_mode = True

class MeetupBase(BaseModel):
    date: str
    time: str
    description: Optional[str] = None
    location: Location

class MeetupCreate(MeetupBase):
    owner_username: str
    send_invitations_user_ids: List[int]

class Meetup(MeetupBase):
    id: int
    users: List[User]

    class Config:
        orm_mode = True

class WeatherReportBase(BaseModel):
    time: str
    temperature: float
    temperature_max: float
    temperature_min: float

class WeatherReportCreate(WeatherReportBase):
    date: str
    country_code: str
    city: str

class WeatherReport(WeatherReportCreate):
    id: int
