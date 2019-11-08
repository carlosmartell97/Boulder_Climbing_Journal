//
//  TutorialViewController.swift
//  Boulder Climbing Journal
//
//  Created by gdaalumno on 11/6/19.
//  Copyright © 2019 gdaalumno. All rights reserved.
//

import UIKit


class TutorialViewController: UIViewController{
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    func exit() {
        print("exiting tutorial...")
        
        self.dismiss(animated: true, completion: nil)
        
        //self.present(ViewController(), animated: true, completion: nil)
        
        //let storyBoard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        //let viewController = storyBoard.instantiateViewController(withIdentifier: "viewController") as! ViewController
        //self.present(viewController, animated: false, completion: nil)
        
        //let viewController = ViewController()
        //self.navigationController?.pushViewController(viewController, animated: true)
    }
    @IBAction func TutorialClicked1(_ sender: Any) { self.exit() }
    @IBAction func TutorialClicked2(_ sender: Any) { self.exit() }
    @IBAction func TutorialClicked3(_ sender: Any) { self.exit() }
    
}
