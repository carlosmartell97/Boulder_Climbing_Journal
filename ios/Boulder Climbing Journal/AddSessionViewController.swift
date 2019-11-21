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

    override func viewDidLoad() {
        super.viewDidLoad(); print("viewDidLoad() AddSessionViewController")
        dateButton.setTitle(dateToday(), for: .normal)
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
}
