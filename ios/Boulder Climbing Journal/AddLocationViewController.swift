//
//  AddLocationViewController.swift
//  Boulder Climbing Journal
//
//  Created by Leonardo Valle Aviña on 11/21/19.
//  Copyright © 2019 gdaalumno. All rights reserved.
//

import UIKit
import CoreData

class AddLocationViewController: UIViewController {
    
    @IBOutlet weak var noGymsLoggedView: UIView!
    @IBOutlet weak var gymListView: UIView!
    @IBOutlet weak var gymNamesTableView: UITableView!
    var gymNameTextfield: UITextField?
    var gymNamesList: [String] = []
    var selectedGym: String = "?"
    
    @IBAction func addFirstGymButtonClicked(_ sender: Any) {
        print("add first gym!")
        showAlertNewGym()
    }
    @IBAction func addNewGymButtonClicked(_ sender: Any) {
        print("add NEW gym!")
        showAlertNewGym()
    }
    
    var isFirstGymAdded: Bool = false
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return
        }
        let managedContext = appDelegate.persistentContainer.viewContext
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Gym")
        do {
            let listGyms: [NSManagedObject] = try managedContext.fetch(fetchRequest)
            let gymsSavedCount: Int = listGyms.count
            print("gyms saved: \(gymsSavedCount)")
            if(gymsSavedCount == 0){
                isFirstGymAdded = true
                gymListView.removeFromSuperview()
                self.view.addSubview(noGymsLoggedView)
            }
            else {
                for gym in listGyms {
                    let name = gym.value(forKey: "name") as! String
                    gymNamesList.append(name)
                }
                print(gymNamesList)
            }
        } catch let error as NSError {
            print("Couldn't retrieve gyms. \(error): \(error.userInfo)")
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad(); print("viewDidLoad() AddLocationViewController")
        //self.gymNamesTableView.dataSource = self
        //self.gymNamesTableView.delegate = self
    }
    
    func showAlertNewGym() {
        let alertController = UIAlertController(title: "Add gym name", message: nil, preferredStyle: .alert)
        alertController.addTextField(configurationHandler: gymNameTextfield)
        let addAction = UIAlertAction(title: "Add", style: .default, handler: self.addHandler)
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)
        alertController.addAction(cancelAction)
        alertController.addAction(addAction)
        self.present(alertController, animated: true)
    }
    
    func gymNameTextfield(textField: UITextField){
        gymNameTextfield = textField
        gymNameTextfield?.placeholder = "name"
    }

    func addHandler(alert: UIAlertAction){
        let gymName: String = (gymNameTextfield?.text)!
        print("new gym name: \( gymName )")
        selectedGym = gymName
        
        gymNamesList.append(gymName)
        saveInCoreData(gymName: gymName)
        
        if(isFirstGymAdded){
            self.performSegue(withIdentifier: "unwindToAddSessionViewController", sender: self)
        }
    }
    
    func saveInCoreData(gymName: String) {
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
            return
        }
        let managedContext = appDelegate.persistentContainer.viewContext
        let entity = NSEntityDescription.entity(forEntityName: "Gym", in: managedContext)!
        let managedObject = NSManagedObject(entity: entity, insertInto: managedContext)
        managedObject.setValue(gymName, forKey: "name")
        do {
            try managedContext.save()
            if(!isFirstGymAdded){
                gymNamesTableView.reloadData()
            }
        } catch let error as NSError {
            print("Couldn't be saved. \(error): \(error.userInfo)")
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let destinationVC = segue.destination as! AddSessionViewController
        destinationVC.sessionLocation = selectedGym
    }
}

extension AddLocationViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return gymNamesList.count
    }
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = gymNamesTableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        cell.textLabel?.text = gymNamesList[indexPath.row]
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        print("selected row #\(indexPath.row): \(gymNamesList[indexPath.row])")
        selectedGym = gymNamesList[indexPath.row]
        //detailsEventClicked = tuplesEventos[indexPath.section].1[indexPath.row].eventoAplicacion
        //performSegue(withIdentifier: "CellWasClicked", sender: self)
        gymNameTextfield?.text = gymNamesList[indexPath.row]
        self.performSegue(withIdentifier: "unwindToAddSessionViewController", sender: self)
    }
}
