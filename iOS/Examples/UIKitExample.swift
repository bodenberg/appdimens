/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - UIKit Example
 *
 * Description:
 * Example showing how to use AppDimens with UIKit.
 */

import UIKit
import AppDimens

class UIKitExampleViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }
    
    private func setupUI() {
        view.backgroundColor = .systemBackground
        
        // Create a container view with dynamic width
        let containerView = UIView()
        containerView.backgroundColor = .systemBlue
        containerView.layer.cornerRadius = 16.fxpt // Fixed corner radius
        view.addSubview(containerView)
        
        // Create a title label with fixed font size
        let titleLabel = UILabel()
        titleLabel.text = "AppDimens UIKit Example"
        titleLabel.textAlignment = .center
        titleLabel.textColor = .white
        titleLabel.fxFontSize(20) // Fixed font size
        containerView.addSubview(titleLabel)
        
        // Create a button with fixed height and dynamic width
        let button = UIButton(type: .system)
        button.setTitle("Tap Me", for: .normal)
        button.backgroundColor = .white
        button.setTitleColor(.systemBlue, for: .normal)
        button.fxTitleFontSize(16) // Fixed font size
        button.fxCornerRadius(8) // Fixed corner radius
        containerView.addSubview(button)
        
        // Create a description label with fixed font size
        let descriptionLabel = UILabel()
        descriptionLabel.text = "This example demonstrates how to use AppDimens with UIKit for responsive layouts."
        descriptionLabel.numberOfLines = 0
        descriptionLabel.textAlignment = .center
        descriptionLabel.textColor = .white
        descriptionLabel.fxFontSize(14) // Fixed font size
        containerView.addSubview(descriptionLabel)
        
        // Setup constraints
        containerView.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        button.translatesAutoresizingMaskIntoConstraints = false
        descriptionLabel.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            // Container view - dynamic width, fixed height
            containerView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            containerView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            containerView.widthAnchor.constraint(equalToConstant: 300.dypt), // Dynamic width
            containerView.heightAnchor.constraint(equalToConstant: 200.fxpt), // Fixed height
            
            // Title label
            titleLabel.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 20.fxpt),
            titleLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 16.fxpt),
            titleLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -16.fxpt),
            
            // Button
            button.centerXAnchor.constraint(equalTo: containerView.centerXAnchor),
            button.centerYAnchor.constraint(equalTo: containerView.centerYAnchor),
            button.widthAnchor.constraint(equalToConstant: 120.dypt), // Dynamic width
            button.heightAnchor.constraint(equalToConstant: 44.fxpt), // Fixed height
            
            // Description label
            descriptionLabel.bottomAnchor.constraint(equalTo: containerView.bottomAnchor, constant: -20.fxpt),
            descriptionLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 16.fxpt),
            descriptionLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -16.fxpt)
        ])
        
        // Add button action
        button.addTarget(self, action: #selector(buttonTapped), for: .touchUpInside)
    }
    
    @objc private func buttonTapped() {
        let alert = UIAlertController(
            title: "Button Tapped!",
            message: "This button uses AppDimens for responsive sizing.",
            preferredStyle: .alert
        )
        
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        present(alert, animated: true)
    }
}
