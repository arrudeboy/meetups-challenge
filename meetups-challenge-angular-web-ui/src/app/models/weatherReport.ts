export class WeatherReport {

  id?: number
  date: string
  time: string
  temperature: number
  temperatureMax: number
  temperatureMin: number
  countryCode: string
  city: string

  constructor(date: string, time: string, temperature: number, temperatureMax: number, temperatureMin: number, countryCode: string, city: string, id?: number) {
    this.id = id;
    this.date = date;
    this.time = time;
    this.temperature = temperature;
    this.temperatureMax = temperatureMax;
    this.temperatureMin = temperatureMin;
    this.countryCode = countryCode;
    this.city = city;
  }

}
