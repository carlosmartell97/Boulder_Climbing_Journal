//
//  ViewController.swift
//  Boulder Climbing Journal
//
//  Created by gdaalumno on 11/6/19.
//  Copyright © 2019 gdaalumno. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad(); print("viewDidLoad() ViewController")
        
        if !hasSeenTutorial() {
            sendToTutorial()
        }
    }

    func hasSeenTutorial() -> Bool{
        return UserDefaults.standard.bool(forKey: "hasSeenTutorialBoulderClimbingJournal")
    }
    func sendToTutorial(){
        print("sending to tutorial...")
        //self.present(PageViewController(), animated: true, completion: nil)
        
        let storyBoard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        let pageViewController = storyBoard.instantiateViewController(withIdentifier: "pageViewController") as! PageViewController
        self.present(pageViewController, animated: true, completion: nil)

        //let pageViewController = PageViewController()
        // self.navigationController?.pushViewController(pageViewController, animated: true)
    }
}

