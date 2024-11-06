import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        return Main_iosKt.MainViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    init() {
       KoinModuleKt.doInitKoin()
    }
    
    var body: some View {
        ComposeView().ignoresSafeArea(.keyboard)
    }
}
