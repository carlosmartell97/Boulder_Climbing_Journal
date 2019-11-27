//
//  AddSessionViewController.swift
//  Boulder Climbing Journal
//
//  Created by gdaalumno on 11/13/19.
//  Copyright © 2019 gdaalumno. All rights reserved.
//

import UIKit

class AddSessionViewController: UIViewController {
    
    @IBOutlet weak var dateButton: RoundButton!
    @IBOutlet weak var locationButton: RoundButton!
    
    var sessionDate: String = "When?"
    var sessionLocation: String = "Where?"

    override func viewDidLoad() {
        super.viewDidLoad(); print("viewDidLoad() AddSessionViewController")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        dateButton.setTitle(dateToday(), for: .normal)
        
        print("session date: \(sessionDate)")
        print("session location: \(sessionLocation)")
        locationButton.setTitle(sessionLocation, for: .normal)
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
}
