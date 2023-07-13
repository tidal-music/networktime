import samples_multiplatform_kotlin_shared
import SwiftUI

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        VStack {
            ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has its own keyboard handler
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
