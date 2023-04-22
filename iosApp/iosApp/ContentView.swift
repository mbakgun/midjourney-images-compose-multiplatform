import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        let viewModel = MjImagesViewModel.init(fetchUseCase: MjImagesFetchUseCase.init())
        return Main_iosKt.MainViewController(viewModel: viewModel)
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
