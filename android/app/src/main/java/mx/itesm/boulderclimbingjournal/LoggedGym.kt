package mx.itesm.boulderclimbingjournal

class LoggedGym {
    var id: Int = 0
    var name: String? = null
    constructor(name: String) {
        this.name = name
    }
    constructor(id:Int, name: String) {
        this.id = id
        this.name = name
    }
}