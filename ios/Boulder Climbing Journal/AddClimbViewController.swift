//
//  AddClimbViewController.swift
//  Boulder Climbing Journal
//
//  Created by Leonardo Valle Aviña on 11/28/19.
//  Copyright © 2019 gdaalumno. All rights reserved.
//

import UIKit

class AddClimbViewController: UIViewController {
    
    @IBOutlet weak var vbButton: UIButton!
    @IBOutlet weak var v0Button: UIButton!
    @IBOutlet weak var v1Button: UIButton!
    @IBOutlet weak var v2Button: UIButton!
    @IBOutlet weak var v3Button: UIButton!
    @IBOutlet weak var v4Button: UIButton!
    @IBOutlet weak var v5Button: UIButton!
    @IBOutlet weak var v6Button: UIButton!
    @IBOutlet weak var v7Button: UIButton!
    @IBOutlet weak var v8Button: UIButton!
    @IBOutlet weak var v9Button: UIButton!
    @IBOutlet weak var v10Button: UIButton!
    @IBOutlet weak var onsightButton: UIButton!
    @IBOutlet weak var flashButton: UIButton!
    @IBOutlet weak var attemptsButton: UIButton!
    @IBOutlet weak var repeatButton: UIButton!
    @IBOutlet weak var onsightLabel: UILabel!
    @IBOutlet weak var flashLabel: UILabel!
    @IBOutlet weak var attemptsLabel: UILabel!
    @IBOutlet weak var repeatLabel: UILabel!
    
    var previousButton: UIButton!
    var previousImage: UIImage!
    var previousDescButton: UIButton!
    var previousDescImage: UIImage!
    var previousDescLabel: UILabel!
    
