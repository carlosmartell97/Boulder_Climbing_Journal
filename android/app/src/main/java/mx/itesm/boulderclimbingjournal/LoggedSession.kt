package mx.itesm.boulderclimbingjournal

class LoggedSession {
    var id: Int = 0
    var date: String? = null
    var location: String? = null
    var notes: String? = null
    var points: Int = 0
    var climbs: Int = 0
    constructor(date: String, location: String, notes: String, points: Int, climbs: Int) {
        this.date = date
        this.location = location
        this.notes = notes
        this.points = points
        this.climbs = climbs
    }
    constructor(id:Int, date: String, location: String, notes: String, points: Int, climbs: Int) {
        this.id = id
        this.date = date
        this.location = location
        this.notes = notes
        this.points = points
        this.climbs = climbs
    }
}