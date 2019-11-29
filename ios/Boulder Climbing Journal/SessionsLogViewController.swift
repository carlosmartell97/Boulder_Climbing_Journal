//
//  SessionsLogViewController.swift
//  Boulder Climbing Journal
//
//  Created by Leonardo Valle Aviña on 11/29/19.
//  Copyright © 2019 gdaalumno. All rights reserved.
//

import UIKit
import CoreData

class SessionsLogViewController: UIViewController {
    
    @IBOutlet weak var logTableView: UITableView!
    var loggedSessions: [LoggedSession] = []
    
    class LoggedSession {
        var date: String!
        var gym: String!
        var climbs: Int!
        var points: Int!
        init(date: String, gym: String, climbs: Int, points: Int){
            self.date = date
            self.gym = gym
            self.climbs = climbs
            self.points = points
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

        loggedSessions = []
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return
        }
        let managedContext = appDelegate.persistentContainer.viewContext
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Session")
        do {
            let listSessions: [NSManagedObject] = try managedContext.fetch(fetchRequest)
            for elemento in listSessions {
                let date = elemento.value(forKey: "date") as! String
                let gym = elemento.value(forKey: "gym") as! String
                let climbs = elemento.value(forKey: "climbs") as! Int
                let points = elemento.value(forKey: "points") as! Int
                loggedSessions.append(LoggedSession(date:date, gym: gym, climbs: climbs, points: points))
            }
            //print(tuplesEventos)
        } catch let error as NSError {
            print("Couldn't be saved. \(error): \(error.userInfo)")
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        let nibName = UINib(nibName: "LoggedSessionTableViewCell", bundle: nil)
        logTableView.register(nibName, forCellReuseIdentifier: "LoggedSessionCell")
    }
}

extension SessionsLogViewController: UITableViewDataSource, UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return loggedSessions.count
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = logTableView.dequeueReusableCell(withIdentifier: "LoggedSessionCell", for: indexPath) as! LoggedSessionTableViewCell
        
        cell.dateLabel.text
            = loggedSessions[indexPath.row].date
        cell.locationLabel.text
        = loggedSessions[indexPath.row].gym
        cell.climbsLabel.text
            = String(format: "%d climbs", loggedSessions[indexPath.row].climbs)
        cell.pointsLabel.text
            = String(format: "%d points", loggedSessions[indexPath.row].points)
        return cell
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 120.0
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
}