    var selectedGrade: String!
    var selectedDesc: String!
    var questionMessageOk: String = "Got it"
    var missingMessageTitle: String = "MISSING INFO"
    var missingMessageOk: String = "Okay"
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        onsightLabel.textColor = UIColor(red:32.0/255, green: 32.0/255, blue: 32.0/255, alpha: 0.6)
        flashLabel.textColor = UIColor(red:32.0/255, green: 32.0/255, blue: 32.0/255, alpha: 0.6)
        attemptsLabel.textColor = UIColor(red:32.0/255, green: 32.0/255, blue: 32.0/255, alpha: 0.6)
        repeatLabel.textColor = UIColor(red:32.0/255, green: 32.0/255, blue: 32.0/255, alpha: 0.6)
    }
    
    @IBAction func vbButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "VB_tp.png", image_selected: "VB.png", sender: sender)
        selectedGrade = "VB"
    }
    @IBAction func v0ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V0_tp.png", image_selected: "V0.png", sender: sender)
        selectedGrade = "V0"
    }
    @IBAction func v1ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V1_tp.png", image_selected: "V1.png", sender: sender)
        selectedGrade = "V1"
    }
    @IBAction func v2ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V2_tp.png", image_selected: "V2.png", sender: sender)
        selectedGrade = "V2"
    }
    @IBAction func v3ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V3_tp.png", image_selected: "V3.png", sender: sender)
        selectedGrade = "V3"
    }
    @IBAction func v4ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V4_tp.png", image_selected: "V4.png", sender: sender)
        selectedGrade = "V4"
    }
    @IBAction func v5ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V5_tp.png", image_selected: "V5.png", sender: sender)
        selectedGrade = "V5"
    }
    @IBAction func v6ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V6_tp.png", image_selected: "V6.png", sender: sender)
        selectedGrade = "V6"
    }
    @IBAction func v7ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V7_tp.png", image_selected: "V7.png", sender: sender)
        selectedGrade = "V7"
    }
    @IBAction func v8ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V8_tp.png", image_selected: "V8.png", sender: sender)
        selectedGrade = "V8"
    }
    @IBAction func v9ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V9_tp.png", image_selected: "V9.png", sender: sender)
        selectedGrade = "V9"
    }
    @IBAction func v10ButtonClicked(_ sender: UIButton) {
        selectGrade(image_tp: "V10_tp.png", image_selected: "V10.png", sender: sender)
        selectedGrade = "V10"
    }
    
    @IBAction func onsightButtonClicked(_ sender: UIButton) {
        selectDesc(image_tp: "onsight_tp.png", image_selected: "onsight.png", sender: sender)
        onsightLabel.textColor = UIColor(red:32.0/255, green: 32.0/255, blue: 32.0/255, alpha: 1.0)
        selectedDesc = "onsight"
        previousDescLabel = onsightLabel
    }
    @IBAction func flashButtonClicked(_ sender: UIButton) {
        selectDesc(image_tp: "flash_tp.png", image_selected: "flash.png", sender: sender)
        flashLabel.textColor = UIColor(red:32.0/255, green: 32.0/255, blue: 32.0/255, alpha: 1.0)
        selectedDesc = "flash"
        previousDescLabel = flashLabel
    }
    @IBAction func attemptsButtonClicked(_ sender: UIButton) {
        selectDesc(image_tp: "attempts_tp.png", image_selected: "attempts.png", sender: sender)
        attemptsLabel.textColor = UIColor(red:32.0/255, green: 32.0/255, blue: 32.0/255, alpha: 1.0)
        selectedDesc = "attempts"
        previousDescLabel = attemptsLabel
    }
    @IBAction func repeatButtonClicked(_ sender: UIButton) {
        selectDesc(image_tp: "repeat_tp.png", image_selected: "repeat.png", sender: sender)
        repeatLabel.textColor = UIColor(red:32.0/255, green: 32.0/255, blue: 32.0/255, alpha: 1.0)
        selectedDesc = "repeat"
        previousDescLabel = repeatLabel
    }
    
    @IBAction func onsightQuestionButtonClicked(_ sender: UIButton) {
        showPopupMessage(title: "ONSIGHT ASCENTS", explanation: "onsight ascents are those you complete on your first attempt by yourself, without any prior tips, information, nor seeing someone else complete it before you.", okMessage: questionMessageOk)
    }
    @IBAction func flashQuestionButtonClicked(_ sender: UIButton) {
        showPopupMessage(title: "FLASH ASCENTS", explanation: "flash ascents are those you complete on your first attempt, with some information beforehand (tips, seeing someone else complete it before you, etc).", okMessage: questionMessageOk)
    }
    @IBAction func attemptsQuestionButtonClicked(_ sender: UIButton) {
        showPopupMessage(title: "ATTEMPTS ASCENTS", explanation: "these are ascents that took you more than one attempt to complete.", okMessage: questionMessageOk)
    }
    @IBAction func repeatQuestionButtonClicked(_ sender: UIButton) {
        showPopupMessage(title: "REPEAT ATTEMPTS", explanation: "these are ascents you had already completed before some other day.", okMessage: questionMessageOk)
    }
    
    func showPopupMessage(title: String, explanation: String, okMessage: String){
        let alertController = UIAlertController(title: title, message: explanation, preferredStyle: .alert)
        let okAction = UIAlertAction(title: okMessage, style: .cancel, handler: nil)
        alertController.addAction(okAction)
        self.present(alertController, animated: true)
    }
        
    @IBAction func addClimbButtonClicked(_ sender: RoundButton) {
        if(selectedGrade==nil && selectedDesc==nil){
            showPopupMessage(title: missingMessageTitle, explanation: "Select a climb grade and description first.", okMessage: missingMessageOk)
        }
        else if(selectedGrade == nil){
            showPopupMessage(title: missingMessageTitle, explanation: "Select a climb grade first.", okMessage: missingMessageOk)
        }
        else if(selectedDesc == nil){
            showPopupMessage(title: missingMessageTitle, explanation: "Select a climb description first.", okMessage: missingMessageOk)
        }
        else {
            self.performSegue(withIdentifier: "unwindToAddSessionViewController", sender: self)
        }
    }
    
    func selectGrade(image_tp: String, image_selected: String, sender: UIButton){
        if(previousButton != nil){
            previousButton.setBackgroundImage(previousImage, for: .normal)
        }
        sender.setBackgroundImage(UIImage(named: image_selected), for: .normal)
        previousButton = sender
        previousImage = UIImage(named: image_tp)
    }
    func selectDesc(image_tp: String, image_selected: String, sender: UIButton){
        if(previousDescButton != nil){
            previousDescButton.setBackgroundImage(previousDescImage, for: .normal)
            previousDescLabel.textColor = UIColor(red:32.0/255, green: 32.0/255, blue: 32.0/255, alpha: 0.6)
        }
        sender.setBackgroundImage(UIImage(named: image_selected), for: .normal)
        previousDescButton = sender
        previousDescImage = UIImage(named: image_tp)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let destinationVC = segue.destination as! AddSessionViewController
        destinationVC.addClimb(grade: selectedGrade, description: selectedDesc)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
