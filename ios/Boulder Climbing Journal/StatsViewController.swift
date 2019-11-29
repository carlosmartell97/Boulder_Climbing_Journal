//
//  StatsViewController.swift
//  Boulder Climbing Journal
//
//  Created by Leonardo Valle Aviña on 11/29/19.
//  Copyright © 2019 gdaalumno. All rights reserved.
//

import UIKit
import SwiftCharts
import CoreData

class StatsViewController: UIViewController {
    
    var chartView1: BarsChart!
    var chartView2: BarsChart!
    var loggedSessions: [LoggedSession] = []
    var maxGlobalGradeFrequency: Double = 0.0
    var maxGlobalDescFrequency: Double = 0.0
    var globalGradesCount: [(grade:String, count: Double)] = [
        ("VB", 0.0),
        ("V0", 0.0),
        ("V1", 0.0),
        ("V2", 0.0),
        ("V3", 0.0),
        ("V4", 0.0),
        ("V5", 0.0),
        ("V6", 0.0),
        ("V7", 0.0),
        ("V8", 0.0),
        ("V9", 0.0),
        ("V10", 0.0),
    ]
    var globalDescsCount: [(description: String, count: Double)] = [
        ("onsight", 0.0),
        ("flash", 0.0),
        ("attempts", 0.0),
        ("repeat", 0.0)
    ]
    
    class LoggedSession {
        var date: String!
        var gym: String!
        var totalClimbs: Int!
        var points: Int!
        var gradesCount: [(grade: String, count: Int)] = [
            ("VB", 0),
            ("V0", 0),
            ("V1", 0),
            ("V2", 0),
            ("V3", 0),
            ("V4", 0),
            ("V5", 0),
            ("V6", 0),
            ("V7", 0),
            ("V8", 0),
            ("V9", 0),
            ("V10", 0),
        ]
        var descsCount: [(description: String, count: Int)] = [
            ("onsight", 0),
            ("flash", 0),
            ("attempts", 0),
            ("repeat", 0)
        ]
        init(date: String, gym: String, totalClimbs: Int, points: Int){
            self.date = date
            self.gym = gym
            self.totalClimbs = totalClimbs
            self.points = points
        }
        func addClimb(grade: String, description : String){
            for (i,_) in gradesCount.enumerated() {
                if(gradesCount[i].grade==grade){ gradesCount[i].count+=1 }
            }
            for (i,_) in descsCount.enumerated() {
                if(descsCount[i].description==description){ descsCount[i].count+=1 }
            }
        }
    }
    
    func updateGlobalGradesCount(grade: String){
        for (i,_) in globalGradesCount.enumerated() {
            if(globalGradesCount[i].grade==grade){
                globalGradesCount[i].count+=1
                if(globalGradesCount[i].count > maxGlobalGradeFrequency){
                    maxGlobalGradeFrequency = globalGradesCount[i].count
                }
            }
        }
    }
    func updateGlobalDescsCount(description: String){
        for (i,_) in globalDescsCount.enumerated() {
            if(globalDescsCount[i].description==description){
                globalDescsCount[i].count+=1
                if(globalDescsCount[i].count > maxGlobalDescFrequency){
                    maxGlobalDescFrequency = globalDescsCount[i].count
                }
            }
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        loadLoggedSessions()
        loadLoggedClimbs()
        loadCharts()
    }
    
    func loadLoggedSessions(){
        print("loadLoggedSessions()")
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
                let totalClimbs = elemento.value(forKey: "climbs") as! Int
                let points = elemento.value(forKey: "points") as! Int
                loggedSessions.append(LoggedSession(date:date, gym: gym, totalClimbs: totalClimbs, points: points))
            }
            print("logged sessions: \(loggedSessions)")
        } catch let error as NSError {
            print("Couldn't retrieve sessions. \(error): \(error.userInfo)")
        }
    }
    
    func loadLoggedClimbs(){
        print("loadLoggedClimbs()")
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return
        }
        let managedContext = appDelegate.persistentContainer.viewContext
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Climb")
        do {
            let listSessions: [NSManagedObject] = try managedContext.fetch(fetchRequest)
            for elemento in listSessions {
                let date = elemento.value(forKey: "sessionDate") as! String
                let grade = elemento.value(forKey: "grade") as! String
                let description = elemento.value(forKey: "ascentDescription") as! String
                for loggedSession in loggedSessions {
                    if(loggedSession.date == date){
                        loggedSession.addClimb(
                            grade: grade, description: description
                        )
                        updateGlobalGradesCount(grade: grade)
                        updateGlobalDescsCount(description: description)
                    }
                }
            }
            print("global grades count: \(globalGradesCount)")
            print("global descs count: \(globalDescsCount)")
        } catch let error as NSError {
            print("Couldn't retrieve climbs. \(error): \(error.userInfo)")
        }
    }
    
    func loadCharts(){
        var strideSize1: Double!
        if(Double(Int(maxGlobalDescFrequency/10)) == 0){
            strideSize1 = 1.0
        } else {
            strideSize1 = Double(Int(maxGlobalDescFrequency/10))
        }
        let chart1 = BarsChart(
            frame:
                CGRect(x: 20, y: 130, width: self.view.frame.width-80, height: 350),
            chartConfig:
                BarsChartConfig(
                    valsAxisConfig: ChartAxisConfig(from: 0, to: maxGlobalGradeFrequency+strideSize1*2, by: strideSize1)
                ),
            xTitle: "Grade",
            yTitle: "Climbs",
            bars: globalGradesCount,
            color: UIColor(red: 0.0/255, green: 150.0/255, blue: 136.0/255, alpha: 1.0),
            barWidth: 15
        )
        self.view.addSubview(chart1.view)
        self.chartView1 = chart1
        
        var strideSize2: Double!
        if(Double(Int(maxGlobalDescFrequency/10)) == 0){
            strideSize2 = 1.0
        } else {
            strideSize2 = Double(Int(maxGlobalDescFrequency/10))
        }
        let chart2 = BarsChart(
            frame:
                CGRect(x: 20, y: 500, width: self.view.frame.width-80, height: 350),
            chartConfig:
                BarsChartConfig(
                    valsAxisConfig: ChartAxisConfig(from: 0, to: maxGlobalDescFrequency+strideSize2*2, by: strideSize2)
                ),
            xTitle: "Description",
            yTitle: "Climbs",
            bars: globalDescsCount,
            color: UIColor(red: 255.0/255, green: 193.0/255, blue: 7.0/255, alpha: 1.0),
            barWidth: 15
        )
        self.view.addSubview(chart2.view)
        self.chartView2 = chart2
    }
}
