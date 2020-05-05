/*
* Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
*/

import UIKit
import MultiPlatformLibrary

class TestViewController: UIViewController {
    
    let testing = Testing()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        testing.socket.connect()
    }
    
    @IBAction
    func onSendEventPressed() {
        testing.login()
    }
}
