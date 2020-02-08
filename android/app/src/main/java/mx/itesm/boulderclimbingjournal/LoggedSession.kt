package mx.itesm.boulderclimbingjournal

class LoggedSession {
    var id: Int = 0
    var date: String? = null
    var location: String? = null
    var notes: String? = null
    var points: Int = 0
    var climbs: Int = 0
    var onsightClimbs: Int = 0
    var flashClimbs: Int = 0
    var attemptsClimbs: Int = 0
    var repeatClimbs: Int = 0
    constructor(date: String, location: String, notes: String, points: Int, climbs: Int,
                onsightClimbs: Int, flashClimbs: Int, attemptsClimbs: Int, repeatClimbs: Int) {
        this.date = date
        this.location = location
        this.notes = notes
        this.points = points
        this.climbs = climbs
        this.onsightClimbs = onsightClimbs
        this.flashClimbs = flashClimbs
        this.attemptsClimbs = attemptsClimbs
        this.repeatClimbs = repeatClimbs
    }
    constructor(id:Int, date: String, location: String, notes: String, points: Int, climbs: Int,
                onsightClimbs: Int, flashClimbs: Int, attemptsClimbs: Int, repeatClimbs: Int) {
        this.id = id
        this.date = date
        this.location = location
        this.notes = notes
        this.points = points
        this.climbs = climbs
        this.onsightClimbs = onsightClimbs
        this.flashClimbs = flashClimbs
        this.attemptsClimbs = attemptsClimbs
        this.repeatClimbs = repeatClimbs
    }
}