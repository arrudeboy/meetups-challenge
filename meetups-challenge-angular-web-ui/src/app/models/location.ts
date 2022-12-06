export class Location {

  id? : number
  countryCode: string
  city: string

  constructor(countryCode: string, city: string, id? : number) {
    this.id = id;
    this.countryCode = countryCode
    this.city = city
  }

}
