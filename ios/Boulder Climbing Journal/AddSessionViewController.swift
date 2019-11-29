//
//  AddSessionViewController.swift
//  Boulder Climbing Journal
//
//  Created by gdaalumno on 11/13/19.
//  Copyright © 2019 gdaalumno. All rights reserved.
//

import UIKit
import CoreData

class AddSessionViewController: UIViewController {
    
    @IBOutlet weak var dateButton: RoundButton!
    @IBOutlet weak var locationButton: RoundButton!
    @IBOutlet weak var climbsTableView: UITableView!
    @IBOutlet weak var pointsLabel: UILabel!
    
    var sessionDate: String = "When?"
    var sessionLocation: String = "Where?"
    var sessionPoints: Int = 0
    var sessionClimbs: Int = 0
    var climbs: [(grade: String, climbs: [Climb])] = [
        (grade: "VB", []),
        (grade: "V0", []),
        (grade: "V1", []),
        (grade: "V2", []),
        (grade: "V3", []),
        (grade: "V4", []),
        (grade: "V5", []),
        (grade: "V6", []),
        (grade: "V7", []),
        (grade: "V8", []),
        (grade: "V9", []),
        (grade: "V10", []),
    ]
    var sections: [String] = []
    
    class Climb {
        var grade: String!
        var description: String!
        init(grade: String, description: String){
            self.grade = grade
            self.description = description
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad(); print("viewDidLoad() AddSessionViewController")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        sessionDate = dateToday()
        dateButton.setTitle(sessionDate, for: .normal)
        
        print("session date: \(sessionDate)")
        print("session location: \(sessionLocation)")
        print("climbs: \(climbs)")
        locationButton.setTitle(sessionLocation, for: .normal)
    }
    
    func updateSections(){
        sections = []
        for (grade, loggedClimbs) in climbs {
            if loggedClimbs.count != 0 {
                sections.append(grade)
            }
        }
    }
    
    func dateToday() -> String {
        let today = Date()
        return dateToText(date: today)
    }
    
    func dateToText(date: Date) -> String {
        let format = DateFormatter()
        format.dateFormat = "MMM d, YYYY"
        let formattedDate = format.string(from: date)
        
        return "\(formattedDate)"
    }
    
    @IBAction func unwindToAddSessionController(_ sender: UIStoryboardSegue) {}
    
    func addClimb(grade: String, description: String){
        sessionClimbs += 1

        var multiplier: Double!
        switch description {
        case "onsight":
            multiplier = 2.0
        case "flash":
            multiplier = 1.5
        case "attempts":
            multiplier = 1.0
        default:    // "repeat"
            multiplier = 0.8
        }

        switch grade {
        case "VB":
            climbs[0].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(50 * multiplier!)
        case "V0":
            climbs[1].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(80 * multiplier!)
        case "V1":
            climbs[2].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(120 * multiplier!)
        case "V2":
            climbs[3].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(200 * multiplier!)
        case "V3":
            climbs[4].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(300 * multiplier!)
        case "V4":
            climbs[5].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(420 * multiplier!)
        case "V5":
            climbs[6].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(570 * multiplier!)
        case "V6":
            climbs[7].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(750 * multiplier!)
        case "V7":
            climbs[8].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(950 * multiplier!)
        case "V8":
            climbs[9].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(1200 * multiplier!)
        case "V9":
            climbs[10].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(1500 * multiplier!)
        default:    // V10
            climbs[11].climbs.append(Climb(grade: grade, description: description));
            sessionPoints += Int(1800 * multiplier!)
        }
        
        pointsLabel.text = String(format: "%d points", sessionPoints)
        
        climbsTableView.reloadData()
    }
    
    @IBAction func finishSessionButtonClicked(_ sender: RoundButton) {
        if(sessionLocation == "Where?"){
            showPopupMessage(title: "MISSING INFO", explanation: "you must indicate the location of this session.", okMessage: "Okay")
            return
        }
        if(sessionClimbs == 0){
            showPopupMessage(title: "MISSING INFO", explanation: "you must add at least one climb to this session.", okMessage: "Okay")
            return
        }
        saveSessionInCoreDate()
        saveClimbsInCoreData()
        self.performSegue(withIdentifier: "unwindToMainMenuController", sender: self)
    }
    
    func showPopupMessage(title: String, explanation: String, okMessage: String){
        let alertController = UIAlertController(title: title, message: explanation, preferredStyle: .alert)
        let okAction = UIAlertAction(title: okMessage, style: .cancel, handler: nil)
        alertController.addAction(okAction)
        self.present(alertController, animated: true)
    }
    
    func saveSessionInCoreDate(){
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return
        }
        let managedContext = appDelegate.persistentContainer.viewContext
        let entity = NSEntityDescription.entity(forEntityName: "Session", in: managedContext)!
        let managedObject = NSManagedObject(entity: entity, insertInto: managedContext)
        managedObject.setValue(sessionClimbs, forKey: "climbs")
        managedObject.setValue(sessionDate, forKey: "date")
        managedObject.setValue(sessionLocation, forKey: "gym")
        managedObject.setValue(sessionPoints, forKey: "points")
        do {
            try managedContext.save()
        } catch let error as NSError {
            print("Session couldn't be saved. \(error): \(error.userInfo)")
        }
    }
    
    func saveClimbsInCoreData() {
        for(_, loggedClimbs) in climbs {
            if(loggedClimbs.count == 0){ continue }
            for i in 0...loggedClimbs.count-1 {
                guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
                    return
                }
                let managedContext = appDelegate.persistentContainer.viewContext
                let entity = NSEntityDescription.entity(forEntityName: "Climb", in: managedContext)!
                let managedObject = NSManagedObject(entity: entity, insertInto: managedContext)
                managedObject.setValue(loggedClimbs[i].grade, forKey: "grade")
                managedObject.setValue(sessionDate, forKey: "sessionDate")
                managedObject.setValue(loggedClimbs[i].description, forKey: "ascentDescription")
                do {
                    try managedContext.save()
                } catch let error as NSError {
                    print("Climb couldn't be saved. \(error): \(error.userInfo)")
                }
            }
        }
    }
}

extension AddSessionViewController: UITableViewDataSource, UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        updateSections()
        print("SECTIONS: \(sections)")
        return sections.count
    }
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return sections[section]
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = climbsTableView.dequeueReusableCell(withIdentifier: "LoggedClimbCell", for: indexPath)
        var gradeText: String = ""
        for i in 0...climbs.count-1 {
            if(sections[indexPath.section] == climbs[i].grade){
                if(climbs[i].climbs.count != 0){
                    gradeText += climbs[i].climbs[0].description
                }
                if(climbs[i].climbs.count > 1){
                    for loggedClimb in 1...climbs[i].climbs.count-1{
                        gradeText += ", "+climbs[i].climbs[loggedClimb].description
                    }
                }
            }
        }
        
        cell.textLabel?.text = gradeText
        return cell
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80.0
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
}
