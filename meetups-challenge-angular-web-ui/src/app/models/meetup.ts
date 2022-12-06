export class Meetup {

  id? : number
  date : string
  time : string
  description : string
  location: Location

  constructor(date: string, time: string, description: string, location: Location, id?: number) {
    this.id = id;
    this.date = date;
    this.time = time;
    this.description = description;
    this.location = location;
  }

}
