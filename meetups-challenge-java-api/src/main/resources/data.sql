INSERT INTO users(username,password,email) VALUES('arturo.chari','0123456789','arturofelixchari@gmail.com');
INSERT INTO users(username,password,email) VALUES('felix.chari','0123456789','arturo.chari@ibm.com');

INSERT INTO locations(country_code,city) VALUES ('AR', 'Buenos Aires');
INSERT INTO locations(country_code,city) VALUES ('UY', 'Montevideo');

INSERT INTO meetups(meetup_date,meetup_time,description,location_id) VALUES ('2022-01-15','16:00:00','Test meetup',1);
--INSERT INTO weather_reports(city,country_code,date,time,temperature,temperature_max,temperature_min) VALUES ('Buenos Aires','AR','2022-01-15','06:00:00',24.0,40.7,18.0);