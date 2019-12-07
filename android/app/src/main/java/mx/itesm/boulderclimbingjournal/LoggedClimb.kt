package mx.itesm.boulderclimbingjournal

class LoggedClimb {
    var id: Int = 0
    var grade: String? = null
    var description: String? = null
    var notes: String? = null
    constructor(grade: String, description: String, notes: String) {
        this.grade = grade
        this.description = description
        this.notes = notes
    }
    constructor(id:Int, grade: String, description: String, notes: String) {
        this.id = id
        this.grade = grade
        this.description = description
        this.notes = notes
    }
}