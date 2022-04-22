import re
from sqlalchemy import Column, BigInteger, Numeric, String, Boolean, ForeignKey, Table
from sqlalchemy.orm import relationship, validates

from config.db import Base

meetup_users = Table("meetup_users", Base.metadata, Column("meetup_id", BigInteger, ForeignKey("meetups.id")), Column("user_id", BigInteger, ForeignKey("users.id")), Column("check_in", Boolean, default=False))

class User(Base):
    __tablename__ = "users"

    id = Column(BigInteger, primary_key=True, index=True)
    username = Column(String, unique=True, index=True, nullable=False)
    email = Column(String, nullable=False)
    password = Column(String, nullable=False)

    meetups = relationship("Meetup", secondary=meetup_users, back_populates="users")

    @validates('username') 
    def validate_username(self, key, username):
        if len(username) < 5 or len(username) > 20:
            raise AssertionError('Username must be between 5 and 20 characters') 
        
        return username 
        
    @validates('email')
    def validate_email(self, key, email):
        if not re.match("[^@]+@[^@]+\.[^@]+", email):
            raise AssertionError('Provided email is not an email address')
            
        return email

    @validates('password')
    def validate_password(self, key, password):
        if len(password) < 10 or len(password) > 50:
            raise AssertionError('Password must be between 10 and 50 characters') 
            
        return password


class Meetup(Base):
    __tablename__ = "meetups"

    id = Column(BigInteger, primary_key=True, index=True)
    meetup_date = Column(String, nullable=False)
    meetup_time = Column(String, nullable=False)
    description = Column(String, nullable=False, default="No description")
    location_id = Column(BigInteger, ForeignKey("locations.id"))

    users = relationship("User", secondary=meetup_users, back_populates="meetups")
    location = relationship("Location", back_populates="meetups")

    @validates('meetup_date')
    def validate_date(self, key, meetup_date):
        if not re.match("^\\d{4}-\\d{2}-\\d{2}$", meetup_date):
            raise AssertionError('Provided date is not a formatted date (yyyy-MM-dd)')
            
        return meetup_date

    @validates('meetup_time')
    def validate_time(self, key, meetup_time):
        if not re.match("^\\d{2}:\\d{2}:\\d{2}$", meetup_time):
            raise AssertionError('Provided time is not a formatted time (HH:mm:ss)')
            
        return meetup_time


class Location(Base):
    __tablename__ = "locations"

    id = Column(BigInteger, primary_key=True, index=True)
    country_code = Column(String, nullable=False)
    city = Column(String, nullable=False)

    meetups = relationship("Meetup", back_populates="location")

    @validates('country_code')
    def validate_country_code(self, key, country_code):
        if not re.match("[A-Z].*[A-Z]", country_code):
            raise AssertionError('Provided country_code is not a valid country_code')

        return country_code


class WeatherReport(Base):
    __tablename__ = "weather_reports"

    id = Column(BigInteger, primary_key=True, index=True)
    date = Column(String, nullable=False)
    time = Column(String, nullable=False)
    temperature = Column(Numeric(3,2), nullable=False)
    temperature_max = Column(Numeric(3,2), nullable=False)
    temperature_min = Column(Numeric(3,2), nullable=False)
    country_code = Column(String, nullable=False)
    city = Column(String, nullable=False)

    @validates('date')
    def validate_date(self, key, date):
        if not re.match("^\\d{4}-\\d{2}-\\d{2}$", date):
            raise AssertionError('Provided date is not a formatted date (yyyy-MM-dd)')
            
        return date

    @validates('time')
    def validate_time(self, key, time):
        if not re.match("^\\d{2}:\\d{2}:\\d{2}$", time):
            raise AssertionError('Provided time is not a formatted time (HH:mm:ss)')

    @validates('country_code')
    def validate_country_code(self, key, country_code):
        if not re.match("[A-Z].*[A-Z]", country_code):
            raise AssertionError('Provided country_code is not a valid country_code')

        return country_code
