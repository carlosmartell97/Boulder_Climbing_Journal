//
//  RoundButton.swift
//  Boulder Climbing Journal
//
//  Created by gdaalumno on 11/6/19.
//  Copyright © 2019 gdaalumno. All rights reserved.
//

import UIKit

@IBDesignable
class RoundButton : UIButton {
    @IBInspectable var cornerRadius: CGFloat = 0{
        didSet{
            self.layer.cornerRadius = cornerRadius
        }
    }
}
