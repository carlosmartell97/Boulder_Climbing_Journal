package mx.itesm.boulderclimbingjournal

import java.io.Serializable

class LoggedClimb: Serializable {
    var id: Int = 0
    var grade: String? = null
    var description: String? = null
    var notes: String? = null
    var points: Int = 0
    constructor(grade: String, description: String, notes: String, points: Int) {
        this.grade = grade
        this.description = description
        this.notes = notes
        this.points = points
    }
    constructor(id:Int, grade: String, description: String, notes: String, points: Int) {
        this.id = id
        this.grade = grade
        this.description = description
        this.notes = notes
        this.points = points
    }
}