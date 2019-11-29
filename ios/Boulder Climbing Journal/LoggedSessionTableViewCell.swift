//
//  LoggedSessionTableViewCell.swift
//  Boulder Climbing Journal
//
//  Created by Leonardo Valle Aviña on 11/29/19.
//  Copyright © 2019 gdaalumno. All rights reserved.
//

import UIKit

class LoggedSessionTableViewCell: UITableViewCell {

    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var locationLabel: UILabel!
    @IBOutlet weak var pointsLabel: UILabel!
    @IBOutlet weak var climbsLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
